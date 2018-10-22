package cz.krejcar25.sudoku;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.function.Function;

public class MainMenuView extends BaseView {
    public MainMenuView(Main applet) {
        super(applet, 800, 600);
        buttons = new ArrayList<Button>();
        int bsx = 280;
        int bsy = 40;
        int bbx = 2;
        int bby = 2;
        buttons.add(new Button(sizex / 4, 200, bsx, bsy, bbx, bby, "Sudoku 9x9", this::buttonClick9x9));
        buttons.add(new Button(3 * sizex / 4, 200, bsx, bsy, bbx, bby, "Sudoku 6x6", this::buttonClick6x6));
        buttons.add(new Button(sizex / 4, 280, bsx, bsy, bbx, bby, "Sudoku 4x4", this::buttonClick4x4));
        buttons.add(new Button(3 * sizex / 4, 280, bsx, bsy, bbx, bby, "Sudoku 16x16", this::buttonClick16x16));
        buttons.add(new Button(sizex / 4, 360, bsx, bsy, bbx, bby, "Settings", this::buttonClickSettings));
        buttons.add(new Button(3 * sizex / 4, 360, bsx, bsy, bbx, bby, "Scoreboard", this::buttonClickScoreboard));
    }

    private ArrayList<Button> buttons;

    public void show() {
        applet.push();
        applet.background(220);
        applet.textSize(40);
        applet.fill(51);
        applet.textAlign(PApplet.CENTER, PApplet.CENTER);
        applet.text("Main menu", 400, 100);

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).show(applet.g);
        }

        if (overlay != null) applet.image(overlay.show(), overlay.x, overlay.y);
        applet.pop();
    }

    public void click(int mx, int my) {
        if (applet.mouseButton == PApplet.LEFT) {
            for (Button button : buttons) {
                if (button.isClick(mx, my)) button.click.click();
            }
        }
    }

    private void buttonClick9x9() {
        Sudoku9x9View view = new Sudoku9x9View(applet, applet.desiredClues);
        view.generate();
        applet.stack.push(view);
    }

    private void buttonClick6x6() {
        Sudoku6x6View view = new Sudoku6x6View(applet, 12);
        view.generate();
        applet.stack.push(view);
    }

    private void buttonClick4x4() {
        Sudoku4x4View view = new Sudoku4x4View(applet, 5);
        view.generate();
        applet.stack.push(view);
    }

    private void buttonClick16x16() {
        Sudoku16x16View view = new Sudoku16x16View(applet,85);
        view.generate();
        applet.stack.push(view);
    }

    private void buttonClickSettings() {

    }

    private void buttonClickScoreboard() {

    }
}
