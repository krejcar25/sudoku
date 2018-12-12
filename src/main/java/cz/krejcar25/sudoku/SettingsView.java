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
        Toggle numFirst = new Toggle(applet, 300, 100, 40, 20, new IToggleEvents() {
            public void toggled(Toggle sender) {
                applet.settings.setDefaultNumberFirst(sender.state);
                applet.settings.save();
            }

            public void switchedOn(Toggle sender) {

            }

            public void switchedOff(Toggle sender) {

            }
        });
        numFirst.state = applet.settings.isDefaultNumberFirst();
        controls.add(numFirst);
        Toggle notes = new Toggle(applet, 300, 130, 40, 20, new IToggleEvents() {
            public void toggled(Toggle sender) {
                applet.settings.setDefaultNotes(sender.state);
                applet.settings.save();
            }

            public void switchedOn(Toggle sender) {

            }

            public void switchedOff(Toggle sender) {

            }
        });
        notes.state = applet.settings.isDefaultNotes();
        controls.add(notes);
    }

    public void show() {
        content = applet.createGraphics(800, 1200);
        setResizable(content.width, content.height);
        content.beginDraw();
        content.pushStyle();
        content.background(51);
        for (IControl c : controls) c.show(content);
        content.textSize(40);
        content.textAlign(PGraphics.LEFT,PGraphics.TOP);
        content.fill(220);
        content.text("Settings", 50, 50);
        content.textSize(20);
        content.text("Number first by default", 50, 100);
        content.text("Write notes by default", 50, 130);
        content.text("End of scrollable section", 280, 900);
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

