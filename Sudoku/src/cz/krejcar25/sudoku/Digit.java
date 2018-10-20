package cz.krejcar25.sudoku;

import processing.core.*;

public class Digit {
    int x;
    int y;
    float sx;
    float sy;
    final byte[] digits = new byte[]{0x7E, 0x30, 0x6D, 0x79, 0x33, 0x5B, 0x5F, 0x70, 0x7F, 0x7B, 0x77, 0x1F, 0x4E, 0x3D, 0x4F, 0x47};
    PApplet applet;

    final int on;
    final int off;

    Digit(PApplet applet, int x, int y) {
        this.applet = applet;
        this.x = x;
        this.y = y;
        this.sx = 230;
        this.sy = 370;

        this.on = applet.color(255, 0, 0);
        this.off = applet.color(51, 0, 0);
    }

    public float getx(float y) {
        return y * (sx / sy);
    }

    public float gety(float x) {
        return x * (sy / sx);
    }

    public PGraphics show(int digit, boolean decimal) {
        return show(applet, (decimal) ? (byte) (digits[digit] + 0x80) : digits[digit]);
    }

    public PGraphics show(PApplet applet, byte segments) {
        PGraphics g = applet.createGraphics((int) sx, (int) sy);
        g.beginDraw();
        g.noStroke();
        PShape h = g.createShape();
        PShape v = g.createShape();

        h.beginShape();
        h.vertex(25, 0);
        h.vertex(125, 0);
        h.vertex(150, 25);
        h.vertex(125, 50);
        h.vertex(25, 50);
        h.vertex(0, 25);
        h.endShape(PApplet.CLOSE);

        v.beginShape();
        v.vertex(35, 0);
        v.vertex(60, 25);
        v.vertex(50, 125);
        v.vertex(25, 150);
        v.vertex(0, 125);
        v.vertex(10, 25);
        v.endShape(PApplet.CLOSE);

        g.fill(isBitOn(segments, 0));
        g.ellipse(215, 355, 30, 30);

        // A
        h.setFill(isBitOn(segments, 1));
        g.shape(h, 50, 0);
        // B
        v.setFill(isBitOn(segments, 2));
        g.shape(v, 170, 30);
        // C
        v.setFill(isBitOn(segments, 3));
        g.shape(v, 160, 190);
        // D
        h.setFill(isBitOn(segments, 4));
        g.shape(h, 30, 320);
        // E
        v.setFill(isBitOn(segments, 5));
        g.shape(v, 0, 190);
        // F
        v.setFill(isBitOn(segments, 6));
        g.shape(v, 10, 30);
        // G
        h.setFill(isBitOn(segments, 7));
        g.shape(h, 40, 160);

        g.endDraw();
        return g;
    }

    private int isBitOn(byte segments, int segment) {
        return (((segments >> (byte) (7 - segment)) & 0x1) == 1) ? on : off;
    }
}
