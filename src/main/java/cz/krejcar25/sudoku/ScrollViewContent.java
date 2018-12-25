package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.ui.Drawable;

public abstract class ScrollViewContent extends Drawable {
    public ScrollViewContent(SudokuApplet sudokuApplet, int width, int height) {
        super(sudokuApplet, 0, 0, width, height);
    }

    public abstract void click(int mx, int my, boolean rmb);
}
