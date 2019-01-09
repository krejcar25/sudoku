package cz.krejcar25.sudoku.scoreboard;

import java.util.Comparator;

public class ScoreboardComparator implements Comparator<ScoreboardEntry> {
    private final Scoreboard.SortBy sortBy;
    private final int sortOrder;

    public ScoreboardComparator(Scoreboard.SortBy sortBy, int sortOrder) {
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(ScoreboardEntry o1, ScoreboardEntry o2) {
        int diff;
        switch (sortBy) {
            case Date:
                diff = Long.compare(o1.getDateStarted().getTime(), o2.getDateStarted().getTime());
                break;
            case Time:
                diff = Long.compare(o1.getDurationMillis(), o2.getDurationMillis());
                break;
            default:
                diff = 0;
                break;
        }
        diff *= sortOrder;
        return Math.toIntExact(diff);
    }
}