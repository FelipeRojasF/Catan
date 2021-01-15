package com.catan.infrastructure.persistence.mongo.repository;

import com.catan.domain.entity.Card;
import com.catan.domain.repository.CardRepository;
import com.catan.infrastructure.persistence.mongo.mapper.CardMapper;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class CardRepositoryImpl implements CardRepository {

    private MongoDatabase catanDatabase;
    private CardMapper cardMapper;

    @Autowired
    CardRepositoryImpl(MongoDatabase mongoDatabase, CardMapper cardMapper) {
        this.catanDatabase = mongoDatabase;
        this.cardMapper = cardMapper;
    }

    @Override
    public List<Card> getCards() throws RuntimeException{
        List<Card> cards = new ArrayList<>();

        MongoIterable documents = this.catanDatabase
                .getCollection("cards")
                .find(new Document());

        Consumer<Document> consumer = document -> cards.add(cardMapper.fromDocument(document));
        documents.forEach(consumer);

        return cards;
    }

    @Override
    public Card insertCard(Card card) {
        ObjectId id = new ObjectId();
        Document document = new Document("_id", id);
        document.append("number",card.getNumber());
        document.append("description",card.getDescription());

        catanDatabase.getCollection("cards").insertOne(document);

        Card created = new Card();
        created.setId(id.toString());
        created.setNumber(card.getNumber());
        created.setDescription(card.getDescription());


        return created;
    }

    @Override
    public Card getCardById(String id) {

        ObjectId documentId = new ObjectId(id);

        Document document = this.catanDatabase
                .getCollection("cards")
                .find(eq("_id",documentId)).first();

        return cardMapper.fromDocument(document);
    }

    @Override
    public Card deleteCard(String id) {

        ObjectId documentId = new ObjectId(id);

        Document document = this.catanDatabase
                .getCollection("cards")
                .find(eq("_id",documentId)).first();

        this.catanDatabase
                .getCollection("cards")
                .deleteOne(eq("_id",documentId));

        return cardMapper.fromDocument(document);
    }
}
