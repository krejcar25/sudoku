package cz.krejcar25.sudoku;

import processing.core.PApplet;
import java.util.ArrayList;

class FlashSquareList {
    ArrayList<FlashSquare> squares;
    PApplet applet;

    FlashSquareList(PApplet applet) {
        squares = new ArrayList<>();
        this.applet = applet;
    }

    void newNow(int x, int y, int lifespan, int c) {
        push(new FlashSquare(x, y, applet.frameCount, lifespan, c));
    }

    void push(FlashSquare square) {
        squares.add(square);
    }

    boolean contains(int x, int y) {
        return colorOf(x, y) != -1;
    }

    int colorOf(int x, int y) {
        for (int i = squares.size() - 1; i >= 0; i--) {
            FlashSquare square = squares.get(i);
            if (!(square.isValid(applet.frameCount))) {
                squares.remove(square);
            } else if (square.x == x && square.y == y) return square.c;
        }
        return -1;
    }

    @Override
    public FlashSquareList clone() {
        FlashSquareList clone = new FlashSquareList(applet);
        clone.squares.addAll(squares);
        return clone;
    }
}
