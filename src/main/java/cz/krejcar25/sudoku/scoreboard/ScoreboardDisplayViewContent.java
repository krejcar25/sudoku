package cz.krejcar25.sudoku.scoreboard;

import cz.krejcar25.sudoku.SudokuApplet;
import cz.krejcar25.sudoku.Timer;
import cz.krejcar25.sudoku.game.GridDifficulty;
import cz.krejcar25.sudoku.game.GridProperties;
import cz.krejcar25.sudoku.ui.ScrollView;
import cz.krejcar25.sudoku.ui.ScrollViewContent;
import processing.core.PGraphics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ScoreboardDisplayViewContent extends ScrollViewContent
{
	private final GridProperties gridProperties;
	private final GridDifficulty gridDifficulty;
	private ArrayList<ScoreboardEntry> entries;

	private int contentY = 170;
	private int tableHeight;
	private int contentTextSize = 25;
	private int tableMargin = 20;

	private Scoreboard.SortBy sortBy;
	private int sortOrder;

	private PGraphics table;

	public ScoreboardDisplayViewContent(ScrollView scrollView, GridProperties gridProperties, GridDifficulty gridDifficulty)
	{
		super(scrollView, 800, 600);
		this.gridProperties = gridProperties;
		this.gridDifficulty = gridDifficulty;
		this.sortBy = Scoreboard.SortBy.Time;
		this.sortOrder = 1;
		this.entries = getRootApplet().scoreboard.getEntries(gridProperties, gridDifficulty, sortBy, sortOrder);

		this.tableHeight = 150 + entries.size() * (contentTextSize + 5);
		int requiredHeight = SudokuApplet.floor(contentY + this.tableHeight);
		this.height = (requiredHeight > scrollView.height) ? requiredHeight : scrollView.height;
		generateTable();
	}

	@Override
	public void click(int mx, int my, boolean rmb)
	{
		if (rmb) scrollView.removeFromViewStack();
		else if (SudokuApplet.isBetween(tableMargin, mx, width - tableMargin) && SudokuApplet.isBetween(contentY, my, contentY + 40))
		{
			Scoreboard.SortBy newSort = (mx < width / 2f) ? Scoreboard.SortBy.Date : Scoreboard.SortBy.Time;
			if (sortBy == newSort) sortOrder *= -1;
			else
			{
				sortBy = newSort;
				sortOrder = 1;
			}
			entries = getRootApplet().scoreboard.getEntries(gridProperties, gridDifficulty, sortBy, sortOrder);
			generateTable();
		}
	}

	@Override
	protected void draw()
	{
		textSize(40);
		fill(51);
		textAlign(CENTER, CENTER);
		text(String.format("Scoreboard for %s %s:", gridDifficulty.toString(), gridProperties.getName()), width / 2f, 100);
		image(this.table, tableMargin, contentY);
	}

	private void generateTable()
	{
		this.table = this.parent.createGraphics(this.width - 2 * tableMargin, tableHeight);
		this.table.beginDraw();
		this.table.fill(51);
		this.table.textAlign(CENTER, CENTER);
		this.table.textSize(30);
		this.table.stroke(51);
		this.table.strokeWeight(1);
		int xleft = this.table.width / 4;
		int xright = 3 * this.table.width / 4;
		this.table.line(0, 0, this.table.width, 0);
		this.table.line(0, 0, 0, 40);
		this.table.line(this.table.width / 2f, 0, this.table.width / 2f, 40);
		this.table.line(this.table.width - 1, 0, this.table.width - 1, 40);
		this.table.line(0, 40, this.table.width, 40);
		this.table.text("Game date", xleft, 20);
		this.table.text("Time spent", xright, 20);
		this.table.pushMatrix();
		this.table.pushStyle();
		this.table.textAlign(RIGHT, CENTER);
		float xIndicator = sortBy == Scoreboard.SortBy.Date ? this.table.width / 2f : this.table.width;
		this.table.text(sortOrder > 0 ? "▲" : "▼", xIndicator, 20);
		this.table.popStyle();
		this.table.popMatrix();
		this.table.pushMatrix();
		this.table.pushStyle();
		this.table.translate(0, 40);
		this.table.textSize(25);
		for (ScoreboardEntry entry : entries)
		{
			this.table.pushMatrix();
			this.table.pushStyle();
			float lineHeight = this.parent.g.textSize + 5;
			this.table.line(0, 0, 0, lineHeight);
			this.table.line(this.table.width / 2f, 0, this.table.width / 2f, lineHeight);
			this.table.line(this.table.width, 0, this.table.width, lineHeight);
			this.table.line(0, lineHeight, this.table.width, lineHeight);
			this.table.pushMatrix();
			this.table.pushStyle();
			this.table.textAlign(LEFT, CENTER);
			this.table.text(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(entry.getDateStarted()), 10, lineHeight / 2f);
			this.table.text(Timer.formatMillis(entry.getDurationMillis()), this.table.width / 2f + 10, lineHeight / 2f);
			this.table.popStyle();
			this.table.popMatrix();
			this.table.popStyle();
			this.table.popMatrix();
			this.table.translate(0, lineHeight);
		}
		this.table.popStyle();
		this.table.popMatrix();
		this.table.endDraw();
	}
}
