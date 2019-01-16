package cz.krejcar25.sudoku.networkControl;

import javafx.util.Pair;

import java.util.ArrayList;

public enum NetworkLearningSimulatorScenario {
    OR(new double[][]{
            new double[]{0d, 0d},
            new double[]{1d, 0d},
            new double[]{0d, 1d},
            new double[]{1d, 1d}
    }, new double[][]{
            new double[]{0d},
            new double[]{1d},
            new double[]{1d},
            new double[]{1d}
    }),
    XOR(new double[][]{
            new double[]{0d, 0d},
            new double[]{1d, 0d},
            new double[]{0d, 1d},
            new double[]{1d, 1d}
    }, new double[][]{
            new double[]{0d},
            new double[]{1d},
            new double[]{1d},
            new double[]{0d}
    });

    private ArrayList<Pair<double[], double[]>> trainingData;

    NetworkLearningSimulatorScenario(double[][] inputs, double[][] outputs) {
        this.trainingData = new ArrayList<>();
        for (int i = 0; i < inputs.length && i < outputs.length; i++) {
            this.trainingData.add(new Pair<>(inputs[i], outputs[i]));
        }
    }

    public ArrayList<Pair<double[], double[]>> getTrainingData() {
        return trainingData;
    }
}
