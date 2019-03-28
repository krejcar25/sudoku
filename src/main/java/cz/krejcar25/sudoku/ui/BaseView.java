package cz.krejcar25.sudoku.ui;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public abstract class BaseView extends Drawable {
	protected ViewStack viewStack;
	protected volatile BaseOverlay overlay;

	public BaseView(Applet applet) {
		super(applet, 0, 0, applet.width, applet.height);
	}

	protected BaseView(Applet applet, int sizex, int sizey) {
		super(applet, 0, 0, sizex, sizey);
	}

	public final void mousePressed(@NotNull MouseEvent mouseEvent) {
		mouseDown(mouseEvent.getX() - x, mouseEvent.getY() - y, mouseEvent.getButton() == RIGHT);
	}

	private void mouseDown(float mx, float my, boolean rmb) {
		mouseDown((int) mx, (int) my, rmb);
	}

	protected abstract void mouseDown(int mx, int my, boolean rmb);

	public final void mouseReleased(@NotNull MouseEvent mouseEvent) {
		mouseUp(mouseEvent.getX() - x, mouseEvent.getY() - y, mouseEvent.getButton() == RIGHT);
	}

	private void mouseUp(float mx, float my, boolean rmb) {
		mouseUp((int) mx, (int) my, rmb);
	}

	protected abstract void mouseUp(int mx, int my, boolean rmb);

	public final void mouseClicked(@NotNull MouseEvent mouseEvent) {
		click(mouseEvent.getX() - x, mouseEvent.getY() - y, mouseEvent.getButton() == RIGHT);
	}

	private void click(float mx, float my, boolean rmb) {
		click((int) mx, (int) my, rmb);
	}

	protected abstract void click(int mx, int my, boolean rmb);

	@Contract(pure = true)
	final boolean isInViewStack() {
		return viewStack != null;
	}

	public final void removeFromViewStack() {
		viewStack.removeSpecific(this);
		viewStack = null;
	}

	@Contract(pure = true)
	public final ViewStack getViewStack() {
		return viewStack;
	}

	final void setViewStack(ViewStack viewStack) {
		if (this.viewStack == null) {
			this.viewStack = viewStack;
		}
	}

	public abstract void mouseDrag(MouseEvent mouseEvent);

	public abstract void scroll(MouseEvent event);

	public abstract void keyDown(KeyEvent keyEvent);

	public BaseOverlay getOverlay() {
		return overlay;
	}

	public void setOverlay(BaseOverlay overlay) {
		this.overlay = overlay;
	}
}
