package cz.krejcar25.sudoku.game;

import org.jetbrains.annotations.Nullable;

public enum GridDifficulty
{
	Debug(-1, false), Easy(0, true), Medium(1, true), Hard(2, true), Extreme(3, true);

	private final int level;
	private final boolean selectable;

	GridDifficulty(int level, boolean selectable)
	{
		this.level = level;
		this.selectable = selectable;
	}

	@Nullable
	public static GridDifficulty fromLevel(int level)
	{
		for (GridDifficulty difficulty : values()) if (difficulty.level == level) return difficulty;
		return null;
	}

	public int getLevel()
	{
		return level;
	}

	@Override
	public String toString()
	{
		switch (this)
		{
			case Debug:
				return "Debug";
			case Easy:
				return "Easy";
			case Medium:
				return "Medium";
			case Hard:
				return "Hard";
			case Extreme:
				return "Extreme";
			default:
				assert false;
				return "Nonsense";
		}
	}

	public boolean isSelectable()
	{
		return selectable;
	}
}
