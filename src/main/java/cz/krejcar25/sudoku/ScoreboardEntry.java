package cz.krejcar25.sudoku;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ScoreboardEntry implements Serializable {
    private GridProperties gridSize;
    private GridDifficulty difficulty;
    private Date dateStarted;
    private long durationMillis;

    public ScoreboardEntry(GridProperties gridSize, GridDifficulty difficulty, Date dateStarted, long durationMillis) {
        this.gridSize = gridSize;
        this.difficulty = difficulty;
        this.dateStarted = dateStarted;
        this.durationMillis = durationMillis;
    }

    // Generates random entry for testing
    public ScoreboardEntry(GridProperties gridSize, GridDifficulty difficulty) {
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

    public GridProperties getGridSize() {
        return gridSize;
    }

    public GridDifficulty getDifficulty() {
        return difficulty;
    }

    public Date getDateStarted() {
        return dateStarted;
    }

    public long getDurationMillis() {
        return durationMillis;
    }
}
