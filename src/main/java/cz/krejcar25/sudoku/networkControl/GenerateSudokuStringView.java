package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.game.GridProperties;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class GenerateSudokuStringView extends BaseView {
    private final GridProperties gridProperties;
    private final int count;

    public GenerateSudokuStringView(Applet applet, GridProperties gridProperties, int count) {
        super(applet, 800, 600);
        this.gridProperties = gridProperties;
        this.count = count;
    }

    @Override
    public void mouseDown(int mx, int my, boolean rmb) {

    }

    @Override
    public void mouseUp(int mx, int my, boolean rmb) {

    }

    @Override
    public void click(int mx, int my, boolean rmb) {

    }

    @Override
    public void mouseDrag(MouseEvent mouseEvent) {

    }

    @Override
    public void scroll(MouseEvent event) {

    }

    @Override
    public void keyDown(KeyEvent keyEvent) {

    }

    @Override
    public void keyUp(KeyEvent keyEvent) {

    }

    @Override
    protected void draw() {
        background(220);
        textSize(30);
        text("Training", 0, 30);
    }
}
