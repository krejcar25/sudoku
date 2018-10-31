package cz.krejcar25.sudoku;

import java.util.ArrayList;

import processing.core.*;

public abstract class BaseOverlay {
    BaseView parent;
    public int x;
    public int y;
    int sx;
    int sy;
    OverlayResult result;
    ArrayList<Button> buttons;

    BaseOverlay(BaseView parent, int x, int y, int sx, int sy, OverlayType type, IButtonClick... clicks) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;
        this.result = OverlayResult.None;
        this.buttons = new ArrayList<>();

        int bsx = 135;
        int bsy = 40;
        int bbx = 2;
        int bby = 2;
        if (clicks.length != type.buttonCount) throw new IllegalArgumentException();
        switch (type.buttonCount) {
            case 1:
                buttons.add(new Button(sx / 2, sy - 40, bsx, bsy, bbx, bby, type.buttonLabels[0], clicks[0]));
                break;
            case 2:
                buttons.add(new Button(sx / 3, sy - 40, bsx, bsy, bbx, bby, type.buttonLabels[0], clicks[0]));
                buttons.add(new Button(2 * sx / 3, sy - 40, bsx, bsy, bbx, bby, type.buttonLabels[1], clicks[1]));
                break;
            case 3:
                buttons.add(new Button(sx / 4, sy - 40, bsx, bsy, bbx, bby, type.buttonLabels[0], clicks[0]));
                buttons.add(new Button(sx / 2, sy - 40, bsx, bsy, bbx, bby, type.buttonLabels[1], clicks[1]));
                buttons.add(new Button(3 * sx / 4, sy - 40, bsx, bsy, bbx, bby, type.buttonLabels[2], clicks[2]));
                break;
        }
    }

    void drawButtons(PGraphics g) {
        for (Button button : buttons) {
            button.show(g);
        }
    }

    public abstract void click(int x, int y);

    public abstract PGraphics show();

    public abstract OverlayResult getResult();
}
