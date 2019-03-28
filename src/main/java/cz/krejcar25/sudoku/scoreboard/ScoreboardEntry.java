package cz.krejcar25.sudoku.scoreboard;

import cz.krejcar25.sudoku.game.GridDifficulty;
import cz.krejcar25.sudoku.game.GridProperties;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

class ScoreboardEntry implements Serializable {
	private final GridProperties gridSize;
	private final GridDifficulty difficulty;
	private final Date dateStarted;
	private final long durationMillis;

	ScoreboardEntry(GridProperties gridSize, GridDifficulty difficulty, Date dateStarted, long durationMillis) {
		this.gridSize = gridSize;
		this.difficulty = difficulty;
		this.dateStarted = dateStarted;
		this.durationMillis = durationMillis;
	}

	// Generates random entry for testing
	ScoreboardEntry(GridProperties gridSize, GridDifficulty difficulty) {
		this.gridSize = gridSize;
		this.difficulty = difficulty;
		GregorianCalendar dateStarted = new GregorianCalendar();
		dateStarted.set(Calendar.YEAR, randomBetween(2000, 2018));
		dateStarted.set(Calendar.DAY_OF_YEAR, randomBetween(1, dateStarted.getActualMaximum(Calendar.DAY_OF_YEAR)));
		this.dateStarted = new Date(dateStarted.getTimeInMillis());
		this.durationMillis = randomBetween(60000, 7200000);
	}

	private int randomBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	GridProperties getGridSize() {
		return gridSize;
	}

	GridDifficulty getDifficulty() {
		return difficulty;
	}

	Date getDateStarted() {
		return dateStarted;
	}

	long getDurationMillis() {
		return durationMillis;
	}
}
