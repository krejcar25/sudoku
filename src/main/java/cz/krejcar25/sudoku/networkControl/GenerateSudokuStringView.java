package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.game.BaseGenerator;
import cz.krejcar25.sudoku.game.GridCore;
import cz.krejcar25.sudoku.game.GridDifficulty;
import cz.krejcar25.sudoku.game.GridProperties;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;

public class GenerateSudokuStringView extends BaseView implements Runnable {
    private final GridProperties gridProperties;
    private final int count;
    private volatile int cycle;
    private ArrayList<String> sudokus;
    private Thread thread;

    public GenerateSudokuStringView(Applet applet, GridProperties gridProperties, int count) {
        super(applet, 800, 600);
        this.gridProperties = gridProperties;
        this.count = count;
        this.sudokus = new ArrayList<>();
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void mouseDown(int mx, int my, boolean rmb) {

    }

    @Override
    public void mouseUp(int mx, int my, boolean rmb) {

    }

    @Override
    public void click(int mx, int my, boolean rmb) {

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
        background(220);
        textSize(30);
        fill(51);
        text("Training", 0, 30);
        fill(150);
        noStroke();
        rect(100, 400, 600, 100);
        fill(0, 200, 0);
        rect(105, 405, Applet.map((float) cycle / count, 0, 1, 0, 590), 90);
    }

    @Override
    public void run() {
        for (cycle = 0; cycle < count; cycle++) {
            GridCore core = new GridCore(gridProperties);
            new BaseGenerator(core).generate(gridProperties.getClueCount(GridDifficulty.Easy), false);
            sudokus.add(core.getGridString());
        }

        JFileChooser c = new JFileChooser();
        String extension = ".scs";
        c.addChoosableFileFilter(new FileNameExtensionFilter("Sudoku Core List", extension));
        c.setAcceptAllFileFilterUsed(false);
        int rVal = c.showSaveDialog(parent.frame);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            File file = c.getSelectedFile();
            String path = file.getAbsolutePath();

            if(!path.endsWith(extension))
            {
                file = new File(path + extension);
            }
            try (FileOutputStream out = new FileOutputStream(file)) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                for (String line : sudokus) {
                    writer.write(line);
                    writer.newLine();
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        removeFromViewStack();
    }
}
