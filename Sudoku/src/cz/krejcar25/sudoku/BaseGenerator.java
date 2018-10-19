package cz.krejcar25.sudoku;

import java.util.ArrayList;

public abstract class BaseGenerator {
    IGameView parent;
    int targetCount;
    ArrayList<ArrayList<ArrayList<Integer>>> numbers;
    StopWatch timer;
    protected boolean used = false;

    BaseGenerator(IGameView parent, int targetCount) {
        this.parent = parent;
        this.targetCount = targetCount;
        timer = new StopWatch("GridGenerator");
        numbers = new ArrayList<>();
    }

    abstract BaseGrid generate();
}
