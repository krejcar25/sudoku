package cz.krejcar25.sudoku;

import processing.core.*;

public class Button {
    int x;
    int y;
    private int sx;
    private int sy;
    private int bx;
    private int by;
    private String label;
    IButtonClick click;

    Button(int x, int y, int sx, int sy, int bx, int by, String label, IButtonClick click) {
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;
        this.bx = bx;
        this.by = by;
        this.label = label;
        this.click = click;
    }

    public void show(PGraphics g) {
        g.pushMatrix();
        g.pushStyle();
        g.translate(x, y);
        g.pushMatrix();
        g.fill(0);
        g.rectMode(PApplet.CENTER);
        g.rect(0, 0, sx, sy);
        g.fill(51);
        g.rect(0, 0, sx - 2 * bx, sy - 2 * by);
        g.popMatrix();

        g.pushMatrix();
        g.fill(255);
        g.textSize(sy - 2 * by);
        g.textAlign(PApplet.CENTER, PApplet.CENTER);
        g.text(label, 0, -3);
        g.popMatrix();
        g.popStyle();
        g.popMatrix();
    }

    boolean isClick(int x, int y) {
        return ((this.x - this.sx / 2) < x && x < (this.x + this.sx / 2)) && ((this.y - this.sy / 2) < y && y < (this.y + this.sy / 2));
    }
}

