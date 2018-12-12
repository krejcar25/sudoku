package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.IControl;
import cz.krejcar25.sudoku.control.Toggle;
import cz.krejcar25.sudoku.event.IToggleEvents;
import processing.core.*;

import java.util.ArrayList;

public class SettingsView extends ScrollView {
    ArrayList<IControl> controls;

    SettingsView(Main applet) {
        super(applet, 800, 600);
        controls = new ArrayList<>();
        controls.add(new Toggle(applet, 280, 400, 80, 40, new IToggleEvents() {
            public void toggled(Toggle sender) {

            }

            public void switchedOn(Toggle sender) {

            }

            public void switchedOff(Toggle sender) {

            }
        }));
    }

    public void show() {
        content = applet.createGraphics(800, 1200);
        setResizable(content.width, content.height);
        content.beginDraw();
        content.pushStyle();
        content.background(51);
        for (IControl c : controls) c.show(content);
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
            return;
        }
        for (IControl c : controls) if (c.isClick(x, y)) c.click();
    }

    private void saveConfig() {

    }
}

