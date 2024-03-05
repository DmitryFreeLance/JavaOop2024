package ru.academits.shagaev.shapes_main;

import ru.academits.shagaev.shapes_comparators.ShapeAreaComparator;
import ru.academits.shagaev.shapes_comparators.ShapePerimeterComparator;
import ru.academits.shagaev.shapes.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = {
                new Square(4),
                new Triangle(1, 1, 3, 4, 7, 0),
                new Rectangle(5, 4),
                new Circle(4),
                new Square(6)
        };

        Square square = new Square(4);
        System.out.println("Длина стороны квадрата = " + square.getSideLength());

        Circle circle = new Circle(6);
        System.out.println("Радиус окружности = " + circle.getRadius());

        Arrays.sort(shapes, new ShapeAreaComparator());
        System.out.println("Фигура с максимальной площадью = " + shapes[4]);

        Arrays.sort(shapes, new ShapePerimeterComparator());
        System.out.println("Фигура с вторым по величине периметром = " + shapes[3]);
    }
}