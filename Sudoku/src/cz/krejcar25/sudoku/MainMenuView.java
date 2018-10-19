package cz.krejcar25.sudoku;

import processing.core.PApplet;

import java.util.ArrayList;

public class MainMenuView extends BaseView {
    public MainMenuView() {
        super(800, 600);
        buttons = new ArrayList<Button>();
        int bsx = 280;
        int bsy = 40;
        int bbx = 2;
        int bby = 2;
        buttons.add(new Button(sizex / 4, 200, bsx, bsy, bbx, bby, "Sudoku 9x9"));
        buttons.add(new Button(3 * sizex / 4, 200, bsx, bsy, bbx, bby, "Sudoku 6x6"));
        buttons.add(new Button(sizex / 4, 280, bsx, bsy, bbx, bby, "Sudoku 4x4"));
        buttons.add(new Button(3 * sizex / 4, 280, bsx, bsy, bbx, bby, "Sudoku 16x16"));
        buttons.add(new Button(sizex / 4, 360, bsx, bsy, bbx, bby, "Settings"));
        buttons.add(new Button(3 * sizex / 4, 360, bsx, bsy, bbx, bby, "Scoreboard"));
    }

    private ArrayList<Button> buttons;

    public void show() {
        Main.pa.push();
        Main.pa.background(220);
        Main.pa.textSize(40);
        Main.pa.fill(51);
        Main.pa.textAlign(PApplet.CENTER, PApplet.CENTER);
        Main.pa.text("Main menu", 400, 100);

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).show(Main.pa.g);
        }

        if (overlay != null) Main.pa.image(overlay.show(), overlay.x, overlay.y);
        Main.pa.pop();
    }

    public void click(int mx, int my) {
        if (Main.pa.mouseButton == PApplet.LEFT) {
            Sudoku9x9View view = new Sudoku9x9View(Main.pa.desiredClues);
            view.generate();
            Main.pa.stack.push(view);
        }
    }
}
