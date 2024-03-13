package ru.academits.shagaev.vector_main;

import ru.academits.shagaev.vector.Vector;

public class Main {
    public static void main(String[] args) {
        double[] values = {5, 2.1, 3.92, 7};

        Vector vector2 = new Vector(values);
        Vector vector3 = new Vector(new Vector(4, values));

        Vector[] vectors = {
                new Vector(3),
                new Vector(2),
                new Vector(1),
                new Vector(4),
                new Vector(values),
                new Vector(vector2),
                new Vector(vector3)
        };

        System.out.println("Размерность вектора #5 равна: " + vectors[5].getDimension());

        vectors[5].setComponent(0, 3.3);
        vectors[5].reverse();
        vectors[5].subtract(vector3);
        System.out.println("Компонента вектора #5 с индексом 0 равна: " + vectors[5].getComponent(0));
        System.out.println("Длина вектора #5 = " + vectors[5].getLength());

        Vector vector7 = Vector.getSum(vectors[5], vector2);
        System.out.println("Сложение двух векторов(static): " + vector7);

        Vector vector8 = Vector.getDifference(vectors[5], vector2);
        System.out.println("Разность двух векторов(static): " + vector8);

        double vector9 = Vector.getScalarProduct(vectors[5], vector2);
        System.out.println("Скалярное произведение двух векторов(static): " + vector9);
    }
}
