package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Button;
import processing.event.KeyEvent;

public class ScoreboardDisplayView extends ScrollView {
    public ScoreboardDisplayView(SudokuApplet sudokuApplet, GridProperties gridProperties, GridDifficulty gridDifficulty) {
        super(sudokuApplet, 800, 600);
        content = new ScoreboardDisplayViewContent(this, gridProperties, gridDifficulty);
        additionalControls.add(Button.getStandardBackButton(this));
    }

    @Override
    public void keyUp(KeyEvent keyEvent) {

    }
}
