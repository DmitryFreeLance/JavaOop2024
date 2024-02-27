package ru.academits.shagaev.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    public Vector(int dimension) {
        if (dimension <= 0) {
            throw new IllegalArgumentException("Размерность вектора не может быть меньше 0, принятый аргумент = " + dimension);
        }

        this.components = new double[dimension];
    }

    public Vector(Vector vector) {
        this.components = Arrays.copyOf(vector.components, vector.components.length);
    }

    public Vector(double[] values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Нельзя создать вектор нулевой длины");
        }

        this.components = Arrays.copyOf(values, values.length);
    }

    public Vector(int dimension, double[] values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Нельзя создать вектор нулевой длины");
        }

        this.components = Arrays.copyOf(values, dimension);
    }

    public int getDimension() {
        return components.length;
    }

    public void add(Vector vector) {
        int newDimension = Math.max(components.length, vector.components.length);
        resizeVector(newDimension);

        for (int i = 0; i < newDimension; i++) {
            components[i] = (i < components.length ? components[i] : 0) + (i < vector.components.length ? vector.components[i] : 0);
        }
    }

    public void getSubtraction(Vector vector) {
        int newDimension = Math.max(components.length, vector.components.length);
        resizeVector(newDimension);

        for (int i = 0; i < newDimension; i++) {
            components[i] = (i < components.length ? components[i] : 0) - (i < vector.components.length ? vector.components[i] : 0);
        }
    }

    private void resizeVector(int newDimension) {
        if (components.length != newDimension) {
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

    public void setComponent(int index, double value) {
        this.components[index] = value;
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        int newDimension = Math.max(vector1.components.length, vector2.components.length);
        double[] components = new double[newDimension];

        for (int i = 0; i < newDimension; i++) {
            double value1 = (i < vector1.components.length ? vector1.components[i] : 0);
            double value2 = (i < vector2.components.length ? vector2.components[i] : 0);
            components[i] = value1 + value2;
        }

        return new Vector(components);
    }

    public static Vector getSubtraction(Vector vector1, Vector vector2) {
        int newDimension = Math.max(vector1.components.length, vector2.components.length);
        double[] components = new double[newDimension];

        for (int i = 0; i < newDimension; i++) {
            double value1 = (i < vector1.components.length ? vector1.components[i] : 0);
            double value2 = (i < vector2.components.length ? vector2.components[i] : 0);
            components[i] = value1 - value2;
        }

        return new Vector(components);
    }


    public static double getScalarMultiply(Vector vector1, Vector vector2) {
        int minLength = Math.min(vector1.components.length, vector2.components.length);
        double result = 0;

        for (int i = 0; i < minLength; i++) {
            result += vector1.components[i] * vector2.components[i];
        }

        return result;
    }

    public double getLength() {
        double sum = 0;

        for (double component : components) {
            sum += component * component;
        }

        return sum;
    }


    @Override
    public String toString() {
        if (components.length == 0) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for (int i = 0; i < components.length - 1; i++) {
            sb.append(components[i]).append(", ");
        }

        sb.append(components[components.length - 1]).append("}");
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


