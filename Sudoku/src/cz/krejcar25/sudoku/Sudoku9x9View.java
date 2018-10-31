package cz.krejcar25.sudoku;

import processing.core.*;

public class Sudoku9x9View extends BaseView implements IGameView {
    private BaseSolver solver;
    private Sudoku9x9Generator generator;
    private Grid9x9 game;
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

    public void click(int mx, int my) {
        if (overlay == null) {
            //noinspection IntegerDivisionInFloatingPointContext
            int sx = PApplet.floor(this.sizex / game.cols());
            //noinspection IntegerDivisionInFloatingPointContext
            int sy = PApplet.floor(this.sizey / (game.rows() + game.extraRows));

            //noinspection IntegerDivisionInFloatingPointContext
            int x = PApplet.floor(mx / sx);
            //noinspection IntegerDivisionInFloatingPointContext
            int y = PApplet.floor(my / sy);

            game.click(x, y, applet.mouseButton == PApplet.RIGHT);
        } else {
            overlay.click(mx - overlay.x, my - overlay.y);
        }
    }

    public void generate() {
        game = (Grid9x9) generator.generate();
    }

    public void newGenerator() {
        generator = new Sudoku9x9Generator(this, clueCount);
    }

    public BaseSolver getSolver() {
        return solver;
    }

    public void newSolver() {
        solver = game.getSolver();
    }

    public Sudoku9x9Generator getGenerator() {
        return generator;
    }

    public Grid9x9 getGrid() {
        return game;
    }
}
