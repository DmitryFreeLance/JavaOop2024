package ru.academits.shagaev.shapes;

public class Rectangle implements Shape {
    private final double height;
    private final double width;

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
        return "Прямоугольник с высотой: " + height + " и шириной: " + width;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + Double.hashCode(height);
        result = prime * result + Double.hashCode(width);

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

        Rectangle rectangle = (Rectangle) obj;
        return height == rectangle.height && width == rectangle.width;
    }
}