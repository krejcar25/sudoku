package cz.krejcar25.sudoku.scoreboard;

import cz.krejcar25.sudoku.ui.ScrollViewContent;
import cz.krejcar25.sudoku.SudokuApplet;
import cz.krejcar25.sudoku.Timer;
import cz.krejcar25.sudoku.game.GridDifficulty;
import cz.krejcar25.sudoku.game.GridProperties;
import cz.krejcar25.sudoku.ui.ScrollView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ScoreboardDisplayViewContent extends ScrollViewContent {
    private final GridProperties gridProperties;
    private final GridDifficulty gridDifficulty;
    ArrayList<ScoreboardEntry> entries;

    private int contentY = 170;
    private int contentTextSize = 25;
    int tableMargin = 20;

    private Scoreboard.SortBy sortBy;
    private int sortOrder;

    public ScoreboardDisplayViewContent(ScrollView scrollView, GridProperties gridProperties, GridDifficulty gridDifficulty) {
        super(scrollView, 800, 600);
        this.gridProperties = gridProperties;
        this.gridDifficulty = gridDifficulty;
        this.sortBy = Scoreboard.SortBy.Time;
        this.sortOrder = 1;
        this.entries = getRootApplet().scoreboard.getEntries(gridProperties, gridDifficulty, sortBy, sortOrder);
    }

    @Override
    public void click(int mx, int my, boolean rmb) {
        if (rmb) scrollView.removeFromViewStack();
        else if (SudokuApplet.isBetween(tableMargin, mx, width - tableMargin) && SudokuApplet.isBetween(130, my, contentY)) {
            Scoreboard.SortBy newSort = (mx < width / 2f) ? Scoreboard.SortBy.Date : Scoreboard.SortBy.Time;
            if (sortBy == newSort) sortOrder *= -1;
            else {
                sortBy = newSort;
                sortOrder = 1;
            }
            entries = getRootApplet().scoreboard.getEntries(gridProperties, gridDifficulty, sortBy, sortOrder);
        }
    }

    @Override
    protected void beforeDraw() {
        int requiredHeight = SudokuApplet.floor(contentY + 50 + entries.size() * (contentTextSize + 5));
        int actualHeight = (requiredHeight > scrollView.height) ? requiredHeight : height;
        setSize(width, actualHeight);
    }

    @Override
    protected void draw() {
        textSize(40);
        fill(51);
        textAlign(CENTER, CENTER);
        text("Scoreboard for " + gridDifficulty.toString() + " " + gridProperties.getName() + ":", width / 2f, 100);
        textSize(30);
        stroke(51);
        strokeWeight(1);
        int xleft = ((width - (2 * tableMargin)) / 4) + tableMargin;
        int xright = ((3 * (width - (2 * tableMargin))) / 4) + tableMargin;
        line(tableMargin, 130, width - tableMargin, 130);
        line(tableMargin, 130, tableMargin, 170);
        line(width / 2f, 130, width / 2f, 170);
        line(width - tableMargin, 130, width - tableMargin, 170);
        line(tableMargin, 170, width - tableMargin, 170);
        text("Game date", xleft, 150);
        text("Time spent", xright, 150);
        push();
        textAlign(RIGHT, CENTER);
        float xIndicator = sortBy == Scoreboard.SortBy.Date ? width / 2f : width - tableMargin;
        text(sortOrder > 0 ? "▲" : "▼", xIndicator, 150);
        pop();
        push();
        translate(0, 170);
        textSize(25);
        int lines = 0;
        for (ScoreboardEntry entry : entries) {
            addLine(entry, tableMargin);
            lines++;
        }
        pop();
    }

    private void addLine(ScoreboardEntry entry, int margin) {
        push();
        float lineHeight = textSize + 5;
        line(margin, 0, margin, lineHeight);
        line(width / 2f, 0, width / 2f, lineHeight);
        line(width - margin, 0, width - margin, lineHeight);
        line(margin, lineHeight, width - margin, lineHeight);
        push();
        textAlign(LEFT, CENTER);
        text(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(entry.getDateStarted()), margin + 10, lineHeight / 2f);
        text(Timer.formatMillis(entry.getDurationMillis()), width / 2f + 10, lineHeight / 2f);
        pop();
        pop();
        translate(0, lineHeight);
    }
}
