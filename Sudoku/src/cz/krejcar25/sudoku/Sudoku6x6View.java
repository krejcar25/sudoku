package cz.krejcar25.sudoku;

import processing.core.PApplet;

public class Sudoku6x6View extends GameView {
    private int clueCount;

    Sudoku6x6View(Main applet, int targetCount) {
        super(applet, 540, 810);
        this.clueCount = targetCount;
        newGenerator();
        //overlay = new WinOverlay();
    }

    public void show() {
        game.show();

        if (overlay != null) applet.image(overlay.show(), overlay.x, overlay.y);
    }

    public void newGenerator() {
        generator = new Sudoku6x6Generator(this, clueCount);
    }

    public void newSolver() {
        solver = game.getSolver();
    }

    public void keyPress() {

    }
}
