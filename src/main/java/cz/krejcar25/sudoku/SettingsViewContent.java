package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Control;
import cz.krejcar25.sudoku.control.ControlLabel;
import cz.krejcar25.sudoku.control.Toggle;
import cz.krejcar25.sudoku.event.ToggleEvents;
import processing.core.PGraphics;

import java.util.ArrayList;

public class SettingsViewContent extends ScrollViewContent {
    ArrayList<Control> controls;

    public SettingsViewContent(SettingsView settingsView) {
        super(settingsView, 800, 1200);
        controls = new ArrayList<>();

        Toggle numFirst = new Toggle(settingsView, 600, 150, 80, 40, new ToggleEvents() {
            @Override
            public void toggled(Control sender) {
                getApplet().settings.setDefaultNumberFirst(((Toggle) sender).state);
                getApplet().settings.save();
            }

            @Override
            public void switchedOn(Control sender) {

            }

            @Override
            public void switchedOff(Control sender) {

            }
        });
        numFirst.state = getApplet().settings.isDefaultNumberFirst();
        ControlLabel numFirstLabel = new ControlLabel(numFirst, ControlLabel.CONTROL_RIGHT, "Number first by default");
        numFirstLabel.fixLabelOnX(50);
        controls.add(numFirstLabel);
        Toggle notes = new Toggle(settingsView, 600, 200, 80, 40, new ToggleEvents() {
            @Override
            public void toggled(Control sender) {
                getApplet().settings.setDefaultNotes(((Toggle) sender).state);
                getApplet().settings.save();
            }

            @Override
            public void switchedOn(Control sender) {

            }

            @Override
            public void switchedOff(Control sender) {

            }
        });
        notes.state = getApplet().settings.isDefaultNotes();
        ControlLabel notesLabel = new ControlLabel(notes, ControlLabel.CONTROL_RIGHT, "Write notes by default");
        notesLabel.fixLabelOnX(50);
        controls.add(notesLabel);
    }

    @Override
    protected void draw() {
        push();
        background(51);
        for (Control c : controls) {
            c.update();
            image(c, c.x, c.y);
        }
        textSize(80);
        textAlign(PGraphics.LEFT, PGraphics.TOP);
        fill(220);
        text("Settings", 50, 50);
        textSize(40);
        text("End of scrollable section", 280, 900);
        pop();
    }

    @Override
    public void click(int mx, int my, boolean rmb) {
        for (Control c : controls) if (c.isClick(mx, my)) c.click();
    }
}
