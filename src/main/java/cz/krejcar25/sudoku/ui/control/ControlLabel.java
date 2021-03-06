package cz.krejcar25.sudoku.ui.control;

import cz.krejcar25.sudoku.ui.style.Color;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

public class ControlLabel extends Control {
	public static final int CONTROL_LEFT = 0;
	public static final int CONTROL_RIGHT = 1;

	private final Control internalControl;
	@MagicConstant(intValues = {CONTROL_LEFT, CONTROL_RIGHT})
	private final int controlSide;
	private float controlMargin = 10;

	public Color textColor;

	public ControlLabel(@NotNull Control internalControl, @MagicConstant(intValues = {CONTROL_LEFT, CONTROL_RIGHT}) int controlSide, String label) {
		super(internalControl.baseView, internalControl.x, internalControl.y, 1, internalControl.height);
		this.internalControl = internalControl;
		this.controlSide = controlSide;
		this.label = label;
		autosize();
		if (controlSide == CONTROL_RIGHT) x -= textWidth(label);
		internalControl.x -= this.x;
		internalControl.y -= this.y;

		textColor = new Color(220);
	}

	public void centerOnX(int centerX) {
		x = centerX - width / 2f;
		switch (controlSide) {
			case CONTROL_LEFT:
				internalControl.x = 0;
				break;
			case CONTROL_RIGHT:
				internalControl.x = labelSize() + controlMargin;
				break;
		}
	}

	public void fixLabelOnX(int x) {
		if (controlSide == CONTROL_RIGHT) {
			float textLength = labelSize();
			if ((this.x + internalControl.x) - x > textLength) {
				float move = x - this.x;
				this.x = x;
				internalControl.x -= move;
				controlMargin = internalControl.x - textLength;
			}
		}
	}

	private void autosize() {
		this.width = (int) (internalControl.width + labelSize() + controlMargin);
	}

	private float labelSize() {
		push();
		textSize(4 * height / 5f);
		float labelSize = (label.isEmpty()) ? 0 : textWidth(label);
		pop();
		return labelSize;
	}

	@Override
	protected void beforeDraw() {
		autosize();
	}

	@Override
	protected void draw() {
		textAlign(LEFT, TOP);
		textSize(4 * height / 5f);
		fill(textColor);

		if (controlSide == CONTROL_LEFT) {
			internalControl.update();

			text(label, internalControl.width + controlMargin, 0);
		}
		else {
			text(label, 0, 0);

			internalControl.update();
		}
	}

	@Override
	public boolean isClick(int mx, int my) {
		return internalControl.isClick(mx - this.x, my - this.y);
	}

	@Override
	public void click() {
		internalControl.click();
	}
}
