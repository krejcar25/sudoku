package cz.krejcar25.sudoku;

import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.ScrollView;
import cz.krejcar25.sudoku.ui.control.Button;
import cz.krejcar25.sudoku.ui.style.Color;
import processing.event.KeyEvent;

public class HelpView extends ScrollView {
    public HelpView(Applet applet) {
        super(applet, 800, 600);
        content = new HelpViewContent(this);
        additionalControls.add(Button.getStandardBackButton(this));
        horizontalScrollBarColor = new Color(220);
        verticalScrollBarColor = new Color(220);
    }

    @Override
    public void keyUp(KeyEvent keyEvent) {

    }
}
