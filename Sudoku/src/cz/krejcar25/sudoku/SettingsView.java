package cz.krejcar25.sudoku;

import processing.core.*;

public class SettingsView extends BaseView {
    public SettingsView(Main applet) {
        super(applet,800, 600);
    }

    public void show() {
        applet.push();
        applet.background(51);
        applet.textSize(40);
        applet.fill(220);
        applet.text("Settings", 280, 350);

        if (overlay != null) applet.image(overlay.show(), overlay.x, overlay.y);
        applet.pop();
    }

    public void click(int x, int y) {
        if (applet.mouseButton == PApplet.RIGHT) {
            applet.stack.pop();
        }
    }
}
