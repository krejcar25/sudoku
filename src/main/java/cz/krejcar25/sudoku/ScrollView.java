package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.style.Color;
import cz.krejcar25.sudoku.ui.Drawable;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public abstract class ScrollView extends BaseView {
    protected Drawable content;

    public int horizontalScrollBarWidth = 5;
    public ScrollBarVisibility horizontalScrollBarVisibility = ScrollBarVisibility.Automatic;
    public Color horizontalScrollBarColor = new Color(51);

    public ScrollBarVisibility verticalScrollBarVisibility = ScrollBarVisibility.Automatic;
    public int verticalScrollBarWidth = 5;
    public Color verticalScrollBarColor = new Color(51);

    public int horizontalScroll = 0;
    public int verticalScroll = 0;

    public int scrollSpeed = 30;

    private int hScrollO = -1;
    private int vScrollO = -1;

    public ScrollView(SudokuApplet applet, int width, int height) {
        super(applet, width, height);
    }

    @Override
    protected void draw() {
        boolean showH = horizontalScrollBarVisibility.showScrollBar(width, content.width);
        boolean showV = verticalScrollBarVisibility.showScrollBar(height, content.height);

        background(220);
        content.update();
        image(content.get(horizontalScroll, verticalScroll, width - (showV ? verticalScrollBarWidth : 0), height - (showH ? horizontalScrollBarWidth : 0)), 0, 0);
        push();
        noStroke();

        if (showH) {
            push();
            translate(0, height - horizontalScrollBarWidth);
            fill(horizontalScrollBarColor.r, horizontalScrollBarColor.g, horizontalScrollBarColor.b);
            float r = (((float) width) / content.width);
            rect(horizontalScroll * r, 0, (width - (showV ? verticalScrollBarWidth : 0)) * r, horizontalScrollBarWidth);
            pop();
        }

        if (showV) {
            push();
            translate(width - verticalScrollBarWidth, 0);
            fill(verticalScrollBarColor.r, verticalScrollBarColor.g, verticalScrollBarColor.b);
            float r = (((float) height) / content.height);
            rect(0, verticalScroll * r, verticalScrollBarWidth, (height - (showH ? horizontalScrollBarWidth : 0)) * r);
            pop();
        }

        pop();
        if (overlay != null) {
            overlay.update();
            image(overlay, overlay.x, overlay.y);
        }
    }

    @Override
    public void keyDown(KeyEvent keyEvent) {
        int x = 0;
        int y = 0;

        if (getApplet().isKeyPressed(SudokuApplet.UP)) y += -scrollSpeed;
        if (getApplet().isKeyPressed(SudokuApplet.RIGHT)) x += scrollSpeed;
        if (getApplet().isKeyPressed(SudokuApplet.DOWN)) y += scrollSpeed;
        if (getApplet().isKeyPressed(SudokuApplet.LEFT)) x += -scrollSpeed;

        scroll(x, y);
    }

    @Override
    protected void mouseDown(int mx, int my, boolean rmb) {
        hScrollO = horizontalScroll;
        vScrollO = verticalScroll;
    }

    @Override
    public void mouseDrag(MouseEvent mouseEvent) {
        horizontalScroll = SudokuApplet.constrain(hScrollO - (mouseEvent.getX() - mousePressX), 0, content.width - width);
        verticalScroll = SudokuApplet.constrain(vScrollO - (mouseEvent.getY() - mousePressY), 0, content.height - height);
    }

    public void scroll(float x, float y) {
        horizontalScroll = SudokuApplet.floor(SudokuApplet.constrain(horizontalScroll + x, 0, content.width - width));
        verticalScroll = SudokuApplet.floor(SudokuApplet.constrain(verticalScroll + y, 0, content.height - height));
        content.x = -horizontalScroll;
        content.y = -verticalScroll;
    }
}
