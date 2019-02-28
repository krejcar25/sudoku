package cz.krejcar25.sudoku.neuralNetwork;

public class NetworkTest {
    public static void main(String[] args) {
        NeuralNetwork network = new NeuralNetwork(new DeepLayer(1, 1, ActivationFunction.SIGMOID));
        network.addLayer(new DeepLayer(1,1,ActivationFunction.SIGMOID));

        while (true) {
            network.train(new double[]{1d}, new double[]{0d});
            System.out.println(network.estimate(new double[]{1d})[0]);
        }
    }
}
