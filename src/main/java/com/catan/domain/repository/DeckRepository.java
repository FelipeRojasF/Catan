package com.catan.domain.repository;

import com.catan.domain.entity.Deck;


public interface DeckRepository {

    Deck insertDeck(Deck deck);
    Deck getById(String id);
    Deck updateDeck(Deck deck);
}
