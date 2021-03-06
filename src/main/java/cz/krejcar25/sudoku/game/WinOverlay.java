package cz.krejcar25.sudoku.game;

import cz.krejcar25.sudoku.ui.BaseOverlay;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.Clock;
import cz.krejcar25.sudoku.ui.OverlayType;
import cz.krejcar25.sudoku.ui.control.Button;
import processing.core.PApplet;

class WinOverlay extends BaseOverlay {
	WinOverlay(BaseView baseView, BaseGrid grid) {
		super(baseView, PApplet.constrain((baseView.width - 540) / 2, 0, baseView.width - 540), (int) (grid.getCore().ncr * grid.getSy() / 2) - 100, PApplet.constrain(baseView.width, 0, 540), 200, OverlayType.OK, (sender) ->
		{
			System.out.println("WinOverlay OK button has received click event, popping");
			baseView.getViewStack().pop(2);
		});
		Clock clock = grid.getGameClock();

		getRootApplet().scoreboard.addEntry(grid.getGridProperties(), grid.getGridDifficulty(), clock.getTimer().getStartDate(), clock.getTimer().getElapsedTime());
	}

	@Override
	protected void draw() {
		background(0);
		push();
		fill(200, 210, 200);
		rect(10, 10, width - 20, height - 20);
		pop();
		textSize(40);
		textAlign(PApplet.CENTER, PApplet.CENTER);
		text("You have won!", width / 2f, 60);
		drawButtons();
	}

	@Override
	public void click(int mx, int my, boolean rmb) {
		System.out.println("WinOverlay has received click event");
		for (Button button : buttons) {
			if (button.isClick(mx - x, my - y)) {
				System.out.println("WinOverlay has found appropriate button");
				button.click();
			}
		}
	}
}
