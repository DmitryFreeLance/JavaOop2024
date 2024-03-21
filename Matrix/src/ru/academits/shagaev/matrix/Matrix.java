package ru.academits.shagaev.matrix;

import ru.academits.shagaev.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] rows;

    public Matrix(int columnsCount, int rowsCount) {
        if (columnsCount <= 0) {
            throw new IllegalArgumentException("Количество столбцов в матрице должно быть" +
                    " больше нуля, переданное количество столбцов: " + columnsCount);
        }

        if (rowsCount <= 0) {
            throw new IllegalArgumentException("Количество строк в матрице должно быть" +
                    " больше нуля, переданное количество строк: " + rowsCount);
        }

        rows = new Vector[rowsCount];

        for (int i = 0; i < columnsCount; i++) {
            rows[i] = new Vector(rowsCount);
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

        int ColumnsCount = 0;

        for (double[] row : values) {
            if (row.length > ColumnsCount) {
                ColumnsCount = row.length;
            }
        }

        if (ColumnsCount == 0) {
            throw new IllegalArgumentException("Количество столбцов не может быть равно нулю");
        }

        rows = new Vector[values.length];

        for (int i = 0; i < values.length; i++) {
            rows[i] = new Vector(ColumnsCount, values[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors.length == 0) {
            throw new IllegalArgumentException("Массив векторов не может быть пустым");
        }

        int maxVectorDimension = vectors[0].getDimension();

        for (Vector vector : vectors) {
            int dimension = vector.getDimension();

            if (dimension > maxVectorDimension) {
                maxVectorDimension = dimension;
            }
        }

        rows = new Vector[vectors.length];

        for (int i = 0; i < vectors.length; i++) {
            rows[i] = new Vector(maxVectorDimension);
            rows[i].add(vectors[i]);
        }
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
            throw new IndexOutOfBoundsException("Индекс строки выходит за границы матрицы. Допустимые значения: от 0 до " + (rows.length - 1) + ", получено: " + index);
        }

        Vector column = new Vector(rows.length);

        for (int i = 0; i < rows.length; i++) {
            column.setComponent(i, rows[i].getComponent(index));
        }

        return column;
    }

    public void transpose() {
        Vector[] transposedRows = new Vector[rows[0].getDimension()];

        for (int i = 0; i < transposedRows.length; i++) {
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

    private Matrix getMinor(int columnToRemoveIndex) {
        int size = rows.length - 1;
        Matrix minor = new Matrix(size, size);

        int destinationRowIndex = 0;

        for (int i = 1; i < rows.length; i++) {
            Vector currentRow = rows[i];
            int destinationColumnIndex = 0;

            for (int j = 0; j < currentRow.getDimension(); j++) {
                if (j == columnToRemoveIndex) {
                    continue;
                }

                minor.rows[destinationRowIndex].setComponent(destinationColumnIndex, currentRow.getComponent(j));
                destinationColumnIndex++;
            }

            destinationRowIndex++;
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
        checkMatricesSizesEquality(matrix, "сложения");

        for (int i = 0; i < rows.length; i++) {
            rows[i].add(matrix.rows[i]);
        }
    }

    public void subtract(Matrix matrix) {
        checkMatricesSizesEquality(matrix, "вычитания");

        for (int i = 0; i < rows.length; i++) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        matrix1.checkMatricesSizesEquality(matrix2, "сложения");

        Matrix result = new Matrix(matrix1);
        result.add(matrix2);
        return result;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        matrix1.checkMatricesSizesEquality(matrix2, "вычитания");

        Matrix result = new Matrix(matrix1);
        result.subtract(matrix2);
        return result;
    }

    private void checkMatricesSizesEquality(Matrix matrix, String operation) {
        if (rows.length != matrix.rows.length || rows[0].getDimension() != matrix.rows[0].getDimension()) {
            throw new IllegalArgumentException("Размеры матриц должны совпадать для " + operation + ": " +
                    "Размеры первой матрицы: " + rows.length + "x" + getColumnsCount() + ", Размеры второй матрицы: " +
                    matrix.rows.length + "x" + matrix.getColumnsCount());
        }
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.rows.length) {
            throw new IllegalArgumentException("Невозможно выполнить умножение матриц: количество столбцов первой матрицы (" +
                    matrix1.getColumnsCount() + ") не совпадает с количеством строк второй матрицы (" +
                    matrix2.rows.length + ")");
        }

        int resultRowsCount = matrix1.rows.length;
        int resultColumnsCount = matrix2.getColumnsCount();
        Matrix result = new Matrix(resultColumnsCount, resultRowsCount);

        for (int i = 0; i < resultRowsCount; i++) {
            for (int j = 0; j < resultColumnsCount; j++) {
                double product = Vector.getScalarProduct(matrix1.rows[i], matrix2.getColumn(j)); // Используем метод getScalarProduct()

                result.rows[i].setComponent(j, product);
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');

        int rowsCount = rows.length;

        if (rowsCount > 0) {
            sb.append(rows[0]);
            for (int i = 1; i < rowsCount; i++) {
                sb.append(", ").append(rows[i]);
            }
        }

        sb.append('}');
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