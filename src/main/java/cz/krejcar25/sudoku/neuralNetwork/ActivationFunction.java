package cz.krejcar25.sudoku.neuralNetwork;

public interface ActivationFunction {
    double func(double value, int x, int y);

    double derivative(double value, int x, int y);

    ActivationFunction SIGMOID = new ActivationFunction() {
        @Override
        public double func(double value, int x, int y) {
            return 1 / (1 + Math.exp(-value));
        }

        @Override
        public double derivative(double value, int x, int y) {
            return func(value, x, y) * (1 - func(value, x, y));
        }
    };

    ActivationFunction TANH = new ActivationFunction() {
        @Override
        public double func(double value, int x, int y) {
            return Math.tanh(value);
        }

        @Override
        /*public double derivative(double value, int x, int y) {
            return 1 - Math.pow(Math.tanh(value), 2);
        }*/
        public double derivative(double value, int x, int y) {
            return 1 - Math.pow(value, 2);
        }
    };
}
