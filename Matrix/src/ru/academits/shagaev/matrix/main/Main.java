package ru.academits.shagaev.matrix.main;

import ru.academits.shagaev.matrix.matrix.Matrix;
import ru.academits.shagaev.vector.Vector;

public class Main {
    public static void main(String[] args) {
        double[][] valuesForMatrix = {{1, 2, 3}, {3, 2, 1}, {1, 2, 3}};
        double[] valuesForVector = {4, 5, 6};

        Vector[] vectors = {new Vector(valuesForVector), new Vector(valuesForVector), new Vector(valuesForVector)};

        Matrix matrix1 = new Matrix(3, 3);
        Matrix matrix2 = new Matrix(matrix1);
        Matrix matrix3 = new Matrix(valuesForMatrix);
        Matrix matrix4 = new Matrix(vectors);

        matrix4.setRow(1, vectors[0]);
        matrix2.transpose();
        matrix3.scalarMultiply(3.0);
        matrix4.getDeterminant();
        matrix2.multiplyByVector(vectors[1]);
        matrix3.subtract(matrix1);

        System.out.println("Результат сложения матриц равен: " + Matrix.getSum(matrix3, matrix4));
        System.out.println("Результат разности матриц равен: " + Matrix.getSubtraction(matrix3, matrix4));
        System.out.println("Результат умножения матриц равен: " + Matrix.getMultiply(matrix3, matrix4));
    }
}
