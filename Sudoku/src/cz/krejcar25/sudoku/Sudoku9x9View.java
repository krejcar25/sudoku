package cz.krejcar25.sudoku;

import processing.core.*;

public class Sudoku9x9View extends BaseView implements IGameView {
    private BaseSolver solver;
    private Sudoku9x9Generator generator;
    private Grid9x9 game;
    private int clueCount;

    public Sudoku9x9View(int targetCount) {
        super(810, 990);
        this.clueCount = targetCount;
        newGenerator();
        overlay = new WinOverlay();
    }

    public void show() {
        game.show();

        if (overlay != null) Main.pa.image(overlay.show(), overlay.x, overlay.y);
    }

    public void click(int mx, int my) {
        int sx = PApplet.floor(this.sizex / game.cols);
        int sy = PApplet.floor(this.sizey / (game.rows + game.extraRows));

        int x = PApplet.floor(mx / sx);
        int y = PApplet.floor(my / sy);

        game.click(x, y, Main.pa.mouseButton == PApplet.RIGHT);
    }

    public void generate() {
        game = generator.generate();
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
