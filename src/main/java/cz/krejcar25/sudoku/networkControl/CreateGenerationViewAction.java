package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.game.GridProperties;

@FunctionalInterface
interface CreateGenerationViewAction {
	void create(GridProperties gridProperties, int clueCount, int count);
}
