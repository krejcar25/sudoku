package cz.krejcar25.sudoku.ui.control;

import cz.krejcar25.sudoku.SudokuApplet;
import cz.krejcar25.sudoku.event.ToggleEvents;
import cz.krejcar25.sudoku.ui.BaseView;

public class Toggle extends Control {
    public boolean state;
    private ToggleEvents toggleEvents;

    private int motionStart;

    public Toggle(BaseView baseView, int x, int y, int width, int height, ToggleEvents toggleEvents) {
        super(baseView, x, y, width, height);
        if (height * 1.5 > width) throw new IllegalArgumentException();
        this.state = false;
        this.toggleEvents = toggleEvents;
    }

    @Override
    public boolean isClick(int mx, int my) {
	    if (!enabled) return false;
        boolean hor = this.x < mx && mx < (this.x + this.width);
        boolean ver = this.y < my && my < (this.y + this.height);
        return hor && ver;
    }

    @Override
    public void click() {
        state = !state;
        motionStart = parent.frameCount;
        toggleEvents.toggled(this);
        if (state) toggleEvents.switchedOn(this);
        else toggleEvents.switchedOff(this);
    }

    @Override
    protected void draw() {
        push();
        if (state) {
            fill(0, 200, 0);
            stroke(0, 150, 0);
        } else {
            fill(220);
            stroke(150);
        }

        int sheight = height / 2;
        rect(sheight, 0, width - height, height);
        arc(sheight + 1, sheight, height, height, HALF_PI, 3 * HALF_PI, OPEN);
        arc((width - height) + sheight, sheight, height, height, -HALF_PI, HALF_PI, OPEN);

        fill(220);
        int lerpStart = sheight + ((state) ? 0 : (width - height));
        int lerpStop = sheight + ((state) ? (width - height) : 0);
        float lerpAmt = (parent.frameCount - motionStart) / 10f;
        float lerp = SudokuApplet.lerp(lerpStart, lerpStop, lerpAmt);
        ellipse(lerpAmt > 1 ? lerpStop : lerp, sheight, height, height);


        pop();
    }
}
