package cz.krejcar25.sudoku;

import java.awt.*;

public class Grid16x16 extends BaseGrid {
    Grid16x16(BaseView parent) {
        super(parent, 4,4, 2);
        newGamePos = new Point(0, 17);
        helpPos = new Point(1, 17);
        orderTogglePos = new Point(2, 17);
        deletePos = new Point(3, 17);
        smallNumPos = new Point(4, 17);
        settingsPos = new Point(5, 17);
        exitPos = new Point(6, 17);
        timerPos = new Point(14,17);
        drawNumberOffset = 0;
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
