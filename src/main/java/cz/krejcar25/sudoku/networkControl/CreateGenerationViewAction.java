package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.game.GridProperties;

@FunctionalInterface
public interface CreateGenerationViewAction {
    void create(GridProperties gridProperties, int count);
}
