package cz.krejcar25.sudoku;

public class Sudoku4x4Generator extends BaseGenerator {
    Sudoku4x4Generator(BaseView parent, int targetCount) {
        super(parent, targetCount);
        game = new Grid4x4(parent);
    }
}
