package cz.krejcar25.sudoku;

import processing.core.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Main extends PApplet {
    static int desiredClues = 28;

    public static void main(String[] args) {
	    System.out.println("Hello World!");
        PApplet.main("cz.krejcar25.sudoku.Main");
    }

    public void settings() {
        size(10, 10);
    }

    public ViewStack stack;
    public PImage door;

    public void setup() {
        push();
        background(51);
        fill(220);
        textSize(50);
        textAlign(CENTER, CENTER);
        text("Wait", 50, 50);
        pop();
        frame.setTitle("Sudoku");

        stack = new ViewStack(new MainMenuView(this));
        BaseView v = stack.get();
        loadImages();
    }

    public void draw() {
        BaseView view = stack.get();
        surface.setSize(view.sizex, view.sizey);
        view.show();
    }

    public void mouseClicked() {
        stack.get().click(mouseX, mouseY);
    }

    public void keyPressed() {
        if (stack.get() instanceof IGameView) {
            BaseGrid game = ((IGameView)stack.get()).getGrid();
            if (tryParseInt(String.valueOf(key))) game.keyInput(Integer.parseInt(String.valueOf(key)));
        }
    }

    public void loadImages() {
        try {
            door = getImage("/cz/krejcar25/sudoku/media/door.png");
        } catch (Exception ex) {
            return;
        }
        door.loadPixels();
        for (int i = 0; i < door.width * door.height; i++) {
            door.pixels[i] = (door.pixels[i] == 0) ? color(51) : color(220);
        }
        door.updatePixels();
    }

    public static boolean xor(boolean a, boolean b) {
        return (a && !b) || (!a && b);
    }

    public static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public void push() {
        pushMatrix();
        pushStyle();
    }

    public void pop() {
        popStyle();
        popMatrix();
    }

    static <T> T[] shuffle(T[] input) {
        int m = input.length;
        int i;
        T t;

        while (m > 0) {
            i = PApplet.floor(new Random().nextInt(m--));
            t = input[m];
            input[m] = input[i];
            input[i] = t;
        }

        return input;
    }

    public static PImage getImage(String url) throws Exception {
        BufferedImage image = ImageIO.read(Main.class.getResourceAsStream(url));
        return new PImage(image);
    }
}
