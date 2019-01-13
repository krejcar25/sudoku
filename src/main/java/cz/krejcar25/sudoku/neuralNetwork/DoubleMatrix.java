package cz.krejcar25.sudoku.neuralNetwork;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class DoubleMatrix {
    private final int rows;
    private final int cols;
    private double[][] values;

    public DoubleMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        this.values = new double[rows][cols];
    }

    public DoubleMatrix copy() {
        DoubleMatrix dm = new DoubleMatrix(rows, cols);
        dm.map((value, i, j) -> values[i][j]);
        return dm;
    }

    public DoubleMatrix add(DoubleMatrix dm) {
        if (rows != dm.rows || cols != dm.cols)
            throw new IllegalArgumentException("Dimensions of both matrices must be identical");
        map((value, i, j) -> value + dm.values[i][j]);
        return this;
    }

    public DoubleMatrix sub(DoubleMatrix dm) {
        if (rows != dm.rows || cols != dm.cols)
            throw new IllegalArgumentException("Dimensions of both matrices must be identical");
        map((value, i, j) -> value - dm.values[i][j]);
        return this;
    }

    public DoubleMatrix mult(double coefficient) {
        map((value, i, j) -> value * coefficient);
        return this;
    }

    public DoubleMatrix randomise(double min, double max) {
        map((value, i, j) -> (Math.random() * (max - min) + min));
        return this;
    }

    public DoubleMatrix transpose() {
        DoubleMatrix dm = new DoubleMatrix(cols, rows);
        dm.map((value, i, j) -> values[j][i]);
        return dm;
    }

    public DoubleMatrix mult(DoubleMatrix dm) {
        if (cols == dm.cols && rows == dm.rows) return map(((value, i, j) -> value * dm.values[i][j]));
        else throw new IllegalArgumentException("Dimensions of both matrices must be identical");
    }

    public DoubleMatrix matmult(DoubleMatrix dm) {
        if (cols == dm.rows) return new DoubleMatrix(rows, dm.cols).map((value, i, j) -> {
            double sum = 0;
            for (int k = 0; k < cols; k++)
                sum += values[i][k] * dm.values[k][j];
            return sum;
        });
        else throw new IllegalArgumentException(String.format("This matrix's cols (%d) must be the same as the other matrix's rows (%d)!", cols, dm.rows));
    }

    @Override
    public String toString() {
        return Arrays.deepToString(values);
    }

    public double get(int i, int j) {
        return values[i][j];
    }

    public DoubleMatrix set(int i, int j, double val) {
        values[i][j] = val;
        return this;
    }

    public DoubleMatrix map(MapFunction<Double> function) {
        for (int j = 0; j < cols; j++)
            for (int i = 0; i < rows; i++) values[i][j] = function.map(values[i][j], i, j);
        return this;
    }

    public static DoubleMatrix fromArray(@NotNull double... values) {
        return new DoubleMatrix(values.length, 1).map(((value, i, j) -> values[i]));
    }
}
