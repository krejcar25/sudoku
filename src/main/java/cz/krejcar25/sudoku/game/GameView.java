package cz.krejcar25.sudoku.game;

import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.SudokuApplet;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class GameView extends BaseView {
    protected BaseSolver solver;
    protected BaseGenerator generator;
    protected BaseGrid game;
    protected GridProperties gridProperties;

    GameView(SudokuApplet sudokuApplet, GridProperties gridProperties) {
        super(sudokuApplet, gridProperties.getWidth(), gridProperties.getHeight());
        this.gridProperties = gridProperties;
        game = new BaseGrid(this, gridProperties);
        newGenerator();
    }

    @Override
    public void click(int mx, int my, boolean rmb) {
        if (overlay == null) {
            float sx = PApplet.floor((float) this.width / game.cols());
            float sy = PApplet.floor((float) this.height / (game.rows() + game.extraRows));

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

    public GridProperties getGridProperties() {
        return gridProperties;
    }

    public void generate(GridDifficulty gridDifficulty, boolean async) {
        game.gridDifficulty = gridDifficulty;
        generator.generate(gridProperties.getClueCount(gridDifficulty), async);
    }

    public void newGenerator() {
        generator = new BaseGenerator(game);
        game.generator = generator;
    }

    public void newSolver() {
        solver = game.getSolver();
    }

    @Override
    public void mouseDown(int mx, int my, boolean rmb) {

    }

    @Override
    public void mouseUp(int mx, int my, boolean rmb) {

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
