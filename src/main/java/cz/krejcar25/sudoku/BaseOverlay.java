package cz.krejcar25.sudoku;

import java.util.ArrayList;

import cz.krejcar25.sudoku.control.Button;
import cz.krejcar25.sudoku.event.ButtonEvents;
import cz.krejcar25.sudoku.ui.Drawable;
import processing.core.*;
import processing.event.MouseEvent;

public abstract class BaseOverlay extends Drawable {
    BaseView baseView;
    OverlayResult result;
    ArrayList<Button> buttons;

    BaseOverlay(BaseView baseView, int x, int y, int width, int height, OverlayType type, ButtonEvents... clicks) {
        super(baseView.getApplet(), x, y, width, height);
        this.baseView = baseView;
        this.result = OverlayResult.None;
        this.buttons = new ArrayList<>();

        int bsx = 135;
        int bsy = 40;
        if (clicks.length != type.buttonCount) throw new IllegalArgumentException();
        switch (type.buttonCount) {
            case 0:
                break;
            case 1:
                buttons.add(new Button(baseView, this.width / 2, this.height - 40, bsx, bsy, type.buttonLabels[0], clicks[0]));
                break;
            case 2:
                buttons.add(new Button(baseView, this.width / 3, this.height - 40, bsx, bsy, type.buttonLabels[0], clicks[0]));
                buttons.add(new Button(baseView, 2 * this.width / 3, this.height - 40, bsx, bsy, type.buttonLabels[1], clicks[1]));
                break;
            case 3:
                buttons.add(new Button(baseView, this.width / 4, this.height - 40, bsx, bsy, type.buttonLabels[0], clicks[0]));
                buttons.add(new Button(baseView, this.width / 2, this.height - 40, bsx, bsy, type.buttonLabels[1], clicks[1]));
                buttons.add(new Button(baseView, 3 * this.width / 4, this.height - 40, bsx, bsy, type.buttonLabels[2], clicks[2]));
                break;
        }
    }

    protected void drawButtons() {
        for (Button button : buttons) {
            button.update();
            image(button, button.x, button.y);
        }
    }

    public abstract OverlayResult getResult();

    public abstract void click(int mx, int my, boolean rmb);
}
