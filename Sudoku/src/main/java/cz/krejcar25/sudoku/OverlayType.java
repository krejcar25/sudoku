package cz.krejcar25.sudoku;

public enum OverlayType {
    OK(1, new String[] {
            "OK"
    }
    ),
    OKCancel(2, new String[] {
            "OK", "Cancel"
    }
    ),
    YesNo(2, new String[] {
            "Yes", "No"
    }
    ),
    YesNoCancel(3, new String[] {
            "Yes", "No", "Cancel"
    }
    );

    int buttonCount;
    String[] buttonLabels;

    private OverlayType(int buttonCount, String[] buttonLabels) {
        this.buttonCount = buttonCount;
        this.buttonLabels = buttonLabels;
    }
}
