package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.event.IControlClick;
import processing.core.*;

import java.awt.*;

public abstract class BaseGrid {
    BaseView parent;

    private int sizea;
    private int sizeb;
    int extraRows;
    private int baseClues;

    Point newGamePos, helpPos, orderTogglePos, deletePos, smallNumPos, settingsPos, exitPos, timerPos;
    private IControlClick newGameClick, helpClick, orderToggleClick, deleteClick, smallNumClick, settingsClick, exitClick;

    int[][] game;
    boolean[][] baseGame;
    private boolean[][][] notes;

    int selectedx = -1;
    int selectedy = -1;
    int selectedn = -1;

    protected int drawNumberOffset = 1;

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

    StopWatch timer;

    BaseGrid(BaseView parent, int sizea, int sizeb, int extraRows) {
        this.parent = parent;
        this.sizea = sizea;
        this.sizeb = sizeb;
        this.extraRows = extraRows;
        this.flashSquares = new FlashSquareList(parent.applet);
        smallNumbers = parent.applet.settings.isDefaultNotes();
        numFirst = parent.applet.settings.isDefaultNumberFirst();
        finalised = false;
        timer = new StopWatch("9x9 Grid");
        setColours();
        setClicks();

        int cols = cols();
        int rows = rows();
        game = new int[cols][rows];
        baseGame = new boolean[cols][rows];
        notes = new boolean[cols][rows][numbers()];

        for (int y = 0; y < rows; y++)
            for (int x = 0; x < cols; x++)
                game[x][y] = -1;
    }

    private void setColours() {
        gameFill = parent.applet.color(255);
        gameStroke = parent.applet.color(51);
        baseFill = parent.applet.color(220);
        neighbourFill = parent.applet.color(255, 255, 200);
        thisFill = parent.applet.color(255, 255, 100);
        buttonFill = parent.applet.color(51);
        buttonStroke = parent.applet.color(220);
        flashFillBad = parent.applet.color(255, 200, 200);
        flashFillGood = parent.applet.color(200, 255, 200);
        darkBgFore = parent.applet.color(220);
        lightBgFore = parent.applet.color(51);
        blue = parent.applet.color(0, 0, 255);
    }

    private void setClicks() {
        newGameClick = () -> {
            if (parent instanceof GameView) {
                ((GameView) parent).newGenerator();
                ((GameView) parent).generate();
            }
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
            timer.pause();
            parent.applet.stack.push(new SettingsView(parent.applet));
        };
        exitClick = () -> parent.applet.stack.pop();
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
        int sx = parent.sizex / cols();
        int sy = parent.sizey / (rows() + extraRows);
        parent.applet.push();
        parent.applet.stroke(0);
        parent.applet.strokeWeight(3);
        for (int i = 1; i < sizeb; i++) {
            int linex = i * sizea * sx;
            parent.applet.line(linex, 0, linex, parent.applet.height - extraRows * sy);
        }
        for (int i = 1; i <= sizea; i++) {
            int liney = i * sizeb * sy;
            parent.applet.line(0, liney, parent.applet.width, liney);
        }
        parent.applet.pop();
    }

    protected void drawNumber(Point p, int num, boolean black) {
        drawNumber(p.x, p.y, num, black);
    }

    private void drawNumber(int x, int y, int num, boolean black) {
        if (num > -1) {
            parent.applet.push();
            if (black) parent.applet.fill(lightBgFore);
            else if (y >= rows()) parent.applet.fill(darkBgFore);
            else parent.applet.fill(blue);
            drawText(x, y, String.format("%X",num + drawNumberOffset));
            parent.applet.pop();
        }
    }

    private void drawText(Point p, String text) {
        drawText(p.x, p.y, text);
    }

    private void drawText(int x, int y, String text) {
        parent.applet.push();
        int sx = parent.sizex / cols();
        int sy = parent.sizey / (rows() + extraRows);
        parent.applet.translate(x * sx, y * sy);
        //noinspection IntegerDivisionInFloatingPointContext
        parent.applet.translate(sx / 2, sy / 2);
        parent.applet.translate(0, -7);
        parent.applet.strokeWeight(0);
        parent.applet.textAlign(PApplet.CENTER, PApplet.CENTER);
        parent.applet.textSize(sy);
        parent.applet.text(text, 0, 0);
        parent.applet.pop();
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
        return Main.xor(x == ((ofx != -1) ? ofx : selectedx), y == ((ofy != -1) ? ofy : selectedy));
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
        if (output) Main.println("Clues locked: " + baseClues);
    }

