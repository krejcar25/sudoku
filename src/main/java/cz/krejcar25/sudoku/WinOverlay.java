package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Button;
import processing.core.*;

import processing.event.MouseEvent;

public class WinOverlay extends BaseOverlay {
    WinOverlay(GameView baseView) {
        super(baseView, PApplet.constrain((baseView.width - 540) / 2, 0, baseView.width - 540), (baseView.game.rows() * baseView.game.sy / 2) - 100, PApplet.constrain(baseView.width, 0, 540), 200, OverlayType.OK, (sender) -> {
            System.out.println("WinOverlay OK button has received click event, popping");
            baseView.viewStack.pop(2);
        });
    }

    @Override
    protected void draw() {
        background(0);
        push();
        fill(200, 210, 200);
        rect(10, 10, width - 20, height - 20);
        pop();
        textSize(40);
        textAlign(PApplet.CENTER, PApplet.CENTER);
        //noinspection IntegerDivisionInFloatingPointContext
        text("You have won!", width / 2, 60);
        drawButtons();
    }

    @Override
    public OverlayResult getResult() {
        return result;
    }

    @Override
    public void click(int mx, int my, boolean rmb) {
        System.out.println("WinOverlay has received click event");
        for (Button button : buttons) {
            if (button.isClick(mx - x, my - y)) {
                System.out.println("WinOverlay has found appropriate button");
                button.click();
            }
        }
    }
}
