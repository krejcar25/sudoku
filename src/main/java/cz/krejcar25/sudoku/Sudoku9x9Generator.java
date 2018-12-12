package cz.krejcar25.sudoku;

import processing.core.*;
import java.util.ArrayList;

public class Sudoku9x9Generator extends BaseGenerator {
    Sudoku9x9Generator(BaseView parent, int targetCount) {
        super(parent, targetCount);
        game = new Grid9x9(parent);
    }
}
