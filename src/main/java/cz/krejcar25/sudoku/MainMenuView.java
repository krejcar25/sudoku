package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Button;
import cz.krejcar25.sudoku.event.ButtonEvents;
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
        int baseY = 200;
        int yDelta = 80;
        int index = 0;
        for (GridProperties gridProperties : GridProperties.values()) {
            int bx = (2 * (index % 2) + 1) * width / 4;
            int by = baseY + ((index / 2) * yDelta);
            Button button = new Button(this, bx, by, bsx, bsy, gridProperties.getName(), sender -> viewStack.push(new DifficultySelectView(getApplet(), gridProperties)));
            buttons.add(button);
            index++;
        }

        String[] labels = {"Settings", "Scoreboard", "Help"};
        ButtonEvents[] buttonEvents = {
                sender -> viewStack.push(new SettingsView(getApplet())),
                this::buttonClickScoreboard,
                sender -> viewStack.push(new HelpView(getApplet()))
        };
        for (int i = 0; i < labels.length && labels.length == buttonEvents.length; i++) {
            int bx = (2 * (index % 2) + 1) * width / 4;
            int by = baseY + ((index / 2) * yDelta);
            Button button = new Button(this, bx, by, bsx, bsy, labels[i], buttonEvents[i]);
            buttons.add(button);
            index++;
        }
//        Button settingButton = new Button(this, (2 * (index % 2) + 1) * width / 4, ((index / 2) * yDelta), bsx, bsy, "Settings", );
//        Button scoreboardButton = new Button(this, (2 * ((index + 1) % 2) + 1) * width / 4, (((index + 1) / 2) * yDelta), bsx, bsy, "Scoreboard", this::buttonClickScoreboard);
//
//        ButtonStyle nyiStyle = new ButtonStyle();
//        nyiStyle.border = new Color(255, 0, 0);
//        nyiStyle.background = new Color(255);
//        nyiStyle.foreground = new Color(255, 0, 0);
//
//        scoreboardButton.style = nyiStyle;
//        buttons.add(settingButton);
//        buttons.add(scoreboardButton);
//
//        buttons.add(new Button(this, (2 * ((index + 2) % 2) + 1) * width / 4, (((index + 2) / 2) * yDelta), bsx, bsy, "Help", sender -> viewStack.push(new HelpView(getApplet()))));
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
