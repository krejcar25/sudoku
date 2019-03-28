package cz.krejcar25.sudoku.neuralNetwork;

public interface TrainingDataPair {
	int getRequiredInputCount();

	int getRequiredOutputCount();

	double[] getInput();

	double[] getDesiredOutput();
}
