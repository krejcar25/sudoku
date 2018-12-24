package cz.krejcar25.sudoku;

import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Sudoku6x6View extends GameView {
    Sudoku6x6View(SudokuApplet applet) {
        super(applet, 540, 810, 18, 16, 14, 12);
        game = new Grid6x6(this);
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
