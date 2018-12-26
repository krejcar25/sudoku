package cz.krejcar25.sudoku.control;

public class ControlLabel extends Control {
    public static final boolean CONTROL_LEFT = true;
    public static final boolean CONTROL_RIGHT = false;

    private Control internalControl;
    private boolean controlOnLeft;
    private int controlMargin = 10;

    public ControlLabel(Control internalControl, boolean controlSide, String label) {
        super(internalControl.baseView, 0, internalControl.y, 1, internalControl.height);
        this.internalControl = internalControl;
        this.controlOnLeft = controlSide;
        this.label = label;
        autosize();
        x = (int) (internalControl.x - (controlSide ? 0 : textWidth(label)));
    }

    public void centerOnX(int centerX) {
        x = centerX - width / 2;
        if (controlOnLeft) internalControl.x = x;
        else internalControl.x = (int) (x + textWidth(label) + controlMargin);
    }

    public int fixLabelOnX(int x) {
        if (controlOnLeft) return 1;
        int textLength = (int) textWidth(label);
        if (internalControl.x - x < textLength) return 2;
        controlMargin = internalControl.x - x - textLength;
        this.x = x;
        return 0;
    }

    private void autosize() {
        push();
        textSize(4 * height / 5f);
        float labelSize = (label.equals("")) ? 0 : textWidth(label);
        setSize((int) (internalControl.width + labelSize + controlMargin), height);
        pop();
    }

    @Override
    protected void beforeDraw() {
        autosize();
    }

    @Override
    protected void draw() {
        background(0, 0);
        textAlign(LEFT, TOP);
        textSize(4 * height / 5f);

        if (controlOnLeft) {
            internalControl.update();
            image(internalControl, 0, 0);

            text(label, internalControl.width + controlMargin, 0);
        } else {
            text(label, 0, 0);

            internalControl.update();
            image(internalControl, (int) textWidth(label) + controlMargin, 0);
        }
    }

    @Override
    public boolean isClick(int mx, int my) {
        return internalControl.isClick(mx, my);
    }

    @Override
    public void click() {
        internalControl.click();
    }
}
