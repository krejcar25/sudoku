package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.game.GridCore;
import cz.krejcar25.sudoku.neuralNetwork.TrainingDataSet;

import java.util.ArrayList;

public class GridCoreTrainingDataSet implements TrainingDataSet<GridCore> {
	private final ArrayList<GridCore> cores;

	public GridCoreTrainingDataSet() {
		cores = new ArrayList<>();
	}

	public void addCore(String coreString) {
		addCore(GridCore.fromGridString(coreString));
	}

	private void addCore(GridCore core) {
		cores.add(core);
	}

	@Override
	public GridCore[] getAllData() {
		return cores.toArray(new GridCore[0]);
	}

	@Override
	public GridCore getRandomPair() {
		return cores.get((int) Math.floor(Math.random() * cores.size()));
	}

}
