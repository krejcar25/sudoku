package cz.krejcar25.sudoku.style;

import processing.core.PGraphics;

public class Color {
    public int r;
    public int g;
    public int b;
    public int a;

    public Color(int gray) {
        this.r = gray;
        this.g = gray;
        this.b = gray;
    }

    public Color(int gray, int a) {
        this.r = gray;
        this.g = gray;
        this.b = gray;
        this.a = a;
    }

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
}
