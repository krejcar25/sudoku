package cz.krejcar25.sudoku.ui;

public enum OverlayResult {
    Yes, No, OK, Cancel, None;

    public boolean shouldDisplay() {
        return this != None;
    }
}
