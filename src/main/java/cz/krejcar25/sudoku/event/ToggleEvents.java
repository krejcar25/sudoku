package cz.krejcar25.sudoku.event;

import cz.krejcar25.sudoku.control.Toggle;

public interface ToggleEvents {
    void toggled(Toggle sender);

    void switchedOn(Toggle sender);

    void switchedOff(Toggle sender);
}