package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Button;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class ScoreboardView extends BaseView {
    private final ViewStack childViews;
    private Button backButton;

    public ScoreboardView(SudokuApplet sudokuApplet) {
        super(sudokuApplet, 800, 600);
        childViews = new ViewStack(new ScoreboardSizeSelectView(sudokuApplet));
        backButton = Button.getStandardBackButton(this);
    }

    @Override
    protected void mouseDown(int mx, int my, boolean rmb) {
        childViews.get().mouseDown(mx, my, rmb);
    }

    @Override
    protected void mouseUp(int mx, int my, boolean rmb) {
        childViews.get().mouseUp(mx, my, rmb);
    }

    @Override
    protected void click(int mx, int my, boolean rmb) {
        boolean pass = true;
        if (childViews.isbase()) {
            pass = false;
            if (rmb) removeFromViewStack();
            else if (backButton.isClick(mx, my)) backButton.click();
            else pass = true;
        }
        if (pass) childViews.get().click(mx, my, rmb);
    }

    @Override
    public void mouseDrag(MouseEvent mouseEvent) {
        childViews.get().mouseDrag(mouseEvent);
    }

    @Override
    public void scroll(MouseEvent event) {
        childViews.get().scroll(event);
    }

    @Override
    public void keyDown(KeyEvent keyEvent) {
        childViews.get().keyDown(keyEvent);
    }

    @Override
    public void keyUp(KeyEvent keyEvent) {
        childViews.get().keyUp(keyEvent);
    }

    @Override
    protected void draw() {
        background(220);
        BaseView view = childViews.get();
        view.update();
        image(view, view.x, view.y);

        backButton.update();
        if (childViews.isbase()) image(backButton, backButton.x, backButton.y);
    }
}
