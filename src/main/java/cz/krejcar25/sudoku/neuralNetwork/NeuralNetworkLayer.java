package cz.krejcar25.sudoku.neuralNetwork;

public class NeuralNetworkLayer {
    private final NeuralNetwork network;
    private final int inCount;
    private final int nodes;
    private DoubleMatrix weights;
    private DoubleMatrix bias;


    public NeuralNetworkLayer(NeuralNetwork network, int inCount, int nodes) {
        this.network = network;
        this.inCount = inCount;
        this.nodes = nodes;

        this.weights = new DoubleMatrix(nodes, inCount).randomise(-1,1);
        this.bias = new DoubleMatrix(nodes, 1).randomise(-1,1);
    }

    public DoubleMatrix estimate(DoubleMatrix input) {
        return weights.mult(input).add(bias).map(network.getActivationFunction()::func);
    }

    public DoubleMatrix train(DoubleMatrix prediction, DoubleMatrix previousPrediction, DoubleMatrix currentErrors) {
        DoubleMatrix gradients = prediction.copy()
                .map(network.getActivationFunction()::derivative)
                .mult(currentErrors)
                .mult(network.getLearningRate());
        DoubleMatrix pt = previousPrediction.transpose();
        DoubleMatrix deltas = pt.mult(gradients);
        weights.add(deltas);
        bias.add(gradients);

        return weights.transpose().mult(currentErrors);
    }

    public NeuralNetwork getNetwork() {
        return network;
    }

    public int getInCount() {
        return inCount;
    }

    public int getNodes() {
        return nodes;
    }

    public DoubleMatrix getWeights() {
        return weights;
    }

    public DoubleMatrix getBias() {
        return bias;
    }
}
