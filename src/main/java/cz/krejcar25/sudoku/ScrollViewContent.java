package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.ui.Drawable;

public abstract class ScrollViewContent extends Drawable {
    protected ScrollView scrollView;

    public ScrollViewContent(ScrollView scrollView, int width, int height) {
        super(scrollView.getApplet(), 0, 0, width, height);
        this.scrollView = scrollView;
    }

    public abstract void click(int mx, int my, boolean rmb);
}
