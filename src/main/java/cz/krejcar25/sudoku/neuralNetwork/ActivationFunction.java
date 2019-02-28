package cz.krejcar25.sudoku.neuralNetwork;

import java.io.Serializable;

public interface ActivationFunction extends Serializable
{
    double func(double value, int x, int y);

    double derivative(double value, int x, int y);

    ActivationFunction SIGMOID = new ActivationFunction() {
        @Override
        public double func(double value, int x, int y) {
            return 1 / (1 + Math.exp(-value));
        }

        @Override
        public double derivative(double value, int x, int y) {
            return value * (1 - value);
        }
    };

    ActivationFunction TANH = new ActivationFunction() {
        @Override
        public double func(double value, int x, int y) {
            return Math.tanh(value);
        }

        @Override
        public double derivative(double value, int x, int y) {
            return 1 - Math.pow(value, 2);
        }
    };
}
