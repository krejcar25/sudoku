package cz.krejcar25.sudoku;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import cz.krejcar25.sudoku.networkControl.NetworkControlApplet;
import cz.krejcar25.sudoku.scoreboard.Scoreboard;
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
    public NetworkControlApplet networkControlApplet;

    @Override
    public void settings() {
        size(100, 100);
        pixelDensity(displayDensity());
    }

    public PImage door;
    public PImage icon;

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
        BaseView v = stack.get();
        loadImages();
        surface.setIcon(icon);
    }

    @Override
    public void draw() {
        BaseView view = stack.get();
        boolean isResizable = view.isResizable();
        surface.setResizable(true);
        if (isResizable) {
            int w = constrain(width, 50, view.getWidthLimit());
            int h = constrain(height, 50, view.getHeightLimit());

            surface.setSize(w, h);
            view.setSize(w * pixelDensity, h * pixelDensity);
        } else {
            surface.setSize(view.width / pixelDensity, view.height / pixelDensity);
        }
        scale(1f / pixelDensity);
        view.update();
	    image(view);
    }

    private void loadSettings() {
        try {
            Settings.isValidConfig(Settings.DEF_PATH);
            settings = Settings.loadSettings(Settings.DEF_PATH);
        } catch (FileNotFoundException e) {
            Settings.prepare(Settings.DEF_PATH);
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
        } catch (StackOverflowError error) {
            // Tried to prepare the settings on and on and it didn`t work somehow... Check the file system
            println("Wow, something evil happened! 🤬 Much confised. Such unlucky. Goodbye 😭");
            exit();
        }
    }

    private void loadScoreboard() {
        try {
            Scoreboard.isValidScoreboard(Scoreboard.DEF_PATH);
            scoreboard = Scoreboard.loadScoreboard(Scoreboard.DEF_PATH);
        } catch (FileNotFoundException e) {
            Scoreboard.prepare(Scoreboard.DEF_PATH);
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
        } catch (StackOverflowError error) {
            // Tried to prepare the settings on and on and it didn`t work somehow... Check the file system
            println("Wow, something evil happened! 🤬 Much confised. Such unlucky. Goodbye 😭");
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

    private static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
