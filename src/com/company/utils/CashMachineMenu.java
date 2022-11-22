package com.company.utils;

import com.company.DAO.CashMachineDAO;
import com.company.model.*;
import com.company.utils.CardType;
import com.company.utils.Input;

public class CashMachineMenu {
    private final CashMachine cashMachine = new CashMachine(CashMachineDAO.getCashMachineMoney());
    private final CardStorage cardStorage = new CardStorage(CashMachineDAO.getCards());

    private void save() {
        CashMachineDAO.writeFile(cashMachine.getBalance(), cardStorage.getStorage());
    }

    private Card inputCard() {
        Card card;
        while (true) {
            String cardNumber = Input.inCardNumber();
            if (cardStorage.hasCard(cardNumber)) {
                card = cardStorage.getCard(cardNumber);
                if (card.isBlock()) {
                    System.out.println("Карта заблокирована!");
                    continue;
                }
                for (int i = 0; i < 3; i++) {
                    System.out.printf("Введите PIN, количество попыток - %d: ", 3-i);
                    String pin = Input.inPin();
                    if (card.checkPin(pin)) {
                        System.out.println("Верный PIN!");
                        cashMachine.setHasCard(true);
                        return card;
                    } else
                        System.out.println("Неверный PIN!");
                }
                card.block();
                System.out.println("Карта заблокироввана на сутки!");
                save();
            } else {
                card = newCard(cardNumber);
                cashMachine.setHasCard(true);
                save();
                return card;
            }
        }
    }


    private Card newCard(String cardNumber) {
        System.out.println("Новая карта!\nПридумайте пароль.");
        String pin = Input.inPin();
        System.out.println("Выберете тип карты: 1 -> PREMIUM(коммиссия 1%), 2 -> STANDARD(коммиссия 3%), 3 -> BASIC(коммиссия 5%)");
        CardType cardType = Input.inCardType();
        Card card = null;
        boolean work = true;
        while (work) {
            work = false;
            switch (cardType) {
                case PREMIUM -> card = new PremiumCard(cardNumber, pin);
                case STANDARD -> card = new StandardCard(cardNumber, pin);
                case BASIC -> card = new BasicCard(cardNumber, pin);
                default -> {
                    work = true;
                    System.out.println("Неверный тип карты!");
                }
            }
        }
        cardStorage.addCard(card);
        return card;
    }

    private void topUp(Card card) {
        System.out.println("Введите сумму пополнения (не более 1000000): ");
        float money = Input.inputMoney();
        if (money <= 1_000_000) {
            cashMachine.topUp(money);
            card.topUp(money);
            System.out.println("Операция произведена успешно!");
        } else System.out.println("Превышен лимит, внесите сумму не более 1000000!");
    }

    private void cashOutMoney(Card card) {
        float count = Input.inputMoney();
        if (cashMachine.getBalance() < card.calculatePercentage(count)) {
            System.out.println("В банкомате недостаточно средств!");
        } else if (!card.canWithdrawal(count)) {
            System.out.println("На счету недостаточно средств!");
        } else {
            float money = card.withdrawal(count);
            cashMachine.withdrawal(money);
            System.out.println("Операция выполнена успешно. Выдано: " + money);
        }
    }


    private void operations(Card card) {
        boolean work = true;
        while (work) {
            System.out.println("Номер операции 1 - Проверить баланс карты, 2 - пополнить баланс, 3 - снять средства с карты, 4 - достать карту.");
            switch (Input.inString()) {
                case "1" -> System.out.println("Баланс: " + card.getBalance());
                case "2" -> topUp(card);
                case "3" -> cashOutMoney(card);

                case "4" -> {
                    cashMachine.setHasCard(false);
                    work = false;
                }
                default -> System.out.println("Повторите выбор действия");
            }
            System.out.println();
            save();
        }
    }


    public void start() {
        Card card = null;

        while (true) {
            if (!cashMachine.hasCard()) {
                card = inputCard();
            } else {
                operations(card);
            }
        }
    }
}
