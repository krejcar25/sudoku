package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.ui.Drawable;
import cz.krejcar25.sudoku.ui.MouseButton;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public abstract class BaseView extends Drawable {
    protected ViewStack viewStack;
    BaseOverlay overlay;
    protected int mousePressX = -1;
    protected int mousePressY = -1;
    protected MouseButton mouseButton = MouseButton.None;

    public BaseView(SudokuApplet applet) {
        super(applet, 0, 0, applet.width, applet.height);
    }

    protected BaseView(SudokuApplet applet, int sizex, int sizey) {
        super(applet, 0, 0, sizex, sizey);
    }

    public final void mousePressed(MouseEvent mouseEvent) {
        mousePressX = mouseEvent.getX();
        mousePressY = mouseEvent.getY();
        mouseButton = mouseEvent.getButton() == RIGHT ? MouseButton.Right : MouseButton.Left;
        mouseDown(mouseEvent.getX() - x, mouseEvent.getY() - y, mouseEvent.getButton() == RIGHT);
    }

    protected abstract void mouseDown(int mx, int my, boolean rmb);

    public final void mouseReleased(MouseEvent mouseEvent) {
        mousePressX = -1;
        mousePressY = -1;
        mouseButton = MouseButton.None;
        mouseUp(mouseEvent.getX() - x, mouseEvent.getY() - y, mouseEvent.getButton() == RIGHT);
    }

    protected abstract void mouseUp(int mx, int my, boolean rmb);

    public final void mouseClicked(MouseEvent mouseEvent) {
        click(mouseEvent.getX() - x, mouseEvent.getY() - y, mouseEvent.getButton() == RIGHT);
    }

    protected abstract void click(int mx, int my, boolean rmb);

    public final void setViewStack(ViewStack viewStack) {
        this.viewStack = viewStack;
    }

    public final boolean isInViewStack() {
        return viewStack != null;
    }

    public final void removeFromViewStack() {
        viewStack.removeSpecific(this);
        viewStack = null;
    }

    public abstract void mouseDrag(MouseEvent mouseEvent);

    public abstract void scroll(MouseEvent event);

    public abstract void keyDown(KeyEvent keyEvent);

    public abstract void keyUp(KeyEvent keyEvent);
}
