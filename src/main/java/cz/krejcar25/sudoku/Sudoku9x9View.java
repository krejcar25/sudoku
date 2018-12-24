package cz.krejcar25.sudoku;

import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Sudoku9x9View extends GameView {
    Sudoku9x9View(SudokuApplet applet) {
        super(applet, 810, 990, 30, 27, 24, 21);
        game = new Grid9x9(this);
        newGenerator();
    }

    @Override
    public void newGenerator() {
        generator = new BaseGenerator(game);
        game.generator = generator;
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
