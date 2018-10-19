package cz.krejcar25.sudoku;

import processing.core.*;

public abstract class BaseGrid {
    IGameView parent;

    int cols;
    int rows;
    int extraRows;
    int baseClues;

    int[][] game;
    boolean[][] baseGame;
    boolean[][][] notes;

    int selectedx = -1;
    int selectedy = -1;
    int selectedn = -1;

    protected int gameFill = Main.pa.color(255);
    protected int gameStroke = Main.pa.color(51);
    protected int baseFill = Main.pa.color(220);
    protected int neighbourFill = Main.pa.color(255, 255, 200);
    protected int thisFill = Main.pa.color(255, 255, 100);
    protected int buttonFill = Main.pa.color(51);
    protected int buttonStroke = Main.pa.color(220);
    protected int flashFillBad = Main.pa.color(255, 200, 200);
    protected int flashFillGood = Main.pa.color(200, 255, 200);
    protected int darkBgFore = Main.pa.color(220);
    protected int lightBgFore = Main.pa.color(51);
    protected int blue = Main.pa.color(0, 0, 255);

    FlashSquareList flashSquares;
    boolean smallNumbers;
    boolean numFirst;

    boolean finalised;

    protected StopWatch timer;

    public BaseGrid(IGameView parent, int extraRows) {
        this.parent = parent;
        this.extraRows = extraRows;
        this.flashSquares = new FlashSquareList();
        smallNumbers = false;
        numFirst = true;
        finalised = false;
        timer = new StopWatch("9x9 Grid");
    }

    public abstract void show();
    public abstract void click(int x, int y, boolean right);
    public abstract void select(int x, int y);
    public abstract void placeNumber(int num, int x, int y);
    public abstract boolean canPlaceNumber(int num, int atx, int aty, int flashTime);
    public abstract void lockAsBase(boolean output, boolean finalise);
    public abstract void keyInput(int k);
    public abstract BaseSolver getSolver();
    public abstract BaseGrid clone();
}
