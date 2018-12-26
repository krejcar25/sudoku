package cz.krejcar25.sudoku.control;

import cz.krejcar25.sudoku.BaseView;
import cz.krejcar25.sudoku.event.ButtonEvents;
import cz.krejcar25.sudoku.style.ButtonStyle;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import processing.core.*;
import processing.event.MouseEvent;

public class Button extends Control {
    private ButtonEvents click;

    public ButtonStyle style;

    public Button(BaseView baseView, int centerX, int centerY, int width, int height, String label, ButtonEvents click) {
        super(baseView, centerX - width / 2, centerY - height / 2, width, height);
        this.label = label;
        this.click = click;

        style = new ButtonStyle();
    }

    @Override
    protected void draw() {
        push();
        strokeWeight(0);
        push();
        fill(style.border.r, style.border.g, style.border.b);
        push();
        rect(0, 0, width, height);
        pop();
        fill(style.background.r, style.background.g, style.background.b);
        rect(style.borderThickness.left, style.borderThickness.top, width - style.borderThickness.totalRl(), height - style.borderThickness.totalTb());
        pop();

        push();
        fill(style.foreground.r, style.foreground.g, style.foreground.b);
        textSize(height - style.borderThickness.totalTb());
        textAlign(PApplet.CENTER, PApplet.CENTER);
        //noinspection IntegerDivisionInFloatingPointContext
        translate(width / 2, height / 2);
        text(label, 0, -3);
        pop();
        pop();
    }

    @Override
    public boolean isClick(int mx, int my) {
        int right = (this.x + width);
        boolean hor = this.x < mx && mx < right;
        int bottom = (this.y + this.height);
        boolean ver = this.y < my && my < bottom;
        return hor && ver;
    }

    @Override
    public void click() {
        click.click(this);
    }

    @NotNull
    @Contract("_ -> new")
    public static Button getStandardBackButton(BaseView baseView) {
        return new Button(baseView, 30, 15, 60, 30, "Back", sender -> sender.baseView.removeFromViewStack());
    }
}

