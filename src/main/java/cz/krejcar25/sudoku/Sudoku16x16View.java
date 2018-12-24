package cz.krejcar25.sudoku;

import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Sudoku16x16View extends GameView {
    Sudoku16x16View(SudokuApplet applet) {
        super(applet, 880, 990, 200, 180, 170, 150);
        game = new Grid16x16(this);
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
