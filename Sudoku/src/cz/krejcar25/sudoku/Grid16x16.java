package cz.krejcar25.sudoku;

import java.awt.*;

public class Grid16x16 extends BaseGrid {
    Grid16x16(BaseView parent) {
        super(parent, 4,4,4,4, 2);
        newGamePos = new Point(0, 0);
        helpPos = new Point(0, 1);
        orderTogglePos = new Point(0, 2);
        deletePos = new Point(0, 3);
        smallNumPos = new Point(0, 4);
        settingsPos = new Point(0, 5);
        exitPos = new Point(0, 6);
        timerPos = new Point(0,14);
    }

    public BaseGrid clone() {
        BaseGrid clone = new Grid16x16(parent);
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
        return new Sudoku16x16Solver(this);
    }
}
