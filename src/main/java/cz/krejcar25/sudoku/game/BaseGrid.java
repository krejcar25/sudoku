package cz.krejcar25.sudoku.game;

import cz.krejcar25.sudoku.SettingsView;
import cz.krejcar25.sudoku.SudokuApplet;
import cz.krejcar25.sudoku.event.ControlClick;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.Clock;
import cz.krejcar25.sudoku.ui.Drawable;
import processing.core.PApplet;

import java.awt.*;

public class BaseGrid extends Drawable {
    BaseView view;

    private int extraRows;

    private Point newGamePos;
    private Point helpPos;
    private Point orderTogglePos;
    private Point deletePos;
    private Point smallNumPos;
    private Point settingsPos;
    private Point exitPos;
    private Point timerPos;
    private int drawNumberOffset;
    private GridProperties gridProperties;
    private ControlClick newGameClick;
    private ControlClick helpClick;
    private ControlClick orderToggleClick;
    private ControlClick deleteClick;
    private ControlClick smallNumClick;
    private ControlClick settingsClick;
    private ControlClick exitClick;

    private GridCore core;
    private boolean[][][] notes;

    private int selectedn = -1;

    final int gameFill;
    final int gameStroke;
    final int baseFill;
    final int neighbourFill;
    final int thisFill;
    final int buttonFill;
    final int buttonStroke;
    final int flashFillBad;
    final int flashFillGood;
    final int darkBgFore;
    final int lightBgFore;
    final int blue;

    FlashSquareList flashSquares;
    private boolean smallNumbers;
    private boolean numFirst;

    private final float sx;
    private final float sy;

    private Clock gameClock;

    volatile BaseGenerator generator;
    volatile boolean shouldUpdateGrid = true;

    GridDifficulty gridDifficulty;

    private Drawable gear;

    BaseGrid(BaseView parent, GridProperties gridProperties) {
        super(parent.getApplet(), 0, 0, parent.width, parent.height);
        this.view = parent;
        this.core = new GridCore(gridProperties);
        this.core.setOwner(this);
        this.extraRows = gridProperties.getControlRows() + 1;
        this.flashSquares = new FlashSquareList(getApplet());
        smallNumbers = getRootApplet().settings.isDefaultNotes();
        numFirst = getRootApplet().settings.isDefaultNumberFirst();

        gameFill = color(255);
        gameStroke = color(51);
        baseFill = color(220);
        neighbourFill = color(255, 255, 200);
        thisFill = color(255, 255, 100);
        buttonFill = color(51);
        buttonStroke = color(220);
        flashFillBad = color(255, 200, 200);
        flashFillGood = color(200, 255, 200);
        darkBgFore = color(220);
        lightBgFore = color(51);
        blue = color(0, 0, 255);

        setClicks();

        this.sx = (float) width / core.ncr;
        this.sy = (float) height / (core.ncr + extraRows);

        notes = new boolean[core.ncr][core.ncr][core.ncr];

        newGamePos = gridProperties.getNewGamePos();
        helpPos = gridProperties.getHelpPos();
        orderTogglePos = gridProperties.getOrderTogglePos();
        deletePos = gridProperties.getDeletePos();
        smallNumPos = gridProperties.getSmallNumPos();
        settingsPos = gridProperties.getSettingsPos();
        exitPos = gridProperties.getExitPos();
        timerPos = gridProperties.getTimerPos();

        drawNumberOffset = gridProperties.getDrawNumberOffset();

        this.gridProperties = gridProperties;

	    gameClock = new Clock(getApplet(), (timerPos.x * sx + 10), 0, gridProperties.getName());
	    gameClock.setDisplayHeightWithWidth(2 * sx - 20);
	    gameClock.y = timerPos.y * sy + (sy - gameClock.getDisplayHeight()) / 2;

        gear = new Drawable(getApplet(), 0, 0, 80, 80) {
            @Override
            protected void draw() {
                translate(width / 2f, height / 2f);
                fill(darkBgFore);
                ellipse(0, 0, width / 2f, height / 2f);
                fill(buttonFill);
                ellipse(0, 0, width / 3f, height / 3f);
                rectMode(PApplet.CENTER);
                fill(darkBgFore);

                for (int i = 0; i < 8; i++) {
                    push();
                    noStroke();
	                this.g.rotate(PApplet.PI * i / 4);
                    translate(width / 4f, 0);
                    rect(0, 0, width / 8f, height / 8f);
                    pop();
                }
            }
        };
        gear.update();
    }

