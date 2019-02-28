package cz.krejcar25.sudoku.ui.control;

import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.Drawable;


public abstract class Control<O> extends Drawable
{
    protected BaseView baseView;
    protected String tooltip;
    protected String label;
	public O userObject;
	protected boolean enabled;

    protected Control(BaseView baseView, float x, float y, int width, int height) {
        super(baseView.getApplet(), x, y, width, height);
        this.baseView = baseView;
	    this.enabled = true;
    }

    protected Control(Applet applet, float x, float y, int width, int height) {
        super(applet, x, y, width, height);
        this.baseView = null;
	    this.enabled = true;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

	public final BaseView getBaseView()
	{
		return baseView;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

    public final boolean isClick(float mx, float my) {
        return isClick((int) mx, (int) my);
    }

    public abstract boolean isClick(int mx, int my);

    public abstract void click();
}
