package cz.krejcar25.sudoku.control;

import cz.krejcar25.sudoku.BaseView;
import cz.krejcar25.sudoku.event.ToggleEvents;

public class Checkbox extends Control {
    public boolean state = false;
    private final int checkboxWidth;
    private ToggleEvents toggleEvents;

    public Checkbox(BaseView baseView, int x, int y, int width, int height, ToggleEvents toggleEvents) {
        super(baseView, x, y, width, height);
        this.checkboxWidth = width;
        this.toggleEvents = toggleEvents;
    }

    @Override
    public boolean isClick(int mx, int my) {
        return x < mx && mx < (x + checkboxWidth) && y < my && my < (y + height);
    }

    @Override
    public void click() {
        state = !state;

        toggleEvents.toggled(this);
        if (state) toggleEvents.switchedOn(this);
        else toggleEvents.switchedOff(this);
    }

    @Override
    protected void draw() {
        push();

        background(220);

        if (state) fill(150, 255, 150);
        else fill(255, 150, 150);

        strokeWeight(2);
        rect(0, 0, checkboxWidth - 2, height - 2);

        stroke(51);
        strokeWeight(4);

        int margin = 6;
        if (state) {
            line(margin, height / 2f, checkboxWidth / 2f, height - margin);
            line(checkboxWidth / 2f, height - margin, checkboxWidth - margin, margin);
        } else {
            line(margin, margin, checkboxWidth - margin, height - margin);
            line(margin, height - margin, checkboxWidth - margin, margin);
        }

        fill(51);

        pop();
    }
}
