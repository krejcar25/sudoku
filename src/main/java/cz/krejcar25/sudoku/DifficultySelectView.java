package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Button;
import cz.krejcar25.sudoku.control.Checkbox;
import cz.krejcar25.sudoku.control.Control;
import cz.krejcar25.sudoku.control.ControlLabel;
import cz.krejcar25.sudoku.event.ToggleEvents;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class DifficultySelectView extends BaseView {
    public final GameView gameView;
    private final ArrayList<Control> controls;
    private Checkbox animateCheckbox;

    public DifficultySelectView(SudokuApplet sudokuApplet, GridProperties gridProperties) {
        super(sudokuApplet, 800,600);
        this.gameView = new GameView(sudokuApplet, gridProperties);
        int bsx = 280;
        int bsy = 40;
        controls = new ArrayList<>();
        controls.add(new Button(this, width / 4, 200, bsx, bsy, "Easy", sender -> startGeneration(getApplet().isKeyPressed(SHIFT) ? GridDifficulty.Debug : GridDifficulty.Easy)));
        controls.add(new Button(this, 3 * width / 4, 200, bsx, bsy, "Medium", sender -> startGeneration(GridDifficulty.Medium)));
        controls.add(new Button(this, width / 4, 280, bsx, bsy, "Hard", sender -> startGeneration(GridDifficulty.Hard)));
        controls.add(new Button(this, 3 * width / 4, 280, bsx, bsy, "Extreme", sender -> startGeneration(GridDifficulty.Extreme)));
        controls.add(new Button(this, 25, 10, 50, 20, "Back", sender -> sudokuApplet.stack.removeSpecific(this)));
        animateCheckbox = new Checkbox(this, 300, 400, 30, 30, new ToggleEvents() {
            @Override
            public void toggled(Control sender) {

            }

            @Override
            public void switchedOn(Control sender) {

            }

            @Override
            public void switchedOff(Control sender) {

            }
        });
        ControlLabel animateLabel = new ControlLabel(animateCheckbox, ControlLabel.CONTROL_LEFT, "Animate game generation");
        animateLabel.centerOnX(400);
        controls.add(animateLabel);
    }

    private void startGeneration(GridDifficulty gridDifficulty) {
        overlay = new GeneratingOverlay(this, gridDifficulty);
        new Thread(() -> {
            gameView.generate(gridDifficulty, animateCheckbox.state);
            getApplet().stack.push(gameView);
        }).start();
    }

    @Override
    protected void mouseDown(int mx, int my, boolean rmb) {

    }

    @Override
    protected void mouseUp(int mx, int my, boolean rmb) {

    }

    @Override
    protected void click(int mx, int my, boolean rmb) {
        if (!rmb) {
            for (Control control : controls) if (control.isClick(mx - x, my - y)) control.click();
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

        push();
        background(220);
        textSize(40);
        fill(51);
        textAlign(CENTER, CENTER);
        text("Select difficulty", 400, 100);

        for (Control control : controls) {
            control.update();
            image(control, control.x, control.y);
        }

        if (overlay != null) {
            overlay.update();
            image(overlay, overlay.x, overlay.y);
        }
        pop();
    }
}
