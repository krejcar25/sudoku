package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.game.GridDifficulty;
import cz.krejcar25.sudoku.game.GridProperties;

@FunctionalInterface
interface CreateGenerationViewAction
{
	void create(GridProperties gridProperties, GridDifficulty gridDifficulty, int count);
}
