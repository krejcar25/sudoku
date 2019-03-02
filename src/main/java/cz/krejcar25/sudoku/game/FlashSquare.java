package cz.krejcar25.sudoku.game;

class FlashSquare
{
	final int x;
	final int y;
	final int c;
	private final int timestamp;
	private final int lifespan;

	FlashSquare(int x, int y, int timestamp, int lifespan, int c)
	{
		this.x = x;
		this.y = y;
		this.timestamp = timestamp;
		this.lifespan = lifespan;
		this.c = c;
	}

	boolean isValid(int time)
	{
		return time < timestamp + lifespan;
	}
}
