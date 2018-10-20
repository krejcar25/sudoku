package cz.krejcar25.sudoku;

import processing.core.PApplet;

public class Sudoku4x4View extends BaseView implements IGameView {
    private BaseSolver solver;
    private Sudoku4x4Generator generator;
    private Grid4x4 game;
    private int clueCount;

    public Sudoku4x4View(Main applet, int targetCount) {
        super(applet, 360, 720);
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
        game = (Grid4x4)generator.generate();
    }

    public void newGenerator() {
        generator = new Sudoku4x4Generator(this, clueCount);
    }

    public BaseSolver getSolver() {
        return solver;
    }

    public void newSolver() {
        solver = game.getSolver();
    }

    public Sudoku4x4Generator getGenerator() {
        return generator;
    }

    public Grid4x4 getGrid() {
        return game;
    }
}
