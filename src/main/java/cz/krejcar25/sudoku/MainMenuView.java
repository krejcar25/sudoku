package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.game.DifficultySelectView;
import cz.krejcar25.sudoku.game.GridProperties;
import cz.krejcar25.sudoku.neuralNetwork.ActivationFunction;
import cz.krejcar25.sudoku.neuralNetwork.DeepLayer;
import cz.krejcar25.sudoku.neuralNetwork.NetworkXORTest;
import cz.krejcar25.sudoku.neuralNetwork.NeuralNetwork;
import cz.krejcar25.sudoku.scoreboard.ScoreboardView;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.control.Button;
import cz.krejcar25.sudoku.event.ButtonEvents;
import cz.krejcar25.sudoku.networkControl.NetworkControlApplet;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class MainMenuView extends BaseView {
    private ArrayList<Button> buttons;

    MainMenuView(SudokuApplet applet) {
        super(applet, 800, 600);
        buttons = new ArrayList<>();
        int bsx = 340;
        int bsy = 40;
        int baseY = 200;
        int yDelta = 80;
        int index = 0;
        for (GridProperties gridProperties : GridProperties.values()) {
            int bx = (2 * (index % 2) + 1) * width / 4;
            int by = baseY + ((index / 2) * yDelta);
            Button button = new Button(this, bx, by, bsx, bsy, gridProperties.getName(), sender -> viewStack.push(new DifficultySelectView(getRootApplet(), gridProperties)));
            buttons.add(button);
            index++;
        }

        String[] labels = {"Settings", "Scoreboard", "Help", "Network Control", "XOR Test"};
        ButtonEvents[] buttonEvents = {
                sender -> viewStack.push(new SettingsView(getApplet())),
                sender -> viewStack.push(new ScoreboardView(getApplet())),
                sender -> viewStack.push(new HelpView(getApplet())),
                this::openNetworkControlApplet,
                this::openXORTestApplet

        };
        for (int i = 0; i < labels.length && labels.length == buttonEvents.length; i++) {
            int bx = (2 * (index % 2) + 1) * width / 4;
            int by = baseY + ((index / 2) * yDelta);
            Button button = new Button(this, bx, by, bsx, bsy, labels[i], buttonEvents[i]);
            buttons.add(button);
            index++;
        }
    }

    private void openNetworkControlApplet(Button button) {
        SudokuApplet applet = getRootApplet();
        if (applet.networkControlApplet == null) {
            NeuralNetwork demo = new NeuralNetwork(new DeepLayer(20, 30, ActivationFunction.TANH))
                    .addLayer(new DeepLayer(30, 40, ActivationFunction.TANH))
                    .addLayer(new DeepLayer(40, 50, ActivationFunction.TANH));
            applet.networkControlApplet = new NetworkControlApplet(applet, closedApplet -> ((NetworkControlApplet) closedApplet).getOwner().networkControlApplet = null, demo);
            SudokuApplet.runSketch(new String[]{"NetworkControlApplet"}, applet.networkControlApplet);
        }
    }

    private void openXORTestApplet(Button button) {
        SudokuApplet applet = getRootApplet();
        SudokuApplet.runSketch(new String[]{"NetworkXORTest"}, new NetworkXORTest(applet));
    }

    @Override
    public void mouseDown(int mx, int my, boolean rmb) {

    }

    @Override
    public void mouseUp(int mx, int my, boolean rmb) {

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

    @Override
    public void keyDown(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == ESC) getApplet().exit();
    }

    @Override
    public void keyUp(KeyEvent keyEvent) {

    }
}
