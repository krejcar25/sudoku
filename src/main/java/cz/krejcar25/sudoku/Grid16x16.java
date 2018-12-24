package cz.krejcar25.sudoku;

import java.awt.*;

public class Grid16x16 extends BaseGrid {
    Grid16x16(GameView gameView) {
        super(gameView, 4,4, 2);
        newGamePos = new Point(0, 17);
        helpPos = new Point(1, 17);
        orderTogglePos = new Point(2, 17);
        deletePos = new Point(3, 17);
        smallNumPos = new Point(4, 17);
        settingsPos = new Point(5, 17);
        exitPos = new Point(6, 17);
        timerPos = new Point(14,17);
        drawNumberOffset = 0;
        gameClock = new Clock(getApplet(), timerPos.x * sx + 10, (int) (timerPos.y * sy + (sy - Clock.getHeightFromWidth(2 * sx - 20)) / 2), "16x16 Grid");
    }

    @Override
    public BaseGrid clone() {
        BaseGrid clone = new Grid16x16(view);
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
        return new Sudoku16x16Solver(this);
    }
}
