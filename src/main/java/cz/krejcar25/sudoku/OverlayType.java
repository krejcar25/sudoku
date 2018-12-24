package cz.krejcar25.sudoku;

public enum OverlayType {
    Info(new String[0]),
    OK(new String[] {
            "OK"
    }
    ),
    OKCancel(new String[] {
            "OK", "Cancel"
    }
    ),
    YesNo(new String[] {
            "Yes", "No"
    }
    ),
    YesNoCancel(new String[] {
            "Yes", "No", "Cancel"
    }
    );
    final int buttonCount;
    final String[] buttonLabels;

    private OverlayType(String[] buttonLabels) {
        this.buttonCount = buttonLabels.length;
        this.buttonLabels = buttonLabels;
    }
}
