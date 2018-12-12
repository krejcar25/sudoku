package cz.krejcar25.sudoku;

public class Sudoku16x16Generator extends BaseGenerator {
    Sudoku16x16Generator(BaseView parent, int targetCount) {
        super(parent, targetCount);
        game = new Grid16x16(parent);
    }
}
