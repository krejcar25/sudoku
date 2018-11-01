package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.event.IButtonClick;
import cz.krejcar25.sudoku.style.ButtonStyle;
import processing.core.*;

public class Button {
    final int x;
    final int y;
    private int sx;
    private int sy;
    //private int bx;
    private int by;
    private String label;
    IButtonClick click;

    ButtonStyle style;

    Button(int x, int y, int sx, int sy, int bx, int by, String label, IButtonClick click) {
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;
        //this.bx = bx;
        this.by = by;
        this.label = label;
        this.click = click;

        style = new ButtonStyle();
    }

    public void show(PGraphics g) {
        g.pushMatrix();
        g.pushStyle();
        g.translate(x, y);
        g.pushMatrix();
        g.fill(style.border.r, style.border.g, style.border.b);
        g.pushMatrix();
        //noinspection IntegerDivisionInFloatingPointContext
        g.translate(-sx / 2, -sy / 2);
        g.translate(-style.borderThickness.left, -style.borderThickness.top);
        g.rect(0, 0, sx + style.borderThickness.totalRl(), sy + style.borderThickness.totalTb());
        g.popMatrix();
        g.rectMode(PApplet.CENTER);
        g.fill(style.background.r, style.background.g, style.background.b);
        g.rect(0, 0, sx, sy);
        g.popMatrix();

        g.pushMatrix();
        g.fill(style.foreground.r, style.foreground.g, style.foreground.b);
        g.textSize(sy - 2 * by);
        g.textAlign(PApplet.CENTER, PApplet.CENTER);
        g.text(label, 0, -3);
        g.popMatrix();
        g.popStyle();
        g.popMatrix();
    }

    boolean isClick(int x, int y) {
        int left = (this.x - this.sx / 2);
        int right = (this.x + this.sx / 2);
        boolean hor = left < x && x < right;
        int top = (this.y - this.sy / 2);
        int bottom = (this.y + this.sy / 2);
        boolean ver = top < y && y < bottom;
        return hor && ver;
    }
}

