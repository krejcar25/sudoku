package cz.krejcar25.sudoku.ui;

import processing.event.KeyEvent;
import processing.event.MouseEvent;

public abstract class BaseView extends Drawable {
    protected ViewStack viewStack;
    protected BaseOverlay overlay;
    protected MouseButton mouseButton = MouseButton.None;

    public BaseView(Applet applet) {
        super(applet, 0, 0, applet.width, applet.height);
    }

    protected BaseView(Applet applet, int sizex, int sizey) {
        super(applet, 0, 0, sizex, sizey);
    }

    public final void mousePressed(MouseEvent mouseEvent) {
        mouseButton = mouseEvent.getButton() == RIGHT ? MouseButton.Right : MouseButton.Left;
        mouseDown(mouseEvent.getX() - x, mouseEvent.getY() - y, mouseEvent.getButton() == RIGHT);
    }

    public abstract void mouseDown(int mx, int my, boolean rmb);

    public final void mouseReleased(MouseEvent mouseEvent) {
        mouseButton = MouseButton.None;
        mouseUp(mouseEvent.getX() - x, mouseEvent.getY() - y, mouseEvent.getButton() == RIGHT);
    }

    public abstract void mouseUp(int mx, int my, boolean rmb);

    public final void mouseClicked(MouseEvent mouseEvent) {
        click(mouseEvent.getX() - x, mouseEvent.getY() - y, mouseEvent.getButton() == RIGHT);
    }

    public abstract void click(int mx, int my, boolean rmb);

    public final void setViewStack(ViewStack viewStack) {
        if (this.viewStack == null) {
            this.viewStack = viewStack;
        }
    }

    public final boolean isInViewStack() {
        return viewStack != null;
    }

    public final void removeFromViewStack() {
        viewStack.removeSpecific(this);
        viewStack = null;
    }

    public final ViewStack getViewStack() {
        return viewStack;
    }

    public abstract void mouseDrag(MouseEvent mouseEvent);

    public abstract void scroll(MouseEvent event);

    public abstract void keyDown(KeyEvent keyEvent);

    public abstract void keyUp(KeyEvent keyEvent);

    public BaseOverlay getOverlay() {
        return overlay;
    }

    public void setOverlay(BaseOverlay overlay) {
        this.overlay = overlay;
    }
}
