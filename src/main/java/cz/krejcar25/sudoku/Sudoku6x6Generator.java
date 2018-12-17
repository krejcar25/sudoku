package cz.krejcar25.sudoku;

public class Sudoku6x6Generator extends BaseGenerator {
    Sudoku6x6Generator(GameView parent, int targetCount) {
        super(parent, targetCount);
        game = new Grid6x6(parent);
    }
}
