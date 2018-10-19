package cz.krejcar25.sudoku;

import processing.core.*;

public class WinOverlay extends BaseOverlay {
    public WinOverlay() {
        super(105, 300, 600, 200, OverlayType.OK);
    }

    public PGraphics show() {
        PGraphics g = Main.pa.createGraphics(sx, sy);
        g.beginDraw();
        g.background(0);
        g.pushStyle();
        g.fill(200, 210, 200);
        g.rect(10, 10, sx-20, sy-20);
        g.popStyle();
        g.textSize(40);
        g.textAlign(PApplet.CENTER, PApplet.CENTER);
        g.text("You have won!", sx / 2, 60);
        drawButtons(g);
        g.endDraw();
        return g;
    }

    public OverlayResult getResult() {
        return OverlayResult.None;
    }
}
