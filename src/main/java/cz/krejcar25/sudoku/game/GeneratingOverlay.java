package cz.krejcar25.sudoku.game;

import cz.krejcar25.sudoku.ui.BaseOverlay;
import cz.krejcar25.sudoku.ui.Clock;
import cz.krejcar25.sudoku.ui.OverlayResult;
import cz.krejcar25.sudoku.ui.OverlayType;

public class GeneratingOverlay extends BaseOverlay {
    private Clock clock;
    private int clueCount;
    private GridDifficulty gridDifficulty;

    GeneratingOverlay(DifficultySelectView baseView, GridDifficulty gridDifficulty) {
        super(baseView, 105, 350, 600, 200, OverlayType.Info);
        this.clock = baseView.gameView.getGenerator().timer;
        this.gridDifficulty = gridDifficulty;
        this.clueCount = baseView.gameView.gridProperties.getClueCount(gridDifficulty);
    }

    @Override
    protected void draw() {
        background(0);
        push();
        fill(200, 210, 200);
        rect(10, 10, width - 20, height - 20);
        pop();
        textSize(40);
        textAlign(CENTER, CENTER);
        text("Generation is in progress", width / 2f, 60);
        textSize(20);
        //TODO Remove ClueCount text and clueCount and gridDifficulty variables and constructor parameters in the final version
        // Used just as a debugging display, at times when I need to see how many clues I should be left with
        text("ClueCount: " + clueCount + " (" + gridDifficulty + ")", width / 2f, 100);
        clock.update();
        float clockWidth = Clock.getWidthFromHeight(60);
        image(clock, (width - clockWidth) / 2, 120, clockWidth, 60);
    }

    @Override
    public OverlayResult getResult() {
        return result;
    }

    @Override
    public void click(int mx, int my, boolean rmb) {
        System.out.println("GeneratingOverlay has received click event");
    }
}
