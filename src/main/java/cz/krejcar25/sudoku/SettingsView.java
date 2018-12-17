package cz.krejcar25.sudoku;

import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class SettingsView extends ScrollView {
    SettingsView(SudokuApplet applet) {
        super(applet, 800, 600);
        content = new SettingsViewContent(this, 800,1200);
    }

    @Override
    protected void mouseUp(int mx, int my, boolean rmb) {

    }

    @Override
    protected void draw() {
        setResizable(content.width, content.height);

        super.draw();
    }

    public void click(int mx, int my, boolean rmb) {
        if (rmb) {
            viewStack.removeSpecific(this);
            return;
        }
        ((SettingsViewContent) content).click(mx - x, my - y, rmb);
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

