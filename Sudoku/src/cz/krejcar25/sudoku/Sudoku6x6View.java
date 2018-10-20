package cz.krejcar25.sudoku;

import processing.core.PApplet;

public class Sudoku6x6View extends BaseView implements IGameView {
    private BaseSolver solver;
    private Sudoku6x6Generator generator;
    private Grid6x6 game;
    private int clueCount;

    public Sudoku6x6View(Main applet, int targetCount) {
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
        game = (Grid6x6)generator.generate();
    }

    public void newGenerator() {
        generator = new Sudoku6x6Generator(this, clueCount);
    }

    public BaseSolver getSolver() {
        return solver;
    }

    public void newSolver() {
        solver = game.getSolver();
    }

    public Sudoku6x6Generator getGenerator() {
        return generator;
    }

    public Grid6x6 getGrid() {
        return game;
    }
}
