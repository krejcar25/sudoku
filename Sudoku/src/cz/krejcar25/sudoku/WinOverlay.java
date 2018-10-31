package cz.krejcar25.sudoku;

import processing.core.*;

public class WinOverlay extends BaseOverlay {
    WinOverlay(BaseView parent) {
        super(parent, 105, 300, 600, 200, OverlayType.OK, () -> {
            System.out.println("WinOverlay OK button has received click event, popping");
            parent.applet.stack.pop();
        });
    }

    public PGraphics show() {
        PGraphics g = parent.applet.createGraphics(sx, sy);
        g.beginDraw();
        g.background(0);
        g.pushStyle();
        g.fill(200, 210, 200);
        g.rect(10, 10, sx - 20, sy - 20);
        g.popStyle();
        g.textSize(40);
        g.textAlign(PApplet.CENTER, PApplet.CENTER);
        //noinspection IntegerDivisionInFloatingPointContext
        g.text("You have won!", sx / 2, 60);
        drawButtons(g);
        g.endDraw();
        return g;
    }

    public OverlayResult getResult() {
        return result;
    }

    public void click(int x, int y) {
        System.out.println("WinOverlay has received click event");
        for (Button button : buttons) {
            if (button.isClick(x, y)) {
                System.out.println("WinOverlay has found appropriate button");
                button.click.click();
            }
        }
    }
}
