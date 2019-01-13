package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.SudokuApplet;
import cz.krejcar25.sudoku.event.AppletCloseEvent;
import cz.krejcar25.sudoku.neuralNetwork.NeuralNetwork;
import cz.krejcar25.sudoku.ui.*;
import processing.event.MouseEvent;

public class NetworkControlApplet extends Applet implements ChildApplet {
    private final SudokuApplet owner;
    private final AppletCloseEvent closeEvent;

    public NetworkControlApplet(SudokuApplet owner, AppletCloseEvent closeEvent, NeuralNetwork network) {
        this.owner = owner;
        this.closeEvent = closeEvent;
        this.stack = new ViewStack(new NetworkChartView(this, network));
    }

    public SudokuApplet getOwner() {
        return owner;
    }

    @Override
    public SudokuApplet getRootOwner() {
        return owner;
    }

    @Override
    public void settings() {
        size(800, 600);
        pixelDensity(displayDensity());
    }

    @Override
    public void setup() {
        surface.setTitle("Neural Network Control");
        setCloseOnExit(false);
    }

    @Override
    public void draw() {
        BaseView view = stack.get();
        view.update();
        image(view, 0, 0);
    }

    @Override
    public void exitActual() {
        super.exitActual();

        closeEvent.appletClosed(this);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        stack.get().mouseClicked(scaleMouseEvent(mouseEvent));
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        stack.get().mousePressed(scaleMouseEvent(mouseEvent));
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        stack.get().mouseReleased(scaleMouseEvent(mouseEvent));
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        stack.get().mouseDrag(scaleMouseEvent(mouseEvent));
    }

    @Override
    public void mouseWheel(MouseEvent mouseEvent) {
        stack.get().scroll(mouseEvent);
    }
}
