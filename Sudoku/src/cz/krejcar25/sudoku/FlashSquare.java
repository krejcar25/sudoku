package cz.krejcar25.sudoku;

import processing.core.*;
import java.util.ArrayList;

class FlashSquare {
    int x, y, timestamp, lifespan;
    int c;
    FlashSquare(int x, int y, int lifespan, int c) {
        this.x = x;
        this.y = y;
        this.timestamp = Main.pa.frameCount;
        this.lifespan = lifespan;
        this.c = c;
    }

    boolean isValid() {
        return Main.pa.frameCount < timestamp + lifespan;
    }
}
