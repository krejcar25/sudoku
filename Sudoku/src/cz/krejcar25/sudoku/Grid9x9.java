package cz.krejcar25.sudoku;

import java.util.ArrayList;
import processing.core.*;

public class Grid9x9 extends BaseGrid {
    FlashSquareList flashSquares = new FlashSquareList();
    boolean smallNumbers = false;
    boolean numFirst = true;

    Grid9x9(IGameView parent) {
        super(parent, 2);

        cols = 9;
        rows = 9;

        game = new int[cols][rows];
        baseGame = new boolean[cols][rows];
        notes = new boolean[cols][rows][9];

        for (int y = 0; y < (rows); y++)
            for (int x = 0; x < cols; x++)
                game[x][y] = -1;
    }

    public void show() {
        int sx = ((BaseView)parent).sizex / cols;
        int sy = ((BaseView)parent).sizey / (rows + extraRows);

        if (timer.isPaused()) timer.start();

        int[] counts = new int[9];

        for (int y = 0; y < (rows + extraRows); y++) {
            for (int x = 0; x < cols; x++) {
                Main.pa.push();
                Main.pa.stroke((y > 8) ? buttonStroke : gameStroke);
                Main.pa.fill(cellBg(x, y));

                Main.pa.rect(x*sx, y*sy, sx, sy);
                Main.pa.pop();

                if (y < 9 && game[x][y] > -1) {
                    drawNumber(x, y, game[x][y], baseGame[x][y]);
                    counts[game[x][y]]++;
                }
                BaseSolver solver = parent.getSolver();
                boolean solverRunning = solver != null && solver.used && solver.cycleAllowed;
                if (y < 9 && (solverRunning || game[x][y] < 0)) {
                    Main.pa.push();
                    Main.pa.translate(x * sx, y * sy);
                    int sxs = sx / 3;
                    int sys = sy / 3;
                    Main.pa.translate(sxs / 2, sys / 2 - 3);
                    Main.pa.textSize(sys);
                    Main.pa.textAlign(PApplet.CENTER, PApplet.CENTER);
                    Main.pa.fill(0);
                    Main.pa.strokeWeight(0);
                    for (int i = 0; i < 9; i++) {
                        if ((solverRunning) ? solver.numbers.get(x).get(y).contains(i) : notes[x][y][i]) Main.pa.text(i + 1, (i % 3) * sxs, Main.pa.floor(i / 3) * sys);
                    }
                    Main.pa.pop();
                }
            }
        }

        Main.pa.push();
        Main.pa.stroke(0);
        Main.pa.strokeWeight(3);
        Main.pa.line(3 * sx, 0, 3 * sx, Main.pa.height - extraRows * sy);
        Main.pa.line(6 * sx, 0, 6 * sx, Main.pa.height - extraRows * sy);
        Main.pa.line(0, 3 * sy, Main.pa.width, 3 * sy);
        Main.pa.line(0, 6 * sy, Main.pa.width, 6 * sy);
        Main.pa.line(0, 9 * sy, Main.pa.width, 9 * sy);
        Main.pa.pop();

        boolean allNine = true;

        for (int i = 0; i < 9; i++) {
            drawNumber(i, 9, i, selectedn == i);
            Main.pa.push();
            Main.pa.translate(i * sx, 9 * sy);
            int sxs = sx / 3;
            int sys = sy / 3;
            Main.pa.translate(sxs / 2, sys / 2 - 3);
            Main.pa.textSize(sys);
            Main.pa.textAlign(PApplet.CENTER, PApplet.CENTER);
            Main.pa.fill((selectedn == i) ? lightBgFore : darkBgFore);
            Main.pa.strokeWeight(0);
            Main.pa.text(counts[i], 2 * sxs, 0);
            if (counts[i] < 9) allNine = false;
            Main.pa.pop();
        }

        if (allNine && timer.isRunning()) {
            timer.stop();
            Main.pa.println("Solved");
        }

        Main.pa.push();
        Main.pa.fill(darkBgFore);
        drawText(0, 10, "*");
        Main.pa.pop();

        Main.pa.push();
        Main.pa.fill((flashSquares.contains(1, 10)) ? lightBgFore : darkBgFore);
        drawText(1, 10, "?");
        Main.pa.pop();

        if (smallNumbers) {
            Main.pa.push();
            Main.pa.translate(4 * sx, 10 * sy);
            Main.pa.strokeWeight(1);
            Main.pa.stroke(220);
            Main.pa.line(sx / 3, 0, sx / 3, sy);
            Main.pa.line(2 * sx / 3, 0, 2 * sx / 3, sy);
            Main.pa.line(0, sy / 3, sx, sy / 3);
            Main.pa.line(0, 2 * sy / 3, sx, 2 * sy / 3);
            Main.pa.pop();
        } else {
            Main.pa.push();
            Main.pa.fill(220);
            Main.pa.strokeWeight(0);
            drawText(4, 10, "#");
            Main.pa.pop();
        }

        Main.pa.push();
        Main.pa.fill((selectedn == -2) ? lightBgFore : darkBgFore);
        drawText(3, 10, "x");
        Main.pa.pop();

        if (numFirst) {
            Main.pa.push();
            Main.pa.fill(220);
            Main.pa.strokeWeight(0);
            drawText(2, 10, "N");
            Main.pa.pop();
        } else {
            Main.pa.push();
            Main.pa.fill(220);
            Main.pa.strokeWeight(0);
            drawText(2, 10, "C");
            Main.pa.pop();
        }

        Main.pa.push();
        Main.pa.translate(5 * sx, 10 * sy);
        Main.pa.translate(sx / 2, sy / 2);
        Main.pa.fill(darkBgFore);
        Main.pa.ellipse(0, 0, sx / 2, sy / 2);
        Main.pa.fill(buttonFill);
        Main.pa.ellipse(0, 0, sx / 3, sy / 3);
        Main.pa.rectMode(PApplet.CENTER);
        Main.pa.fill(darkBgFore);

        for (int i = 0; i < 8; i++) {
            Main.pa.push();
            Main.pa.rotate(PApplet.PI * i / 4);
            Main.pa.translate(sx / 4, 0);
            Main.pa.rect(0, 0, sx / 8, sx / 8);
            Main.pa.pop();
        }
        Main.pa.pop();

        Main.pa.push();
        Main.pa.translate(6 * sx, 10 * sy);
        Main.pa.image(Main.pa.door, 10, 10, sx - 20, sy - 20);
        Main.pa.pop();

        Main.pa.push();
        Main.pa.fill(buttonFill);
        Main.pa.stroke(buttonStroke);
        Main.pa.strokeWeight(1);
        Main.pa.rect(7 * sx, 10 * sy, 2 * sx, sy);
        Main.pa.pop();

        Main.pa.image(timer.show(), 7 * sx + 10, 10 * sy + (sy - timer.gety(2 * sx - 20)) / 2, 2 * sx - 20, timer.gety(2 * sx - 20));
    }

