package com.catan.infrastructure.persistence.mongo.mapper;

import com.catan.domain.entity.Card;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public class CardMapper {

    public Card fromDocument(Document document) {
        Card card = new Card();
        card.setId(document.get("_id", ObjectId.class).toString());
        card.setNumber(document.get("number", Integer.class).intValue());
        card.setDescription(document.get("description", String.class));
        return card;
    }
}
