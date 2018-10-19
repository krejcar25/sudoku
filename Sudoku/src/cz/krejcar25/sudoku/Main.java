package cz.krejcar25.sudoku;

import processing.core.*;

public class Main extends PApplet {
    static int desiredClues = 28;
    static Main pa;

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
        pa = this;

        push();
        background(51);
        fill(220);
        textSize(50);
        textAlign(CENTER, CENTER);
        text("Wait", 50, 50);
        pop();

        stack = new ViewStack(new MainMenuView());
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
        door = loadImage("media/door.png");
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

    public static PVector[] shuffle(PVector[] input) {
        int m = input.length;
        int i;
        PVector t;

        while (m > 0) {
            i = floor(pa.random(m--));
            t = input[m];
            input[m] = input[i];
            input[i] = t;
        }

        return input;
    }

    public void push() {
        pushMatrix();
        pushStyle();
    }

    public void pop() {
        popStyle();
        popMatrix();
    }
}
