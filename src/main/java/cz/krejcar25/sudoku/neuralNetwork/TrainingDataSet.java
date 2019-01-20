package cz.krejcar25.sudoku.neuralNetwork;

public interface TrainingDataSet {
    TrainingDataPair[] getAllData();

    default boolean checkDataDimensions(int input, int output) {
        for (TrainingDataPair pair : getAllData()) {
            if (pair.getRequiredInputCount() != input || pair.getRequiredOutputCount() != output) return false;
        }
        return true;
    }

    TrainingDataPair getRandomPair();

    int getDataCount();
}
