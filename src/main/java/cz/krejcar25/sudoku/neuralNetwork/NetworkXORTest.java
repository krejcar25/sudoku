package cz.krejcar25.sudoku.neuralNetwork;

import cz.krejcar25.sudoku.SudokuApplet;
import cz.krejcar25.sudoku.networkControl.NetworkControlApplet;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.ChildApplet;
import cz.krejcar25.sudoku.ui.ViewStack;

public class NetworkXORTest extends Applet implements ChildApplet {
    private final SudokuApplet owner;
    private NeuralNetwork network;

    public NetworkXORTest(SudokuApplet owner) {
        this.owner = owner;
        this.network = new NeuralNetwork(new DeepLayer(2, 4, ActivationFunction.SIGMOID));
        this.network.addLayer(new DeepLayer(4, 2, ActivationFunction.SIGMOID));
        this.network.addLayer(new DeepLayer(2, 1, ActivationFunction.SIGMOID));
        runSketch(new String[]{"NetworkControlApplet"}, new NetworkControlApplet(owner, applet -> {
        }, network));
        setCloseOnExit(false);
        stack = new ViewStack(new XORTestView(this));
    }

    @Override
    public void settings() {
        size(800, 800);
        pixelDensity(displayDensity());
    }

    @Override
    public void setup() {

    }

    @Override
    public void draw() {
        background(0);
        for (int i = 0; i < 10; i++) {
            int choose = (int) (random(4));
            double[] in;
            double[] out;
            switch (choose) {
                case 0:
                    in = new double[]{0d, 0d};
                    out = new double[]{0d};
                    break;
                case 1:
                    in = new double[]{1d, 0d};
                    out = new double[]{1d};
                    break;
                case 2:
                    in = new double[]{0d, 1d};
                    out = new double[]{1d};
                    break;
                case 3:
                    in = new double[]{1d, 1d};
                    out = new double[]{0d};
                    break;
                default:
                    // Nonsense!
                    in = new double[]{-1d, -1d};
                    out = new double[]{-1d};
                    break;
            }

            network.train(in, out);
        }

        double tileSize = 20;

        double tilesX = width / tileSize;
        double tilesY = height / tileSize;

        strokeWeight(0);

        for (int y = 0; y < tilesY; y++) {
            for (int x = 0; x < tilesX; x++) {
                fill(map((float) network.estimate(new double[]{x / tilesX, y / tilesY})[0], -1, 1, 0, 255));
                rect((float) (x * tileSize), (float) (y * tileSize), (float) (tileSize), (float) (tileSize));
            }
        }

        fill(255, 0, 0);
        textSize((float) tileSize * 2);
        textAlign(LEFT, TOP);
        text(frameRate, 0, 0);
        textAlign(RIGHT, TOP);
        text(network.getTrainCycles(), width, 0);
    }

    @Override
    public Applet getOwner() {
        return owner;
    }

    @Override
    public SudokuApplet getRootOwner() {
        return owner;
    }
}
