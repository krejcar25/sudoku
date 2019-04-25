package cz.krejcar25.sudoku.game;

import cz.krejcar25.sudoku.ui.BaseOverlay;
import cz.krejcar25.sudoku.ui.Clock;
import cz.krejcar25.sudoku.ui.OverlayType;
import processing.core.PApplet;

public class GeneratingOverlay extends BaseOverlay {
	private Clock clock;

	GeneratingOverlay(DifficultySelectView difficultySelectView) {
		super(difficultySelectView, 105, 350, 600, 200, OverlayType.Info);
		init(difficultySelectView.gameView);
	}

	GeneratingOverlay(GameView gameView) {
		super(gameView, PApplet.constrain((gameView.width - 540) / 2, 0, gameView.width - 540), (int) (gameView.getGrid().getCore().ncr * gameView.getGrid().getSy() / 2) - 100, PApplet.constrain(gameView.width, 0, 540), 200, OverlayType.Info);
		init(gameView);
	}

	private void init(GameView gameView) {
		this.clock = new Clock(getApplet(), 0, 120, gameView.getGenerator().timer);
		this.clock.setDisplayWidthWithHeight(60);
		this.clock.x = (width - this.clock.getDisplayWidth()) / 2f;
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
		clock.update();
		image(clock, clock.x, clock.y, clock.getDisplayWidth(), clock.getDisplayHeight());
	}

	@Override
	public void click(int mx, int my, boolean rmb) {
		System.out.println("GeneratingOverlay has received click event");
	}
}
