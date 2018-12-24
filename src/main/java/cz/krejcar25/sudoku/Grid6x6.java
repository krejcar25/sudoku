package cz.krejcar25.sudoku;

import java.awt.*;

public class Grid6x6 extends BaseGrid {
    Grid6x6(GameView gameView) {
        super(gameView, 3,2, 3);
        newGamePos = new Point(0, 7);
        helpPos = new Point(1, 7);
        orderTogglePos = new Point(2, 7);
        deletePos = new Point(3, 7);
        smallNumPos = new Point(0, 8);
        settingsPos = new Point(1, 8);
        exitPos = new Point(2, 8);
        timerPos = new Point(4,7);
        gameClock = new Clock(getApplet(), timerPos.x * sx + 10, (int) (timerPos.y * sy + (sy - Clock.getHeightFromWidth(2 * sx - 20)) / 2), "6x6 Grid");
    }

    @Override
    public BaseGrid clone() {
        BaseGrid clone = new Grid6x6(view);
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
        return new Sudoku6x6Solver(this);
    }
}
