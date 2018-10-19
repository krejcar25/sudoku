package cz.krejcar25.sudoku;

import processing.core.*;

public class SettingsView extends BaseView {
    public SettingsView() {
        super(800, 600);
    }

    public void show() {
        Main.pa.push();
        Main.pa.background(51);
        Main.pa.textSize(40);
        Main.pa.fill(220);
        Main.pa.text("Settings", 280, 350);

        if (overlay != null) Main.pa.image(overlay.show(), overlay.x, overlay.y);
        Main.pa.pop();
    }

    public void click(int x, int y) {
        if (Main.pa.mouseButton == PApplet.RIGHT) {
            Main.pa.stack.pop();
        }
    }
}
