package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.style.Color;
import processing.core.PGraphics;

public abstract class ScrollView extends BaseView {
    protected PGraphics content;

    public int horizontalScrollBarWidth = 5;
    public ScrollBarVisibility horizontalScrollBarVisibility = ScrollBarVisibility.Automatic;
    public Color horizontalScrollBarColor = new Color(51);

    public ScrollBarVisibility verticalScrollBarVisibility = ScrollBarVisibility.Automatic;
    public int verticalScrollBarWidth = 5;
    public Color verticalScrollBarColor = new Color(51);

    public int horizontalScroll = 0;
    public int verticalScroll = 0;

    public int scrollSpeed = 30;

    public ScrollView(Main applet, int sizex, int sizey) {
        super(applet, sizex, sizey);
    }

    public void show() {
        boolean showH = horizontalScrollBarVisibility.showScrollBar(sizex, content.width);
        boolean showV = verticalScrollBarVisibility.showScrollBar(sizey, content.height);

        applet.background(220);
        applet.image(content.get(horizontalScroll, verticalScroll, sizex - (showV ? verticalScrollBarWidth : 0), sizey - (showH ? horizontalScrollBarWidth : 0)), 0, 0);
        applet.push();
        applet.noStroke();

        if (showH) {
            applet.push();
            applet.translate(0, sizey - horizontalScrollBarWidth);
            applet.fill(horizontalScrollBarColor.r, horizontalScrollBarColor.g, horizontalScrollBarColor.b);
            float r = (((float) sizex) / content.width);
            applet.rect(horizontalScroll * r, 0, (sizex - (showV ? verticalScrollBarWidth : 0)) * r, horizontalScrollBarWidth);
            applet.pop();
        }

        if (showV) {
            applet.push();
            applet.translate(sizex - verticalScrollBarWidth, 0);
            applet.fill(verticalScrollBarColor.r, verticalScrollBarColor.g, verticalScrollBarColor.b);
            float r = (((float) sizey) / content.height);
            applet.rect(0, verticalScroll * r, verticalScrollBarWidth, (sizey - (showH ? horizontalScrollBarWidth : 0)) * r);
            applet.pop();
        }

        applet.pop();
        if (overlay != null) content.image(overlay.show(), overlay.x, overlay.y);
    }

    public void keyPress() {
        int x = 0;
        int y = 0;

        if (applet.isKeyPressed(Main.UP)) y += -scrollSpeed;
        if (applet.isKeyPressed(Main.RIGHT)) x += scrollSpeed;
        if (applet.isKeyPressed(Main.DOWN)) y += scrollSpeed;
        if (applet.isKeyPressed(Main.LEFT)) x += -scrollSpeed;

        scroll(x, y);
    }

    public void scroll(float x, float y) {
        if (0 <= (horizontalScroll + x) && (horizontalScroll + x) <= (content.width - sizex)) horizontalScroll += x;
        if (0 <= (verticalScroll + y) && (verticalScroll + y) <= (content.height - sizey)) verticalScroll += y;
    }
}
