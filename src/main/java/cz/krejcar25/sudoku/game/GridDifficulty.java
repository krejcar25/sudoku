package cz.krejcar25.sudoku.game;

import org.jetbrains.annotations.Nullable;

public enum GridDifficulty
{
	Custom(-2, true, false, "Custom"),
	Debug(-1, false, false, "Debug"),
	Easy(0, true, true, "Easy"),
	Medium(1, true, true, "Medium"),
	Hard(2, true, true, "Hard"),
	Extreme(3, true, true, "Extreme");

	private final int level;
	private final boolean selectable;
	private final boolean playable;
	private final String name;

	GridDifficulty(int level, boolean selectable, boolean playable, String name)
	{
		this.level = level;
		this.selectable = selectable;
		this.playable = playable;
		this.name = name;
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
		return this.name;
	}

	public boolean isSelectable()
	{
		return selectable;
	}

	public boolean isPlayable()
	{
		return playable;
	}
}
