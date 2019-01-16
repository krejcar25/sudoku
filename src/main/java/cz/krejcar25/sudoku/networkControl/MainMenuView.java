package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.event.ButtonEvents;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.control.Button;
import cz.krejcar25.sudoku.ui.control.Control;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class MainMenuView extends BaseView {
    ArrayList<Control> controls;

    public MainMenuView(Applet applet) {
        super(applet, 800, 600);
        controls = new ArrayList<>();

        int bsx = 340;
        int bsy = 40;
        int baseY = 200;
        int yDelta = 80;
        int index = 0;

        String[] labels = {"XOR Test", "OR Test", "Generate grids"};
        ButtonEvents[] buttonEvents = {
                (sender) -> viewStack.push(new NetworkLearningSimulationView(sender.getApplet(), NetworkLearningSimulatorScenario.XOR)),
                (sender) -> viewStack.push(new NetworkLearningSimulationView(sender.getApplet(), NetworkLearningSimulatorScenario.OR)),
                (sender) -> new GeneratorSelectionDialog(((gridProperties, count) -> viewStack.push(new GenerateSudokuStringView(applet, gridProperties, count)))).setVisible(true)
        };

        for (int i = 0; i < labels.length && labels.length == buttonEvents.length; i++) {
            int bx = (2 * (index % 2) + 1) * width / 4;
            int by = baseY + ((index / 2) * yDelta);
            Button button = new Button(this, bx, by, bsx, bsy, labels[i], buttonEvents[i]);
            controls.add(button);
            index++;
        }
    }

    @Override
    public void mouseDown(int mx, int my, boolean rmb) {

    }

    @Override
    public void mouseUp(int mx, int my, boolean rmb) {

    }

    @Override
    public void click(int mx, int my, boolean rmb) {
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
        textAlign(PApplet.CENTER, PApplet.CENTER);
        text("Network Control", 400, 100);

        for (Control button : controls) {
            button.update();
            image(button, button.x, button.y);
        }

        if (overlay != null) {
            overlay.update();
            image(overlay, overlay.x, overlay.y);
        }
        pop();
    }
}