    private void setClicks() {
        newGameClick = () -> {
            gameClock = new Clock(getApplet(), gameClock.x, gameClock.y, gridProperties.getName());

            generator = new BaseGenerator(core);
            if (view instanceof GameView) view.setOverlay(new GeneratingOverlay((GameView) view, gridDifficulty));
            if (!getApplet().isKeyPressed(Applet.SHIFT)) shouldUpdateGrid = false;
            Thread t = new Thread(() -> {
                generator.generate(gridProperties.getClueCount(gridDifficulty), false);
                view.setOverlay(null);
                shouldUpdateGrid = true;
            });
            t.start();

        };
        helpClick = () -> {
            int count = getSolver().countSolutions();
            if (count < 1) flashSquares.newNow(helpPos.x, helpPos.y, 40, flashFillBad);
            else if (count > 1) flashSquares.newNow(helpPos.x, helpPos.y, 40, neighbourFill);
            else flashSquares.newNow(helpPos.x, helpPos.y, 40, flashFillGood);
        };
        orderToggleClick = () -> {
            numFirst = !numFirst;
            core.select(-1, -1);
            selectedn = -1;
        };
        deleteClick = () -> {
            if (numFirst) selectedn = (selectedn == -2) ? -1 : -2;
            else placeNumber(-2, core.getSelectedx(), core.getSelectedy());
        };
        smallNumClick = () -> smallNumbers = !smallNumbers;
        settingsClick = () -> {
            gameClock.pause();
            view.getViewStack().push(new SettingsView(getApplet()));
        };
        exitClick = () -> {
            view.getViewStack().pop(2);
            generator.stopGeneration();
        };
    }

    private void drawBigLines() {
        push();
        stroke(0);
        strokeWeight(3);
        for (int i = 1; i < core.sizeb; i++) {
            int linex = Applet.floor(i * core.sizea * sx);
            line(linex, 0, linex, height - extraRows * sy);
        }
        for (int i = 1; i <= core.sizea; i++) {
            int liney = Applet.floor(i * core.sizeb * sy);
            line(0, liney, width, liney);
        }
        pop();
    }

    protected void drawNumber(Point p, int num, boolean black) {
        drawNumber(p.x, p.y, num, black);
    }

    private void drawNumber(int x, int y, int num, boolean black) {
        if (num > -1) {
            push();
            if (black) fill(lightBgFore);
            else if (y >= core.ncr) fill(darkBgFore);
            else fill(blue);
            drawText(x, y, String.format("%X", num + drawNumberOffset));
            pop();
        }
    }

    private void drawText(Point p, String text) {
        drawText(p.x, p.y, text);
    }

    private void drawText(int x, int y, String text) {
        push();
        translate(x * sx, y * sy);
        translate(sx / 2f, sy / 2f);
        translate(0, -7);
        strokeWeight(0);
        textAlign(PApplet.CENTER, PApplet.CENTER);
        textSize(sy);
        text(text, 0, 0);
        pop();
    }

