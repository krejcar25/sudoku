package cz.krejcar25.sudoku;

import processing.core.PApplet;

public class Sudoku16x16View extends GameView {
    private int clueCount;

    Sudoku16x16View(Main applet, int targetCount) {
        super(applet, 880, 990);
        this.clueCount = targetCount;
        newGenerator();
    }

    public void show() {
        game.show();

        if (overlay != null) applet.image(overlay.show(), overlay.x, overlay.y);
    }

    public void newGenerator() {
        generator = new Sudoku16x16Generator(this, clueCount);
    }

    public void newSolver() {
        solver = game.getSolver();
    }

    public void keyPress() {

    }
}
