package cz.krejcar25.sudoku.control;

import cz.krejcar25.sudoku.Main;
import cz.krejcar25.sudoku.event.IToggleEvents;
import processing.core.PGraphics;

import static processing.core.PConstants.*;

public class Toggle implements IControl {
    public boolean state;
    private Main applet;
    public int x;
    public int y;
    public int sx;
    public int sy;
    private IToggleEvents toggleEvents;

    private int motionStart;

    public Toggle(Main applet, int x, int y, int sx, int sy, IToggleEvents toggleEvents) {
        this.applet = applet;
        this.x = x;
        this.y = y;
        if (sy * 1.5 > sx) throw new IllegalArgumentException();
        this.sx = sx;
        this.sy = sy;
        this.state = false;
        this.toggleEvents = toggleEvents;
    }

    public void show(PGraphics g) {
        g.pushMatrix();
        g.pushStyle();
        g.translate(x, y);

        if (state) {
            g.fill(0, 200, 0);
            g.stroke(0, 150, 0);
        } else {
            g.fill(220);
            g.stroke(150);
        }

        int ssy = sy / 2;
        g.rect(ssy, 0, sx - sy, sy);
        g.arc(ssy + 1, ssy, sy, sy, HALF_PI, 3 * HALF_PI, OPEN);
        g.arc((sx - sy) + ssy, ssy, sy, sy, -HALF_PI, HALF_PI, OPEN);

        g.fill(220);
        int lerpStart = ssy + ((state) ? 0 : (sx - sy));
        int lerpStop = ssy + ((state) ? (sx - sy) : 0);
        float lerpAmt = (applet.frameCount - motionStart) / 10f;
        float lerp = Main.lerp(lerpStart, lerpStop, lerpAmt);
        g.ellipse(lerpAmt > 1 ? lerpStop : lerp, ssy, sy, sy);


        g.popStyle();
        g.popMatrix();
    }

    public boolean isClick(int x, int y) {
        boolean hor = this.x < x && x < (this.x + this.sx);
        boolean ver = this.y < y && y < (this.y + this.sy);
        return hor && ver;
    }

    public void click() {
        state = !state;
        motionStart = applet.frameCount;
        toggleEvents.toggled(this);
        if (state) toggleEvents.switchedOn(this);
        else toggleEvents.switchedOff(this);
    }
}
