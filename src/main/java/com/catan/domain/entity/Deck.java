package com.catan.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private String id;
    private List<Card> availableCards;
    private List<Card> discardedCards;
    private int drawnCards;

    public Deck(){
        this(new ArrayList<>(), new ArrayList<>());
    }

    public Deck(List<Card> availableCards, List<Card> discardedCards){
        this.availableCards = availableCards;
        this.discardedCards = discardedCards;
        drawnCards = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Card> getAvailableCards() {
        return availableCards;
    }

    public void setAvailableCards(List<Card> availableCards) {
        this.availableCards = availableCards;
    }

    public List<Card> getDiscardedCards() {
        return discardedCards;
    }

    public void setDiscardedCards(List<Card> discardedCards) {
        this.discardedCards = discardedCards;
    }

    public int getDrawnCards() {
        return drawnCards;
    }

    public void setDrawnCards(int drawnCards) {
        this.drawnCards = drawnCards;
    }

    public void setNextDrawnCard(){
        drawnCards++;
    }

    public boolean hasId(){
        return id != null;
    }
}
