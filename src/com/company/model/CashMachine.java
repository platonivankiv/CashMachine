package com.company.model;

public class CashMachine {

    private float balance;
    private boolean hasCard;

    public CashMachine(float balance) {
        this.balance = balance;
        hasCard = false;
    }

    public Boolean hasCard() {
        return hasCard;
    }

    public void setHasCard(boolean hasCard) {
        this.hasCard = hasCard;
    }

    public Float getBalance() {
        return balance;
    }

    public void topUp(float count) {

        balance += count;
    }

    public void withdrawal(float count){
        if(count > balance) throw new RuntimeException("В банкомате недостаточно средств!");
        balance -= count;
    }

    public Boolean canWithdrawal(float count){
        return balance >= count;

    }
}


