package com.company.utils;

import java.util.Scanner;

public class Input {

    private static Scanner scanner = new Scanner(System.in);

    public static String inString(){
        return scanner.nextLine();
    }

    public static String inCardNumber() {
        while (true) {
            System.out.print("Введите номер карты (XXXX-XXXX-XXXX-XXXX): ");
            String cardNumber = scanner.nextLine();
            if (!Valid.checkNumber(cardNumber)) {
                System.out.println("Неверный формат!");
                continue;
            }
            if (!Valid.mayExist(cardNumber)) {
                System.out.println("Введенный номер карты не может существовать!");
                continue;
            }
            return cardNumber;
        }
    }

    public static String inPin() {
        while (true) {
            System.out.print("Введите pin (4 числа): ");
            String pin = scanner.nextLine();
            if (!pin.matches("^\\d{4}")) {
                System.out.println("Неверный формат!");
                continue;
            }
            return pin;
        }

    }

    public static Float inBalance() {
        while (true) {
            System.out.print("Введите баланс: ");
            String balance = scanner.nextLine();
            if (!balance.matches("^\\d+(.\\d{2})?")) {
                System.out.println("Неверный формат!");
                continue;
            }
            return Float.parseFloat(balance);
        }
    }

    public static CardType inCardType() {
        while (true) {
            switch (scanner.nextLine()) {
                case "1" -> {
                    return CardType.PREMIUM;
                }
                case "2" -> {
                    return CardType.STANDARD;
                }
                case "3" -> {
                    return CardType.BASIC;
                }
                default -> System.out.println("Неверный ввод");
            }

        }
    }

    public static Float inputMoney() {
        while (true) {
            System.out.println("Введите сумму денег: ");
            String money = scanner.nextLine();
            if (!money.matches("^\\d+(.\\d{2})?")) {
                System.out.println("Число введено неверно!");
                continue;
            }
            return Float.parseFloat(money);
        }
    }

}
