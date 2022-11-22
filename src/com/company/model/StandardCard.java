package com.company.model;

import com.company.utils.CardType;

public class StandardCard extends Card {
    public StandardCard(String cardNum, String pin) {
        super(cardNum, pin, CardType.STANDARD);
    }

    @Override
    public Float withdrawal(float count) {
        if(count > balance) throw new RuntimeException("Недостаточно средств!");
        balance -= count;
        return calculatePercentage(count);
    }

    @Override
    public Float calculatePercentage(float count) {
        return (float)(count-(count*0.03));
    }
}
