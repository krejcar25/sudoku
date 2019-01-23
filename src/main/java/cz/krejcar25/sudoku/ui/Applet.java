package cz.krejcar25.sudoku.ui;

import com.besaba.revonline.pastebinapi.paste.PasteBuilder;
import com.besaba.revonline.pastebinapi.paste.PasteExpire;
import com.besaba.revonline.pastebinapi.paste.PasteVisiblity;
import cz.krejcar25.sudoku.Main;
import cz.krejcar25.sudoku.SudokuApplet;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class Applet extends PApplet {
    private List<Character> keysPressed;
    private List<Integer> keyCodesPressed;
    protected ViewStack stack;

    private boolean closeOnExit = true;

    public Applet() {
        keysPressed = new ArrayList<>();
        keyCodesPressed = new ArrayList<>();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (!keyEvent.isAutoRepeat()) {
            keysPressed.add(keyEvent.getKey());
            keyCodesPressed.add(keyEvent.getKeyCode());
        }
        stack.get().keyDown(keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keysPressed.removeAll(Collections.singletonList(keyEvent.getKey()));
        keyCodesPressed.removeAll(Collections.singletonList(keyEvent.getKeyCode()));
        stack.get().keyUp(keyEvent);
    }

    @Override
    public void handleDraw() {
        try {
            super.handleDraw();
        } catch (Exception ex) {
            // Some shit happened during the render. What do chief?
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            PasteBuilder builder = Main.pastebinFactory.createPaste()
                    .setTitle("Sudoku exception: " + ex.getMessage())
                    .setMachineFriendlyLanguage("text")
                    .setExpire(PasteExpire.Never)
                    .setVisiblity(PasteVisiblity.Public)
                    .setRaw(sw.toString());
            System.out.println("Pasted at: " + Main.pastebin.post(builder.build()).get());
            exit();
        }
    }

    public boolean isKeyPressed(char key) {
        return keysPressed.contains(key);
    }

    public boolean isKeyPressed(int keyCode) {
        return keyCodesPressed.contains(keyCode);
    }

    public static boolean xor(boolean a, boolean b) {
        return (a && !b) || (!a && b);
    }

    public static <T> void shuffle(T[] input) {
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

    public static <T extends Comparable> boolean isBetween(T min, T val, T max) {
        return max.compareTo(val) > 0 && val.compareTo(min) > 0;
    }

    protected static PImage getImage(String url) throws Exception {
        BufferedImage image = ImageIO.read(SudokuApplet.class.getResourceAsStream(url));
        return new PImage(image);
    }

    protected MouseEvent scaleMouseEvent(MouseEvent mouseEvent) {
        int scale = pixelDensity;
        return new MouseEvent(mouseEvent.getNative(), mouseEvent.getMillis(), mouseEvent.getAction(), mouseEvent.getModifiers(), scale * mouseEvent.getX(), scale * mouseEvent.getY(), mouseEvent.getButton(), mouseEvent.getCount());
    }

    protected void push() {
        pushMatrix();
        pushStyle();
    }

    protected void pop() {
        popStyle();
        popMatrix();
    }

    public PSurfaceAWT.SmoothCanvas getCanvas() {
        return (PSurfaceAWT.SmoothCanvas) initSurface().getNative();
    }

    public void setCloseOnExit(boolean close) {
        closeOnExit = close;
    }

    public boolean doesCloseOnExit() {
        return closeOnExit;
    }

    @Override
    public void exitActual() {
        if (closeOnExit) super.exitActual();
    }
}
