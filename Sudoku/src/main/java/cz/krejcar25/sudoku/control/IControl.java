package cz.krejcar25.sudoku.control;

import processing.core.PGraphics;

public interface IControl {
    void show(PGraphics g);
    boolean isClick(int x, int y);
    void click();
}
