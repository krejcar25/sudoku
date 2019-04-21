package cz.krejcar25.sudoku;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import cz.krejcar25.sudoku.scoreboard.Scoreboard;
import cz.krejcar25.sudoku.scoreboard.ScoreboardWasMessedAroundWithException;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.ViewStack;
import processing.core.PImage;
import processing.event.MouseEvent;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SudokuApplet extends Applet {
	public Settings settings;
	public Scoreboard scoreboard;
	public PImage door;
	private boolean scoreboardTamperedWithFlag = false;
	private PImage icon;

	@Override
	public void settings() {
		size(100, 100);
		pixelDensity(displayDensity());
	}

	@Override
	public void setup() {
		loadSettings();
		loadScoreboard();

		push();
		background(51);
		fill(220);
		textSize(50);
		textAlign(CENTER, CENTER);
		text("Wait", 50, 50);
		pop();
		surface.setTitle("Sudoku");

		stack = new ViewStack(new MainMenuView(this));
		if (scoreboardTamperedWithFlag) {
			scoreboardTamperedWithFlag = false;
			stack.push(new IPromiseToNeverEverMessAroundWithTheScoreboardFileAgainView(this));
		}
		loadImages();
		surface.setIcon(icon);
	}

	@Override
	public void draw() {
		BaseView view = stack.get();
		surface.setSize(view.width / pixelDensity, view.height / pixelDensity);
		scale(1f / pixelDensity);
		view.update();
	}

	private void loadSettings() {
		try {
			try {
				Settings.isValidConfig();
				settings = Settings.loadSettings();
			} catch (FileNotFoundException e) {
				Settings.prepare();
				loadSettings();
			} catch (InvalidFormatException | JsonParseException e) {
				println("An error occurred during parsing the configuration file. More details below...");
				e.printStackTrace();
				e.getMessage();
				println("The program will now exit...");
				exit();
			} catch (IOException e) {
				e.printStackTrace();
				exit();
			} catch (IllegalArgumentException e) {
				println("The specified path is a dictionary. This is not allowed.");
				exit();
			}
		} catch (StackOverflowError error) {
			// Tried to prepare the settings on and on and it didn't work somehow... Check the file system
			println("Wow, something evil happened woith settings! 🤬 Much confised. Such unlucky. Goodbye 😭");
			exit();
		}
	}

	private void loadScoreboard() {
		try {
			try {
				Scoreboard.isValidScoreboard();
				scoreboard = Scoreboard.loadScoreboard();
			} catch (ScoreboardWasMessedAroundWithException e) {
				scoreboardTamperedWithFlag = true;
				Scoreboard.prepare();
				loadScoreboard();
			} catch (FileNotFoundException e) {
				Scoreboard.prepare();
				loadScoreboard();
			} catch (InvalidFormatException | JsonParseException e) {
				println("An error occurred during parsing the scoreboard file. More details below...");
				e.printStackTrace();
				e.getMessage();
				println("The program will now exit...");
				exit();
			} catch (IOException e) {
				e.printStackTrace();
				exit();
			} catch (IllegalArgumentException e) {
				println("The specified path is a dictionary. This is not allowed.");
				exit();
			}
		} catch (StackOverflowError error) {
			// Tried to prepare the scoreboard on and on and it didn't work somehow... Check the file system
			println("Wow, something evil happened with scoreboard! 🤬 Much confised. Such unlucky. Goodbye 😭");
			exit();
		}
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		stack.get().mouseClicked(scaleMouseEvent(mouseEvent));
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		stack.get().mousePressed(scaleMouseEvent(mouseEvent));
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		stack.get().mouseReleased(scaleMouseEvent(mouseEvent));
	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
		stack.get().mouseDrag(scaleMouseEvent(mouseEvent));
	}

	@Override
	public void mouseWheel(MouseEvent mouseEvent) {
		stack.get().scroll(mouseEvent);
	}

	private void loadImages() {
		try {
			door = getImage("/image/door.png");
			icon = getImage("/image/icon.png");
		} catch (Exception ex) {
			return;
		}
		door.loadPixels();
		for (int i = 0; i < door.width * door.height; i++) {
			door.pixels[i] = (door.pixels[i] == 0) ? color(51) : color(220);
		}
		door.updatePixels();
	}
}
