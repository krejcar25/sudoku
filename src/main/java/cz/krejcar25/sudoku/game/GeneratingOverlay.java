package cz.krejcar25.sudoku.game;

import cz.krejcar25.sudoku.ui.BaseOverlay;
import cz.krejcar25.sudoku.ui.Clock;
import cz.krejcar25.sudoku.ui.OverlayResult;
import cz.krejcar25.sudoku.ui.OverlayType;
import processing.core.PApplet;
import processing.core.PVector;

public class GeneratingOverlay extends BaseOverlay {
    private Clock clock;
    private int clueCount;
    private GridDifficulty gridDifficulty;
    private PVector clockSize;

    GeneratingOverlay(DifficultySelectView difficultySelectView, GridDifficulty gridDifficulty) {
        super(difficultySelectView, 105, 350, 600, 200, OverlayType.Info);
        init(difficultySelectView.gameView, gridDifficulty);
    }

    GeneratingOverlay(GameView gameView, GridDifficulty gridDifficulty) {
        super(gameView, PApplet.constrain((gameView.width - 540) / 2, 0, gameView.width - 540), (int) (gameView.getGrid().getCore().ncr * gameView.getGrid().getSy() / 2) - 100, PApplet.constrain(gameView.width, 0, 540), 200, OverlayType.Info);
        init(gameView, gridDifficulty);
    }

    private void init(GameView gameView, GridDifficulty gridDifficulty) {
        clockSize = new PVector();
        clockSize.y = 60;
        clockSize.x = Clock.getWidthFromHeight(clockSize.y);
        this.clock = new Clock(getApplet(), (width - clockSize.x) / 2, 120, gameView.getGenerator().timer);
        this.gridDifficulty = gridDifficulty;
        this.clueCount = gameView.gridProperties.getClueCount(gridDifficulty);
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
        image(clock, clock.x, clock.y, clockSize.x, clockSize.y);
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
