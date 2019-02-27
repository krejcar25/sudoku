package cz.krejcar25.sudoku.game;

import cz.krejcar25.sudoku.SudokuApplet;
import cz.krejcar25.sudoku.Timer;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class BaseGenerator implements Runnable {
    private static final Random random = new Random();
    private ArrayList<ArrayList<ArrayList<Integer>>> numbers;
    private boolean used;
    GridCore core;
    Timer timer;
    private int clueCount;
    private volatile boolean shouldRun = true;

    public BaseGenerator(GridCore core) {
        this.core = core;
        numbers = new ArrayList<>();
        used = false;
        timer = new Timer("GridGenerator");
    }

    private void replenish(int x, int y) {
        ArrayList<Integer> list = numbers.get(x).get(y);
        list.clear();
        for (int i = 0; i < core.ncr; i++) {
            list.add(i);
        }
    }

    public void generate(int clueCount, boolean async) {
        this.clueCount = clueCount;
        Thread thread = new Thread(this);
        if (async) thread.start();
        else thread.run();
    }

    void stopGeneration() {
        shouldRun = false;
    }

    @Override
    public void run() {
        if (used) return;
        used = true;

        timer.start();

        int gx = 0;
        int gy = 0;

        for (int x = 0; x < core.ncr; x++) {
            numbers.add(new ArrayList<>());
            for (int y = 0; y < core.ncr; y++) {
                numbers.get(x).add(new ArrayList<>());
                replenish(x, y);
            }
        }

        do {
            if (available(gx, gy)) {
                int n = random.nextInt(numbers.get(gx).get(gy).size());
                try {
                    if (core.canPlaceNumber(numbers.get(gx).get(gy).get(n), gx, gy, -1)) {
                        core.set(gx, gy, numbers.get(gx).get(gy).get(n));
                        gx++;
                    } else {
                        numbers.get(gx).get(gy).remove(n);
                    }
                } catch (IndexOutOfBoundsException ex) {
                    if (core.getOwner() != null) core.getOwner().view.removeFromViewStack();
                }
            } else {
                replenish(gx, gy);
                core.set(gx, gy, -1);
                gx--;
            }

            if (gx < 0) {
                gx += core.ncr;
                gy--;
            } else if (gx >= core.ncr) {
                gx -= core.ncr;
                gy++;
            }
        } while (gy >= 0 && gy < core.ncr && shouldRun);

        core.setAsSolved();

        Point[] cells = new Point[PApplet.ceil((core.ncr * core.ncr) / 2f)];

        for (int i = 0; i < cells.length; i++) cells[i] = new Point(i % core.ncr, i / core.ncr);

        SudokuApplet.shuffle(cells);
        int removed = 0;
        int remStart = core.ncr * core.ncr;

        for (int i = 0; removed < (remStart - clueCount - 1) && i < cells.length && shouldRun; i++) {
            Point v = cells[i];
            int x = v.x;
            int y = v.y;
            int ox = core.ncr - v.x - 1;
            int oy = core.ncr - v.y - 1;

            SudokuApplet.println("Trying to remove clue " + i + ", " + removed + " already removed, " + (remStart - clueCount - removed) + " remaining.");

            if (core.get(x, y) > -1) {
                int cell = core.get(x, y);
                int mirror = core.get(ox, oy);
                core.set(x, y, -1);
                core.set(ox, oy, -1);
	            //core.lockAsBase(false, false);
                int solutions = new BaseSolver(core).countSolutions();
                if (solutions > 1) {
                    core.set(x, y, cell);
                    core.set(ox, oy, mirror);
                    SudokuApplet.println("Actually I had to put it back, found multiple solutions...");
                } else removed = removed + 2;
            }
        }

        if (!shouldRun) return;

        core.select(-1, -1);

        PApplet.println("removal target: " + (((core.ncr * core.ncr) - 1) - clueCount) + ", removed: " + removed);
        core.lockAsBase(true, true);
        timer.stop();
        PApplet.println("Generation finished in " + timer.getElapsedTimeSecs() + " seconds (" + timer.getElapsedTime() + " milliseconds, to be precise)");
        if (core.getOwner() != null) core.getOwner().getGameClock().start();
    }

    private boolean available(int x, int y) {
        return numbers.get(x).get(y).size() > 0;
    }
}
