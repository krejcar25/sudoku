package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.game.GridCore;
import cz.krejcar25.sudoku.neuralNetwork.TrainingDataSet;

import java.util.ArrayList;

public class GridCoreTrainingDataSet implements TrainingDataSet<GridCore>
{
    private ArrayList<GridCore> cores;

    public GridCoreTrainingDataSet() {
        cores = new ArrayList<>();
    }

    public void addCore(String coreString) {
        addCore(GridCore.fromGridString(coreString));
    }

    public void addCore(GridCore core) {
        cores.add(core);
    }

    @Override
    public GridCore[] getAllData()
    {
	    return cores.toArray(new GridCore[cores.size()]);
    }

    @Override
    public GridCore getRandomPair()
    {
        return cores.get((int) Math.floor(Math.random() * cores.size()));
    }

    @Override
    public int getDataCount() {
        return cores.size();
    }
}
