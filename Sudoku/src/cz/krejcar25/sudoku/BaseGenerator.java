package cz.krejcar25.sudoku;

import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

abstract class BaseGenerator {
    BaseView parent;
    int targetCount;
    ArrayList<ArrayList<ArrayList<Integer>>> numbers;
    StopWatch timer;
    boolean used;
    BaseGrid game;

    BaseGenerator(BaseView parent, int targetCount) {
        this.parent = parent;
        this.targetCount = targetCount;
        timer = new StopWatch("GridGenerator");
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

    public BaseGrid generate() {
        timer.start();
        if (used) return null;
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

        while (true) {
            if (available(gx, gy)) {
                int n = PApplet.floor(parent.applet.random(numbers.get(gx).get(gy).size()));
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

            if (gy < 0 || gy >= rows) break;
        }

        Point[] cells = new Point[cols * rows];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                cells[cols * y + x] = new Point(x, y);
            }
        }

        cells = Main.shuffle(cells);
        int removed = 0;
        int remStart = (cols * rows) - 1;

        for (int i = remStart; removed < (remStart - targetCount) && i >= 0; i--) {
            Point v = cells[i];
            int x = PApplet.floor(v.x);
            int y = PApplet.floor(v.y);

            if (game.game[x][y] > -1) {
                int cell = game.game[x][y];
                game.game[x][y] = -1;
                game.lockAsBase(false, false);
                int solutions = game.getSolver().countSolutions();
                if (solutions > 1) {
                    game.game[x][y] = cell;
                } else removed++;
            }
        }

        game.select(-1, -1);

        PApplet.println("removal target: " + (((cols * rows) - 1) - targetCount) + ", removed: " + removed);
        game.lockAsBase(true, true);
        timer.stop();
        PApplet.println("Generation finished in " + timer.getElapsedTimeSecs() + " seconds (" + timer.getElapsedTime() + " milliseconds, to be precise)");
        game.timer.start();
        return game;
    }

    private boolean available(int x, int y) {
        return numbers.get(x).get(y).size() > 0;
    }
}
