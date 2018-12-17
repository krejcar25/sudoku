package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Button;
import cz.krejcar25.sudoku.style.ButtonStyle;
import cz.krejcar25.sudoku.style.Color;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class MainMenuView extends BaseView {
    MainMenuView(SudokuApplet applet) {
        super(applet, 800, 600);
        buttons = new ArrayList<>();
        int bsx = 280;
        int bsy = 40;
        buttons.add(new Button(this, width / 4, 200, bsx, bsy, "Sudoku 9x9", this::buttonClick9x9));
        buttons.add(new Button(this, 3 * width / 4, 200, bsx, bsy, "Sudoku 6x6", this::buttonClick6x6));
        buttons.add(new Button(this, width / 4, 280, bsx, bsy, "Sudoku 4x4", this::buttonClick4x4));
        buttons.add(new Button(this, 3 * width / 4, 280, bsx, bsy, "Sudoku 16x16", this::buttonClick16x16));
        Button settingButton = new Button(this, width / 4, 360, bsx, bsy, "Settings", this::buttonClickSettings);
        Button scoreboardButton = new Button(this, 3 * width / 4, 360, bsx, bsy, "Scoreboard", this::buttonClickScoreboard);

        ButtonStyle nyiStyle = new ButtonStyle();
        nyiStyle.border = new Color(255, 0, 0);
        nyiStyle.background = new Color(255);
        nyiStyle.foreground = new Color(255, 0, 0);

        scoreboardButton.style = nyiStyle;
        buttons.add(settingButton);
        buttons.add(scoreboardButton);
    }

    @Override
    protected void mouseDown(int mx, int my, boolean rmb) {

    }

    @Override
    protected void mouseUp(int mx, int my, boolean rmb) {

    }

    private ArrayList<Button> buttons;

    @Override
    protected void draw() {
        push();
        background(220);
        textSize(40);
        fill(51);
        textAlign(PApplet.CENTER, PApplet.CENTER);
        text("Main menu", 400, 100);

        for (Button button : buttons) {
            button.update();
            image(button, button.x, button.y);
        }

        if (overlay != null) {
            overlay.update();
            image(overlay, overlay.x, overlay.y);
        }
        pop();
    }

    @Override
    public void click(int mx, int my, boolean rmb) {
        if (!rmb) {
            for (Button button : buttons) if (button.isClick(mx - x, my - y)) button.click();
        }
    }

    @Override
    public void mouseDrag(MouseEvent mouseEvent) {

    }

    @Override
    public void scroll(MouseEvent event) {

    }

    private void buttonClick9x9(Button sender) {
        Sudoku9x9View view = new Sudoku9x9View(getApplet(), 30);
        view.generate();
        viewStack.push(view);
    }

    private void buttonClick6x6(Button sender) {
        Sudoku6x6View view = new Sudoku6x6View(getApplet(), 12);
        view.generate();
        viewStack.push(view);
    }

    private void buttonClick4x4(Button sender) {
        Sudoku4x4View view = new Sudoku4x4View(getApplet(), 6);
        view.generate();
        viewStack.push(view);
    }

    private void buttonClick16x16(Button sender) {
        Sudoku16x16View view = new Sudoku16x16View(getApplet(), 150);
        view.generate();
        viewStack.push(view);
    }

    private void buttonClickSettings(Button sender) {
        SettingsView view = new SettingsView(getApplet());
        viewStack.push(view);
    }

    private void buttonClickScoreboard(Button sender) {

    }

    @Override
    public void keyDown(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == ESC) getApplet().exit();
    }

    @Override
    public void keyUp(KeyEvent keyEvent) {

    }
}
