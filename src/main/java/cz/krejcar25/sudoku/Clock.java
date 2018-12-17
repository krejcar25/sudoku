package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.ui.Drawable;
import processing.core.PApplet;

public class Clock extends Drawable {
    private static final int baseWidth = 975;
    private static final int baseHeight = 370;

    private final Timer timer;
    private Digit minD;
    private Digit minU;
    private Digit secD;
    private Digit secU;

    public Clock(SudokuApplet sudokuApplet, int x, int y, String name) {
        super(sudokuApplet, x, y, baseWidth, baseHeight);
        timer = new Timer(name);
        minD = new Digit(getApplet(), 0, 0);
        minU = new Digit(getApplet(), minD.width, 0);
        secD = new Digit(getApplet(), minD.width + minU.width + 50, 0);
        secU = new Digit(getApplet(), minD.width + minU.width + 50 + secD.width, 0);
    }

    public void start() {
        timer.start();
    }

    public void pause() {
        timer.pause();
    }

    public void stop() {
        timer.stop();
    }

    public boolean isPaused() {
        return timer.isPaused();
    }

    public boolean isRunning() {
        return timer.isRunning();
    }

    public static float getWidthFromHeight(float y) {
        return y * baseWidth / baseHeight;
    }

    public static float getHeightFromWidth(float x) {
        return x * baseHeight / baseWidth;
    }

    @Override
    protected void draw() {
        long time = timer.getElapsedTimeSecs();
        minD.setDigit(PApplet.abs(PApplet.floor(PApplet.floor(time / 60) / 10)), false);
        minD.update();
        minU.setDigit(PApplet.abs(PApplet.floor(time / 60) % 10), false);
        minU.update();
        secD.setDigit(PApplet.abs(PApplet.floor(PApplet.floor(time % 60) / 10)), false);
        secD.update();
        secU.setDigit(PApplet.abs(PApplet.floor(time % 60) % 10), false);
        secU.update();

        background(51);

        image(minD, minD.x, minD.y);
        image(minU, minU.x, minU.y);
        pushStyle();
        fill((timer.getElapsedTime() % 1000 < 500) ? minD.on : minD.off);
        ellipse(minD.width + minU.width + 30, 120, 30, 30);
        ellipse(minD.width + minU.width + 20, 250, 30, 30);
        popStyle();
        image(secD, secD.x, secD.y);
        image(secU, secU.x, secU.y);
    }
}
