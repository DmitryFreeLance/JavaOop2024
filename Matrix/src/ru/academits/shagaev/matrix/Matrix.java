package ru.academits.shagaev.matrix;

import ru.academits.shagaev.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] rows;

    public Matrix(int columnsCount, int rowsCount) {
        if (columnsCount <= 0) {
            throw new IllegalArgumentException("Количество столбцов в матрице должно быть больше нуля: " + columnsCount);
        }

        if (rowsCount <= 0) {
            throw new IllegalArgumentException("Количество строк в матрице должно быть больше нуля: " + rowsCount);
        }

        this.rows = new Vector[columnsCount];

        for (int i = 0; i < columnsCount; i++) {
            this.rows[i] = new Vector(rowsCount);
        }
    }

    public Matrix(Matrix matrix) {
        rows = new Vector[matrix.rows.length];

        for (int i = 0; i < matrix.rows.length; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public Matrix(double[][] values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("Массив значений не может быть пустым");
        }

        int rowsCount = values.length;
        int maxColumnCount = 0;

        for (double[] row : values) {
            int columnCount = row.length;

            if (columnCount > maxColumnCount) {
                maxColumnCount = columnCount;
            }
        }

        if (maxColumnCount == 0) {
            throw new IllegalArgumentException("Массив значений не может содержать строки без элементов");
        }

        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(maxColumnCount, values[i]);
        }
    }


    public Matrix(Vector[] vectors) {
        if (vectors.length == 0) {
            throw new IllegalArgumentException("Массив векторов не может быть пустым");
        }

        int rowsLength = vectors.length;
        int maxVectorDimension = vectors[0].getDimension();

        for (Vector vector : vectors) {
            int dimension = vector.getDimension();

            if (dimension > maxVectorDimension) {
                maxVectorDimension = dimension;
            }
        }

        rows = new Vector[rowsLength];

        for (int i = 0; i < rowsLength; i++) {
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
            throw new IndexOutOfBoundsException("Индекс строки выходит за границы матрицы. Допустимые значения: от 0 до " + (rows.length - 1) + ", получено: " + index);
        }

        return new Vector(rows[index]);
    }

    public void setRow(int index, Vector vector) {
        if (index < 0 || index >= rows.length) {
            throw new IndexOutOfBoundsException("Индекс строки выходит за границы матрицы. Допустимые значения: от 0 до " + (rows.length - 1) + ", получено: " + index);
        }

        if (vector.getDimension() != getColumnsCount()) {
            throw new IllegalArgumentException("Размер вектора не соответствует количеству столбцов матрицы. Ожидаемый размер: "
                    + getColumnsCount() + ", полученный размер: " + vector.getDimension());
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
        int columnsCount = getColumnsCount();

        Vector[] transposedRows = new Vector[columnsCount];

        for (int i = 0; i < columnsCount; i++) {
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
            throw new IllegalStateException("Матрица должна быть квадратной для вычисления определителя: Получены размеры: " + rows.length + "x" + getColumnsCount());
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
            resultComponents[i] = Vector.getScalarProduct(rows[i], vector);
        }

        return new Vector(resultComponents);
    }

    public void add(Matrix matrix) {
        checkMatrixSizesEquality(matrix, "сложения");

        for (int i = 0; i < rows.length; i++) {
            rows[i].add(matrix.rows[i]);
        }
    }

    public void subtract(Matrix matrix) {
        checkMatrixSizesEquality(matrix, "вычитания");

        for (int i = 0; i < rows.length; i++) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        matrix1.checkMatrixSizesEquality(matrix2, "сложения");

        Matrix result = new Matrix(matrix1);
        result.add(matrix2);
        return result;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        matrix1.checkMatrixSizesEquality(matrix2, "вычитания");

        Matrix result = new Matrix(matrix1);
        result.subtract(matrix2);
        return result;
    }

    private void checkMatrixSizesEquality(Matrix matrix, String operation) {
        if (rows.length != matrix.rows.length || rows[0].getDimension() != matrix.rows[0].getDimension()) {
            throw new IllegalArgumentException("Размеры матриц должны совпадать для " + operation + ": " +
                    rows.length + "x" + getColumnsCount() + " и " +
                    matrix.rows.length + "x" + matrix.getColumnsCount());
        }
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Невозможно выполнить умножение матриц: количество столбцов первой матрицы (" +
                    matrix1.getColumnsCount() + ") не совпадает с количеством строк второй матрицы (" +
                    matrix2.getRowsCount() + ")");
        }

        int resultRowsCount = matrix1.getRowsCount();
        int resultColumnsCount = matrix2.getColumnsCount();
        Matrix result = new Matrix(resultColumnsCount, resultRowsCount);

        for (int i = 0; i < resultRowsCount; i++) {
            for (int j = 0; j < resultColumnsCount; j++) {
                double product = 0;

                for (int k = 0; k < matrix1.getColumnsCount(); k++) {
                    product += Vector.getScalarProduct(matrix1.rows[i], matrix2.getColumn(j));
                }

                result.rows[i].setComponent(j, product);
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int rowsCount = getRowsCount();

        for (int i = 0; i < rowsCount; i++) {
            sb.append(rows[i]);

            if (i < rowsCount - 1) {
                sb.append(", ");
            }
        }

        sb.append(']');
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