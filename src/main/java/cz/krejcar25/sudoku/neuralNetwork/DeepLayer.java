package cz.krejcar25.sudoku.neuralNetwork;

@NeuralNetworkLayerProperties(label = "Deep Layer")
public class DeepLayer extends NeuralNetworkLayer {
	private DoubleMatrix weights;
	private DoubleMatrix bias;
	private ActivationFunction activationFunction;

	public DeepLayer(int inCount, int nodes, ActivationFunction activationFunction) {
		super(inCount, nodes);

		this.weights = new DoubleMatrix(nodes, inCount).randomise(-1, 1);
		this.bias = new DoubleMatrix(nodes, 1).randomise(-1, 1);
		this.activationFunction = activationFunction;
	}

	public DoubleMatrix estimate(DoubleMatrix input) {
		return weights.matmult(input).add(bias).map(activationFunction::func);
	}

	public DoubleMatrix train(DoubleMatrix prediction, DoubleMatrix previousPrediction, DoubleMatrix currentErrors) {
		NeuralNetwork network = getNetwork();

		DoubleMatrix gradients = prediction
				.map(activationFunction::derivative)
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

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

	public static NeuralNetworkLayer create(int inCount, int nodes)
	{
		return new DeepLayer(inCount, nodes, ActivationFunction.SIGMOID);
	}
}
