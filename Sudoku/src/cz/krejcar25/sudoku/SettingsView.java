package cz.krejcar25.sudoku;

import processing.core.*;

public class SettingsView extends ScrollView {
    SettingsView(Main applet) {
        super(applet,800, 600);
    }

    public void show() {
        content = applet.createGraphics(800, 1200);
        setResizable(content.width, content.height);
        content.beginDraw();
        content.pushStyle();
        content.background(51);
        content.textSize(40);
        content.fill(220);
        content.text("Settings", 280, 350);
        content.text("Footer", 280, 900);
        content.popStyle();
        content.endDraw();

        super.show();
    }

    public void click(int x, int y) {
        if (applet.mouseButton == PApplet.RIGHT) {
            applet.stack.pop();
        }
    }
}
