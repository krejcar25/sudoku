package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.ui.Drawable;
import processing.core.*;

public class Timer {

    private long startTime = 0;
    private long stopTime = 0;
    private long pauseStart = -1;
    private long pauseTotal = 0;
    private boolean running = false;
    private boolean finished = false;
    public String name;

    public Timer(String name) {
        this.name = name;
    }

    public void start() {
        start(0);
    }

    public void start(long offset) {
        if (finished) return;
        long startTime = System.currentTimeMillis() - offset;
        if (running && pauseStart > -1) {
            pauseTotal += (startTime - pauseStart);
            pauseStart = -1;
            System.out.println("Timer " + name + " resumed at " + startTime);
        } else if (!running) {
            this.startTime = startTime;
            System.out.println("Timer " + name + " started at " + startTime);
            this.running = true;
        }
    }

    public void pause() {
        pause(0);
    }

    public void pause(long offset) {
        if (running && pauseStart == -1) {
            long pauseStart = System.currentTimeMillis() - offset;
            this.pauseStart = pauseStart;
            System.out.println("Timer " + name + " paused at " + pauseStart);
        }
    }

    public void stop() {
        if (!running) return;
        this.stopTime = System.currentTimeMillis();
        System.out.println("Timer " + name + " stopped at " + stopTime);
        this.running = false;
        this.finished = true;
    }


    //elaspsed time in milliseconds
    public long getElapsedTime() {
        long elapsed = -pauseTotal;
        if (running) {
            elapsed += (System.currentTimeMillis() - startTime);
        } else {
            elapsed += (stopTime - startTime);
        }
        return elapsed;
    }


    //elaspsed time in seconds
    public long getElapsedTimeSecs() {
        return getElapsedTime() / 1000;
    }

    public boolean isPaused() {
        return pauseStart > -1;
    }

    public boolean isRunning() {
        return running;
    }
}
