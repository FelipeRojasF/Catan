package com.catan.infrastructure.persistence.mongo.mapper;

import com.catan.domain.entity.Card;
import com.catan.domain.entity.Deck;
import com.catan.domain.repository.CardRepository;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeckMapper {

    @Autowired
    CardRepository cardRepository;

    public Deck fromDocument(Document document) {

        List<String> availableCardsId = document.get("availableCards", List.class);
        List<String> discardedCardsId = document.get("discardedCards", List.class);

        List<Card> availableCards = new ArrayList<>();
        List<Card> discardedCards = new ArrayList<>();

        for (String id: availableCardsId) {
            availableCards.add(cardRepository.getCardById(id));
        }

        for (String id: discardedCardsId) {
            discardedCards.add(cardRepository.getCardById(id));
        }

        Deck deck = new Deck(availableCards, discardedCards);
        deck.setId(document.get("_id", ObjectId.class).toString());
        deck.setDrawnCards(document.get("drawnCards", Integer.class).intValue());

        return deck;
    }

    public Document toDocument(Deck deck){
        List<String> listAvailableCards =  deck.getAvailableCards().stream()
                .map(card -> card.getId())
                .collect(Collectors.toList());

        List<String> listDiscardedCards =  deck.getDiscardedCards().stream()
                .map(card -> card.getId())
                .collect(Collectors.toList());

        ObjectId id = deck.hasId() ? new ObjectId(deck.getId()) : new ObjectId();



        Document document = new Document("_id", id);
        document.append("availableCards",listAvailableCards);
        document.append("discardedCards",listDiscardedCards);
        document.append("drawnCards",deck.getDrawnCards());

        //******************************************************************************

        /*List<Document> docAvailableCards = new ArrayList<>();
        List<Document> docDiscardedCards = new ArrayList<>();

        deck.getAvailableCards().forEach(card -> {Document doc = new Document();
            doc.append("_id", card.getId());
            doc.append("number",card.getNumber());
            doc.append("description", card.getDescription());
            docAvailableCards.add(doc);});

        deck.getDiscardedCards().forEach(card -> {Document doc = new Document();
            doc.append("_id", card.getId());
            doc.append("number",card.getNumber());
            doc.append("description", card.getDescription());
            docDiscardedCards.add(doc);});

        ObjectId id = new ObjectId();
        Document document = new Document("_id", id);
        document.append("availableCards",docAvailableCards);
        document.append("discardedCards",docDiscardedCards);
        document.append("drawnCards",deck.getDrawnCards());*/

        return document;
    }
}
