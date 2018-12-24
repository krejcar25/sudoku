package cz.krejcar25.sudoku;

import processing.core.PApplet;

import java.awt.*;

public class Grid4x4 extends BaseGrid {
    Grid4x4(GameView gameView) {
        super(gameView, 2,2, 4);
        newGamePos = new Point(0, 5);
        helpPos = new Point(1, 5);
        orderTogglePos = new Point(2, 5);
        deletePos = new Point(3, 5);
        smallNumPos = new Point(0, 6);
        settingsPos = new Point(1, 6);
        exitPos = new Point(2, 6);
        timerPos = new Point(1,7);
        gameClock = new Clock(getApplet(), timerPos.x * sx + 10, (int) (timerPos.y * sy + (sy - Clock.getHeightFromWidth(2 * sx - 20)) / 2), "4x4 Grid");
    }

    @Override
    public BaseGrid clone() {
        BaseGrid clone = new Grid4x4(view);
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
        return new Sudoku4x4Solver(this);
    }
}
