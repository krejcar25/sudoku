package cz.krejcar25.sudoku.game;

import cz.krejcar25.sudoku.SudokuApplet;
import cz.krejcar25.sudoku.neuralNetwork.TrainingDataPair;
import processing.core.PApplet;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Base64;

public class GridCore implements TrainingDataPair {
    public static final String FILETYPE = "scs";
    public static final String FILETYPE_DESC = "Sudoku Core List files";

    public final int sizea;
    public final int sizeb;
    public final int ncr;
    public final GridProperties gridProperties;

    private int baseClues;
    private int[][] grid;
    private boolean[][] baseGrid;
    private int[][] solvedGrid;
    private int selectedx;
    private int selectedy;
    private BaseGrid owner;

    public GridCore(GridProperties gridProperties) {
        this.sizea = gridProperties.getSizea();
        this.sizeb = gridProperties.getSizeb();
        this.ncr = sizea * sizeb;
        this.gridProperties = gridProperties;

        grid = new int[ncr][ncr];
        baseGrid = new boolean[ncr][ncr];
        solvedGrid = new int[ncr][ncr];

        for (int y = 0; y < ncr; y++)
            for (int x = 0; x < ncr; x++) {
                grid[x][y] = -1;
                solvedGrid[x][y] = -1;
            }
    }

    private GridCore(int ncr, int[][] grid, int[][] solvedGrid) {
        this.ncr = ncr;
        this.grid = grid;
        this.baseGrid = new boolean[ncr][ncr];
        this.solvedGrid = solvedGrid;
        lockAsBase(false, true);

        int sizea = -1;
        int sizeb = -1;
        GridProperties gridProperties = null;

        for (GridProperties gp : GridProperties.values()) {
            if (gp.getSizea() * gp.getSizeb() == ncr) {
                sizea = gp.getSizea();
                sizeb = gp.getSizeb();
                gridProperties = gp;
                break;
            }
        }

        this.sizea = sizea;
        this.sizeb = sizeb;
        this.gridProperties = gridProperties;
    }

    public BaseGrid getOwner() {
        return owner;
    }

    public void setOwner(BaseGrid owner) {
        this.owner = owner;
    }

    public int get(int x, int y) {
        return grid[x][y];
    }

    public void set(int x, int y, int n) {
        grid[x][y] = n;
    }

    public boolean isBaseGame(int x, int y) {
        return baseGrid[x][y];
    }

    public int getSelectedx() {
        return selectedx;
    }

    public int getSelectedy() {
        return selectedy;
    }

    boolean isRowCol(int x, int y) {
        return isRowCol(x, y, -1, -1);
    }

    boolean isRowCol(int x, int y, int ofx, int ofy) {
        return SudokuApplet.xor(x == ((ofx != -1) ? ofx : selectedx), y == ((ofy != -1) ? ofy : selectedy));
    }

    boolean isSc(int x, int y) {
        return isSc(x, y, -1, -1);
    }

    boolean isSc(float x, float y, float ofx, float ofy) {
        boolean isScX = PApplet.floor(x / sizea) == PApplet.floor(((ofx != -1) ? ofx : selectedx) / sizea);
        boolean isScY = PApplet.floor(y / sizeb) == PApplet.floor(((ofy != -1) ? ofy : selectedy) / sizeb);

        return isScX && isScY;
    }

    boolean isRowColSc(int x, int y) {
        return isRowColSc(x, y, -1, -1);
    }

    boolean isRowColSc(int x, int y, int ofx, int ofy) {
        return isRowCol(x, y, ofx, ofy) || isSc(x, y, ofx, ofy);
    }

    boolean canPlaceNumber(int num, int atx, int aty, int flashTime) {
        boolean can = true;
        for (int y = 0; y < ncr; y++) {
            for (int x = 0; x < ncr; x++) {
                if (isRowColSc(x, y, atx, aty) && grid[x][y] == num) {
                    can = false;
                    if (owner != null)
                        if (flashTime > -1) owner.flashSquares.newNow(x, y, flashTime, owner.flashFillBad);
                }
            }
        }
        return can;
    }

    void select(int x, int y) {
        selectedx = x;
        selectedy = y;
    }

    void lockAsBase(boolean output, boolean finalise) {
        baseClues = 0;
        for (int y = 0; y < ncr; y++) {
            for (int x = 0; x < ncr; x++) {
                if (grid[x][y] > -1) {
                    baseGrid[x][y] = true;
                    baseClues++;
                } else {
                    baseGrid[x][y] = false;
                }
            }
        }
        if (output) SudokuApplet.println("Clues locked: " + baseClues);
    }

    void setAsSolved() {
        for (int x = 0; x < ncr; x++) System.arraycopy(grid[x], 0, solvedGrid[x], 0, ncr);
    }

    @Override
    public GridCore clone() {
        GridCore clone = new GridCore(gridProperties);

        clone.baseClues = baseClues;
        clone.grid = new int[clone.ncr][clone.ncr];
        for (int y = 0; y < clone.ncr; y++) {
            for (int x = 0; x < clone.ncr; x++) {
                clone.grid[x][y] = grid[x][y];
                clone.baseGrid[x][y] = baseGrid[x][y];
            }
        }
        clone.selectedx = selectedx;
        clone.selectedy = selectedy;

        return clone;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(grid);
    }

    public String getGridString() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 * (2 * ncr * ncr + 1));
        IntBuffer intBuffer = byteBuffer.asIntBuffer();

        intBuffer.put(ncr);

        for (int[] row : grid) for (int cell : row) intBuffer.put(cell);
        for (int[] row : solvedGrid) for (int cell : row) intBuffer.put(cell);

        return Base64.getEncoder().encodeToString(byteBuffer.array());
    }

    public static GridCore fromGridString(String s) {
        byte[] bytes = Base64.getDecoder().decode(s);
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        int ncr = intBuffer.get(0);

        int[][] grid = new int[ncr][ncr];
        int[][] solved = new int[ncr][ncr];

        for (int x = 0; x < ncr; x++) {
            for (int y = 0; y < ncr; y++) {
                grid[x][y] = intBuffer.get(x * ncr + y + 1);
                solved[x][y] = intBuffer.get(ncr * ncr + x * ncr + y + 1);
            }
        }

        return new GridCore(ncr, grid, solved);
    }

    @Override
    public int getRequiredInputCount() {
        return ncr * ncr * ncr;
    }

    @Override
    public int getRequiredOutputCount() {
        return ncr * ncr * ncr;
    }

    @Override
    public double[] getInput() {
        return getBinaryDoublesFromGrid(grid);
    }

    @Override
    public double[] getDesiredOutput() {
        return getBinaryDoublesFromGrid(solvedGrid);
    }

    private double[] getBinaryDoublesFromGrid(int[][] grid) {
        double[] out = new double[getRequiredInputCount()];
        int index = 0;
        int temp;
        for (int[] row : grid)
            for (int cell : row)
                for (temp = index; index < temp + ncr; index++) out[index] = index % ncr == cell ? 1 : -1;
        return out;
    }
}
