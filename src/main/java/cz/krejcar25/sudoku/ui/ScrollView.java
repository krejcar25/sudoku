package cz.krejcar25.sudoku.ui;

import cz.krejcar25.sudoku.SudokuApplet;
import cz.krejcar25.sudoku.ui.control.Control;
import cz.krejcar25.sudoku.ui.style.Color;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;

public abstract class ScrollView extends BaseView {
	protected final ArrayList<Control> additionalControls;
	@SuppressWarnings("FieldCanBeLocal")
	private final int horizontalScrollBarWidth = 5;
	private final ScrollBarVisibility horizontalScrollBarVisibility = ScrollBarVisibility.Automatic;
	private final ScrollBarVisibility verticalScrollBarVisibility = ScrollBarVisibility.Automatic;
	@SuppressWarnings("FieldCanBeLocal")
	private final int verticalScrollBarWidth = 5;
	private final int scrollSpeed = 30;
	protected Color horizontalScrollBarColor = new Color(51);
	protected Color verticalScrollBarColor = new Color(51);
	protected ScrollViewContent content;
	private int horizontalScroll = 0;
	private int verticalScroll = 0;
	private int hScrollO = -1;
	private int vScrollO = -1;
	private int mousePressX = -1;
	private int mousePressY = -1;

	protected ScrollView(Applet applet, int width, int height) {
		super(applet, width, height);
		additionalControls = new ArrayList<>();
	}

	@Override
	protected void draw() {
		boolean showH = horizontalScrollBarVisibility.showScrollBar(width, content.width);
		boolean showV = verticalScrollBarVisibility.showScrollBar(height, content.height);

		background(220);
		content.update();
		push();
		noStroke();

		if (showH) {
			push();
			translate(0, height - horizontalScrollBarWidth);
			fill(horizontalScrollBarColor.r, horizontalScrollBarColor.g, horizontalScrollBarColor.b);
			float r = (((float) width) / content.width);
			rect(horizontalScroll * r, 0, (width - (showV ? verticalScrollBarWidth : 0)) * r, horizontalScrollBarWidth);
			pop();
		}

		if (showV) {
			push();
			translate(width - verticalScrollBarWidth, 0);
			fill(verticalScrollBarColor.r, verticalScrollBarColor.g, verticalScrollBarColor.b);
			float r = (((float) height) / content.height);
			rect(0, verticalScroll * r, verticalScrollBarWidth, (height - (showH ? horizontalScrollBarWidth : 0)) * r);
			pop();
		}

		pop();
		push();
		for (Control control : additionalControls) {
			control.update();
		}
		pop();
		if (overlay != null) {
			overlay.update();
		}
	}

	@Override
	public void keyDown(KeyEvent keyEvent) {
		int x = 0;
		int y = 0;

		if (getApplet().isKeyPressed(SudokuApplet.UP)) y += -scrollSpeed;
		if (getApplet().isKeyPressed(SudokuApplet.RIGHT)) x += scrollSpeed;
		if (getApplet().isKeyPressed(SudokuApplet.DOWN)) y += scrollSpeed;
		if (getApplet().isKeyPressed(SudokuApplet.LEFT)) x += -scrollSpeed;

		scroll(x, y);
	}

	@Override
	public void click(int mx, int my, boolean rmb) {
		if (rmb) {
			removeFromViewStack();
			return;
		}
		content.click(mx - x + horizontalScroll, my - y + verticalScroll, false);
		for (Control control : additionalControls) {
			if (control.isClick(mx, my)) control.click();
		}
	}

	@Override
	public void mouseDown(int mx, int my, boolean rmb) {
		hScrollO = horizontalScroll;
		vScrollO = verticalScroll;
		mousePressX = mx;
		mousePressY = my;
	}

	@Override
	public void mouseUp(int mx, int my, boolean rmb) {
		mousePressX = -1;
		mousePressY = -1;
	}

	@Override
	public void mouseDrag(MouseEvent mouseEvent) {
		horizontalScroll = SudokuApplet.constrain(hScrollO - (mouseEvent.getX() - mousePressX), 0, content.width - width);
		verticalScroll = SudokuApplet.constrain(vScrollO - (mouseEvent.getY() - mousePressY), 0, content.height - height);
		content.x = -horizontalScroll;
		content.y = -verticalScroll;
	}

	@Override
	public void scroll(MouseEvent mouseEvent) {
		boolean hor = getApplet().isKeyPressed(SHIFT);
		scroll(hor ? scrollSpeed * mouseEvent.getCount() : 0, hor ? 0 : scrollSpeed * mouseEvent.getCount());
	}

	private void scroll(float x, float y) {
		horizontalScroll = SudokuApplet.floor(SudokuApplet.constrain(horizontalScroll + x, 0, content.width - width));
		verticalScroll = SudokuApplet.floor(SudokuApplet.constrain(verticalScroll + y, 0, content.height - height));
		content.x = -horizontalScroll;
		content.y = -verticalScroll;
	}
}
