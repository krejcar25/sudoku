package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Control;
import cz.krejcar25.sudoku.control.Toggle;
import cz.krejcar25.sudoku.event.ToggleEvents;
import cz.krejcar25.sudoku.ui.Drawable;
import processing.core.PGraphics;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class SettingsViewContent extends Drawable {
    ArrayList<Control> controls;
    private final SettingsView parentView;

    public SettingsViewContent(SettingsView settingsView, int width, int height) {
        super(settingsView.getApplet(), 0, 0, width, height);
        parentView = settingsView;
        controls = new ArrayList<>();

        Toggle numFirst = new Toggle(parentView, 300, 100, 40, 20, new ToggleEvents() {
            @Override
            public void toggled(Toggle sender) {
                getApplet().settings.setDefaultNumberFirst(sender.state);
                getApplet().settings.save();
            }

            @Override
            public void switchedOn(Toggle sender) {

            }

            @Override
            public void switchedOff(Toggle sender) {

            }
        });
        numFirst.state = getApplet().settings.isDefaultNumberFirst();
        controls.add(numFirst);
        Toggle notes = new Toggle(parentView, 300, 130, 40, 20, new ToggleEvents() {
            @Override
            public void toggled(Toggle sender) {
                getApplet().settings.setDefaultNotes(sender.state);
                getApplet().settings.save();
            }

            @Override
            public void switchedOn(Toggle sender) {

            }

            @Override
            public void switchedOff(Toggle sender) {

            }
        });
        notes.state = getApplet().settings.isDefaultNotes();
        controls.add(notes);
    }

    @Override
    protected void draw() {
        push();
        background(51);
        for (Control c : controls) {
            c.update();
            image(c, c.x, c.y);
        }
        textSize(40);
        textAlign(PGraphics.LEFT, PGraphics.TOP);
        fill(220);
        text("Settings", 50, 50);
        textSize(20);
        text("Number first by default", 50, 100);
        text("Write notes by default", 50, 130);
        text("End of scrollable section", 280, 900);
        pop();
    }

    public void click(int mx, int my, boolean rmb) {
        for (Control c : controls) if (c.isClick(mx - x, my - y)) c.click();
    }
}
