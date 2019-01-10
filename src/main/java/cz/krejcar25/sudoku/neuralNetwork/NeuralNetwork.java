package cz.krejcar25.sudoku.neuralNetwork;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NeuralNetwork {
    private final int inputCount;
    private final int[] hiddenCounts;
    private final int outputCount;

    private ArrayList<NeuralNetworkLayer> layers;

    private double learningRate;

    private ActivationFunction activationFunction;

    public NeuralNetwork(int inputCount, int outputCount, @NotNull int... hiddenCounts) {
        this.inputCount = inputCount;
        this.outputCount = outputCount;
        this.hiddenCounts = hiddenCounts;
        this.layers = new ArrayList<>();

        this.layers.add(new NeuralNetworkLayer(this, inputCount, hiddenCounts[0]));
        for (int i = 1; i < hiddenCounts.length; i++)
            layers.add(new NeuralNetworkLayer(this, hiddenCounts[i - 1], hiddenCounts[i]));
        this.layers.add(new NeuralNetworkLayer(this, hiddenCounts[hiddenCounts.length - 1], outputCount));

        this.learningRate = 0.1;

        this.activationFunction = ActivationFunction.SIGMOID;
    }

    public double[] estimate(double[] input) {
        DoubleMatrix intermediate = DoubleMatrix.fromArray(input);
        for (NeuralNetworkLayer layer : layers) {
            intermediate = layer.estimate(intermediate);
        }
        double[] output = new double[outputCount];
        for (int i = 0; i < outputCount; i++) {
            output[i] = intermediate.get(i, 0);
        }
        return output;
    }

    public void train(double[] input, double[] desiredOutput) {
        DoubleMatrix in = DoubleMatrix.fromArray(input);
        ArrayList<DoubleMatrix> intermediates = new ArrayList<>();
        DoubleMatrix intermediate = in;
        for (NeuralNetworkLayer layer : layers) {
            intermediate = layer.estimate(intermediate);
            intermediates.add(intermediate.copy());
        }

        DoubleMatrix errors = DoubleMatrix.fromArray(desiredOutput).sub(intermediate);
        for (int i = intermediates.size() - 1; i >= 0; i--) {
            errors = layers.get(i).train(intermediates.get(i), i == 0 ? in : intermediates.get(i - 1), errors);
        }
    }

    public int getInputCount() {
        return inputCount;
    }

    public int[] getHiddenCounts() {
        return hiddenCounts;
    }

    public int getOutputCount() {
        return outputCount;
    }

    public ArrayList<NeuralNetworkLayer> getLayers() {
        return layers;
    }
    public double getLearningRate() {
        return learningRate;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }
}
