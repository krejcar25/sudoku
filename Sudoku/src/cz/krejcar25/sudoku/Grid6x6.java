package cz.krejcar25.sudoku;

import java.awt.*;

public class Grid6x6 extends BaseGrid {
    Grid6x6(BaseView parent) {
        super(parent, 3,2,2,3, 3);
        newGamePos = new Point(0, 7);
        helpPos = new Point(1, 7);
        orderTogglePos = new Point(2, 7);
        deletePos = new Point(3, 7);
        smallNumPos = new Point(0, 8);
        settingsPos = new Point(1, 8);
        exitPos = new Point(2, 8);
        timerPos = new Point(4,7);
    }

    public BaseGrid clone() {
        BaseGrid clone = new Grid6x6(parent);
        return super.clone(clone);
    }

    public void keyInput(int k) {
        if (k > 0) {
            if (numFirst) {
                selectedn = (selectedn == (k - 1)) ? -1 : (k - 1);
            } else {
                placeNumber(k, selectedx, selectedy);
            }
        }
    }

    public BaseSolver getSolver() {
        return new Sudoku6x6Solver(this);
    }
}
