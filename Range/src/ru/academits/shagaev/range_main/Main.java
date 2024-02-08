package ru.academits.shagaev.range_main;

import ru.academits.shagaev.range.Range;

public class Main {
    public static void main(String[] args) {
        int number = 5;

        Range range = new Range(1.1, 8.9);
        range.setFrom(-1.2);
        range.setTo(7);
        range.getLength();

        System.out.println("Длина диапазона = " + Math.abs(range.getLength()));

        if (range.isInside(number)) {
            System.out.println("Число входит в диапазон");
        } else {
            System.out.println("Число не входит в диапазон");
        }

        double sum = range.getFrom() + range.getTo() + number; // для реализации геттеров
        System.out.println("Сумма всех слагаемых = " + sum);
    }
}
