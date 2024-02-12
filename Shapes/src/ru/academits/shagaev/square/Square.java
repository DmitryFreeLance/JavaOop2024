package ru.academits.shagaev.square;

import ru.academits.shagaev.shape.Shape;

import java.util.Objects;

public class Square implements Shape {
    final private double side;

    public Square(double side) {
        this.side = side;
    }

    public double getWidth() {
        return side;
    }

    public double getHeight() {
        return side;
    }

    public double getArea() {
        return side * side;
    }

    public double getPerimeter() {
        return side * 4;
    }

    @Override
    public String toString() {
        return ("Квадрат со стороной: " + side);
    }

    @Override
    public int hashCode() {
        return Objects.hash(side);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Square square = (Square) obj;
        return side == square.side;
    }
}
