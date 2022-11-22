package com.company.model;


import com.company.model.Card;

import java.util.List;

public class CardStorage {
    private List<Card> storage;

    public CardStorage(List<Card> storage){
        this.storage = storage;
    }

    public void addCard(Card card){
        storage.add(card);
    }

    public Boolean hasCard(String cardNumber){
        return storage
                .stream()
                .filter(card -> card.getCardNumber().equals(cardNumber))
                .count() != 0;
    }

    public List<Card> getStorage() {
        return storage;
    }

    public Card getCard(String cardNumber){
        Card c = storage
                .stream()
                .filter(card -> card.getCardNumber().equals(cardNumber))
                .findFirst()
                .orElse(null);

        if(c == null)
            throw new NullPointerException("Карта не найдена!");
        return c;
    }







}
