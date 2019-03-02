package cz.krejcar25.sudoku.game;

import cz.krejcar25.sudoku.SudokuApplet;
import cz.krejcar25.sudoku.event.ToggleEvents;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.control.Button;
import cz.krejcar25.sudoku.ui.control.Checkbox;
import cz.krejcar25.sudoku.ui.control.Control;
import cz.krejcar25.sudoku.ui.control.ControlLabel;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class DifficultySelectView extends BaseView {
    public final GameView gameView;
    private final ArrayList<Control> controls;
    private Checkbox animateCheckbox;

    public DifficultySelectView(SudokuApplet sudokuApplet, GridProperties gridProperties) {
        super(sudokuApplet, 800, 600);
        this.gameView = new GameView(sudokuApplet, gridProperties);
        int bsx = 340;
        int bsy = 40;
        controls = new ArrayList<>();
	    controls.add(new Button<>(this, width / 4f, 200, bsx, bsy, "Easy", sender -> startGeneration(getApplet().isKeyPressed(SHIFT) ? GridDifficulty.Debug : GridDifficulty.Easy)));
	    controls.add(new Button<>(this, 3 * width / 4f, 200, bsx, bsy, "Medium", sender -> startGeneration(GridDifficulty.Medium)));
	    controls.add(new Button<>(this, width / 4f, 280, bsx, bsy, "Hard", sender -> startGeneration(GridDifficulty.Hard)));
	    controls.add(new Button<>(this, 3 * width / 4f, 280, bsx, bsy, "Extreme", sender -> startGeneration(GridDifficulty.Extreme)));
        controls.add(Button.getStandardBackButton(this));
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
        ControlLabel animateLabel = new ControlLabel(animateCheckbox, ControlLabel.CONTROL_LEFT, "Animate grid generation");
        animateLabel.centerOnX(400);
        controls.add(animateLabel);
    }

    private void startGeneration(GridDifficulty gridDifficulty) {
        overlay = new GeneratingOverlay(this, gridDifficulty);
        new Thread(() -> {
            gameView.generate(gridDifficulty, animateCheckbox.state);
            viewStack.push(gameView);
        }).start();
    }

    @Override
    public void mouseDown(int mx, int my, boolean rmb) {

    }

    @Override
    public void mouseUp(int mx, int my, boolean rmb) {

    }

    @Override
    public void click(int mx, int my, boolean rmb) {
        if (rmb) {
            removeFromViewStack();
            throw new RuntimeException("This is a test exception thrown in a DifficultySelectView");
        } else for (Control control : controls) if (control.isClick(mx - x, my - y)) control.click();
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
        text("Select difficulty for " + gameView.gridProperties.getName(), 400, 100);

        for (Control control : controls) {
            control.update();
	        //image(control, control.x, control.y);
        }

        if (overlay != null) {
            overlay.update();
	        //image(overlay, overlay.x, overlay.y);
        }
        pop();
    }
}
