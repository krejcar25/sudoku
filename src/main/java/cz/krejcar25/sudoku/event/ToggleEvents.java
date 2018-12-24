package cz.krejcar25.sudoku.event;

import cz.krejcar25.sudoku.control.Control;

public interface ToggleEvents {
    void toggled(Control sender);

    void switchedOn(Control sender);

    void switchedOff(Control sender);
}