    private void drawNumber(int x, int y, int num, boolean black) {
        if (num > -1) {
            Main.pa.push();
            if (black) Main.pa.fill(lightBgFore);
            else if (y > 8) Main.pa.fill(darkBgFore);
            else Main.pa.fill(blue);
            drawText(x, y, Integer.toString(num + 1));
            Main.pa.pop();
        }
    }

    private void drawText(int x, int y, String text) {
        Main.pa.push();
        int sx = ((BaseView)parent).sizex / cols;
        int sy = ((BaseView)parent).sizey / (rows+extraRows);
        Main.pa.translate(x * sx, y * sy);
        Main.pa.translate(sx / 2, sy / 2);
        Main.pa.translate(0, -7);
        Main.pa.strokeWeight(0);
        Main.pa.textAlign(PApplet.CENTER, PApplet.CENTER);
        Main.pa.textSize(sy);
        Main.pa.text(text, 0, 0);
        Main.pa.pop();
    }

    private int cellBg(int x, int y) {
        if (flashSquares.contains(x, y)) return flashSquares.colorOf(x, y);
        else if (x == selectedn && y == 9) return thisFill;
        else if (x == 3 && y == 10 && selectedn == -2) return thisFill;
        else if (y < 9 && selectedn != -1 && selectedn!=-2 && (selectedn == game[x][y] || (game[x][y] == -1 && notes[x][y][selectedn]))) return neighbourFill;
        else if (y > 8) return buttonFill;
        else if (x == selectedx && y == selectedy) return thisFill;
        else if (x < 9 && selectedx > -1 && selectedy > -1 && isRowColSc(x, y)) return neighbourFill;
        else if (y < 9 && baseGame[x][y]) return baseFill;
        else return gameFill;
    }

    boolean isRowCol(int x, int y) {
        return isRowCol(x, y, -1, -1);
    }
    boolean isRowCol(int x, int y, int ofx, int ofy) {
        return Main.xor(x == ((ofx != -1) ? ofx : selectedx), y == ((ofy != -1) ? ofy : selectedy));
    }

