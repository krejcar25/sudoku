package cz.krejcar25.sudoku.ui.style;

public class Thickness {
	public final float top;
	public final float left;
	private final float right;
	private final float bottom;

	public Thickness(float trbl) {
		this.top = trbl;
		this.right = trbl;
		this.bottom = trbl;
		this.left = trbl;
	}

	public float totalTb() {
		return top + bottom;
	}

	public float totalRl() {
		return right + left;
	}
}
