package com.catan.domain.service;

import com.catan.domain.entity.Card;
import com.catan.domain.entity.Deck;
import com.catan.domain.repository.CardRepository;
import com.catan.domain.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.shuffle;

@Service
public class DeckService {

    private static int DISCARDED_CARDS = 5;

    private CardRepository cardRepository;
    private DeckRepository deckRepository;

    @Autowired
    public DeckService(CardRepository cardRepository, DeckRepository deckRepository) {
        this.cardRepository = cardRepository;
        this.deckRepository = deckRepository;
    }

    public Deck createDeck(){
        List<Card> allCards = cardRepository.getCards();
        shuffle(allCards);

        List<Card> discardedCards = allCards.subList(0,DISCARDED_CARDS);
        List<Card> availableCards = allCards.subList(DISCARDED_CARDS, allCards.size());

        Deck deck = new Deck(availableCards,discardedCards);
        return deckRepository.insertDeck(deck);
    }

    public Card findCard(String deckId){
        Deck deck = deckRepository.getById(deckId);
        int actualCard = deck.getDrawnCards();
        List<Card> cardList = deck.getAvailableCards();
        String cardId = cardList.get(actualCard).getId();

        Card card = cardRepository.getCardById(cardId);

        deck.setNextDrawnCard();
        deckRepository.updateDeck(deck);

        return card;
    }

}
