package cz.krejcar25.sudoku.ui.control;

import cz.krejcar25.sudoku.event.ToggleEvents;
import cz.krejcar25.sudoku.ui.BaseView;

public class Checkbox extends Control {
	private final int checkboxWidth;
	private final ToggleEvents toggleEvents;
	public boolean state = false;

	public Checkbox(BaseView baseView, int x, int y, int width, int height, ToggleEvents toggleEvents) {
		super(baseView, x, y, width, height);
		this.checkboxWidth = width;
		this.toggleEvents = toggleEvents;
	}

	@Override
	public boolean isClick(int mx, int my) {
		if (!enabled) return false;
		return x < mx && mx < (x + checkboxWidth) && y < my && my < (y + height);
	}

	@Override
	public void click() {
		state = !state;

		toggleEvents.toggled(this);
		if (state) toggleEvents.switchedOn(this);
		else toggleEvents.switchedOff(this);
	}

	@Override
	protected void draw() {
		push();

		background(220);

		if (state) fill(150, 255, 150);
		else fill(255, 150, 150);

		strokeWeight(2);
		rect(0, 0, checkboxWidth - 2, height - 2);

		stroke(51);
		strokeWeight(4);

		int margin = 6;
		if (state) {
			line(margin, height / 2f, checkboxWidth / 2f, height - margin);
			line(checkboxWidth / 2f, height - margin, checkboxWidth - margin, margin);
		}
		else {
			line(margin, margin, checkboxWidth - margin, height - margin);
			line(margin, height - margin, checkboxWidth - margin, margin);
		}

		fill(51);

		pop();
	}
}
