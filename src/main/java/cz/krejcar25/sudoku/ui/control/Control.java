package cz.krejcar25.sudoku.ui.control;

import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.Drawable;


public abstract class Control<O> extends Drawable {
	final BaseView baseView;
	public O userObject;
	String label;
	boolean enabled;

	Control(BaseView baseView, float x, float y, int width, int height) {
		super(baseView.getApplet(), x, y, width, height);
		this.baseView = baseView;
		this.enabled = true;
	}

	Control(Applet applet, float x, float y, int width, int height) {
		super(applet, x, y, width, height);
		this.baseView = null;
		this.enabled = true;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public final BaseView getBaseView() {
		return baseView;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public final boolean isClick(float mx, float my) {
		return isClick((int) mx, (int) my);
	}

	public abstract boolean isClick(int mx, int my);

	public abstract void click();
}
