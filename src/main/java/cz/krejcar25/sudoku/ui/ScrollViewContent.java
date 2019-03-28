package cz.krejcar25.sudoku.ui;

public abstract class ScrollViewContent extends Drawable {
	protected final ScrollView scrollView;

	protected ScrollViewContent(ScrollView scrollView, int width, int height) {
		super(scrollView.getApplet(), 0, 0, width, height);
		this.scrollView = scrollView;
	}

	public final void click(float mx, float my, boolean rmb) {
		click((int) mx, (int) my, rmb);
	}

	protected abstract void click(int mx, int my, boolean rmb);
}
