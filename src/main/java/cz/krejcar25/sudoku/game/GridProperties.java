package cz.krejcar25.sudoku.game;

import java.awt.*;

public enum GridProperties {
	Grid4x4(2, 2, 3, 0, 5, 1, 5, 2, 5, 3, 5, 0, 6, 1, 6, 2, 6, 1, 7, 1, "4x4", 360, 720, 11, 9, 7, 6),
	Grid6x6(3, 2, 2, 0, 7, 1, 7, 2, 7, 3, 7, 0, 8, 1, 8, 2, 8, 4, 7, 1, "6x6", 540, 810, 18, 16, 14, 12),
	Grid9x9(3, 3, 1, 0, 10, 1, 10, 2, 10, 3, 10, 4, 10, 5, 10, 6, 10, 7, 10, 1, "9x9", 720, 880, 30, 27, 24, 21),
	Grid16x16(4, 4, 1, 0, 17, 1, 17, 2, 17, 3, 17, 4, 17, 5, 17, 6, 17, 14, 17, 0, "16x16", 720, 810, 200, 180, 170, 150);

	private final int sizea, sizeb;
	private final int controlRows;
	private final Point newGamePos, helpPos, orderTogglePos, deletePos, smallNumPos, settingsPos, exitPos, timerPos;
	private final String name;
	private final int drawNumberOffset;
	private final int width, height;
	private final int[] clueCounts;

	GridProperties(int sizea, int sizeb, int controlRows, int newGameX, int newGameY, int helpX, int helpY, int orderToggleX, int orderToggleY, int deleteX, int deleteY, int smallNumX, int smallNumY, int settingsX, int settingsY, int exitX, int exitY, int timerX, int timerY, int drawNumberOffset, String name, int width, int height, int... clueCounts) {
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
		this.width = width;
		this.height = height;
		this.clueCounts = clueCounts;
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
		return String.format("Sudoku %s", name);
	}

	public String getShortName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getClueCount(GridDifficulty gridDifficulty) {
		return gridDifficulty == GridDifficulty.Debug ? (((sizea * sizeb) * (sizea * sizeb)) - 2) : clueCounts[gridDifficulty.getLevel()];
	}

	@Override
	public String toString() {
		return name;
	}
}
