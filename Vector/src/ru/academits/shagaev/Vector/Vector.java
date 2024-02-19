package ru.academits.shagaev.Vector;

import java.util.Arrays;

public class Vector {
    private final double[] components;

    public Vector(int dimension) {
        if (dimension <= 0) {
            throw new IllegalArgumentException("Размерность вектора не может быть меньше 0");
        } else {
            this.components = new double[dimension];
        }
    }

    public Vector(Vector vector) {
        this.components = Arrays.copyOf(vector.components, vector.components.length);
    }

    public Vector(double[] values) {
        this.components = Arrays.copyOf(values, values.length);
    }

    public Vector(int n, double[] values) {
        components = new double[n];
        System.arraycopy(values, 0, components, 0, Math.min(n, values.length));
    }

    public int getSize() {
        return components.length;
    }

    public Vector sum(Vector vector2) {
        int n = Math.max(this.components.length, vector2.components.length);
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = (i < this.components.length ? this.components[i] : 0) + (i < vector2.components.length ? vector2.components[i] : 0);
        }
        return new Vector(result);
    }

    public Vector subtraction(Vector vector2) {
        int n = Math.max(this.components.length, vector2.components.length);
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = (i < this.components.length ? this.components[i] : 0) - (i < vector2.components.length ? vector2.components[i] : 0);
        }
        return new Vector(result);
    }

    public Vector multiply(double scalar) {
        double[] result = new double[this.components.length];
        for (int i = 0; i < this.components.length; i++) {
            result[i] = this.components[i] * scalar;
        }
        return new Vector(result);
    }

    public Vector reverse() {
        return this.multiply(-1);
    }

    public double getComponent(int index) {
        return this.components[index];
    }

    public void setComponent(int index, double value) {
        this.components[index] = value;
    }

    public static Vector sum(Vector v1, Vector v2) {
        return v1.sum(v2);
    }

    public static Vector subtraction(Vector v1, Vector v2) {
        return v1.subtraction(v2);
    }

    public static double scalarMultiply(Vector v1, Vector v2) {
        int n = Math.min(v1.components.length, v2.components.length);
        double result = 0;
        for (int i = 0; i < n; i++) {
            result += v1.components[i] * v2.components[i];
        }
        return result;
    }

    @Override
    public String toString() {
        return Arrays.toString(components);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(components);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Vector vector = (Vector) object;
        return Arrays.equals(components, vector.components);
    }
}


