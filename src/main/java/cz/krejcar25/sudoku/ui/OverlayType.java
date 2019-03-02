package cz.krejcar25.sudoku.ui;

public enum OverlayType
{
	Info(new String[0]),
	OK(new String[]{
			"OK"
	}
	),
	YesNo(new String[]{
			"Yes", "No"
	}
	);
	final int buttonCount;
	final String[] buttonLabels;

	OverlayType(String[] buttonLabels)
	{
		this.buttonCount = buttonLabels.length;
		this.buttonLabels = buttonLabels;
	}
}
