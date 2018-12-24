package cz.krejcar25.sudoku;

import processing.core.PApplet;
import processing.event.KeyEvent;

public abstract class GameView extends BaseView {
    protected BaseSolver solver;
    protected BaseGenerator generator;
    protected BaseGrid game;
    public final int EasyClueCount;
    public final int MediumClueCount;
    public final int HardClueCount;
    public final int ExtremeClueCount;
    protected int clueCount;

    GameView(SudokuApplet applet, int width, int height, int easyClueCount, int mediumClueCount, int hardClueCount, int extremeClueCount) {
        super(applet, width, height);
        EasyClueCount = easyClueCount;
        MediumClueCount = mediumClueCount;
        HardClueCount = hardClueCount;
        ExtremeClueCount = extremeClueCount;
    }

    @Override
    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    public void click(int mx, int my, boolean rmb) {
        if (overlay == null) {
            int sx = PApplet.floor(this.width / game.cols());
            int sy = PApplet.floor(this.height / (game.rows() + game.extraRows));

            int x = PApplet.floor(mx / sx);
            int y = PApplet.floor(my / sy);

            game.click(x, y, parent.mouseButton == PApplet.RIGHT);
        } else {
            overlay.click(mx, my, rmb);
        }
    }

    @Override
    protected void draw() {
        game.update();
        image(game, game.x, game.y);

        if (overlay != null) {
            overlay.update();
            image(overlay, overlay.x, overlay.y);
        }
    }

    @Override
    public void keyDown(KeyEvent keyEvent) {
        game.keyInput(keyEvent.getKeyCode());
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

    public void generate(int clueCount, boolean async) {
        generator.generate(clueCount, async);
    }

    public abstract void newGenerator();

    public abstract void newSolver();
}
