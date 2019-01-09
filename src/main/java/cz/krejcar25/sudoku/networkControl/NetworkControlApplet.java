package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.ChildApplet;
import cz.krejcar25.sudoku.SudokuApplet;
import cz.krejcar25.sudoku.ui.control.Control;
import cz.krejcar25.sudoku.event.AppletCloseEvent;

import java.util.ArrayList;

public class NetworkControlApplet extends Applet implements ChildApplet {
    private final SudokuApplet owner;
    private final AppletCloseEvent closeEvent;
    private ArrayList<Control> controls;

    public NetworkControlApplet(SudokuApplet owner, AppletCloseEvent closeEvent) {
        this.owner = owner;
        this.closeEvent = closeEvent;
    }

    public SudokuApplet getOwner() {
        return owner;
    }

    @Override
    public SudokuApplet getRootOwner() {
        return owner;
    }

    @Override
    public void settings() {
        size(800,600);
        pixelDensity(displayDensity());
    }

    @Override
    public void setup() {
        surface.setTitle("Neural Network Control");
        setCloseOnExit(false);
    }

    @Override
    public void draw() {
        background(51);
        textSize(50);
        textAlign(LEFT, TOP);
        text("Hello World", 50,50);
    }

    @Override
    public void exitActual() {
        super.exitActual();

        closeEvent.appletClosed(this);
    }
}
