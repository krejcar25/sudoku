package cz.krejcar25.sudoku.neuralNetwork;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@NeuralNetworkLayerProperties(label = "Deep Layer")
public class DeepLayer extends NeuralNetworkLayer {
	static final long serialVersionUID = -5437988244594683918L;
	private final DoubleMatrix weights;
	private final DoubleMatrix bias;
	private final ActivationFunction activationFunction;

	public DeepLayer(int inCount, int nodes, ActivationFunction activationFunction) {
		super(inCount, nodes);

		this.weights = new DoubleMatrix(nodes, inCount).randomise(-1, 1);
		this.bias = new DoubleMatrix(nodes, 1).randomise(-1, 1);
		this.activationFunction = activationFunction;
	}

	@NotNull
	@Contract("_, _ -> new")
	@SuppressWarnings("unused")
	public static NeuralNetworkLayer create(int inCount, int nodes) {
		return new DeepLayer(inCount, nodes, ActivationFunction.SIGMOID);
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

}
