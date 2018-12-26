package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Button;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class HelpView extends ScrollView {
    public HelpView(SudokuApplet sudokuApplet) {
        super(sudokuApplet, 800, 600);
        content = new HelpViewContent(this);
        additionalControls.add(Button.getStandardBackButton(this));
    }

    @Override
    protected void mouseUp(int mx, int my, boolean rmb) {

    }

    @Override
    public void scroll(MouseEvent event) {

    }

    @Override
    public void keyUp(KeyEvent keyEvent) {

    }
}
