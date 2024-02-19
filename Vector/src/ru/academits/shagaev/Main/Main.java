package ru.academits.shagaev.Main;

import ru.academits.shagaev.Vector.Vector;

public class Main {
    public static void main(String[] args) {
        double[] values = {5, 2.1, 3.92, 7};

        Vector vector2 = new Vector(values);

        Vector[] vector = {new Vector(3), new Vector(2), new Vector(1),
                new Vector(4), new Vector(values), new Vector(5, values), new Vector(vector2)};

        System.out.println("Размерность вектора #5 равна: " + vector[5].getSize());
        System.out.println("Результат сложения двух векторов: " + vector[5].sum(vector[6]));
        System.out.println("Результат разности двух векторов: " + vector[5].subtraction(vector[4]));
        System.out.println("Результат умножения вектора на скаляр: " + vector[6].multiply(2));

        vector[6].setComponent(0, 3.3);
        System.out.println("Компонента вектора #6 с индексом 0 равна: " + vector[6].getComponent(0));
        System.out.println("Использование метода reverse: " + vector[6].reverse());

        Vector vector7 = Vector.sum(vector[6], vector2);
        System.out.println("Сложение двух векторов(static): " + vector7);

        Vector vector8 = Vector.subtraction(vector[5], vector2);
        System.out.println("Разность двух векторов(static): " + vector8);

        double vector9 = Vector.scalarMultiply(vector[6], vector2);
        System.out.println("Скалярное произведение двух векторов(static): " + vector9);
    }
}
