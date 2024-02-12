package ru.academits.shagaev.shapes_main;

import ru.academits.shagaev.circle.Circle;
import ru.academits.shagaev.rectangle.Rectangle;
import ru.academits.shagaev.shape.Shape;
import ru.academits.shagaev.square.Square;
import ru.academits.shagaev.triangle.Triangle;

import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = {new Square(4), new Triangle(1, 1, 3, 4, 7, 0),
                new Rectangle(5, 4), new Circle(4), new Square(6)};

        Arrays.sort(shapes, Comparator.comparingDouble(Shape::getArea).reversed());
        System.out.println("Фигура с максимальной площадью = " + shapes[0].toString());

        Arrays.sort(shapes, Comparator.comparingDouble(Shape::getPerimeter).reversed());
        System.out.println("Фигура с вторым по величине периметром = " + shapes[1].toString());
    }
}
