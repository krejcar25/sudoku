package cz.krejcar25.sudoku.neuralNetwork;

public interface TrainingDataSet<T extends TrainingDataPair>
{
	T[] getAllData();

	default boolean checkDataDimensions(int input, int output)
	{
		boolean ret = true;
		for (TrainingDataPair pair : getAllData())
		{
			if (pair.getRequiredInputCount() != input || pair.getRequiredOutputCount() != output) ret = false;
		}
		return !ret;
	}

	T getRandomPair();
}
