package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Button;
import cz.krejcar25.sudoku.style.ButtonStyle;
import cz.krejcar25.sudoku.style.Color;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class MainMenuView extends BaseView {
    private ArrayList<Button> buttons;

    MainMenuView(SudokuApplet applet) {
        super(applet, 800, 600);
        buttons = new ArrayList<>();
        int bsx = 280;
        int bsy = 40;
        buttons.add(new Button(this, width / 4, 200, bsx, bsy, "Sudoku 9x9", sender -> viewStack.push(new DifficultySelectView(getApplet(), new GameView(getApplet(), GridProperties.Grid9x9)))));
        buttons.add(new Button(this, 3 * width / 4, 200, bsx, bsy, "Sudoku 6x6", sender -> viewStack.push(new DifficultySelectView(getApplet(), new GameView(getApplet(), GridProperties.Grid6x6)))));
        buttons.add(new Button(this, width / 4, 280, bsx, bsy, "Sudoku 4x4", sender -> viewStack.push(new DifficultySelectView(getApplet(), new GameView(getApplet(), GridProperties.Grid4x4)))));
        buttons.add(new Button(this, 3 * width / 4, 280, bsx, bsy, "Sudoku 16x16", sender -> viewStack.push(new DifficultySelectView(getApplet(), new GameView(getApplet(), GridProperties.Grid16x16)))));
        Button settingButton = new Button(this, width / 4, 360, bsx, bsy, "Settings", sender -> viewStack.push(new SettingsView(getApplet())));
        Button scoreboardButton = new Button(this, 3 * width / 4, 360, bsx, bsy, "Scoreboard", this::buttonClickScoreboard);

        ButtonStyle nyiStyle = new ButtonStyle();
        nyiStyle.border = new Color(255, 0, 0);
        nyiStyle.background = new Color(255);
        nyiStyle.foreground = new Color(255, 0, 0);

        scoreboardButton.style = nyiStyle;
        buttons.add(settingButton);
        buttons.add(scoreboardButton);

        buttons.add(new Button(this, width/4, 440, bsx, bsy, "Help", sender->viewStack.push(new HelpView(getApplet()))));
    }

    @Override
    protected void mouseDown(int mx, int my, boolean rmb) {

    }

    @Override
    protected void mouseUp(int mx, int my, boolean rmb) {

    }

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
