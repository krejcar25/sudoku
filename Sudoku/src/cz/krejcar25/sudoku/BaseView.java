package cz.krejcar25.sudoku;

public abstract class BaseView {
    public int sizex;
    public int sizey;
    public BaseOverlay overlay;

    public BaseView(int sizex, int sizey) {
        this.sizex = sizex;
        this.sizey = sizey;
    }

    public abstract void show();
    public abstract void click(int mx, int my);
}
