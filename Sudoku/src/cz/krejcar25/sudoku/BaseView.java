package cz.krejcar25.sudoku;

import processing.core.PApplet;

public abstract class BaseView {
    int sizex;
    int sizey;
    BaseOverlay overlay;
    Main applet;
    private boolean resizable = false;
    private int sizexlimit = -1;
    private int sizeylimit = -1;

    BaseView(Main applet, int sizex, int sizey) {
        this.applet = applet;
        this.sizex = sizex;
        this.sizey = sizey;
    }

    protected void setResizable(int limitx, int limity) {
        resizable = true;
        sizexlimit = limitx;
        sizeylimit = limity;
    }

    protected void setNonResizable() {
        resizable = false;
        sizexlimit = -1;
        sizeylimit = -1;
    }

    public boolean isResizable() {
        return resizable;
    }

    public int getSizeXLimit() {
        return sizexlimit;
    }

    public int getSizeYLimit() {
        return sizeylimit;
    }

    public abstract void show();
    public abstract void click(int mx, int my);
    public abstract void keyPress();
}
