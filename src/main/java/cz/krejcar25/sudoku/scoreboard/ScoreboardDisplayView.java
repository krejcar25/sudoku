package cz.krejcar25.sudoku.scoreboard;

import cz.krejcar25.sudoku.game.GridDifficulty;
import cz.krejcar25.sudoku.game.GridProperties;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.ScrollView;
import cz.krejcar25.sudoku.ui.control.Button;

public class ScoreboardDisplayView extends ScrollView {
	ScoreboardDisplayView(Applet applet, GridProperties gridProperties, GridDifficulty gridDifficulty) {
		super(applet, 800, 600);
		content = new ScoreboardDisplayViewContent(this, gridProperties, gridDifficulty);
		additionalControls.add(Button.getStandardBackButton(this));
	}

}
