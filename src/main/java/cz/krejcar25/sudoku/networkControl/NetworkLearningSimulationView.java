package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.neuralNetwork.ActivationFunction;
import cz.krejcar25.sudoku.neuralNetwork.DeepLayer;
import cz.krejcar25.sudoku.neuralNetwork.NetworkTrainRunnable;
import cz.krejcar25.sudoku.neuralNetwork.NeuralNetwork;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.Drawable;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class NetworkLearningSimulationView extends BaseView {
    private NeuralNetwork network;
    private NetworkTrainRunnable trainRunnable;

    private Drawable estimate;
    private double tileSize = 20;
    private boolean shouldTrain = true;

    private int drawIndex = 0;

    private NetworkChartApplet chartApplet;

    public NetworkLearningSimulationView(Applet applet, NetworkLearningSimulatorScenario mode, boolean showChart) {
        super(applet, 800, 800);
        this.network = new NeuralNetwork(new DeepLayer(2, 4, ActivationFunction.SIGMOID));
        this.network.addLayer(new DeepLayer(4, 4, ActivationFunction.SIGMOID));
        this.network.addLayer(new DeepLayer(4, 3, ActivationFunction.SIGMOID));

        if (showChart) {
            chartApplet = new NetworkChartApplet(applet, network, closedApplet -> {
                chartApplet = null;
                getApplet().exit();
            });
            Applet.runSketch(new String[]{"NetworkChartApplet"}, chartApplet);
        }
        trainRunnable = new NetworkTrainRunnable(network, mode.getTrainingData());
        trainRunnable.startTrain();

        estimate = new Drawable(applet, 0, 0, 800, 800) {
            @Override
            protected void draw() {
                double tilesX = width / tileSize;
                double tilesY = height / tileSize;

                strokeWeight(0);
                textSize((int) tileSize);
                textAlign(LEFT, TOP);

                boolean shouldResume = trainRunnable.pause();
                background(0, 255, 0);
                for (int y = 0; y < tilesY; y++) {
                    for (int x = 0; x < tilesX; x++) {
                        double[] est = network.estimate(new double[]{x / tilesX, y / tilesY});
                        float r = PApplet.map((float) est[0], 0, 1, 0, 255);
                        float g = PApplet.map((float) est[1], 0, 1, 0, 255);
                        float b = PApplet.map((float) est[2], 0, 1, 0, 255);
                        fill(r, g, b);
                        rect((float) (x * tileSize), (float) (y * tileSize), (float) (tileSize), (float) (tileSize));
                    }
                }
                fill(255, 0, 0);
                textAlign(RIGHT, BOTTOM);
                text(String.valueOf(trainRunnable.getLastError()), width, height);
                if (shouldResume) trainRunnable.resume();
                drawIndex++;
            }
        };
    }

    @Override
    public void mouseDown(int mx, int my, boolean rmb) {

    }

    @Override
    public void mouseUp(int mx, int my, boolean rmb) {

    }

    @Override
    public void click(int mx, int my, boolean rmb) {
        estimate.update();
    }

    @Override
    public void mouseDrag(MouseEvent mouseEvent) {

    }

    @Override
    public void scroll(MouseEvent event) {

    }

    @Override
    public void keyDown(KeyEvent keyEvent) {
        if (keyEvent.getKey() == ' ') {
            shouldTrain = !shouldTrain;
            if (!shouldTrain) trainRunnable.pause();
            else trainRunnable.resume();
            estimate.update();
        }
    }

    @Override
    public void keyUp(KeyEvent keyEvent) {

    }

    @Override
    public void draw() {
        background(0);
        double tileSize = 20;

        if (shouldTrain && network.getTrainCycles() > drawIndex * 1000) estimate.update();

        image(estimate, 0, 0);

        fill(255, 0, 0);
        textSize((float) tileSize * 2);
        textAlign(LEFT, TOP);
        text(parent.frameRate, 0, 0);
        textAlign(RIGHT, TOP);
        text(network.getTrainCycles(), width, 0);
    }
}
