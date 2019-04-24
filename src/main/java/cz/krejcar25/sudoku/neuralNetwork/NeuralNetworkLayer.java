package cz.krejcar25.sudoku.neuralNetwork;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class NeuralNetworkLayer {
	private final int inCount;
	private final int nodes;
	private final DoubleMatrix weights;
	private final DoubleMatrix bias;
	private final ActivationFunction activationFunction;
	@JsonIgnore
	private NeuralNetwork network;

	@JsonCreator
	private NeuralNetworkLayer() {
		inCount = -1;
		nodes = -1;
		weights = new DoubleMatrix(1, 1);
		bias = new DoubleMatrix(1, 1);
		activationFunction = ActivationFunction.SIGMOID;
	}

	public NeuralNetworkLayer(int inCount, int nodes, ActivationFunction activationFunction) {
		this.inCount = inCount;
		this.nodes = nodes;
		this.weights = new DoubleMatrix(nodes, inCount).randomise(-1, 1);
		this.bias = new DoubleMatrix(nodes, 1).randomise(-1, 1);
		this.activationFunction = activationFunction;
	}

	@NotNull
	@Contract("_, _ -> new")
	@SuppressWarnings("unused")
	public static NeuralNetworkLayer create(int inCount, int nodes) {
		return new NeuralNetworkLayer(inCount, nodes, ActivationFunction.SIGMOID);
	}

	@Contract(pure = true)
	final NeuralNetwork getNetwork() {
		return network;
	}

	public final void setNetwork(NeuralNetwork network) {
		this.network = network;
	}

	@Contract(pure = true)
	public final int getInCount() {
		return inCount;
	}

	@Contract(pure = true)
	public final int getNodes() {
		return nodes;
	}

	public DoubleMatrix estimate(DoubleMatrix input) {
		DoubleMatrix multiplied = weights.matmult(input);
		DoubleMatrix added = multiplied.add(bias);
		DoubleMatrix mapped = added.map(activationFunction.function);
		return mapped;
	}

	public DoubleMatrix train(DoubleMatrix prediction, DoubleMatrix previousPrediction, DoubleMatrix currentErrors) {
		DoubleMatrix gradients = prediction
				.map(activationFunction.derivative)
				.mult(currentErrors)
				.mult(network.getLearningRate());
		DoubleMatrix pt = previousPrediction.transpose();
		DoubleMatrix deltas = gradients.matmult(pt);
		weights.add(deltas);
		bias.add(gradients);

		return weights.transpose().matmult(currentErrors);
	}

	public DoubleMatrix getWeights() {
		return weights;
	}

	public DoubleMatrix getBias() {
		return bias;
	}
}
