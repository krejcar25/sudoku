package cz.krejcar25.sudoku.game;

import cz.krejcar25.sudoku.ui.Clock;
import cz.krejcar25.sudoku.SudokuApplet;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;

class BaseGenerator implements Runnable {
    GameView gameView;
    private ArrayList<ArrayList<ArrayList<Integer>>> numbers;
    public final Clock timer;
    private boolean used;
    BaseGrid game;
    private int clueCount;
    private volatile boolean shouldRun = true;

    BaseGenerator(BaseGrid game) {
        this.gameView = game.view;
        this.game = game;
        timer = new Clock(gameView.getApplet(), 0, 0, "GridGenerator");
        numbers = new ArrayList<>();
        used = false;
    }

    private void replenish(int x, int y) {
        ArrayList<Integer> list = numbers.get(x).get(y);
        list.clear();
        for (int i = 0; i < game.numbers(); i++) {
            list.add(i);
        }
    }

    void generate(int clueCount, boolean async) {
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
        gameView.getApplet().frameRate(3);
        timer.start();
        if (used) return;
        used = true;

        int gx = 0;
        int gy = 0;
        int cols = game.cols();
        int rows = game.rows();

        for (int x = 0; x < cols; x++) {
            numbers.add(new ArrayList<>());
            for (int y = 0; y < rows; y++) {
                numbers.get(x).add(new ArrayList<>());
                replenish(x, y);
            }
        }

        do {
            if (available(gx, gy)) {
                int n = PApplet.floor(gameView.getApplet().random(numbers.get(gx).get(gy).size()));
                if (game.canPlaceNumber(numbers.get(gx).get(gy).get(n), gx, gy, -1)) {
                    game.game[gx][gy] = numbers.get(gx).get(gy).get(n);
                    gx++;
                } else {
                    numbers.get(gx).get(gy).remove(n);
                }
            } else {
                replenish(gx, gy);
                game.game[gx][gy] = -1;
                gx--;
            }

            if (gx < 0) {
                gx += cols;
                gy--;
            } else if (gx >= cols) {
                gx -= cols;
                gy++;
            }
        } while (gy >= 0 && gy < rows && shouldRun);

        Point[] cells = new Point[PApplet.ceil((cols * rows) / 2f)];

        for (int i = 0; i < cells.length; i++) cells[i] = new Point(i % cols, i / cols);

        SudokuApplet.shuffle(cells);
        int removed = 0;
        int remStart = cols * rows;

        for (int i = 0; removed < (remStart - clueCount - 1) && i < cells.length && shouldRun; i++) {
            Point v = cells[i];
            int x = v.x;
            int y = v.y;
            int ox = cols - v.x - 1;
            int oy = cols - v.y - 1;

            SudokuApplet.println("Trying to remove clue " + i + ", " + removed + " already removed, " + (remStart - clueCount - removed) + " remaining.");

            if (game.game[x][y] > -1) {
                int cell = game.game[x][y];
                int mirror = game.game[ox][oy];
                game.game[x][y] = -1;
                game.game[ox][oy] = -1;
                game.lockAsBase(false, false);
                int solutions = game.getSolver().countSolutions();
                if (solutions > 1) {
                    game.game[x][y] = cell;
                    game.game[ox][oy] = mirror;
                    SudokuApplet.println("Actually I had to put it back, found multiple solutions...");
                } else removed = removed + 2;
            }
        }

        if (!shouldRun) return;

        game.select(-1, -1);

        PApplet.println("removal target: " + (((cols * rows) - 1) - clueCount) + ", removed: " + removed);
        game.lockAsBase(true, true);
        timer.stop();
        PApplet.println("Generation finished in " + timer.getTimer().getElapsedTimeSecs() + " seconds (" + timer.getTimer().getElapsedTime() + " milliseconds, to be precise)");
        gameView.getApplet().frameRate(60);
        gameView.generator = null;
        game.gameClock.start();

    }

    private boolean available(int x, int y) {
        return numbers.get(x).get(y).size() > 0;
    }
}
