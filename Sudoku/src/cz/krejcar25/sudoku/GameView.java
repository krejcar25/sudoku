package cz.krejcar25.sudoku;

import processing.core.PApplet;

public abstract class GameView extends BaseView {
    protected BaseSolver solver;
    protected BaseGenerator generator;
    protected BaseGrid game;

    GameView(Main applet, int sizex, int sizey) {
        super(applet, sizex, sizey);
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    public void click(int mx, int my) {
        if (overlay == null) {
            int sx = PApplet.floor(this.sizex / game.cols());
            int sy = PApplet.floor(this.sizey / (game.rows() + game.extraRows));

            int x = PApplet.floor(mx / sx);
            int y = PApplet.floor(my / sy);

            game.click(x, y, applet.mouseButton == PApplet.RIGHT);
        } else {
            overlay.click(mx - overlay.x, my - overlay.y);
        }
    }

    public BaseSolver getSolver() {
        return solver;
    }

    public BaseGenerator getGenerator() {
        return generator;
    }

    public BaseGrid getGrid() {
        return game;
    }

    public void generate() {
        game = generator.generate();
    }

    public abstract void newGenerator();
    public abstract void newSolver();
}