    BaseGrid clone(BaseGrid clone) {
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

    public void show() {
        int cols = cols();
        int rows = rows();
        int sx = parent.sizex / cols;
        int sy = parent.sizey / (rows + extraRows);

        if (timer.isPaused()) timer.start();

        int[] counts = new int[numbers()];

        for (int y = 0; y < (rows + extraRows); y++) {
            for (int x = 0; x < cols; x++) {
                parent.applet.push();
                parent.applet.stroke((y >= rows) ? buttonStroke : gameStroke);
                parent.applet.fill(cellBg(x, y));

                parent.applet.rect(x * sx, y * sy, sx, sy);
                parent.applet.pop();

                if (y < rows && game[x][y] > -1) {
                    drawNumber(x, y, game[x][y], baseGame[x][y]);
                    counts[game[x][y]]++;
                }
                if (y < rows && game[x][y] < 0) {
                    parent.applet.push();
                    parent.applet.translate(x * sx, y * sy);
                    int sxs = sx / sizea;
                    int sys = sy / sizeb;
                    //noinspection IntegerDivisionInFloatingPointContext
                    parent.applet.translate(sxs / 2, sys / 2 - 3);
                    parent.applet.textSize(sys);
                    parent.applet.textAlign(PApplet.CENTER, PApplet.CENTER);
                    parent.applet.fill(0);
                    parent.applet.strokeWeight(0);
                    for (int i = 0; i < numbers(); i++) {
                        if (notes[x][y][i])
                            //noinspection IntegerDivisionInFloatingPointContext
                            parent.applet.text(String.format("%X", i + drawNumberOffset), (i % sizea) * sxs, Main.floor(i / sizea) * sys);
                    }
                    parent.applet.pop();
                }
            }
        }

        drawBigLines();

        boolean allDone = true;

        for (int i = 0; i < numbers(); i++) {
            drawNumber(i, rows, i, selectedn == i);
            parent.applet.push();
            parent.applet.translate(i * sx, rows * sy);
            int sxs = sx / 3;
            int sys = sy / 3;
            //noinspection IntegerDivisionInFloatingPointContext
            parent.applet.translate(sxs / 2, sys / 2 - 3);
            parent.applet.textSize(sys);
            parent.applet.textAlign(PApplet.CENTER, PApplet.CENTER);
            parent.applet.fill((selectedn == i) ? lightBgFore : darkBgFore);
            parent.applet.strokeWeight(0);
            parent.applet.text(counts[i], 2 * sxs, 0);
            if (counts[i] < numbers()) allDone = false;
            parent.applet.pop();
        }

        if (allDone && timer.isRunning()) {
            timer.stop();
            Main.println("Solved");
            parent.overlay = new WinOverlay(parent);
        }

        parent.applet.push();
        parent.applet.fill(darkBgFore);
        drawText(newGamePos, "*");
        parent.applet.pop();

        parent.applet.push();
        parent.applet.fill((flashSquares.contains(helpPos.x, helpPos.y)) ? lightBgFore : darkBgFore);
        drawText(helpPos, "?");
        parent.applet.pop();

        if (smallNumbers) {
            parent.applet.push();
            parent.applet.translate(smallNumPos.x * sx, smallNumPos.y * sy);
            parent.applet.strokeWeight(1);
            parent.applet.stroke(220);
            int sxs = sx / sizea;
            int sys = sy / sizeb;
            for (int i = 1; i < sizea; i++) {
                parent.applet.line(i * sxs, 0, i * sxs, sy);
            }
            for (int i = 1; i < sizeb; i++) {
                parent.applet.line(0, i * sys, sx, i * sys);
            }
            parent.applet.pop();
        } else {
            parent.applet.push();
            parent.applet.fill(220);
            parent.applet.strokeWeight(0);
            drawText(smallNumPos, "#");
            parent.applet.pop();
        }

        parent.applet.push();
        parent.applet.fill((selectedn == -2) ? lightBgFore : darkBgFore);
        drawText(deletePos, "x");
        parent.applet.pop();

        parent.applet.push();
        parent.applet.fill(220);
        parent.applet.strokeWeight(0);
        drawText(orderTogglePos, (numFirst) ? "N" : "C");
        parent.applet.pop();

        parent.applet.push();
        parent.applet.translate(settingsPos.x * sx, settingsPos.y * sy);
        //noinspection IntegerDivisionInFloatingPointContext
        parent.applet.translate(sx / 2, sy / 2);
        parent.applet.fill(darkBgFore);
        //noinspection IntegerDivisionInFloatingPointContext
        parent.applet.ellipse(0, 0, sx / 2, sy / 2);
        parent.applet.fill(buttonFill);
        //noinspection IntegerDivisionInFloatingPointContext
        parent.applet.ellipse(0, 0, sx / 3, sy / 3);
        parent.applet.rectMode(PApplet.CENTER);
        parent.applet.fill(darkBgFore);

        for (int i = 0; i < 8; i++) {
            parent.applet.push();
            parent.applet.noStroke();
            parent.applet.rotate(PApplet.PI * i / 4);
            //noinspection IntegerDivisionInFloatingPointContext
            parent.applet.translate(sx / 4, 0);
            //noinspection IntegerDivisionInFloatingPointContext
            parent.applet.rect(0, 0, sx / 8, sx / 8);
            parent.applet.pop();
        }
        parent.applet.pop();

        parent.applet.push();
        parent.applet.translate(exitPos.x * sx, exitPos.y * sy);
        parent.applet.image(parent.applet.door, 10, 10, sx - 20, sy - 20);
        parent.applet.pop();

        parent.applet.push();
        parent.applet.fill(buttonFill);
        parent.applet.stroke(buttonStroke);
        parent.applet.strokeWeight(1);
        parent.applet.rect(timerPos.x * sx, timerPos.y * sy, 2 * sx, sy);
        parent.applet.image(timer.show(parent.applet), timerPos.x * sx + 10, timerPos.y * sy + (sy - timer.gety(2 * sx - 20)) / 2, 2 * sx - 20, timer.gety(2 * sx - 20));
        parent.applet.pop();
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

    public abstract BaseGrid clone();

    public abstract void keyInput(int k);

    public abstract BaseSolver getSolver();
}
