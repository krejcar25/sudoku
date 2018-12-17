package cz.krejcar25.sudoku.ui;

import cz.krejcar25.sudoku.SudokuApplet;
import processing.awt.PGraphicsJava2D;

public abstract class Drawable extends PGraphicsJava2D {
    public int x;
    public int y;
    private boolean resizable = false;
    private int widthLimit = -1;
    private int heightLimit = -1;


    public Drawable(SudokuApplet applet, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        setParent(applet);
        setPrimary(false);
        setPath(applet.dataPath(""));
        setSize(width, height);

        smooth(4);
        beginDraw();
        fill(-1);
        stroke(0);
        endDraw();
    }

    public final void update() {
        beginDraw();
        fill(-1);
        stroke(0);

        draw();

        endDraw();
    }

    protected abstract void draw();

    public SudokuApplet getApplet() {
        return (SudokuApplet) parent;
    }

    protected void setResizable(int limitx, int limity) {
        resizable = true;
        widthLimit = limitx;
        heightLimit = limity;
    }

    protected void setNonResizable() {
        resizable = false;
        widthLimit = -1;
        heightLimit = -1;
    }

    protected void push() {
        pushMatrix();
        pushStyle();
    }

    protected void pop() {
        popStyle();
        popMatrix();
    }

    public boolean isResizable() {
        return resizable;
    }

    public int getWidthLimit() {
        return widthLimit;
    }

    public int getHeightLimit() {
        return heightLimit;
    }
}
