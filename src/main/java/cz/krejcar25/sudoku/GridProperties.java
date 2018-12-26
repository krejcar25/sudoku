package cz.krejcar25.sudoku;

import java.awt.*;

public enum GridProperties {
    Grid4x4(2, 2, 3, 0, 5, 1, 5, 2, 5, 3, 5, 0, 6, 1, 6, 2, 6, 1, 7, 1, "Sudoku 4x4"),
    Grid6x6(3, 2, 2, 0, 7, 1, 7, 2, 7, 3, 7, 0, 8, 1, 8, 2, 8, 4, 7, 1, "Sudoku 6x6"),
    Grid9x9(3, 3, 1, 0, 10, 1, 10, 2, 10, 3, 10, 4, 10, 5, 10, 6, 10, 7, 10, 1, "Sudoku9x9"),
    Grid16x16(4, 4, 1, 0, 17, 1, 17, 2, 17, 3, 17, 4, 17, 5, 17, 6, 17, 14, 17, 0, "Sudoku 16x16");

    private final int sizea, sizeb;
    private final int controlRows;
    private final Point newGamePos, helpPos, orderTogglePos, deletePos, smallNumPos, settingsPos, exitPos, timerPos;
    private final String name;
    private final int drawNumberOffset;

    GridProperties(int sizea, int sizeb, int controlRows, int newGameX, int newGameY, int helpX, int helpY, int orderToggleX, int orderToggleY, int deleteX, int deleteY, int smallNumX, int smallNumY, int settingsX, int settingsY, int exitX, int exitY, int timerX, int timerY, int drawNumberOffset, String name) {
        this.sizea = sizea;
        this.sizeb = sizeb;
        this.controlRows = controlRows;
        this.newGamePos = new Point(newGameX, newGameY);
        this.helpPos = new Point(helpX, helpY);
        this.orderTogglePos = new Point(orderToggleX, orderToggleY);
        this.deletePos = new Point(deleteX, deleteY);
        this.smallNumPos = new Point(smallNumX, smallNumY);
        this.settingsPos = new Point(settingsX, settingsY);
        this.exitPos = new Point(exitX, exitY);
        this.timerPos = new Point(timerX, timerY);
        this.drawNumberOffset = drawNumberOffset;
        this.name = name;
    }

    public int getSizea() {
        return sizea;
    }

    public int getSizeb() {
        return sizeb;
    }

    public int getControlRows() {
        return controlRows;
    }

    public Point getNewGamePos() {
        return newGamePos;
    }

    public Point getHelpPos() {
        return helpPos;
    }

    public Point getOrderTogglePos() {
        return orderTogglePos;
    }

    public Point getDeletePos() {
        return deletePos;
    }

    public Point getSmallNumPos() {
        return smallNumPos;
    }

    public Point getSettingsPos() {
        return settingsPos;
    }

    public Point getExitPos() {
        return exitPos;
    }

    public Point getTimerPos() {
        return timerPos;
    }

    public int getDrawNumberOffset() {
        return drawNumberOffset;
    }

    public String getName() {
        return name;
    }
}
