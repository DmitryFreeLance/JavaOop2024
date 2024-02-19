package ru.academits.shagaev.shapes_main;

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

        Arrays.sort(shapes, new AreaComparator());
        System.out.println("Фигура с максимальной площадью = " + shapes[0]);

        Arrays.sort(shapes, new PerimeterComparator());
        System.out.println("Фигура с вторым по величине периметром = " + shapes[1]);
    }
}