    boolean isSc(int x, int y) {
        return isSc(x, y, -1, -1);
    }
    boolean isSc(int x, int y, int ofx, int ofy) {
        boolean isScX = Main.pa.floor(x / 3) == Main.pa.floor(((ofx != -1) ? ofx : selectedx) / 3);
        boolean isScY = Main.pa.floor(y / 3) == Main.pa.floor(((ofy != -1) ? ofy : selectedy) / 3);

        return isScX && isScY;
    }

    boolean isRowColSc(int x, int y) {
        return isRowColSc(x, y, -1, -1);
    }
    boolean isRowColSc(int x, int y, int ofx, int ofy) {
        return isRowCol(x, y, ofx, ofy) || isSc(x, y, ofx, ofy);
    }

    public void click(int x, int y, boolean right) {
        if (right) {
            if (x < 9 && y < 9) {
                placeNumber(-2, x, y);
                return;
            }
        }
        if (y == 9) {
            if (numFirst && x < 9) selectedn = (selectedn == x) ? -1 : x;
            else if (x < 9) placeNumber(x, selectedx, selectedy);
        } else if (y == 10) {
            switch (x) {
                case 0:
                    parent.newGenerator();
                    parent.generate();
                    break;
                case 1:
                    int count = getSolver().countSolutions();
                    if (count < 1) flashSquares.newNow(1, 10, 40, flashFillBad);
                    else if (count > 1) flashSquares.newNow(1, 10, 40, neighbourFill);
                    else flashSquares.newNow(1, 10, 40, flashFillGood);
                    break;
                case 2:
                    numFirst = !numFirst;
                    selectedx = -1;
                    selectedy = -1;
                    selectedn = -1;
                    break;
                case 3:
                    if (numFirst) selectedn = (selectedn == -2) ? -1 : -2;
                    else placeNumber(-2, selectedx, selectedy);
                    break;
                case 4:
                    smallNumbers = !smallNumbers;
                    break;
                case 5:
                    timer.pause();
                    Main.pa.stack.push(new SettingsView());
                    break;
                case 6:
                    Main.pa.stack.pop();
                    break;
            }
        }
        if (x == selectedx && y == selectedy) {
            selectedx = -1;
            selectedy = -1;
        } else if (x < 9 && y < 9) {
            if (numFirst) {
                if (selectedn > -1 || selectedn == -2) placeNumber(selectedn, x, y);
            } else {
                selectedx = x;
                selectedy = y;
            }
        }
    }

    public void select(int x, int y) {
        selectedx = x;
        selectedy = y;
    }

    public void placeNumber(int num, int x, int y) {
        if (x > -1 && y > -1 && !baseGame[x][y]) {
            if (num > -1 && canPlaceNumber(num, x, y, 40)) {
                if (smallNumbers) {
                    notes[x][y][num] = !notes[x][y][num];
                } else {
                    game[x][y] = num;
                    for (int ry = 0; ry < rows; ry++) {
                        for (int rx = 0; rx < cols; rx++) {
                            if (isRowColSc(rx, ry, x, y)) notes[rx][ry][num] = false;
                        }
                    }
                }
            } else if (num == -2) {
                if (smallNumbers) {
                    for (int i = 0; i < 9; i++) {
                        notes[x][y][i] = false;
                    }
                } else {
                    game[x][y] = -1;
                }
            }
        }
    }

    public boolean canPlaceNumber(int num, int atx, int aty, int flashTime) {
        boolean can = true;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (isRowColSc(x, y, atx, aty) && game[x][y] == num) {
                    can = false;
                    if (flashTime > -1) flashSquares.newNow(x, y, flashTime, flashFillBad);
                }
            }
        }
        return can;
    }

    public void lockAsBase(boolean output, boolean finalise) {
        baseClues = 0;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (game[x][y] > -1) {
                    baseGame[x][y] = true;
                    baseClues++;
                } else {
                    baseGame[x][y] = false;
                }
            }
        }
        if (output) Main.pa.println("Clues locked: " + baseClues);
    }

    public void keyInput(int k) {
        if (k > 0) {
            if (numFirst) {
                selectedn = (selectedn == (k - 1)) ? -1 : (k - 1);
            } else {
                placeNumber(k, selectedx, selectedy);
            }
        }
    }

    public BaseSolver getSolver() {
        return new Sudoku9x9Solver(this);
    }

    public BaseGrid clone() {
        Grid9x9 clone = new Grid9x9(parent);

        clone.baseClues = baseClues;
        clone.game = new int[clone.cols][clone.rows];
        for (int y = 0; y < clone.rows; y++) {
            for (int x = 0; x < clone.cols; x++) {
                clone.game[x][y] = game[x][y];
                clone.baseGame[x][y] = baseGame[x][y];
                for (int n = 0; n < 9; n++) {
                    clone.notes[x][y][n] = notes[x][y][n];
                }
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
}
