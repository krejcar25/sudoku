package cz.krejcar25.sudoku;

import processing.core.PApplet;

public class Sudoku4x4View extends GameView {
    private int clueCount;

    Sudoku4x4View(Main applet, int targetCount) {
        super(applet, 360, 720);
        this.clueCount = targetCount;
        newGenerator();
        //overlay = new WinOverlay();
    }

    public void show() {
        game.show();

        if (overlay != null) applet.image(overlay.show(), overlay.x, overlay.y);
    }

    public void newGenerator() {
        generator = new Sudoku4x4Generator(this, clueCount);
    }

    public void newSolver() {
        solver = game.getSolver();
    }

    public void keyPress() {

    }
}
