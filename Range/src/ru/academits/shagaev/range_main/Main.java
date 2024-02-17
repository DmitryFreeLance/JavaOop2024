package ru.academits.shagaev.range_main;

import ru.academits.shagaev.range.Range;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int number = 5;

        Range range1 = new Range(1.1, 8.9);
        range1.setFrom(1);
        range1.setTo(6);
        double length1 = range1.getLength();

        Range range2 = new Range(0.3, 2.2);
        range2.setFrom(1);
        range2.setTo(6);
        double length2 = range2.getLength();

        System.out.println("Длина диапазона = " + Math.abs(range1.getLength()));

        if (range1.isInside(number)) {
            System.out.println("Число входит в диапазон");
        } else {
            System.out.println("Число не входит в диапазон");
        }

        // для реализации геттеров
        double sum = range1.getFrom() + range1.getTo() + range2.getFrom() + range2.getTo() + number + length1 + length2;
        System.out.println("Сумма всех слагаемых = " + sum);

        Range intersection = range1.getIntersection(range2);

        System.out.println("Интервал - пересечение двух интервалов: " + intersection.toString());

        Range[] union = range1.getUnion(range2);

        System.out.println("Интервал - объединение двух интервалов: " + Arrays.toString(union));

        Range[] difference = range1.getDifference(range2);

        System.out.println("Интервал - разность двух интервалов: " + Arrays.toString(difference));
    }
}
