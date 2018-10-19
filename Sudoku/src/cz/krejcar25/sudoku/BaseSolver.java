package cz.krejcar25.sudoku;

import java.util.ArrayList;
import processing.core.*;

public abstract class BaseSolver {
    protected BaseGrid game;
    protected boolean used = false;
    protected StopWatch timer;
    ArrayList<ArrayList<ArrayList<Integer>>> numbers;

    int count;
    int x;
    int y;
    boolean tryNext;
    boolean cycleAllowed;
    protected boolean wentBack;

    public BaseSolver(BaseGrid game) {
        this.game = game.clone();
        this.game.lockAsBase(false,false);
        timer = new StopWatch("GridSolver");
        numbers = new ArrayList<>();
        tryNext = true;
        cycleAllowed = false;
        wentBack = false;
    }

    public abstract int countSolutions();
    public abstract boolean prepare();
    public abstract void cycle();
    public abstract int finish();
}
