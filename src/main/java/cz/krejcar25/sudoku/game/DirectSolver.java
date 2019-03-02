package cz.krejcar25.sudoku.game;

import cz.krejcar25.sudoku.Timer;

import java.util.BitSet;

// Stupid sudoku solver with a limited set of solving rules
// useful for assessing the difficulty

class DirectSolver
{
	private final GridCore game;
	private final Rule[] rules;

	DirectSolver(GridCore game)
	{
		this.game = game.copy();
		this.game.lockAsBase(false);
		this.rules = new Rule[]{new LastInBlockRule(), new LastInRowRule(), new LastInColumnRule(), new OnlyRule()};
	}

	int getDifficulty()
	{
		Timer timer = new Timer("DirectSolver");
		timer.start();
		int maxDifficulty = 0;
		while (!isDone())
		{
			for (int i = 0; i < maxDifficulty || ++maxDifficulty <= rules.length; ++i)
			{
				if (rules[i].apply(game))
				{
					break;
				}
			}
			if (maxDifficulty > rules.length)
			{
				break;
			}
		}
		timer.stop();
		return maxDifficulty;
	}

	private boolean isDone()
	{
		for (int x = 0; x < game.ncr; ++x)
		{
			for (int y = 0; y < game.ncr; ++y)
			{
				if (game.get(x, y) < 0)
				{
					return false;
				}
			}
		}
		return true;
	}
}

abstract class Rule
{
	static void set(BitSet bs, int element)
	{
		if (element >= 0)
		{
			bs.set(element);
		}
	}

	abstract boolean apply(GridCore game);
}

/**
 * Fill in the only number not used in a row, column and block
 */
class OnlyRule extends Rule
{
	@Override
	public boolean apply(GridCore game)
	{
		BitSet occupied = new BitSet();
		for (int x = 0; x < game.ncr; ++x)
		{
			for (int y = 0; y < game.ncr; ++y)
			{
				if (game.get(x, y) >= 0)
				{
					continue;
				}
				occupied.clear();
				for (int i = 0; i < game.ncr; ++i)
				{
					set(occupied, game.get(i, y));
					set(occupied, game.get(x, i));
					set(occupied, game.get(x - (x % game.sizea) + i % game.sizea, y - (y % game.sizeb) + i / game.sizea));
				}
				if (occupied.cardinality() == game.ncr - 1)
				{
					game.set(x, y, occupied.nextClearBit(0));
					return true;
				}
			}
		}
		return false;
	}
}

abstract class LastRule extends Rule
{
	abstract boolean canPlace(GridCore game, int number, int i, int j);

	abstract void set(GridCore game, int number, int i, int j);

	@Override
	boolean apply(GridCore game)
	{
		BitSet available = new BitSet();
		for (int i = 0; i < game.ncr; ++i)
		{
			for (int num = 0; num < game.ncr; ++num)
			{
				available.clear();
				for (int j = 0; j < game.ncr; ++j)
				{
					if (canPlace(game, num, i, j))
					{
						available.set(j);
					}
					if (available.cardinality() > 1)
					{
						break;
					}
				}
				if (available.cardinality() == 1)
				{
					int j = available.nextSetBit(0);
					set(game, num, i, j);
					return true;
				}
			}
		}
		return false;
	}
}

class LastInColumnRule extends LastRule
{
	@Override
	boolean canPlace(GridCore game, int number, int x, int y)
	{
		return game.get(x, y) < 0 && game.canPlaceNumber(number, x, y, -1);
	}

	@Override
	void set(GridCore game, int number, int x, int y)
	{
		game.set(x, y, number);
	}
}

class LastInRowRule extends LastRule
{
	@Override
	boolean canPlace(GridCore game, int number, int y, int x)
	{
		return game.get(x, y) < 0 && game.canPlaceNumber(number, x, y, -1);
	}

	@Override
	void set(GridCore game, int number, int y, int x)
	{
		game.set(x, y, number);
	}
}

class LastInBlockRule extends LastRule
{
	@Override
	boolean canPlace(GridCore game, int number, int block, int cell)
	{
		int x = getX(game, block, cell);
		int y = getY(game, block, cell);
		return game.get(x, y) < 0 && game.canPlaceNumber(number, x, y, -1);
	}

	@Override
	void set(GridCore game, int number, int block, int cell)
	{
		int x = getX(game, block, cell);
		int y = getY(game, block, cell);
		game.set(x, y, number);
	}

	private int getY(GridCore game, int block, int cell)
	{
		return game.sizeb * (block / game.sizeb) + (cell / game.sizea);
	}

	private int getX(GridCore game, int block, int cell)
	{
		return game.sizea * (block % game.sizeb) + (cell % game.sizea);
	}
}

