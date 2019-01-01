package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Button;
import cz.krejcar25.sudoku.control.Control;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class ScoreboardSizeSelectView extends BaseView {
    private ArrayList<Control> controls;

    public ScoreboardSizeSelectView(SudokuApplet sudokuApplet) {
        super(sudokuApplet, 800, 600);
        controls = new ArrayList<>();
        int bsx = 340;
        int bsy = 40;
        int baseY = 200;
        int yDelta = 80;
        int index = 0;
        for (GridProperties gridProperties : GridProperties.values()) {
            int bx = (2 * (index % 2) + 1) * width / 4;
            int by = baseY + ((index / 2) * yDelta);
            Button button = new Button(this, bx, by, bsx, bsy, gridProperties.getName(), sender -> viewStack.push(new ScoreboardDifficultySelectView(getApplet(), gridProperties)));
            controls.add(button);
            index++;
        }
        {
            int bx = (2 * (index % 2) + 1) * width / 4;
            int by = baseY + ((index / 2) * yDelta);
            Button resetbutton = new Button(this, bx, by, bsx, bsy, "Reset Scoreboards",
                    sender -> overlay = new BaseOverlay(this, 100, 100, 600, 400, OverlayType.YesNo,
                            yesButton -> {
                                yesButton.getApplet().scoreboard.entries.clear();
                                yesButton.getApplet().scoreboard.save();
                                overlay = null;
                            },
                            noButton -> overlay = null) {
                        @Override
                        public OverlayResult getResult() {
                            return result;
                        }

                        @Override
                        public void click(int mx, int my, boolean rmb) {
                            for (Button button : buttons) if (button.isClick(mx, my)) button.click();
                        }

                        @Override
                        protected void draw() {
                            background(0);
                            push();
                            fill(200, 210, 200);
                            rect(10, 10, width - 20, height - 20);
                            pop();
                            push();
                            textSize(40);
                            textAlign(CENTER, CENTER);
                            rectMode(CENTER);
                            text("Do you really want to clear the Scoreboards?\r\n(This operation can certainly not be undone!)", width / 2f, 150, width - 100, 300);
                            pop();
                            drawButtons();
                        }
                    });
            controls.add(resetbutton);
        }
    }

    @Override
    protected void mouseDown(int mx, int my, boolean rmb) {

    }

    @Override
    protected void mouseUp(int mx, int my, boolean rmb) {

    }

    @Override
    protected void click(int mx, int my, boolean rmb) {
        if (overlay != null) overlay.click(mx - overlay.x, my - overlay.y, rmb);
        else for (Control control : controls) {
            if (control.isClick(mx, my)) control.click();
        }
    }

    @Override
    public void mouseDrag(MouseEvent mouseEvent) {

    }

    @Override
    public void scroll(MouseEvent event) {

    }

    @Override
    public void keyDown(KeyEvent keyEvent) {

    }

    @Override
    public void keyUp(KeyEvent keyEvent) {

    }

    @Override
    protected void draw() {
        background(220);
        fill(51);
        strokeWeight(0);
        textSize(40);
        textAlign(CENTER, CENTER);
        text("Select size:", width / 2f, 100);

        for (Control control : controls) {
            control.update();
            image(control, control.x, control.y);
        }

        if (overlay != null) {
            overlay.update();
            image(overlay, overlay.x, overlay.y);
        }
    }
}
