package ru.academits.shagaev.vector_main;

import ru.academits.shagaev.vector.Vector;

public class Main {
    public static void main(String[] args) {
        double[] array1 = {1.0, 2.0, 3.0};
        double[] array2 = {4.0, 5.0, 6.0};

        Vector vector1 = new Vector(array1);
        Vector vector2 = new Vector(array2);

        vector1.reverse();
        System.out.println(vector1 + " Длина данного вектора равна = " + vector1.getLength());

        System.out.println("Первый вектор: " + vector1);
        System.out.println("Второй вектор: " + vector2);

        vector1.add(vector2);
        System.out.println("Результат после сложения: " + vector1);

        vector1.subtract(vector2);
        System.out.println("Результат после вычитания: " + vector1);

        double scalarProduct = Vector.getScalarProduct(vector1, vector2);
        System.out.println("Скалярное произведение: " + scalarProduct);

        Vector sum = Vector.getSum(vector1, vector2);
        System.out.println("Результат после сложения: " + sum);

        Vector difference = Vector.getDifference(vector1, vector2);
        System.out.println("Результат после вычитания: " + difference);
    }
}