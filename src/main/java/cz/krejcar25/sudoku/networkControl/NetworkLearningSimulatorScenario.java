package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.neuralNetwork.TrainingDataPair;
import cz.krejcar25.sudoku.neuralNetwork.TrainingDataSet;

import java.util.ArrayList;

public enum NetworkLearningSimulatorScenario {
    OR(new double[][]{
            new double[]{0d, 0d},
            new double[]{1d, 0d},
            new double[]{0d, 1d},
            new double[]{1d, 1d}
    }, new double[][]{
            new double[]{0d, 0d, 0d},
            new double[]{1d, 1d, 1d},
            new double[]{1d, 1d, 1d},
            new double[]{1d, 1d, 1d}
    }),
    XOR(new double[][]{
            new double[]{0d, 0d},
            new double[]{1d, 0d},
            new double[]{0d, 1d},
            new double[]{1d, 1d}
    }, new double[][]{
            new double[]{0d, 0d, 0d},
            new double[]{1d, 1d, 1d},
            new double[]{1d, 1d, 1d},
            new double[]{0d, 0d, 0d}
    }),
    RGB(new double[][]{
            new double[]{0d, 0d},
            new double[]{1d, 0d},
            new double[]{0d, 1d},
            new double[]{1d, 1d}
    }, new double[][]{
            new double[]{1d, 0d, 0d},
            new double[]{0d, 1d, 0d},
            new double[]{1d, 1d, 0d},
            new double[]{0d, 0d, 1d}
    });

    private TrainingDataSet2_2 trainingData;

    NetworkLearningSimulatorScenario(double[][] inputs, double[][] outputs) {
        this.trainingData = new TrainingDataSet2_2(inputs, outputs);
    }

    public TrainingDataSet getTrainingData() {
        return trainingData;
    }

    private class TrainingDataSet2_2 implements TrainingDataSet {
        private ArrayList<TrainingDataPair2_2> trainingData;

        public TrainingDataSet2_2(double[][] inputs, double[][] outputs) {
            this.trainingData = new ArrayList<>();
            for (int i = 0; i < inputs.length && i < outputs.length; i++) {
                this.trainingData.add(new TrainingDataPair2_2(inputs[i], outputs[i]));
            }
        }

        @Override
        public TrainingDataPair[] getAllData() {
            return trainingData.toArray(new TrainingDataPair[trainingData.size()]);
        }

        @Override
        public TrainingDataPair getRandomPair() {
            return trainingData.get((int) Math.floor(Math.random() * trainingData.size()));
        }

        @Override
        public int getDataCount() {
            return trainingData.size();
        }

        private class TrainingDataPair2_2 implements TrainingDataPair {
            private double[] input;
            private double[] output;

            public TrainingDataPair2_2(double[] input, double[] output) {
                this.input = input;
                this.output = output;
            }

            @Override
            public int getRequiredInputCount() {
                return input.length;
            }

            @Override
            public int getRequiredOutputCount() {
                return output.length;
            }

            @Override
            public double[] getInput() {
                return input;
            }

            @Override
            public double[] getDesiredOutput() {
                return output;
            }
        }
    }
}
