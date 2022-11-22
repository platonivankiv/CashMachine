package com.company.model;

import com.company.utils.CardType;

public abstract class Card {

    private final String cardNumber;
    private final String pin;
    protected float balance;
    private final CardType cardType;
    private long startBlock;

    public Card(String cardNumber, String pin, CardType cardType) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.cardType = cardType;
        balance = 0;
        startBlock = 0;
    }

    public abstract Float withdrawal(float count);

    public abstract Float calculatePercentage(float count);


    public Float getBalance() {
        return balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void topUp(float count) {
        this.balance += count;
    }

    public Boolean isBlock() {
        return System.currentTimeMillis() - startBlock <= 1000 * 60 * 60 * 24;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setStartBlock(long startBlock) {
        this.startBlock = startBlock;
    }

    public void block() {
        startBlock = System.currentTimeMillis();
    }

    public Boolean checkPin(String pin) {
        return this.pin.equals(pin);
    }

    public Boolean canWithdrawal(float count) {
        return balance >= count;
    }

    @Override
    public String toString() {
        return cardNumber + ' ' + pin + ' ' + balance + ' ' + cardType + ' ' + startBlock;
    }
}





