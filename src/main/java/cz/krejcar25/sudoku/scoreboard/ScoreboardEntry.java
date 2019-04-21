package cz.krejcar25.sudoku.scoreboard;

import cz.krejcar25.sudoku.game.GridDifficulty;
import cz.krejcar25.sudoku.game.GridProperties;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

class ScoreboardEntry {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private final GridProperties gridSize;
	private final GridDifficulty difficulty;
	private final Date dateStarted;
	private final long durationMillis;

	@Contract(pure = true)
	ScoreboardEntry(GridProperties gridSize, GridDifficulty difficulty, Date dateStarted, long durationMillis) {
		this.gridSize = gridSize;
		this.difficulty = difficulty;
		this.dateStarted = dateStarted;
		this.durationMillis = durationMillis;
	}

	// Generates random entry for testing with dates between years 2000 and 2018
	ScoreboardEntry(GridProperties gridSize, GridDifficulty difficulty) {
		this.gridSize = gridSize;
		this.difficulty = difficulty;
		GregorianCalendar dateStarted = new GregorianCalendar();
		dateStarted.set(Calendar.YEAR, randomBetween(2000, 2018));
		dateStarted.set(Calendar.DAY_OF_YEAR, randomBetween(1, dateStarted.getActualMaximum(Calendar.DAY_OF_YEAR)));
		this.dateStarted = new Date(dateStarted.getTimeInMillis());
		this.durationMillis = randomBetween(60000, 7200000);
	}

	@Nullable
	static ScoreboardEntry deserialize(@NotNull String line) throws ParseException {
		if (line.startsWith("#")) return null;
		String[] parts = line.split(",");

		// Analyze each part
		// GridProperties
		GridProperties gp = null;
		for (GridProperties test : GridProperties.values()) if (test.getShortName().equals(parts[0])) gp = test;
		// GridDifficulty
		GridDifficulty difficulty = null;
		for (GridDifficulty test : GridDifficulty.values())
			if (test.getLevel() == Integer.parseInt(parts[1])) difficulty = test;
		// dateStarted
		Date dateStarted = dateFormat.parse(parts[2]);
		// duration
		long duration = Long.parseLong(parts[3]);

		return new ScoreboardEntry(gp, difficulty, dateStarted, duration);
	}

	private int randomBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	String serialize() {
		String sb = gridSize.getShortName() +
				"," +
				difficulty.getLevel() +
				"," +
				dateFormat.format(dateStarted) +
				"," +
				durationMillis;
		return sb;
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
