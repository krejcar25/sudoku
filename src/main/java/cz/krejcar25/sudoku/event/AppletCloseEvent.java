package cz.krejcar25.sudoku.event;

import cz.krejcar25.sudoku.ui.Applet;

@FunctionalInterface
public interface AppletCloseEvent {
    void appletClosed(Applet closedApplet);
}
