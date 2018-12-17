package cz.krejcar25.sudoku;

import java.util.ArrayList;
import java.awt.Point;

import processing.core.*;

public class Grid9x9 extends BaseGrid {
    Grid9x9(GameView gameView) {
        super(gameView, 3, 3, 2);
        newGamePos = new Point(0, 10);
        helpPos = new Point(1, 10);
        orderTogglePos = new Point(2, 10);
        deletePos = new Point(3, 10);
        smallNumPos = new Point(4, 10);
        settingsPos = new Point(5, 10);
        exitPos = new Point(6, 10);
        timerPos = new Point(7, 10);
        gameClock = new Clock(getApplet(), timerPos.x * sx + 10, (int) (timerPos.y * sy + (sy - Clock.getHeightFromWidth(2 * sx - 20)) / 2), "9x9 Grid");
    }

    @Override
    public BaseGrid clone() {
        BaseGrid clone = new Grid9x9(view);
        return super.clone(clone);
    }

    @Override
    public void keyInput(int k) {
        if (k > 0) {
            if (numFirst) {
                selectedn = (selectedn == (k - 1)) ? -1 : (k - 1);
            } else {
                placeNumber(k, selectedx, selectedy);
            }
        }
    }

    @Override
    public BaseSolver getSolver() {
        return new Sudoku9x9Solver(this);
    }
}