    private int cellBg(int x, int y) {
        if (flashSquares.contains(x, y)) return flashSquares.colorOf(x, y);
        else if (x == selectedn && y == core.ncr) return thisFill;
        else if (x == deletePos.x && y == deletePos.y && selectedn == -2) return thisFill;
        else if (y < core.ncr && selectedn != -1 && selectedn != -2 && (selectedn == core.get(x, y) || (core.get(x, y) == -1 && notes[x][y][selectedn])))
            return neighbourFill;
        else if (y >= core.ncr) return buttonFill;
        else if (x == core.getSelectedx() && y == core.getSelectedy()) return thisFill;
        else if (x < core.ncr && core.getSelectedx() > -1 && core.getSelectedy() > -1 && core.isRowColSc(x, y))
            return neighbourFill;
        else if (y < core.ncr && core.isBaseGame(x, y)) return baseFill;
        else return gameFill;
    }

    void placeNumber(int num, int x, int y) {
        if (x > -1 && y > -1 && !core.isBaseGame(x, y)) {
            if (num > -1 && core.canPlaceNumber(num, x, y, 40)) {
                if (smallNumbers) {
                    notes[x][y][num] = !notes[x][y][num];
                } else {
                    core.set(x, y, num);
                    for (int ry = 0; ry < core.ncr; ry++) {
                        for (int rx = 0; rx < core.ncr; rx++) {
                            if (core.isRowColSc(rx, ry, x, y)) notes[rx][ry][num] = false;
                        }
                    }
                }
            } else if (num == -2) {
                if (smallNumbers) {
                    for (int i = 0; i < core.ncr; i++) {
                        notes[x][y][i] = false;
                    }
                } else {
                    core.set(x, y, -1);
                }
            }
        }
    }

    @Override
    protected void draw() {
        if (gameClock.isPaused()) gameClock.start();

        int[] counts = new int[core.ncr];

        for (int y = 0; y < (core.ncr + extraRows); y++) {
            for (int x = 0; x < core.ncr; x++) {
                push();
                stroke((y >= core.ncr) ? buttonStroke : gameStroke);
                fill(cellBg(x, y));

                rect(x * sx, y * sy, sx, sy);
                pop();


                if (y < core.ncr) {
                    int thisNum = core.get(x, y);
                    if (thisNum > -1) {
                        drawNumber(x, y, thisNum, core.isBaseGame(x, y));
                        counts[thisNum]++;
                    } else {
                        push();
                        translate(x * sx, y * sy);
                        int sxs = (int) (sx / core.sizea);
                        int sys = (int) (sy / core.sizeb);
                        translate(sxs / 2f, sys / 2f - 3);
                        textSize(sys);
                        textAlign(PApplet.CENTER, PApplet.CENTER);
                        fill(0);
                        strokeWeight(0);
                        for (int i = 0; i < core.ncr; i++) {
                            if (notes[x][y][i])
                                text(String.format("%X", i + drawNumberOffset), (i % core.sizea) * sxs, SudokuApplet.floor(((float) i) / core.sizea) * sys);
                        }
                        pop();
                    }

                }
            }
        }

        drawBigLines();

        boolean allDone = true;

        for (int i = 0; i < core.ncr; i++) {
            drawNumber(i, core.ncr, i, selectedn == i);
            push();
            translate(i * sx, core.ncr * sy);
            int sxs = (int) (sx / 3);
            int sys = (int) (sy / 3);
            translate(sxs / 2f, sys / 2f - 3);
            textSize(sys);
            textAlign(PApplet.CENTER, PApplet.CENTER);
            fill((selectedn == i) ? lightBgFore : darkBgFore);
            strokeWeight(0);
            text(counts[i], 2 * sxs, 0);
            if (counts[i] < core.ncr) allDone = false;
            pop();
        }

        if (allDone && gameClock.isRunning()) {
            gameClock.stop();
            SudokuApplet.println("Solved");
            view.setOverlay(new WinOverlay(view, this));
        }

        push();
        fill(darkBgFore);
        drawText(newGamePos, "*");
        pop();

        push();
        fill((flashSquares.contains(helpPos.x, helpPos.y)) ? lightBgFore : darkBgFore);
        drawText(helpPos, "?");
        pop();

        if (smallNumbers) {
            push();
            translate(smallNumPos.x * sx, smallNumPos.y * sy);
            strokeWeight(1);
            stroke(220);
            int sxs = (int) (sx / core.sizea);
            int sys = (int) (sy / core.sizeb);
            for (int i = 1; i < core.sizea; i++) {
                line(i * sxs, 0, i * sxs, sy);
            }
            for (int i = 1; i < core.sizeb; i++) {
                line(0, i * sys, sx, i * sys);
            }
            pop();
        } else {
            push();
            fill(220);
            strokeWeight(0);
            drawText(smallNumPos, "#");
            pop();
        }

        push();
        fill((selectedn == -2) ? lightBgFore : darkBgFore);
        drawText(deletePos, "x");
        pop();

        push();
        fill(220);
        strokeWeight(0);
        drawText(orderTogglePos, (numFirst) ? "N" : "C");
        pop();

        push();
        translate(settingsPos.x * sx, settingsPos.y * sy);
        image(gear, 0, 0, sx, sy);

        pop();

        push();
        translate(exitPos.x * sx, exitPos.y * sy);
        image(getRootApplet().door, 10, 10, sx - 20, sy - 20);
        pop();

        push();
        fill(buttonFill);
        stroke(buttonStroke);
        strokeWeight(1);
        rect(timerPos.x * sx, timerPos.y * sy, 2 * sx, sy);
        gameClock.update();
	    image(gameClock, gameClock.x, gameClock.y, gameClock.getDisplayWidth(), gameClock.getDisplayHeight());
        pop();
    }

