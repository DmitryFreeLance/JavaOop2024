package ru.academits.shagaev.shapes;

public class Triangle implements Shape {
    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;
    private final double x3;
    private final double y3;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    private static double getSideLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private double getSideALength() {
        return getSideLength(x1, y1, x2, y2);
    }

    private double getSideBLength() {
        return getSideLength(x2, y2, x3, y3);
    }

    private double getSideCLength() {
        return getSideLength(x3, y3, x1, y1);
    }

    @Override
    public double getWidth() {
        return Math.max(x1, Math.max(x2, x3)) - Math.min(x1, Math.min(x2, x3));
    }

    @Override
    public double getHeight() {
        return Math.max(y1, Math.max(y2, y3)) - Math.min(y1, Math.min(y2, y3));
    }

    @Override
    public double getArea() {
        double sideALength = getSideALength();
        double sideBLength = getSideBLength();
        double sideCLength = getSideCLength();

        double perimeterHalf = (sideALength + sideBLength + sideCLength) / 2;
        return Math.sqrt(perimeterHalf * (perimeterHalf - sideALength) * (perimeterHalf - sideBLength) * (perimeterHalf - sideCLength));
    }

    @Override
    public double getPerimeter() {
        return getSideALength() + getSideBLength() + getSideCLength();
    }

    @Override
    public String toString() {
        return "Треугольник с координатами: (" + x1 + ", " + y1 + "), (" + x2 + ", " + y2 + "), (" + x3 + ", " + y3 + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + Double.hashCode(x1);
        result = prime * result + Double.hashCode(y1);
        result = prime * result + Double.hashCode(x2);
        result = prime * result + Double.hashCode(y2);
        result = prime * result + Double.hashCode(x3);
        result = prime * result + Double.hashCode(y3);

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

        Triangle triangle = (Triangle) obj;
        return x1 == triangle.x1 && y1 == triangle.y1 && x2 == triangle.x2 && y2 == triangle.y2 && x3 == triangle.x3 && y3 == triangle.y3;
    }
}
