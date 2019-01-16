package cz.krejcar25.sudoku.neuralNetwork;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class NetworkTrainRunnable implements Runnable {
    private volatile boolean runAllowed;
    private volatile boolean shouldPause;
    private volatile boolean isPaused;
    private volatile boolean running;
    private NeuralNetwork network;
    private Thread thread;

    ArrayList<Pair<double[], double[]>> trainingData;

    public NetworkTrainRunnable(NeuralNetwork network, ArrayList<Pair<double[], double[]>> trainingData) {
        this.runAllowed = false;
        this.shouldPause = false;
        this.isPaused = false;
        this.running = false;
        this.network = network;
        this.trainingData = trainingData;

        for (int i = 0; i < trainingData.size(); i++) {
            Pair<double[], double[]> pair = trainingData.get(i);
            if (pair.getKey().length != network.getInputCount() || pair.getValue().length != network.getOutputCount())
                throw new IllegalArgumentException(
                        String.format("The input data's length (%d) and output data's length (%d) at index %d must match the network input (%d) and output (%d) node count respectively.",
                                pair.getKey().length,
                                pair.getValue().length,
                                i,
                                network.getInputCount(),
                                network.getOutputCount()
                        )
                );
        }

        this.thread = new Thread(this);
        this.thread.setName("NeuralNetwork-TrainThread");
    }

    @Override
    public void run() {
        this.runAllowed = true;
        this.shouldPause = false;
        this.isPaused = false;
        this.running = true;

        while (runAllowed) {
            if (shouldPause) {
                isPaused = true;
                running = false;
                while (shouldPause) Thread.yield();
                isPaused = false;
                running = true;
            } else {
                int randomElementIndex = ThreadLocalRandom.current().nextInt(trainingData.size()) % trainingData.size();
                Pair<double[], double[]> train = trainingData.get(randomElementIndex);
                network.train(train.getKey(), train.getValue());
            }
        }

        this.isPaused = false;
        this.running = false;
    }

    public void startTrain() {
        thread.start();
    }

    public boolean isRunAllowed() {
        return runAllowed;
    }

    public boolean shouldPause() {
        return shouldPause;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean pause() {
        boolean pausedBefore = isPaused;
        if (runAllowed) shouldPause = true;
        else return false;
        while (!isPaused) Thread.yield();
        return !pausedBefore;
    }

    public boolean resume() {
        boolean pausedBefore = isPaused;
        if (runAllowed) shouldPause = false;
        else return false;
        return pausedBefore;
    }

    public void stop() {
        this.runAllowed = false;
        this.shouldPause = false;
        while (running) ;
    }

    public void stopAsync() {
        this.runAllowed = false;
        this.shouldPause = false;
    }
}
