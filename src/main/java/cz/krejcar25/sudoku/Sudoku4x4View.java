package cz.krejcar25.sudoku;

import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Sudoku4x4View extends GameView {
    private int clueCount;

    Sudoku4x4View(SudokuApplet applet, int targetCount) {
        super(applet, 360, 720);
        this.clueCount = targetCount;
        newGenerator();
        //overlay = new WinOverlay();
    }

    @Override
    public void newGenerator() {
        generator = new Sudoku4x4Generator(this, clueCount);
    }

    @Override
    public void newSolver() {
        solver = game.getSolver();
    }

    @Override
    protected void mouseDown(int mx, int my, boolean rmb) {

    }

    @Override
    protected void mouseUp(int mx, int my, boolean rmb) {

    }

    @Override
    public void mouseDrag(MouseEvent mouseEvent) {

    }

    @Override
    public void scroll(MouseEvent event) {

    }

    @Override
    public void keyUp(KeyEvent keyEvent) {

    }
}
