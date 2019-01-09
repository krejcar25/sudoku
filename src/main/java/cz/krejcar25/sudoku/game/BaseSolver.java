package cz.krejcar25.sudoku.game;

import cz.krejcar25.sudoku.Timer;

import java.util.ArrayList;

public class BaseSolver {
    protected BaseGrid game;
    protected boolean used = false;
    protected Timer timer;
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
        timer = new Timer("GridSolver");
        numbers = new ArrayList<>();
        tryNext = true;
        cycleAllowed = false;
        wentBack = false;

        for (int x = 0; x < game.cols(); x++) {
            numbers.add(new ArrayList<>());
            for (int y = 0; y < game.rows(); y++) {
                numbers.get(x).add(new ArrayList<>());
                replenish(x, y);
            }
        }

        count = 0;
        x = 0;
        y = 0;
    }

    public boolean prepare() {
        if (used) return false;
        used = true;
        cycleAllowed = true;
        return true;
    }

    public void cycle() {
        if (!cycleAllowed) return;

        game.select(x,y);

        if (wentBack) {
            wentBack = false;
            numbers.get(x).get(y).remove(0);
        }

        if (available(x, y)) {
            int n = 0;
            if (game.canPlaceNumber(numbers.get(x).get(y).get(n), x, y, -1) || game.game[x][y] == numbers.get(x).get(y).get(n)) {
                game.game[x][y] = numbers.get(x).get(y).get(n);
                x++;
            } else {
                numbers.get(x).get(y).remove(n);
            }
        } else {
            replenish(x, y);
            if (!game.baseGame[x][y]) game.game[x][y] = -1;
            x--;
            wentBack = true;
        }

        int cols = game.cols();
        int rows = game.rows();
        if (x >= cols) {
            x -= cols;
            y++;
        } else if (x < 0) {
            x += cols;
            y--;
        }

        if (y >= rows) {
            count++;
            x += (cols - 2);
            y--;
            if (!game.baseGame[x][y]) game.game[cols - 1][rows - 1] = -1;
            wentBack = true;
        }
        if (y < 0) {
            tryNext = false;
            cycleAllowed = false;
        }
    }

    public int finish() {
        System.out.println("Solving finished in " + timer.getElapsedTimeSecs() + " seconds (" + timer.getElapsedTime() + " milliseconds, to be precise). Found " + count + " solutions.");
        game.select(-1,-1);
        return count;
    }

    public int countSolutions() {
        timer.start();
        if (!prepare()) return -1;

        while (tryNext) {
            cycle();
        }

        timer.stop();
        return finish();
    }

    private void replenish(int x, int y) {
        ArrayList<Integer> list = numbers.get(x).get(y);
        list.clear();
        if (game.baseGame[x][y]) {
            list.add(game.game[x][y]);
        } else {
            for (int i = 0; i < game.numbers(); i++) {
                list.add(i);
            }
        }
    }

    private boolean available(int x, int y) {
        return numbers.get(x).get(y).size() > 0;
    }
}
