package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.control.Button;
import cz.krejcar25.sudoku.style.ButtonStyle;
import cz.krejcar25.sudoku.style.Color;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

public class MainMenuView extends BaseView {
    MainMenuView(Main applet) {
        super(applet, 800, 600);
        buttons = new ArrayList<>();
        int bsx = 280;
        int bsy = 40;
        int bbx = 2;
        int bby = 2;
        buttons.add(new Button(sizex / 4, 200, bsx, bsy, bbx, bby, "Sudoku 9x9", this::buttonClick9x9));
        buttons.add(new Button(3 * sizex / 4, 200, bsx, bsy, bbx, bby, "Sudoku 6x6", this::buttonClick6x6));
        buttons.add(new Button(sizex / 4, 280, bsx, bsy, bbx, bby, "Sudoku 4x4", this::buttonClick4x4));
        buttons.add(new Button(3 * sizex / 4, 280, bsx, bsy, bbx, bby, "Sudoku 16x16", this::buttonClick16x16));
        ButtonStyle nyiStyle = new ButtonStyle();
        nyiStyle.border = new Color(255, 0, 0);
        nyiStyle.background = new Color(255);
        nyiStyle.foreground = new Color(255, 0, 0);
        Button settingButton = new Button(sizex / 4, 360, bsx, bsy, bbx, bby, "Settings", this::buttonClickSettings);
        settingButton.style = nyiStyle;
        Button scoreboardButton = new Button(3 * sizex / 4, 360, bsx, bsy, bbx, bby, "Scoreboard", this::buttonClickScoreboard);
        scoreboardButton.style = nyiStyle;
        buttons.add(settingButton);
        buttons.add(scoreboardButton);
    }

    private ArrayList<Button> buttons;

    public void show() {
        applet.push();
        applet.background(220);
        applet.textSize(40);
        applet.fill(51);
        applet.textAlign(PApplet.CENTER, PApplet.CENTER);
        applet.text("Main menu", 400, 100);

        for (Button button : buttons) {
            button.show(applet.g);
        }

        if (overlay != null) applet.image(overlay.show(), overlay.x, overlay.y);
        applet.pop();
    }

    public void click(int mx, int my) {
        if (applet.mouseButton == PApplet.LEFT) {
            for (Button button : buttons) {
                if (button.isClick(mx, my)) button.click();
            }
        }
    }

    private void buttonClick9x9(Button sender) {
        Sudoku9x9View view = null;
        for (int i = 0; i < (applet.isKeyPressed(PConstants.SHIFT) ? 50 : 1); i++) {
            view = new Sudoku9x9View(applet, 30);
            view.generate();
        }
        applet.stack.push(view);
    }

    private void buttonClick6x6(Button sender) {
        Sudoku6x6View view = new Sudoku6x6View(applet, 12);
        view.generate();
        applet.stack.push(view);
    }

    private void buttonClick4x4(Button sender) {
        Sudoku4x4View view = new Sudoku4x4View(applet, 6);
        view.generate();
        applet.stack.push(view);
    }

    private void buttonClick16x16(Button sender) {
        Sudoku16x16View view = new Sudoku16x16View(applet, 150);
        view.generate();
        applet.stack.push(view);
    }

    private void buttonClickSettings(Button sender) {
        applet.stack.push(new SettingsView(applet));
    }

    private void buttonClickScoreboard(Button sender) {

    }

    public void keyPress() {
        if (applet.isKeyPressed(Main.ESC)) applet.exit();
    }

    public void mouseDragged(int mx, int my) {

    }
}
