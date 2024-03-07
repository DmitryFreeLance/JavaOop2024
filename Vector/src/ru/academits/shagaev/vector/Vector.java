package ru.academits.shagaev.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    public Vector(int dimension) {
        if (dimension <= 0) {
            throw new IllegalArgumentException("Размерность вектора не может быть меньше или равна 0" + dimension);
        }

        components = new double[dimension];
    }

    public Vector(Vector vector) {
        components = Arrays.copyOf(vector.components, vector.components.length);
    }

    public Vector(double[] components) {
        if (components.length == 0) {
            throw new IllegalArgumentException("Нельзя создать вектор нулевой размерности");
        }

        this.components = Arrays.copyOf(components, components.length);
    }

    public Vector(int dimension, double[] components) {
        if (dimension <= 0) {
            throw new IllegalArgumentException("Нельзя создать вектор нулевой или отрицательной размерности");
        }

        this.components = Arrays.copyOf(components, dimension);
    }

    public int getDimension() {
        return components.length;
    }

    public void add(Vector vector) {
        int newDimension = Math.max(components.length, vector.components.length);
        resizeVector(newDimension);

        for (int i = 0; i < newDimension; i++) {
            components[i] += vector.components[i];
        }
    }

    public void subtract(Vector vector) {
        int newDimension = Math.max(components.length, vector.components.length);
        resizeVector(newDimension);

        for (int i = 0; i < newDimension; i++) {
            components[i] -= vector.components[i];
        }
    }

    private void resizeVector(int newDimension) {
        if (newDimension > components.length) {
            components = Arrays.copyOf(components, newDimension);
        }
    }

    public void multiply(double scalar) {
        for (int i = 0; i < components.length; i++) {
            components[i] *= scalar;
        }
    }

    public void reverse() {
        multiply(-1);
    }

    public double getComponent(int index) {
        return this.components[index];
    }

    public void setComponent(int index, double component) {
        this.components[index] = component;
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        Vector sum = new Vector(vector1);
        sum.add(vector2);
        return sum;
    }

    public static Vector getSubtract(Vector vector1, Vector vector2) {
        Vector subtraction = new Vector(vector1);
        subtraction.subtract(vector2);
        return subtraction;
    }

    public static double getScalarMultiplication(Vector vector1, Vector vector2) {
        int minDimension = Math.min(vector1.components.length, vector2.components.length);
        double result = 0;

        for (int i = 0; i < minDimension; i++) {
            result += vector1.components[i] * vector2.components[i];
        }

        return result;
    }

    public double getLength() {
        double sum = 0;

        for (double component : components) {
            sum += component * component;
        }

        return Math.sqrt(sum);
    }

    public double scalarProduct(Vector vector) {
        if (components.length != vector.getDimension()) {
            throw new IllegalArgumentException("Несовместимые размеры векторов для скалярного произведения: "
                    + components.length + " и " + vector.getDimension());
        }

        double product = 0;
        for (int i = 0; i < components.length; i++) {
            product += components[i] * vector.getComponent(i);
        }

        return product;
    }

    @Override
    public String toString() {
        int dimension = components.length - 1;
        StringBuilder sb = new StringBuilder();
        sb.append('{');

        for (int i = 0; i < dimension; i++) {
            sb.append(components[i]).append(", ");
        }

        sb.append(components[dimension]).append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(components);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Vector vector = (Vector) object;
        return Arrays.equals(components, vector.components);
    }
}