package cz.krejcar25.sudoku;

import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Sudoku9x9View extends GameView {
    private int clueCount;

    Sudoku9x9View(SudokuApplet applet, int targetCount) {
        super(applet, 810, 990);
        this.clueCount = targetCount;
        newGenerator();
    }

    @Override
    public void newGenerator() {
        generator = new Sudoku9x9Generator(this, clueCount);
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
