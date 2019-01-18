package cz.krejcar25.sudoku.neuralNetwork;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Arrays;

public class DoubleMatrix implements Serializable {
    private final int rows;
    private final int cols;
    private double[][] values;

    public DoubleMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        this.values = new double[rows][cols];
    }

    @Contract(" -> new")
    public DoubleMatrix copy() {
        DoubleMatrix dm = new DoubleMatrix(rows, cols);
        dm.map((value, i, j) -> values[i][j]);
        return dm;
    }

    @Contract("null -> fail; !null -> this")
    public DoubleMatrix add(DoubleMatrix dm) {
        if (rows != dm.rows || cols != dm.cols)
            throw new IllegalArgumentException("Dimensions of both matrices must be identical");
        map((value, i, j) -> value + dm.values[i][j]);
        return this;
    }

    @Contract("null -> fail; !null -> this")
    public DoubleMatrix sub(DoubleMatrix dm) {
        if (rows != dm.rows || cols != dm.cols)
            throw new IllegalArgumentException("Dimensions of both matrices must be identical");
        map((value, i, j) -> value - dm.values[i][j]);
        return this;
    }

    @Contract("_ -> this")
    public DoubleMatrix mult(double coefficient) {
        map((value, i, j) -> value * coefficient);
        return this;
    }

    @Contract("_, _ -> this")
    public DoubleMatrix randomise(double min, double max) {
        map((value, i, j) -> (Math.random() * (max - min) + min));
        return this;
    }

    @Contract(" -> new")
    public DoubleMatrix transpose() {
        DoubleMatrix dm = new DoubleMatrix(cols, rows);
        dm.map((value, i, j) -> values[j][i]);
        return dm;
    }

    @Contract("null -> fail; !null -> this")
    public DoubleMatrix mult(DoubleMatrix dm) {
        if (cols == dm.cols && rows == dm.rows) return map(((value, i, j) -> value * dm.values[i][j]));
        else throw new IllegalArgumentException("Dimensions of both matrices must be identical");
    }

    @Contract("null -> fail; !null -> new")
    public DoubleMatrix matmult(DoubleMatrix dm) {
        if (cols == dm.rows) return new DoubleMatrix(rows, dm.cols).map((value, i, j) -> {
            double sum = 0;
            for (int k = 0; k < cols; k++)
                sum += values[i][k] * dm.values[k][j];
            return sum;
        });
        else
            throw new IllegalArgumentException(String.format("This matrix's cols (%d) must be the same as the other matrix's rows (%d)!", cols, dm.rows));
    }

    @Override
    @Contract(" -> new")
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

    @Contract("null -> fail; !null -> this")
    public DoubleMatrix map(MapFunction<Double> function) {
        for (int j = 0; j < cols; j++)
            for (int i = 0; i < rows; i++) values[i][j] = function.map(values[i][j], i, j);
        return this;
    }

    @Contract("null -> fail; !null -> new")
    public static DoubleMatrix fromArray(@NotNull double... values) {
        return new DoubleMatrix(values.length, 1).map(((value, i, j) -> values[i]));
    }
}
