package com.catan.infrastructure.persistence.mongo.repository;

import com.catan.domain.entity.Deck;
import com.catan.domain.repository.DeckRepository;
import com.catan.domain.service.DeckService;
import com.catan.infrastructure.persistence.mongo.mapper.DeckMapper;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

@Service
public class DeckRepositoryImpl implements DeckRepository {

    private MongoDatabase catanDB;
    private DeckMapper deckMapper;

    @Autowired
    public DeckRepositoryImpl(MongoDatabase mongoDatabase, DeckMapper deckMapper){
        this.catanDB = mongoDatabase;
        this.deckMapper = deckMapper;
    }

    @Override
    public Deck insertDeck(Deck deck) {

        Document document = deckMapper.toDocument(deck);

        catanDB.getCollection("deck").insertOne(document);

        Deck created = new Deck(deck.getAvailableCards(),deck.getDiscardedCards());
        created.setId(document.getObjectId("_id").toString());
        created.setDrawnCards(deck.getDrawnCards());

        return created;
    }

    @Override
    public Deck getById(String id) {
        ObjectId documentId = new ObjectId(id);

        Document document = this.catanDB
                .getCollection("deck")
                .find(eq("_id",documentId)).first();

        return deckMapper.fromDocument(document);
    }

    @Override
    public Deck updateDeck(Deck deck) {


        Bson documentQuery = eq("_id", new ObjectId(deck.getId()));
        Document toUpdate = new Document("$set", deckMapper.toDocument(deck));
        catanDB.getCollection("deck")
                .updateMany(documentQuery, toUpdate);

        return deck;
    }
}
