package com.catan.domain.repository;

import com.catan.domain.entity.Card;

import java.util.List;

public interface CardRepository {
    List<Card> getCards();
    Card insertCard(Card card);
    Card getCardById(String id);
    Card deleteCard(String id);
}
