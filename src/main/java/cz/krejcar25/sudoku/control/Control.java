package cz.krejcar25.sudoku.control;

import cz.krejcar25.sudoku.BaseView;
import cz.krejcar25.sudoku.ui.Drawable;
import processing.event.MouseEvent;


public abstract class Control extends Drawable {
    protected BaseView baseView;
    protected String tooltip;
    protected String label;

    protected Control(BaseView baseView, int x, int y, int width, int height) {
        super(baseView.getApplet(), x, y, width, height);
        this.baseView = baseView;
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

    public abstract boolean isClick(int mx, int my);

    public abstract void click();
}
