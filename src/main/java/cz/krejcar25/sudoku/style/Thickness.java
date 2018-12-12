package cz.krejcar25.sudoku.style;

public class Thickness {
    public float top;
    public float right;
    public float bottom;
    public float left;

    public Thickness(float trbl) {
        this.top = trbl;
        this.right = trbl;
        this.bottom = trbl;
        this.left = trbl;
    }

    public Thickness(float tb, float rl) {
        this.top = tb;
        this.right = rl;
        this.bottom = tb;
        this.left = rl;
    }

    public Thickness(float t, float r, float b, float l) {
        this.top = t;
        this.right = r;
        this.bottom = b;
        this.left = l;
    }

    public float totalTb() {
        return top + bottom;
    }

    public float totalRl() {
        return right + left;
    }
}