    public void click(int mx, int my, boolean right) {
        int x = PApplet.floor(mx / sx);
        int y = PApplet.floor(my / sy);

        if (right) {
            if (x < core.ncr && y < core.ncr) {
                placeNumber(-2, x, y);
                return;
            }
        }

        if (y == core.ncr) {
            if (numFirst && x < core.ncr) selectedn = (selectedn == x) ? -1 : x;
            else if (x < core.ncr) placeNumber(x, core.getSelectedx(), core.getSelectedy());
        } else if (y > core.ncr) {
            if (x == newGamePos.x && y == newGamePos.y) newGameClick.click();
            else if (x == helpPos.x && y == helpPos.y) helpClick.click();
            else if (x == orderTogglePos.x && y == orderTogglePos.y) orderToggleClick.click();
            else if (x == deletePos.x && y == deletePos.y) deleteClick.click();
            else if (x == smallNumPos.x && y == smallNumPos.y) smallNumClick.click();
            else if (x == settingsPos.x && y == settingsPos.y) settingsClick.click();
            else if (x == exitPos.x && y == exitPos.y) exitClick.click();
        }

        if (x == core.getSelectedx() && y == core.getSelectedy()) {
            core.select(-1, -1);
        } else if (x < core.ncr && y < core.ncr) {
            if (numFirst) {
                if (selectedn > -1 || selectedn == -2) placeNumber(selectedn, x, y);
            } else {
                core.select(x, y);
            }
        }
    }

    public void hideControls() {
        extraRows = 0;
    }

    public BaseSolver getSolver() {
        return new BaseSolver(core);
    }

    public GridProperties getGridProperties() {
        return gridProperties;
    }

    public GridDifficulty getGridDifficulty() {
        return gridDifficulty;
    }

    public Clock getGameClock() {
        return gameClock;
    }

    public float getSx() {
        return sx;
    }

    public float getSy() {
        return sy;
    }

    public void keyInput(int k) {
        if (k > (1 - drawNumberOffset) && k < (core.ncr + drawNumberOffset)) {
            if (numFirst) {
                selectedn = (selectedn == (k - drawNumberOffset)) ? -1 : (k - drawNumberOffset);
            } else {
                placeNumber(k - drawNumberOffset, core.getSelectedx(), core.getSelectedy());
            }
        }
    }

    public GridCore getCore() {
        return core;
    }
}
