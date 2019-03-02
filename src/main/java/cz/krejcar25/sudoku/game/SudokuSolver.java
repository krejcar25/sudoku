package cz.krejcar25.sudoku.game;

import cz.krejcar25.sudoku.Timer;

import java.util.ArrayList;

class SudokuSolver
{
	private final GridCore grid;
	private boolean used = false;
	private final Timer timer;
	private boolean wentBack;
	private final ArrayList<ArrayList<ArrayList<Integer>>> numbers;
	private int count;
	private int x;
	private int y;
	private boolean tryNext;
	private boolean cycleAllowed;

	SudokuSolver(GridCore grid)
	{
		this.grid = grid.copy();
		this.grid.lockAsBase(false);
		timer = new Timer("GridSolver");
		numbers = new ArrayList<>();
		tryNext = true;
		cycleAllowed = false;
		wentBack = false;

		for (int x = 0; x < grid.ncr; x++)
		{
			numbers.add(new ArrayList<>());
			for (int y = 0; y < grid.ncr; y++)
			{
				numbers.get(x).add(new ArrayList<>());
				replenish(x, y);
			}
		}

		count = 0;
		x = 0;
		y = 0;
	}

	private boolean prepare()
	{
		if (used) return false;
		used = true;
		cycleAllowed = true;
		return true;
	}

	private void cycle()
	{
		if (!cycleAllowed) return;

		grid.select(x, y);

		if (wentBack)
		{
			wentBack = false;
			numbers.get(x).get(y).remove(0);
		}

		if (available(x, y))
		{
			int n = 0;
			if (grid.canPlaceNumber(numbers.get(x).get(y).get(n), x, y, -1) || grid.get(x, y) == numbers.get(x).get(y).get(n))
			{
				grid.set(x, y, numbers.get(x).get(y).get(n));
				x++;
			}
			else
			{
				numbers.get(x).get(y).remove(n);
			}
		}
		else
		{
			replenish(x, y);
			if (!grid.isBaseGame(x, y)) grid.set(x, y, -1);
			x--;
			wentBack = true;
		}

		if (x >= grid.ncr)
		{
			x -= grid.ncr;
			y++;
		}
		else if (x < 0)
		{
			x += grid.ncr;
			y--;
		}

		if (y >= grid.ncr)
		{
			count++;
			x += (grid.ncr - 2);
			y--;
			if (!grid.isBaseGame(x, y)) grid.set(grid.ncr - 1, grid.ncr - 1, -1);
			wentBack = true;
		}
		if (y < 0)
		{
			tryNext = false;
			cycleAllowed = false;
		}
	}

	private int finish()
	{
		System.out.println("Solving finished in " + timer.getElapsedTimeSecs() + " seconds (" + timer.getElapsedTime() + " milliseconds, to be precise). Found " + count + " solutions.");
		grid.select(-1, -1);
		return count;
	}

	int countSolutions()
	{
		timer.start();
		if (!prepare()) return -1;

		while (tryNext)
		{
			cycle();
		}

		timer.stop();
		return finish();
	}

	private void replenish(int x, int y)
	{
		ArrayList<Integer> list = numbers.get(x).get(y);
		list.clear();
		if (grid.isBaseGame(x, y))
		{
			list.add(grid.get(x, y));
		}
		else
		{
			for (int i = 0; i < grid.ncr; i++)
			{
				list.add(i);
			}
		}
	}

	private boolean available(int x, int y)
	{
		return numbers.get(x).get(y).size() > 0;
	}
}
