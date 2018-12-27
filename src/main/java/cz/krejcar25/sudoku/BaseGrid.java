package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.event.ControlClick;
import cz.krejcar25.sudoku.ui.Drawable;
import processing.core.*;

import java.awt.*;

public class BaseGrid extends Drawable {
    GameView view;

    private int sizea;
    private int sizeb;
    int extraRows;
    private int baseClues;

    private Point newGamePos, helpPos, orderTogglePos, deletePos, smallNumPos, settingsPos, exitPos, timerPos;
    private int drawNumberOffset;
    private GridProperties gridProperties;
    private ControlClick newGameClick, helpClick, orderToggleClick, deleteClick, smallNumClick, settingsClick, exitClick;

    int[][] game;
    boolean[][] baseGame;
    private boolean[][][] notes;

    int selectedx = -1;
    int selectedy = -1;
    int selectedn = -1;

    private int gameFill;
    private int gameStroke;
    private int baseFill;
    private int neighbourFill;
    private int thisFill;
    private int buttonFill;
    private int buttonStroke;
    private int flashFillBad;
    private int flashFillGood;
    private int darkBgFore;
    private int lightBgFore;
    private int blue;

    private FlashSquareList flashSquares;
    private boolean smallNumbers;
    boolean numFirst;

    private boolean finalised;

    private final int cols;
    private final int rows;
    protected final int sx;
    protected final int sy;

    Clock gameClock;

    BaseGenerator generator;

    GridDifficulty gridDifficulty;

    BaseGrid(GameView parent, GridProperties gridProperties) {
        super(parent.getApplet(), 0, 0, parent.width, parent.height);
        this.view = parent;
        this.sizea = gridProperties.getSizea();
        this.sizeb = gridProperties.getSizeb();
        this.extraRows = gridProperties.getControlRows() + 1;
        this.flashSquares = new FlashSquareList(getApplet());
        smallNumbers = getApplet().settings.isDefaultNotes();
        numFirst = getApplet().settings.isDefaultNumberFirst();
        finalised = false;
        setColours();
        setClicks();

        this.cols = cols();
        this.rows = rows();
        this.sx = width / cols;
        this.sy = height / (rows + extraRows);

        game = new int[cols][rows];
        baseGame = new boolean[cols][rows];
        notes = new boolean[cols][rows][numbers()];

        for (int y = 0; y < rows; y++)
            for (int x = 0; x < cols; x++)
                game[x][y] = -1;

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

        gameClock = new Clock(getApplet(), timerPos.x * sx + 10, (int) (timerPos.y * sy + (sy - Clock.getHeightFromWidth(2 * sx - 20)) / 2), gridProperties.getName());
    }

    private void setColours() {
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
    }

    private void setClicks() {
        newGameClick = () -> {
            view.newGenerator();
            gameClock = new Clock(getApplet(), gameClock.x, gameClock.y, gridProperties.getName());
            view.generate(gridDifficulty, getApplet().isKeyPressed(SHIFT));
        };
        helpClick = () -> {
            int count = getSolver().countSolutions();
            if (count < 1) flashSquares.newNow(helpPos.x, helpPos.y, 40, flashFillBad);
            else if (count > 1) flashSquares.newNow(helpPos.x, helpPos.y, 40, neighbourFill);
            else flashSquares.newNow(helpPos.x, helpPos.y, 40, flashFillGood);
        };
        orderToggleClick = () -> {
            numFirst = !numFirst;
            selectedx = -1;
            selectedy = -1;
            selectedn = -1;
        };
        deleteClick = () -> {
            if (numFirst) selectedn = (selectedn == -2) ? -1 : -2;
            else placeNumber(-2, selectedx, selectedy);
        };
        smallNumClick = () -> smallNumbers = !smallNumbers;
        settingsClick = () -> {
            gameClock.pause();
            getApplet().stack.push(new SettingsView(getApplet()));
        };
        exitClick = () -> {
            getApplet().stack.pop(2);
            generator.stopGeneration();
        };
    }

    int cols() {
        return sizea * sizeb;
    }

    int rows() {
        return sizea * sizeb;
    }

    int numbers() {
        return sizea * sizeb;
    }

