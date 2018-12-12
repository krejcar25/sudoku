package cz.krejcar25.sudoku;

import processing.core.*;
import processing.event.MouseEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.*;

public class Main extends PApplet {
    private List<Character> keysPressed;
    private List<Integer> keyCodesPressed;
    Settings settings;

    public static void main(String[] args) {
        System.out.println("Hello World!");
        PApplet.main("cz.krejcar25.sudoku.Main");
    }

    public void settings() {
        size(10, 10);
    }

    ViewStack stack;
    PImage door;

    public void setup() {
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
        keysPressed = new ArrayList<>();
        keyCodesPressed = new ArrayList<>();
    }

    public void draw() {
        BaseView view = stack.get();
        boolean isResizable = view.isResizable();
        surface.setResizable(isResizable);
        if (isResizable) {
            int w = constrain(width, 50, view.getSizeXLimit());
            int h = constrain(height, 50, view.getSizeYLimit());

            surface.setSize(w, h);

            view.sizex = w;
            view.sizey = h;
        } else {
            surface.setSize(view.sizex, view.sizey);
        }
        view.show();
    }

    public void mouseClicked() {
        stack.get().click(mouseX, mouseY);
    }

    public void mousePressed() {
        stack.get().mousePressed(mouseX, mouseY);
    }

    public void mouseReleased() {
        stack.get().mouseReleased(mouseX, mouseY);
    }

    public void mouseDragged() {
        stack.get().mouseDragged(mouseX, mouseY);
    }

    public void mouseWheel(MouseEvent event) {
        if (stack.get() instanceof ScrollView) {
            ScrollView view = (ScrollView) stack.get();
            boolean hor = isKeyPressed(SHIFT);
            view.scroll(hor ? view.scrollSpeed * event.getCount() : 0, hor ? 0 : view.scrollSpeed * event.getCount());
        }
    }

    public void keyPressed() {
        keysPressed.add(key);
        keyCodesPressed.add(keyCode);
        if (stack.get() instanceof GameView) {
            BaseGrid game = ((GameView) stack.get()).getGrid();
            if (tryParseInt(String.valueOf(key))) game.keyInput(Integer.parseInt(String.valueOf(key)));
        }
        stack.get().keyPress();
    }

    public void keyReleased() {
        keysPressed.removeAll(Collections.singletonList(key));
        keyCodesPressed.removeAll(Collections.singletonList(keyCode));
    }

    public boolean isKeyPressed(char key) {
        return keysPressed.contains(key);
    }

    public boolean isKeyPressed(int keyCode) {
        return keyCodesPressed.contains(keyCode);
    }

    private void loadImages() {
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

    static boolean xor(boolean a, boolean b) {
        return (a && !b) || (!a && b);
    }

    private static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    void push() {
        pushMatrix();
        pushStyle();
    }

    void pop() {
        popStyle();
        popMatrix();
    }

    static <T> void shuffle(T[] input) {
        int m = input.length;
        int i;
        T t;

        while (m > 0) {
            i = PApplet.floor(new Random().nextInt(m--));
            t = input[m];
            input[m] = input[i];
            input[i] = t;
        }
    }

    private static PImage getImage(String url) throws Exception {
        BufferedImage image = ImageIO.read(Main.class.getResourceAsStream(url));
        return new PImage(image);
    }
}
