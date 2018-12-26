package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Button;
import cz.krejcar25.sudoku.control.Control;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class SettingsView extends ScrollView {
    SettingsView(SudokuApplet applet) {
        super(applet, 800, 600);
        content = new SettingsViewContent(this, 800, 1200);
        //setResizable(content.width, content.height);
        additionalControls.add(new Button(this, 25, 10, 50, 20, "Back", sender -> {
            applet.stack.removeSpecific(this);
        }));
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

