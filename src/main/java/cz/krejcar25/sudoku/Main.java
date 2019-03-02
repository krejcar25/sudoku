package cz.krejcar25.sudoku;

import com.besaba.revonline.pastebinapi.Pastebin;
import com.besaba.revonline.pastebinapi.impl.factory.PastebinFactory;
import processing.core.PApplet;

@SuppressWarnings("SpellCheckingInspection")
public class Main
{
	public static final PastebinFactory pastebinFactory = new PastebinFactory();
	public static final Pastebin pastebin = pastebinFactory.createPastebin("b05b784a8076c6b40a846c50688adfd1");

	public static void main(String[] args)
	{
		System.out.println("Hello World!");
		PApplet.main("cz.krejcar25.sudoku.SudokuApplet");
	}
}
