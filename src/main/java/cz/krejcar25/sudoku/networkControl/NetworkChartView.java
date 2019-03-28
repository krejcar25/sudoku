package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.neuralNetwork.NeuralNetwork;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.ScrollView;
import cz.krejcar25.sudoku.ui.control.Button;

public class NetworkChartView extends ScrollView {
	NetworkChartView(Applet applet, NeuralNetwork network, boolean showBackButton) {
		super(applet, 1000, 1000);
		content = new NetworkChartViewContent(this, network);
		if (showBackButton) additionalControls.add(Button.getStandardBackButton(this));
	}

}
