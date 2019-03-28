package cz.krejcar25.sudoku.neuralNetwork;

import org.jetbrains.annotations.Contract;

import java.io.Serializable;

public abstract class NeuralNetworkLayer implements Serializable {
	static final long serialVersionUID = 108732142133458883L;
	private final int inCount;
	private final int nodes;
	private NeuralNetwork network;


	NeuralNetworkLayer(int inCount, int nodes) {
		this.inCount = inCount;
		this.nodes = nodes;
	}

	@SuppressWarnings("unused")
	public static NeuralNetworkLayer create(int inCount, int nodes) {
		throw new IllegalStateException("The create function was not created for this subclass of NeuralNetworkLayer");
	}

	public abstract DoubleMatrix estimate(DoubleMatrix input);

	public abstract DoubleMatrix train(DoubleMatrix prediction, DoubleMatrix previousPrediction, DoubleMatrix currentErrors);

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
}
