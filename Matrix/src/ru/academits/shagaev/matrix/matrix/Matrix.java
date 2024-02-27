package ru.academits.shagaev.matrix.matrix;

import ru.academits.shagaev.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] rows;

    public Matrix(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Размеры матрицы должны быть положительными");
        }

        rows = new Vector[n];

        for (int i = 0; i < n; i++) {
            rows[i] = new Vector(m);
        }
    }

    public Matrix(Matrix matrix) {
        rows = new Vector[matrix.getRowsCount()];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Vector(matrix.getRow(i));
        }
    }

    public Matrix(double[][] values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Массив значений должен быть непустым");
        }

        int n = values.length;
        int m = values[0].length;
        rows = new Vector[n];

        for (int i = 0; i < n; i++) {

            if (values[i].length != m) {
                throw new IllegalArgumentException("Длины строк массива значений должны быть одинаковыми");
            }

            rows[i] = new Vector(Arrays.copyOf(values[i], m));
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors.length == 0) {
            throw new IllegalArgumentException("Массив векторов не может быть пустым");
        }

        int n = vectors.length;
        int m = vectors[0].getDimension();

        for (Vector vector : vectors) {
            if (vector.getDimension() != m) {
                throw new IllegalArgumentException("Длины векторов должны быть одинаковыми");
            }
        }

        rows = new Vector[n];

        for (int i = 0; i < n; i++) {
            rows[i] = new Vector(vectors[i]);
        }
    }

    public int getRowsCount() {
        return rows.length;
    }

    public int getColumnsCount() {
        return rows[0].getDimension();
    }

    public Vector getRow(int index) {
        return rows[index];
    }

    public void setRow(int index, Vector vector) {
        if (vector.getDimension() != getColumnsCount()) {
            throw new IllegalArgumentException("Размер вектора не соответствует количеству столбцов матрицы");
        }

        rows[index] = new Vector(vector);
    }

    public Vector getColumn(int index) {
        Vector column = new Vector(rows.length);

        for (int i = 0; i < rows.length; i++) {
            column.setComponent(i, rows[i].getComponent(index));
        }

        return column;
    }


    public void transpose() {
        int originalRows = getRowsCount();
        int originalCols = getColumnsCount();

        Vector[] transposedRows = new Vector[originalCols];
        for (int i = 0; i < originalCols; i++) {
            Vector columnVector = new Vector(originalRows);
            for (int j = 0; j < originalRows; j++) {
                columnVector.setComponent(j, rows[j].getComponent(i));
            }
            transposedRows[i] = columnVector;
        }

        rows = transposedRows;
    }

    public void scalarMultiply(double scalar) {
        for (Vector row : rows) {
            row.multiply(scalar);
        }
    }

    public double getDeterminant() {
        if (getRowsCount() != getColumnsCount()) {
            throw new IllegalStateException("Матрица должна быть квадратной для вычисления определителя");
        }

        int size = getRowsCount();

        if (size == 1) {
            return rows[0].getComponent(0);
        }

        if (size == 2) {
            return rows[0].getComponent(0) * rows[1].getComponent(1) - rows[0].getComponent(1) * rows[1].getComponent(0);
        }

        double determinant = 0;

        for (int i = 0; i < size; i++) {
            int sign = (i % 2 == 0) ? 1 : -1;
            determinant += sign * rows[0].getComponent(i) * getCofactor(i).getDeterminant();
        }

        return determinant;
    }

    private Matrix getCofactor(int colToRemove) {
        int size = getRowsCount() - 1;
        Matrix cofactor = new Matrix(size, size);

        int destRow = 0;

        for (int i = 1; i < getRowsCount(); i++) {
            Vector currentRow = rows[i];

            int destCol = 0;

            for (int j = 0; j < currentRow.getDimension(); j++) {
                if (j == colToRemove) {
                    continue;
                }

                cofactor.getRow(destRow).setComponent(destCol, currentRow.getComponent(j));
                destCol++;
            }

            destRow++;
        }

        return cofactor;
    }

    public Vector multiplyByVector(Vector vector) {
        if (getColumnsCount() != vector.getDimension()) {
            throw new IllegalArgumentException("Несовпадение размерностей для умножения матрицы на вектор");
        }

        double[] result = new double[getRowsCount()];

        for (int i = 0; i < getRowsCount(); i++) {
            double sum = 0;

            for (int j = 0; j < getColumnsCount(); j++) {
                sum += rows[i].getComponent(j) * vector.getComponent(j);
            }

            result[i] = sum;
        }

        return new Vector(result);
    }

    public void add(Matrix matrix) {
        if (getRowsCount() != matrix.getRowsCount() || getColumnsCount() != matrix.getColumnsCount()) {
            throw new IllegalArgumentException("Размеры матриц должны совпадать для сложения");
        }

        for (int i = 0; i < getRowsCount(); i++) {
            rows[i].add(matrix.getRow(i));
        }
    }

    public void subtract(Matrix matrix) {
        if (getRowsCount() != matrix.getRowsCount() || getColumnsCount() != matrix.getColumnsCount()) {
            throw new IllegalArgumentException("Размеры матриц должны совпадать для вычитания");
        }

        for (int i = 0; i < getRowsCount(); i++) {
            rows[i].subtract(matrix.getRow(i));
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowsCount() != matrix2.getRowsCount() || matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("Размеры матриц должны совпадать для сложения");
        }

        Matrix result = new Matrix(matrix1.getRowsCount(), matrix1.getColumnsCount());
        for (int i = 0; i < result.getRowsCount(); i++) {
            for (int j = 0; j < result.getColumnsCount(); j++) {
                double value = matrix1.getRow(i).getComponent(j) + matrix2.getRow(i).getComponent(j);
                result.getRow(i).setComponent(j, value);
            }
        }

        return result;
    }

    public static Matrix getSubtraction(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowsCount() != matrix2.getRowsCount() || matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("Размеры матриц должны совпадать для вычитания");
        }

        Matrix result = new Matrix(matrix1.getRowsCount(), matrix1.getColumnsCount());

        for (int i = 0; i < result.getRowsCount(); i++) {
            for (int j = 0; j < result.getColumnsCount(); j++) {
                double value = matrix1.getRow(i).getComponent(j) - matrix2.getRow(i).getComponent(j);
                result.getRow(i).setComponent(j, value);
            }
        }

        return result;
    }

    public static Matrix getMultiply(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Несовпадение размерностей для умножения матриц");
        }

        Matrix result = new Matrix(matrix1.getRowsCount(), matrix2.getColumnsCount());
        for (int i = 0; i < result.getRowsCount(); i++) {
            for (int j = 0; j < result.getColumnsCount(); j++) {
                double value = 0;

                for (int k = 0; k < matrix1.getColumnsCount(); k++) {
                    value += matrix1.getRow(i).getComponent(k) * matrix2.getColumn(j).getComponent(k);
                }

                result.getRow(i).setComponent(j, value);
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for (int i = 0; i < rows.length - 1; i++) {
            sb.append(rows[i].toString()).append(", ");
        }

        sb.append(rows[rows.length - 1].toString()).append("}");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(rows);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Matrix matrix = (Matrix) object;
        return Arrays.equals(rows, matrix.rows);
    }
}