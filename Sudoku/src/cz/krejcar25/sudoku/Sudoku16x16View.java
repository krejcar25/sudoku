package cz.krejcar25.sudoku;

import processing.core.PApplet;

public class Sudoku16x16View extends BaseView implements IGameView {
    private BaseSolver solver;
    private Sudoku16x16Generator generator;
    private Grid16x16 game;
    private int clueCount;

    public Sudoku16x16View(Main applet, int targetCount) {
        super(applet, 540, 810);
        this.clueCount = targetCount;
        newGenerator();
        //overlay = new WinOverlay();
    }

    public void show() {
        game.show();

        if (overlay != null) applet.image(overlay.show(), overlay.x, overlay.y);
    }

    public void click(int mx, int my) {
        int sx = PApplet.floor(this.sizex / game.cols());
        int sy = PApplet.floor(this.sizey / (game.rows() + game.extraRows));

        int x = PApplet.floor(mx / sx);
        int y = PApplet.floor(my / sy);

        game.click(x, y, applet.mouseButton == PApplet.RIGHT);
    }

    public void generate() {
        game = (Grid16x16)generator.generate();
    }

    public void newGenerator() {
        generator = new Sudoku16x16Generator(this, clueCount);
    }

    public BaseSolver getSolver() {
        return solver;
    }

    public void newSolver() {
        solver = game.getSolver();
    }

    public Sudoku16x16Generator getGenerator() {
        return generator;
    }

    public Grid16x16 getGrid() {
        return game;
    }
}
