package cz.krejcar25.sudoku;

import processing.core.PApplet;

public abstract class BaseView {
    int sizex;
    int sizey;
    BaseOverlay overlay;
    Main applet;

    BaseView(Main applet, int sizex, int sizey) {
        this.applet = applet;
        this.sizex = sizex;
        this.sizey = sizey;
    }

    public abstract void show();
    public abstract void click(int mx, int my);
}
