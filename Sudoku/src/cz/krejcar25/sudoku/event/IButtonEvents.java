package cz.krejcar25.sudoku.event;

import cz.krejcar25.sudoku.control.Button;

@FunctionalInterface
public interface IButtonClick {
    void click(Button button);
}