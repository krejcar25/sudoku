package cz.krejcar25.sudoku;

import processing.core.*;

import java.util.ArrayList;

class FlashSquare {
    int x, y;
    private int timestamp, lifespan;
    int c;

    FlashSquare(int x, int y, int timestamp, int lifespan, int c) {
        this.x = x;
        this.y = y;
        this.timestamp = timestamp;
        this.lifespan = lifespan;
        this.c = c;
    }

    boolean isValid(int time) {
        return time < timestamp + lifespan;
    }
}
