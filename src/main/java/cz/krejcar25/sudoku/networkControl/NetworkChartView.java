package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.neuralNetwork.NeuralNetwork;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.ScrollView;
import processing.event.KeyEvent;

public class NetworkChartView extends ScrollView {
    public NetworkChartView(Applet applet, NeuralNetwork network) {
        super(applet, 800,800);
        content = new NetworkChartViewContent(this, network);
    }

    @Override
    public void keyUp(KeyEvent keyEvent) {

    }
}
