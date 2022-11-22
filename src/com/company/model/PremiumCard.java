package com.company.model;

import com.company.utils.CardType;

public class PremiumCard extends Card {
    public PremiumCard(String cardNum, String pin) {
        super(cardNum, pin, CardType.PREMIUM);
    }

    @Override
    public Float withdrawal(float count) {
        if(count > balance) throw new RuntimeException("Недостаточно средств!");
        balance -= count;
        return calculatePercentage(count);
    }

    @Override
    public Float calculatePercentage(float count) {
        return (float)(count-(count*0.01));
    }
}
