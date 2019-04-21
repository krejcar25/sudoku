package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.control.Button;
import cz.krejcar25.sudoku.ui.style.Color;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class IPromiseToNeverEverMessAroundWithTheScoreboardFileAgainView extends BaseView {
	private ArrayList<Button> buttons;

	public IPromiseToNeverEverMessAroundWithTheScoreboardFileAgainView(Applet applet) {
		super(applet, 800, 800);

		buttons = new ArrayList<>();
		Button promise = new Button<>(this, 400, 700, 700, 100, "I promise to never ever do it again!", sender -> removeFromViewStack());
		promise.style.background = new Color(255, 0, 0);
		buttons.add(promise);
	}

	@Override
	protected void mouseDown(int mx, int my, boolean rmb) {

	}

	@Override
	protected void mouseUp(int mx, int my, boolean rmb) {

	}

	@Override
	protected void click(int mx, int my, boolean rmb) {

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
	protected void draw() {
		rectMode(CENTER);
		textAlign(CENTER, CENTER);
		textSize(40);
		text("You have been caught in the act of cheating. You tried to open the scoreboard file and edit it to change the scores showed in the program. That is unacceptable. We have erased the file entirely and generated a new random one. I hope you will remember it.", 400, 300, 700, 500);
		for (Button button : buttons) button.update();
	}
}
