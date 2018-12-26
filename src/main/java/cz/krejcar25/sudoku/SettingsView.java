package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Button;
import cz.krejcar25.sudoku.control.Control;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class SettingsView extends ScrollView {
    SettingsView(SudokuApplet applet) {
        super(applet, 800, 600);
        content = new SettingsViewContent(this);
        //setResizable(content.width, content.height);
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

    private void saveConfig() {

    }
}

