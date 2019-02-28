package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.ui.ScrollViewContent;

public class HelpViewContent extends ScrollViewContent {
    public HelpViewContent(HelpView helpView) {
        super(helpView, 800, 1200);
    }

    @Override
    public void click(int mx, int my, boolean rmb) {

    }

    @Override
    protected void draw() {
        background(51);
        push();
        textAlign(LEFT, TOP);
        translate(50, 50);
        header("Tutorial");
        textLine("Welcome to Sudoku. Here, you can find help about all the functions of the grid and how to work with them.");
        section("Section");
        textLine("Lorem ipsum dolor sit amet et simplicis et al some nonsense");
        pop();
    }

    private void header(String header) {
        textSize(80);
        text(header, 0, 0);
        translate(0, 100);
    }

    private void section(String section) {
        textSize(60);
        text(section, 0, 0);
        translate(0, 80);
    }

    private void textLine(String text) {
        textSize(40);
        int lines = (int) Math.ceil((double) (textWidth(text) / (width - 100))) + 1;
	    text(text, 0, 0, width - 50, lines * this.g.textSize);
	    translate(0, lines * this.g.textSize + 20);
    }
}