    private void drawBigLines() {
        int sx = width / cols();
        int sy = height / (rows() + extraRows);
        push();
        stroke(0);
        strokeWeight(3);
        for (int i = 1; i < sizeb; i++) {
            int linex = i * sizea * sx;
            line(linex, 0, linex, height - extraRows * sy);
        }
        for (int i = 1; i <= sizea; i++) {
            int liney = i * sizeb * sy;
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
            else if (y >= rows()) fill(darkBgFore);
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
        int sx = width / cols();
        int sy = height / (rows() + extraRows);
        translate(x * sx, y * sy);
        //noinspection IntegerDivisionInFloatingPointContext
        translate(sx / 2, sy / 2);
        translate(0, -7);
        strokeWeight(0);
        textAlign(PApplet.CENTER, PApplet.CENTER);
        textSize(sy);
        text(text, 0, 0);
        pop();
    }

    private int cellBg(int x, int y) {
        if (flashSquares.contains(x, y)) return flashSquares.colorOf(x, y);
        else if (x == selectedn && y == rows()) return thisFill;
        else if (x == deletePos.x && y == deletePos.y && selectedn == -2) return thisFill;
        else if (y < rows() && selectedn != -1 && selectedn != -2 && (selectedn == game[x][y] || (game[x][y] == -1 && notes[x][y][selectedn])))
            return neighbourFill;
        else if (y >= rows()) return buttonFill;
        else if (x == selectedx && y == selectedy) return thisFill;
        else if (x < cols() && selectedx > -1 && selectedy > -1 && isRowColSc(x, y)) return neighbourFill;
        else if (y < rows() && baseGame[x][y]) return baseFill;
        else return gameFill;
    }

    boolean isRowCol(int x, int y) {
        return isRowCol(x, y, -1, -1);
    }

    private boolean isRowCol(int x, int y, int ofx, int ofy) {
        return SudokuApplet.xor(x == ((ofx != -1) ? ofx : selectedx), y == ((ofy != -1) ? ofy : selectedy));
    }

    boolean isSc(int x, int y) {
        return isSc(x, y, -1, -1);
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    private boolean isSc(int x, int y, int ofx, int ofy) {
        boolean isScX = PApplet.floor(x / sizea) == PApplet.floor(((ofx != -1) ? ofx : selectedx) / sizea);
        boolean isScY = PApplet.floor(y / sizeb) == PApplet.floor(((ofy != -1) ? ofy : selectedy) / sizeb);

        return isScX && isScY;
    }

    private boolean isRowColSc(int x, int y) {
        return isRowColSc(x, y, -1, -1);
    }

    private boolean isRowColSc(int x, int y, int ofx, int ofy) {
        return isRowCol(x, y, ofx, ofy) || isSc(x, y, ofx, ofy);
    }

    void select(int x, int y) {
        selectedx = x;
        selectedy = y;
    }

    void placeNumber(int num, int x, int y) {
        if (x > -1 && y > -1 && !baseGame[x][y]) {
            if (num > -1 && canPlaceNumber(num, x, y, 40)) {
                if (smallNumbers) {
                    notes[x][y][num] = !notes[x][y][num];
                } else {
                    game[x][y] = num;
                    for (int ry = 0; ry < rows(); ry++) {
                        for (int rx = 0; rx < cols(); rx++) {
                            if (isRowColSc(rx, ry, x, y)) notes[rx][ry][num] = false;
                        }
                    }
                }
            } else if (num == -2) {
                if (smallNumbers) {
                    for (int i = 0; i < numbers(); i++) {
                        notes[x][y][i] = false;
                    }
                } else {
                    game[x][y] = -1;
                }
            }
        }
    }

    boolean canPlaceNumber(int num, int atx, int aty, int flashTime) {
        boolean can = true;
        for (int y = 0; y < rows(); y++) {
            for (int x = 0; x < cols(); x++) {
                if (isRowColSc(x, y, atx, aty) && game[x][y] == num) {
                    can = false;
                    if (flashTime > -1) flashSquares.newNow(x, y, flashTime, flashFillBad);
                }
            }
        }
        return can;
    }

    void lockAsBase(boolean output, boolean finalise) {
        baseClues = 0;
        for (int y = 0; y < rows(); y++) {
            for (int x = 0; x < cols(); x++) {
                if (game[x][y] > -1) {
                    baseGame[x][y] = true;
                    baseClues++;
                } else {
                    baseGame[x][y] = false;
                }
            }
        }
        if (output) SudokuApplet.println("Clues locked: " + baseClues);
    }

    @Override
    public BaseGrid clone() {
        BaseGrid clone = new BaseGrid(view, gridProperties);
        clone.baseClues = baseClues;
        clone.game = new int[clone.cols()][clone.rows()];
        for (int y = 0; y < clone.rows(); y++) {
            for (int x = 0; x < clone.cols(); x++) {
                clone.game[x][y] = game[x][y];
                clone.baseGame[x][y] = baseGame[x][y];
                if (clone.numbers() >= 0) System.arraycopy(notes[x][y], 0, clone.notes[x][y], 0, clone.numbers());
            }
        }
        clone.selectedx = selectedx;
        clone.selectedy = selectedy;
        clone.selectedn = selectedn;

        clone.flashSquares = flashSquares.clone();

        clone.smallNumbers = smallNumbers;
        clone.numFirst = numFirst;
        clone.finalised = finalised;

        return clone;
    }

    @Override
    protected void draw() {
        if (gameClock.isPaused()) gameClock.start();

        int[] counts = new int[numbers()];

        for (int y = 0; y < (rows + extraRows); y++) {
            for (int x = 0; x < cols; x++) {
                push();
                stroke((y >= rows) ? buttonStroke : gameStroke);
                fill(cellBg(x, y));

                rect(x * sx, y * sy, sx, sy);
                pop();


                if (y < rows) {
                    int thisNum = game[x][y];
                    if (thisNum > -1) {
                        drawNumber(x, y, thisNum, baseGame[x][y]);
                        counts[thisNum]++;
                    } else {
                        push();
                        translate(x * sx, y * sy);
                        int sxs = sx / sizea;
                        int sys = sy / sizeb;
                        //noinspection IntegerDivisionInFloatingPointContext
                        translate(sxs / 2, sys / 2 - 3);
                        textSize(sys);
                        textAlign(PApplet.CENTER, PApplet.CENTER);
                        fill(0);
                        strokeWeight(0);
                        for (int i = 0; i < numbers(); i++) {
                            if (notes[x][y][i])
                                //noinspection IntegerDivisionInFloatingPointContext
                                text(String.format("%X", i + drawNumberOffset), (i % sizea) * sxs, SudokuApplet.floor(i / sizea) * sys);
                        }
                        pop();
                    }

                }
            }
        }

        drawBigLines();

        boolean allDone = true;

        for (int i = 0; i < numbers(); i++) {
            drawNumber(i, rows, i, selectedn == i);
            push();
            translate(i * sx, rows * sy);
            int sxs = sx / 3;
            int sys = sy / 3;
            //noinspection IntegerDivisionInFloatingPointContext
            translate(sxs / 2, sys / 2 - 3);
            textSize(sys);
            textAlign(PApplet.CENTER, PApplet.CENTER);
            fill((selectedn == i) ? lightBgFore : darkBgFore);
            strokeWeight(0);
            text(counts[i], 2 * sxs, 0);
            if (counts[i] < numbers()) allDone = false;
            pop();
        }

        if (allDone && gameClock.isRunning()) {
            gameClock.stop();
            SudokuApplet.println("Solved");
            view.overlay = new WinOverlay(view);
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
            int sxs = sx / sizea;
            int sys = sy / sizeb;
            for (int i = 1; i < sizea; i++) {
                line(i * sxs, 0, i * sxs, sy);
            }
            for (int i = 1; i < sizeb; i++) {
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
        //noinspection IntegerDivisionInFloatingPointContext
        translate(sx / 2, sy / 2);
        fill(darkBgFore);
        //noinspection IntegerDivisionInFloatingPointContext
        ellipse(0, 0, sx / 2, sy / 2);
        fill(buttonFill);
        //noinspection IntegerDivisionInFloatingPointContext
        ellipse(0, 0, sx / 3, sy / 3);
        rectMode(PApplet.CENTER);
        fill(darkBgFore);

        for (int i = 0; i < 8; i++) {
            push();
            noStroke();
            rotate(PApplet.PI * i / 4);
            //noinspection IntegerDivisionInFloatingPointContext
            translate(sx / 4, 0);
            //noinspection IntegerDivisionInFloatingPointContext
            rect(0, 0, sx / 8, sx / 8);
            pop();
        }
        pop();

        push();
        translate(exitPos.x * sx, exitPos.y * sy);
        image(getApplet().door, 10, 10, sx - 20, sy - 20);
        pop();

        push();
        fill(buttonFill);
        stroke(buttonStroke);
        strokeWeight(1);
        rect(timerPos.x * sx, timerPos.y * sy, 2 * sx, sy);
        gameClock.update();
        image(gameClock, gameClock.x, gameClock.y, 2 * sx - 20, Clock.getHeightFromWidth(2 * sx - 20));
        pop();
    }

    public void click(int x, int y, boolean right) {
        int cols = cols();
        int rows = rows();
        if (right) {
            if (x < cols && y < rows) {
                placeNumber(-2, x, y);
                return;
            }
        }

        if (y == rows) {
            if (numFirst && x < rows) selectedn = (selectedn == x) ? -1 : x;
            else if (x < rows) placeNumber(x, selectedx, selectedy);
        } else if (y > rows) {
            if (x == newGamePos.x && y == newGamePos.y) newGameClick.click();
            else if (x == helpPos.x && y == helpPos.y) helpClick.click();
            else if (x == orderTogglePos.x && y == orderTogglePos.y) orderToggleClick.click();
            else if (x == deletePos.x && y == deletePos.y) deleteClick.click();
            else if (x == smallNumPos.x && y == smallNumPos.y) smallNumClick.click();
            else if (x == settingsPos.x && y == settingsPos.y) settingsClick.click();
            else if (x == exitPos.x && y == exitPos.y) exitClick.click();
        }

        if (x == selectedx && y == selectedy) {
            selectedx = -1;
            selectedy = -1;
        } else if (x < cols && y < rows) {
            if (numFirst) {
                if (selectedn > -1 || selectedn == -2) placeNumber(selectedn, x, y);
            } else {
                selectedx = x;
                selectedy = y;
            }
        }
    }

    public BaseSolver getSolver() {
        return new BaseSolver(this);
    }

    public void keyInput(int k) {
        if (k > (1 - drawNumberOffset) && k < (numbers() + drawNumberOffset)) {
            if (numFirst) {
                selectedn = (selectedn == (k - drawNumberOffset)) ? -1 : (k - drawNumberOffset);
            } else {
                placeNumber(k - drawNumberOffset, selectedx, selectedy);
            }
        }
    }
}
