package cz.krejcar25.sudoku;

import processing.core.*;

public class Sudoku9x9View extends GameView {
    private int clueCount;

    Sudoku9x9View(Main applet, int targetCount) {
        super(applet, 810, 990);
        this.clueCount = targetCount;
        newGenerator();
    }

    public void show() {
        game.show();

        if (overlay != null) applet.image(overlay.show(), overlay.x, overlay.y);
    }

    public void newGenerator() {
        generator = new Sudoku9x9Generator(this, clueCount);
    }

    public void newSolver() {
        solver = game.getSolver();
    }

    public void keyPress() {

    }
}
