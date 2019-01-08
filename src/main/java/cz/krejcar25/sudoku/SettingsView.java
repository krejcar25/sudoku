package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Button;
import cz.krejcar25.sudoku.style.Color;
import processing.event.KeyEvent;

public class SettingsView extends ScrollView {
    SettingsView(SudokuApplet applet) {
        super(applet, 800, 600);
        content = new SettingsViewContent(this);
        additionalControls.add(Button.getStandardBackButton(this));
        horizontalScrollBarColor = new Color(220);
        verticalScrollBarColor = new Color(220);
    }

    @Override
    public void keyUp(KeyEvent keyEvent) {

    }
}

