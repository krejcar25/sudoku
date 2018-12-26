package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Button;
import processing.core.PApplet;
import sun.security.pkcs11.wrapper.CK_LOCKMUTEX;

public class GeneratingOverlay extends BaseOverlay {
    private Clock clock;

    GeneratingOverlay(DifficultySelectView baseView) {
        super(baseView, 105, 350, 600, 200, OverlayType.Info);
        clock = baseView.gameView.getGenerator().timer;
    }

    @Override
    protected void draw() {
        background(0);
        push();
        fill(200, 210, 200);
        rect(10, 10, width - 20, height - 20);
        pop();
        textSize(40);
        textAlign(CENTER, CENTER);
        //noinspection IntegerDivisionInFloatingPointContext
        text("Generation is in progress", width / 2, 60);
        clock.update();
        float clockWidth = Clock.getWidthFromHeight(60);
        image(clock, (width - clockWidth) / 2, 120, clockWidth, 60);
    }

    @Override
    public OverlayResult getResult() {
        return result;
    }

    @Override
    public void click(int mx, int my, boolean rmb) {
        System.out.println("GeneratingOverlay has received click event");
    }
}
