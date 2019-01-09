package cz.krejcar25.sudoku.event;

import cz.krejcar25.sudoku.ui.control.Button;

@FunctionalInterface
public interface ButtonEvents {
    void click(Button sender);
}

