package com.catan.application.api.controllers.v2.mapper;

import com.catan.application.api.controllers.v2.resource.CardEspResource;
import com.catan.domain.entity.Card;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CardEspMapper {
    public CardEspResource fromCard(Card card){

        return new CardEspResource(card.getId(),card.getNumber(), card.getDescription() );
    }
}
