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

	private final TrainingDataSet2_2 trainingData;

	NetworkLearningSimulatorScenario(double[][] inputs, double[][] outputs) {
		this.trainingData = new TrainingDataSet2_2(inputs, outputs);
	}

	public TrainingDataSet getTrainingData() {
		return trainingData;
	}

	private class TrainingDataSet2_2 implements TrainingDataSet {
		private final ArrayList<TrainingDataPair2_2> trainingData;

		TrainingDataSet2_2(double[][] inputs, double[][] outputs) {
			this.trainingData = new ArrayList<>();
			for (int i = 0; i < inputs.length && i < outputs.length; i++) {
				this.trainingData.add(new TrainingDataPair2_2(inputs[i], outputs[i]));
			}
		}

		@Override
		public TrainingDataPair[] getAllData() {
			return trainingData.toArray(new TrainingDataPair[0]);
		}

		@Override
		public TrainingDataPair getRandomPair() {
			return trainingData.get((int) Math.floor(Math.random() * trainingData.size()));
		}

		private class TrainingDataPair2_2 implements TrainingDataPair {
			private final double[] input;
			private final double[] output;

			TrainingDataPair2_2(double[] input, double[] output) {
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
