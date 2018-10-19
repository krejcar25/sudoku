package cz.krejcar25.sudoku;

public interface IGameView {
    BaseSolver getSolver();
    BaseGenerator getGenerator();
    BaseGrid getGrid();
    void generate();
    void newGenerator();
    void newSolver();
}
