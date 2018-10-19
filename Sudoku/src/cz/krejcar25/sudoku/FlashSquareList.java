package cz.krejcar25.sudoku;

import java.util.ArrayList;

class FlashSquareList {
    ArrayList<FlashSquare> squares;
    FlashSquareList() {
        squares = new ArrayList<FlashSquare>();
    }

    public void newNow(int x, int y, int lifespan, int c) {
        push(new FlashSquare(x, y, lifespan, c));
    }

    public void push(FlashSquare square) {
        squares.add(square);
    }

    public boolean contains(int x, int y) {
        return colorOf(x, y) != -1;
    }

    public int colorOf(int x, int y) {
        for (int i = squares.size() - 1; i >= 0; i--) {
            FlashSquare square = squares.get(i);
            if (!(square.isValid())) {
                squares.remove(square);
            } else if (square.x == x && square.y == y) return square.c;
        }
        return -1;
    }

    public FlashSquareList clone() {
        FlashSquareList clone = new FlashSquareList();
        for (FlashSquare s : squares) {
            clone.squares.add(s);
        }
        return clone;
    }
}
