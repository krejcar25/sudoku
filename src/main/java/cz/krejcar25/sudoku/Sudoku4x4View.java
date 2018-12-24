package cz.krejcar25.sudoku;

import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Sudoku4x4View extends GameView {

    Sudoku4x4View(SudokuApplet applet) {
        super(applet, 360, 720, 11, 9, 7, 6);
        game = new Grid4x4(this);
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
