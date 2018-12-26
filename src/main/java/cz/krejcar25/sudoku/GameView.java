package cz.krejcar25.sudoku;

import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.text.ParseException;

public class GameView extends BaseView {
    protected BaseSolver solver;
    protected BaseGenerator generator;
    protected BaseGrid game;
    public final int EasyClueCount;
    public final int MediumClueCount;
    public final int HardClueCount;
    public final int ExtremeClueCount;

    GameView(SudokuApplet sudokuApplet, GridProperties gridProperties) {
        super(sudokuApplet, gridProperties.getWidth(), gridProperties.getHeight());
        EasyClueCount = gridProperties.getClueCount(GridDifficulty.Easy);
        MediumClueCount = gridProperties.getClueCount(GridDifficulty.Medium);
        HardClueCount = gridProperties.getClueCount(GridDifficulty.Hard);
        ExtremeClueCount = gridProperties.getClueCount(GridDifficulty.Extreme);
        game = new BaseGrid(this, gridProperties);
        newGenerator();
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
        try {
            int key = Integer.parseInt(String.valueOf(keyEvent.getKey()));
            game.keyInput(key);
        } catch (NumberFormatException ex) {
            // We dont care, it may be another key...
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

    public void generate(int clueCount, boolean async) {
        generator.generate(clueCount, async);
    }

    public void newGenerator() {
        generator = new BaseGenerator(game);
        game.generator = generator;
    }

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
