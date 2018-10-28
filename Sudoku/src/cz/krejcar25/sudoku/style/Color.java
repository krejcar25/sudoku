package cz.krejcar25.sudoku.style;

import processing.core.PGraphics;

public class Color {
    public byte r;
    public byte g;
    public byte b;
    public byte a;

    public Color(byte gray) {
        this.r = gray;
        this.g = gray;
        this.b = gray;
    }

    public Color(byte gray, byte a) {
        this.r = gray;
        this.g = gray;
        this.b = gray;
        this.a = a;
    }

    public Color(byte r, byte g, byte b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(byte r, byte g, byte b, byte a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
}
