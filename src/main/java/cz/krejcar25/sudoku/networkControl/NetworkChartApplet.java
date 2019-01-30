package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.SudokuApplet;
import cz.krejcar25.sudoku.event.AppletCloseEvent;
import cz.krejcar25.sudoku.neuralNetwork.NeuralNetwork;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.ChildApplet;
import cz.krejcar25.sudoku.ui.ViewStack;
import processing.event.MouseEvent;

public class NetworkChartApplet extends Applet implements ChildApplet {
    private final Applet owner;
    private final NeuralNetwork network;
    private AppletCloseEvent closeEvent;

    public NetworkChartApplet(Applet owner, NeuralNetwork network, AppletCloseEvent closeEvent) {
        this.owner = owner;
        this.closeEvent = closeEvent;
        this.network = network;
    }

    public Applet getOwner() {
        return owner;
    }

    @Override
    public SudokuApplet getRootOwner() {
        return owner instanceof ChildApplet ? ((ChildApplet) owner).getRootOwner() : (SudokuApplet) owner;
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
        this.stack = new ViewStack(new NetworkChartView(this, network));
    }

    @Override
    public void draw() {
        BaseView view = stack.get();
        surface.setSize(view.width / pixelDensity, view.height / pixelDensity);
        scale(1f / pixelDensity);
        view.update();
        image(view, 0, 0);
    }

    @Override
    public void exitActual() {
        closeEvent.appletClosed(this);
        super.exitActual();
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
