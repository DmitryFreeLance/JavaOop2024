package ru.academits.shagaev.matrix;

import ru.academits.shagaev.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] rows;

    public Matrix(int columns, int rows) {
        if (columns <= 0) {
            throw new IllegalArgumentException("Количество столбцов в матрице должно быть больше 0 : " + columns);
        }

        if (rows <= 0) {
            throw new IllegalArgumentException("Количество строк в матрице должно быть больше 0 : " + rows);
        }

        this.rows = new Vector[columns];

        for (int i = 0; i < columns; i++) {
            this.rows[i] = new Vector(rows);
        }
    }

    public Matrix(Matrix matrix) {
        rows = new Vector[matrix.rows.length];

        for (int i = 0; i < matrix.rows.length; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public Matrix(double[][] values) {
        if (values.length == 0 || values[0].length == 0) {
            throw new IllegalArgumentException("Массив значений должен содержать хотя бы один элемент");
        }

        int columns = values.length;
        int maxRowLength = 0;

        for (double[] row : values) {
            if (row.length > maxRowLength) {
                maxRowLength = row.length;
            }
        }

        rows = new Vector[columns];

        for (int i = 0; i < columns; i++) {
            rows[i] = new Vector(maxRowLength, values[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors.length == 0) {
            throw new IllegalArgumentException("Массив векторов не может быть пустым");
        }

        int columns = vectors.length;
        int maxVectorDimension = vectors[0].getDimension();

        for (Vector vector : vectors) {
            int dimension = vector.getDimension();
            if (dimension > maxVectorDimension) {
                maxVectorDimension = dimension;
            }
        }

        rows = new Vector[columns];

        for (int i = 0; i < columns; i++) {
            rows[i] = new Vector(maxVectorDimension);
            rows[i].add(vectors[i]);
        }
    }

    public int getRowsCount() {
        return rows.length;
    }

    public int getColumnsCount() {
        return rows[0].getDimension();
    }

    public Vector getRow(int index) {
        if (index < 0 || index >= rows.length) {
            throw new IndexOutOfBoundsException("Индекс строки выходит за границы матрицы: " + index);
        }
        return new Vector(rows[index]);
    }

    public void setRow(int index, Vector vector) {
        if (index < 0 || index >= rows.length) {
            throw new IndexOutOfBoundsException("Индекс строки выходит за границы матрицы: " + index);
        }
        if (vector.getDimension() != getColumnsCount()) {
            throw new IllegalArgumentException("Размер вектора не соответствует количеству столбцов матрицы");
        }
        rows[index] = new Vector(vector);
    }

    public Vector getColumn(int index) {
        if (index < 0 || index >= getColumnsCount()) {
            throw new IndexOutOfBoundsException("Индекс столбца выходит за границы матрицы: " + index);
        }
        Vector column = new Vector(rows.length);
        for (int i = 0; i < rows.length; i++) {
            column.setComponent(i, rows[i].getComponent(index));
        }
        return column;
    }

    public void transpose() {
        int columnCount = rows[0].getDimension();

        Vector[] transposedRows = new Vector[columnCount];

        for (int i = 0; i < columnCount; i++) {
            transposedRows[i] = getColumn(i);
        }

        rows = transposedRows;
    }

    public void multiplyByScalar(double scalar) {
        for (Vector row : rows) {
            row.multiply(scalar);
        }
    }

    public double getDeterminant() {
        if (rows.length != getColumnsCount()) {
            throw new IllegalStateException("Матрица должна быть квадратной для вычисления определителя: " + rows.length);
        }

        int size = rows.length;

        if (size == 1) {
            return rows[0].getComponent(0);
        }

        if (size == 2) {
            return rows[0].getComponent(0) * rows[1].getComponent(1) - rows[0].getComponent(1) * rows[1].getComponent(0);
        }

        double determinant = 0;

        for (int i = 0; i < size; i++) {
            int sign = (i % 2 == 0) ? 1 : -1;
            determinant += sign * rows[0].getComponent(i) * getMinor(i).getDeterminant();
        }

        return determinant;
    }

    private Matrix getMinor(int columnToRemove) {
        int size = rows.length - 1;
        Matrix minor = new Matrix(size, size);

        int destinationRow = 0;

        for (int i = 1; i < rows.length; i++) {
            Vector currentRow = rows[i];
            int destinationColumn = 0;

            for (int j = 0; j < currentRow.getDimension(); j++) {
                if (j == columnToRemove) {
                    continue;
                }

                minor.rows[destinationRow].setComponent(destinationColumn, currentRow.getComponent(j));
                destinationColumn++;
            }

            destinationRow++;
        }

        return minor;
    }

    public Vector multiplyByVector(Vector vector) {
        if (getColumnsCount() != vector.getDimension()) {
            throw new IllegalArgumentException("Несовпадение размерностей для умножения матрицы на вектор. Количество столбцов матрицы: " + getColumnsCount() +
                    ", размерность вектора: " + vector.getDimension());
        }

        double[] resultComponents = new double[rows.length];

        for (int i = 0; i < rows.length; i++) {
            resultComponents[i] = rows[i].scalarProduct(vector);
        }

        return new Vector(resultComponents);
    }

    public void add(Matrix matrix) {
        checkMatrixDimensions(matrix, "сложения");

        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].getDimension(); j++) {
                double value = rows[i].getComponent(j) + matrix.rows[i].getComponent(j);
                rows[i].setComponent(j, value);
            }
        }
    }

    public void subtract(Matrix matrix) {
        checkMatrixDimensions(matrix, "вычитания");

        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].getDimension(); j++) {
                double value = rows[i].getComponent(j) - matrix.rows[i].getComponent(j);
                rows[i].setComponent(j, value);
            }
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        matrix1.checkMatrixDimensions(matrix2, "сложения");
        Matrix result = new Matrix(matrix1);
        result.add(matrix2);
        return result;
    }

    public static Matrix getSubtract(Matrix matrix1, Matrix matrix2) {
        matrix1.checkMatrixDimensions(matrix2, "вычитания");
        Matrix result = new Matrix(matrix1);
        result.subtract(matrix2);
        return result;
    }

    private void checkMatrixDimensions(Matrix matrix, String operation) {
        if (rows.length != matrix.rows.length || rows[0].getDimension() != matrix.rows[0].getDimension()) {
            throw new IllegalArgumentException("Размеры матриц должны совпадать для " + operation + ": " +
                    rows.length + "x" + rows[0].getDimension() + " и " +
                    matrix.rows.length + "x" + matrix.rows[0].getDimension());
        }
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Невозможно выполнить умножение матриц: количество столбцов первой матрицы (" +
                    matrix1.getColumnsCount() + ") не совпадает с количеством строк второй матрицы (" +
                    matrix2.getRowsCount() + ")");
        }

        int resultRows = matrix1.getRowsCount();
        int resultColumns = matrix2.getColumnsCount();
        Matrix result = new Matrix(resultColumns, resultRows);

        for (int i = 0; i < resultRows; i++) {
            for (int j = 0; j < resultColumns; j++) {
                result.rows[i].setComponent(j, matrix1.getRow(i).scalarProduct(matrix2.getColumn(j)));
            }
        }

        return result;
    }

    @Override
    public String toString() {
        int size = rows.length - 1;
        StringBuilder sb = new StringBuilder();
        sb.append('{');

        for (int i = 0; i < size; i++) {
            sb.append(rows[i]).append(", ");
        }

        sb.append(rows[size]).append('}');
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