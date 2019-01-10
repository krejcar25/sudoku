package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.neuralNetwork.NeuralNetwork;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.ScrollView;
import cz.krejcar25.sudoku.ui.ScrollViewContent;
import processing.core.PVector;

import java.util.ArrayList;

public class NetworkChartViewContent extends ScrollViewContent {
    private final NeuralNetwork network;

    ArrayList<ArrayList<PVector>> positions;
    private float mx;
    private float my;
    private float d;

    public NetworkChartViewContent(ScrollView scrollView, NeuralNetwork network) {
        super(scrollView, scrollView.width, scrollView.height);
        this.network = network;

        positions = new ArrayList<>();
        mx = 200;
        my = 30;
        d = 30;

        int highestCount = network.getInputCount();

        // Separated in it's own block of code to ease further variable naming
        {
            ArrayList<PVector> layer = new ArrayList<>();
            int count = network.getLayers().get(0).getInCount();
            if (count > highestCount) highestCount = count;
            for (int i = 0; i < count; i++) {
                layer.add(new PVector(d, i * my + (i + 1) * d));
            }
            positions.add(layer);
        }

        for (int l = 0; l < network.getLayers().size(); l++) {
            ArrayList<PVector> layer = new ArrayList<>();
            int count = network.getLayers().get(l).getNodes();
            if (count > highestCount) highestCount = count;
            for (int i = 0; i < count; i++) {
                layer.add(new PVector((l + 2) * mx + (l + 1) * d, i * my + (i + 1) * d));
            }
            positions.add(layer);
        }

        float width = (positions.size() - 1) * mx + (positions.size() + 1) * d;
        float highestHeight = (highestCount - 1) * my + (highestCount + 1) * d;

        for (ArrayList<PVector> layer : positions) {
            float height = (layer.size() - 1) * my + (layer.size() + 1) * d;
            float offset = (highestHeight - height) / 2f;

            for (PVector pos : layer) {
                pos.y += offset;
            }
        }

        setSize((int) Math.ceil(width), (int) Math.ceil(highestHeight + 100));
    }

    @Override
    public void click(int mx, int my, boolean rmb) {

    }

    @Override
    protected void draw() {
        background(220);
        stroke(51);
        fill(51);
        textAlign(CENTER, CENTER);

        for (int l = 1; l < positions.size(); l++) {
            for (int i = 0; i < positions.get(l).size(); i++) {
                PVector to = positions.get(l).get(i);
                for (int p = 0; p < positions.get(l - 1).size(); p++) {
                    strokeWeight(Applet.map((float) network.getLayers().get(l - 1).getWeights().get(i, p), -1, 1, 0, 5));
                    fill(51);
                    PVector from = positions.get(l - 1).get(p);
                    line(from.x, from.y, to.x, to.y);
                }
            }
        }

        for (int l = 1; l < positions.size(); l++) {
            for (int i = 0; i < positions.get(l).size(); i++) {
                PVector to = positions.get(l).get(i);
                strokeWeight(0);
                fill(150);
                ellipse(to.x, to.y, d, d);
                fill(51);
                textSize(d / 2);
                text(String.valueOf((int)Math.floor(network.getLayers().get(l - 1).getBias().get(i, 0) * 10)), to.x, to.y);
            }
        }

        for (int i = 0; i < positions.get(0).size(); i++) {
            ellipse(positions.get(0).get(i).x, positions.get(0).get(i).y, d, d);
        }
    }
}
