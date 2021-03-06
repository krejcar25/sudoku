package cz.krejcar25.sudoku.game;

import processing.core.PApplet;

import java.util.ArrayList;

class FlashSquareList {
	private final ArrayList<FlashSquare> squares;
	private final PApplet applet;

	FlashSquareList(PApplet applet) {
		squares = new ArrayList<>();
		this.applet = applet;
	}

	void newNow(int x, int y, int lifespan, int c) {
		push(new FlashSquare(x, y, applet.frameCount, lifespan, c));
	}

	private void push(FlashSquare square) {
		squares.add(square);
	}

	boolean contains(int x, int y) {
		return colorOf(x, y) != -1;
	}

	int colorOf(int x, int y) {
		for (int i = squares.size() - 1; i >= 0; i--) {
			FlashSquare square = squares.get(i);
			if (!(square.isValid(applet.frameCount))) {
				squares.remove(square);
			}
			else if (square.x == x && square.y == y) return square.c;
		}
		return -1;
	}
}
