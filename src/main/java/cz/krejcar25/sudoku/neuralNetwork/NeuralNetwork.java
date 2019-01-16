package cz.krejcar25.sudoku.neuralNetwork;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NeuralNetwork {
    private final int inputCount;
    private int outputCount;
    private int trainCycles = 0;

    private ArrayList<NeuralNetworkLayer> layers;

    private double learningRate;

    public NeuralNetwork(@NotNull NeuralNetworkLayer layer) {
        this.inputCount = layer.getInCount();
        this.outputCount = layer.getNodes();
        this.layers = new ArrayList<>();
        layers.add(layer);
        layer.setNetwork(this);

        this.learningRate = 0.1;
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
        ArrayList<DoubleMatrix> estimates = new ArrayList<>();
        DoubleMatrix estimate = in;
        for (NeuralNetworkLayer layer : layers) {
            estimate = layer.estimate(estimate);
            estimates.add(estimate);
        }

        DoubleMatrix output = estimates.get(estimates.size() - 1);
        DoubleMatrix errors = DoubleMatrix.fromArray(desiredOutput).sub(output);
        for (int i = estimates.size() - 1; i >= 0; i--) {
            errors = layers.get(i).train(estimates.get(i), i == 0 ? in : estimates.get(i - 1), errors);
        }

        trainCycles++;
    }

    public NeuralNetwork addLayer(NeuralNetworkLayer layer) {
        NeuralNetworkLayer last = layers.get(layers.size() - 1);
        if (last.getNodes() == layer.getInCount()) {
            layers.add(layer);
            outputCount = layer.getNodes();
            layer.setNetwork(this);
            return this;
        } else
            throw new IllegalArgumentException(String.format("Input node count of the added layer (%d) must match the output node count of the last added layer (%d).", layer.getInCount(), last.getNodes()));
    }

    public int getInputCount() {
        return inputCount;
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

    public int getTrainCycles() {
        return trainCycles;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }
}
