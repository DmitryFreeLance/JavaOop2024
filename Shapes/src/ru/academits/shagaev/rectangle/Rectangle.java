package ru.academits.shagaev.rectangle;

import ru.academits.shagaev.shape.Shape;

import java.util.Objects;

public class Rectangle implements Shape {
    final private double height;
    final private double width;

    public Rectangle(double height, double width) {
        this.height = height;
        this.width = width;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getArea() {
        return height * width;
    }

    public double getPerimeter() {
        return (height + width) * 2;
    }

    @Override
    public String toString() {
        return ("Прямоугольник с высотой: " + height + "и шириной: " + width);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Rectangle rectangle = (Rectangle) obj;
        return height == rectangle.height && width == rectangle.width;
    }
}
