package ru.academits.shagaev.shapes;

public class Square implements Shape {
    private final double sideLength;

    public Square(double side) {
        this.sideLength = side;
    }

    public double getWidth() {
        return sideLength;
    }

    public double getHeight() {
        return sideLength;
    }

    public double getArea() {
        return sideLength * sideLength;
    }

    public double getPerimeter() {
        return sideLength * 4;
    }

    @Override
    public String toString() {
        return "Квадрат со стороной: " + sideLength;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + Double.hashCode(sideLength);

        return result;
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
        return sideLength == square.sideLength;
    }
}