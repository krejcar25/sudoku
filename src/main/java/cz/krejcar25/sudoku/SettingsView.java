package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Button;
import processing.event.KeyEvent;

public class SettingsView extends ScrollView {
    SettingsView(SudokuApplet applet) {
        super(applet, 800, 600);
        content = new SettingsViewContent(this);
        additionalControls.add(Button.getStandardBackButton(this));
    }

    @Override
    public void keyUp(KeyEvent keyEvent) {

    }
}

