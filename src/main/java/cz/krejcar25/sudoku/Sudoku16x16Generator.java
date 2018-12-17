package cz.krejcar25.sudoku;

public class Sudoku16x16Generator extends BaseGenerator {
    Sudoku16x16Generator(GameView parent, int targetCount) {
        super(parent, targetCount);
        game = new Grid16x16(parent);
    }
}
