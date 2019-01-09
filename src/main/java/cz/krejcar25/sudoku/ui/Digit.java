package cz.krejcar25.sudoku.ui;

import processing.core.PShape;

public class Digit extends Drawable {
    private final byte[] digits = new byte[]{0x7E, 0x30, 0x6D, 0x79, 0x33, 0x5B, 0x5F, 0x70, 0x7F, 0x7B, 0x77, 0x1F, 0x4E, 0x3D, 0x4F, 0x47};

    public final int on;
    public final int off;

    private byte segments;

    Digit(Applet applet, int x, int y) {
        super(applet, x, y, 230, 370);

        this.on = color(255, 0, 0);
        this.off = color(51, 0, 0);
    }

    public void setDigit(int digit, boolean decimal) {
        segments = (decimal) ? (byte) (digits[digit] + 0x80) : digits[digit];
    }

    @Override
    protected void draw() {
        noStroke();
        PShape h = createShape();
        PShape v = createShape();

        h.beginShape();
        h.vertex(25, 0);
        h.vertex(125, 0);
        h.vertex(150, 25);
        h.vertex(125, 50);
        h.vertex(25, 50);
        h.vertex(0, 25);
        h.endShape(CLOSE);

        v.beginShape();
        v.vertex(35, 0);
        v.vertex(60, 25);
        v.vertex(50, 125);
        v.vertex(25, 150);
        v.vertex(0, 125);
        v.vertex(10, 25);
        v.endShape(CLOSE);

        fill(isBitOn(segments, 0));
        ellipse(215, 355, 30, 30);

        // A
        h.setFill(isBitOn(segments, 1));
        shape(h, 50, 0);
        // B
        v.setFill(isBitOn(segments, 2));
        shape(v, 170, 30);
        // C
        v.setFill(isBitOn(segments, 3));
        shape(v, 160, 190);
        // D
        h.setFill(isBitOn(segments, 4));
        shape(h, 30, 320);
        // E
        v.setFill(isBitOn(segments, 5));
        shape(v, 0, 190);
        // F
        v.setFill(isBitOn(segments, 6));
        shape(v, 10, 30);
        // G
        h.setFill(isBitOn(segments, 7));
        shape(h, 40, 160);
    }

    private int isBitOn(byte segments, int segment) {
        return (((segments >> (byte) (7 - segment)) & 0x1) == 1) ? on : off;
    }
}
