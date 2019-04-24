package cz.krejcar25.sudoku.neuralNetwork;

@SuppressWarnings("unused")
public enum ActivationFunction {
	SIGMOID((value, x, y) -> 1 / (1 + Math.exp(-value)), (value, x, y) -> value * (1 - value)),
	TANH((value, x, y) -> Math.tanh(value), (value, x, y) -> 1 - Math.pow(value, 2));

	public final MapFunction<Double> function;
	public final MapFunction<Double> derivative;

	ActivationFunction(MapFunction<Double> function, MapFunction<Double> derivation) {
		this.function = function;
		this.derivative = derivation;
	}
}
