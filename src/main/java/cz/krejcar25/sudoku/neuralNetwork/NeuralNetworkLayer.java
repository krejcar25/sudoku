package cz.krejcar25.sudoku.neuralNetwork;

import java.io.Serializable;

public abstract class NeuralNetworkLayer implements Serializable {
    private NeuralNetwork network;
    private final int inCount;
    private final int nodes;


    public NeuralNetworkLayer(int inCount, int nodes) {
        this.inCount = inCount;
        this.nodes = nodes;
    }

    public abstract DoubleMatrix estimate(DoubleMatrix input);

    public abstract DoubleMatrix train(DoubleMatrix prediction, DoubleMatrix previousPrediction, DoubleMatrix currentErrors);

    public final NeuralNetwork getNetwork() {
        return network;
    }

    public final void setNetwork(NeuralNetwork network) {
        this.network = network;
    }

    public final int getInCount() {
        return inCount;
    }

    public final int getNodes() {
        return nodes;
    }

	public static NeuralNetworkLayer create(int inCount, int nodes)
	{
		throw new IllegalStateException("The create function was not created for this subclass of NeuralNetworkLayer");
	}
}
