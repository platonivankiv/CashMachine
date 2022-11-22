package com.company.DAO;

import com.company.model.BasicCard;
import com.company.model.Card;
import com.company.model.PremiumCard;
import com.company.model.StandardCard;
import com.company.utils.CardType;
import com.company.utils.FileOperations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CashMachineDAO {

    private static File file;

    private static final List<String> text;

    static {
        try {
            (file = new File(System.getProperty("user.dir") + "/data.txt")).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        text = FileOperations.readFile(file.getPath());
    }


    public static Float getCashMachineMoney() {
        final Float defaultBalance = 10000F;
        if (text.size() == 0 || !text.get(0).matches("^\\d+(.\\d+)?$")) return defaultBalance;
        return Float.parseFloat(text.get(0));
    }

    public static List<Card> getCards() {
        List<Card> allCards = new ArrayList<>();
        for (String line : text) {
            if (!line.matches("^\\d{4}-\\d{4}-\\d{4}-\\d{4} \\d{4} \\d+(.\\d+)? (BASIC|STANDARD|PREMIUM) \\d+$")) {
                continue;
            }
            String[] someElements = line.trim().split(" ");
            switch (CardType.valueOf(someElements[3])) {
                case BASIC -> {
                    Card card = new BasicCard(someElements[0], someElements[1]);
                    card.setBalance(Float.parseFloat(someElements[2]));
                    card.setStartBlock(Long.parseLong(someElements[4]));
                    allCards.add(card);
                }

                case STANDARD -> {
                    Card card = new StandardCard(someElements[0], someElements[1]);
                    card.setBalance(Float.parseFloat(someElements[2]));
                    card.setStartBlock(Long.parseLong(someElements[4]));
                    allCards.add(card);
                }

                case PREMIUM -> {
                    Card card = new PremiumCard(someElements[0], someElements[1]);
                    card.setBalance(Float.parseFloat(someElements[2]));
                    card.setStartBlock(Long.parseLong(someElements[4]));
                    allCards.add(card);
                }
            }
        }
        return allCards;
    }

    public static void writeFile(Float money, List<Card> cards) {
        List<String> outputText = new ArrayList<>();
        outputText.add(money.toString());
        for (Card card : cards) {
            outputText.add(card.toString());
        }
        FileOperations.writeToFile(file.getPath(), outputText);
    }
}

