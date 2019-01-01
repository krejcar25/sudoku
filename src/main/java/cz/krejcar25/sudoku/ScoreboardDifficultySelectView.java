package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Button;
import cz.krejcar25.sudoku.control.Control;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class ScoreboardDifficultySelectView extends BaseView {
    private GridProperties gridProperties;
    private ArrayList<Control> controls;

    public ScoreboardDifficultySelectView(SudokuApplet sudokuApplet, GridProperties gridProperties) {
        super(sudokuApplet, 800, 600);
        this.gridProperties = gridProperties;
        this.controls = new ArrayList<>();

        int bsx = 340;
        int bsy = 40;
        controls.add(new Button(this, width / 4, 200, bsx, bsy, "Easy", sender -> viewStack.push(new ScoreboardDisplayView(sudokuApplet, gridProperties, GridDifficulty.Easy))));
        controls.add(new Button(this, 3 * width / 4, 200, bsx, bsy, "Medium", sender -> viewStack.push(new ScoreboardDisplayView(sudokuApplet, gridProperties, GridDifficulty.Medium))));
        controls.add(new Button(this, width / 4, 280, bsx, bsy, "Hard", sender -> viewStack.push(new ScoreboardDisplayView(sudokuApplet, gridProperties, GridDifficulty.Hard))));
        controls.add(new Button(this, 3 * width / 4, 280, bsx, bsy, "Extreme", sender -> viewStack.push(new ScoreboardDisplayView(sudokuApplet, gridProperties, GridDifficulty.Extreme))));
        controls.add(Button.getStandardBackButton(this));

    }

    @Override
    protected void mouseDown(int mx, int my, boolean rmb) {

    }

    @Override
    protected void mouseUp(int mx, int my, boolean rmb) {

    }

    @Override
    protected void click(int mx, int my, boolean rmb) {
        if (rmb) removeFromViewStack();
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
        push();
        background(220);
        textSize(40);
        fill(51);
        textAlign(CENTER, CENTER);
        text("Display results from \r\n" + gridProperties.getName() + " of difficulty:", 400, 100);

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
