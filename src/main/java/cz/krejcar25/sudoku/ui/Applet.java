package cz.krejcar25.sudoku.ui;

import com.besaba.revonline.pastebinapi.paste.PasteBuilder;
import com.besaba.revonline.pastebinapi.paste.PasteExpire;
import com.besaba.revonline.pastebinapi.paste.PasteVisiblity;
import cz.krejcar25.sudoku.Main;
import cz.krejcar25.sudoku.SudokuApplet;
import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class Applet extends PApplet
{
	protected ViewStack stack;
	private final List<Integer> keyCodesPressed;
	private boolean closeOnExit = true;

	protected Applet()
	{
		keyCodesPressed = new ArrayList<>();
	}

	public static boolean xor(boolean a, boolean b)
	{
		return (a && !b) || (!a && b);
	}

	public static <T> void shuffle(T[] input)
	{
		int m = input.length;
		int i;
		T t;

		while (m > 0)
		{
			i = PApplet.floor(new Random().nextInt(m--));
			t = input[m];
			input[m] = input[i];
			input[i] = t;
		}
	}

	public static <T> boolean isBetween(Comparable<T> min, T val, Comparable<T> max)
	{
		return max.compareTo(val) > 0 && min.compareTo(val) < 0;
	}

	@NotNull
	protected static PImage getImage(String url) throws Exception
	{
		InputStream resourceStream = SudokuApplet.class.getResourceAsStream(url);
		BufferedImage image = ImageIO.read(resourceStream);
		return new PImage(image);
	}

	@Override
	public void keyPressed(KeyEvent keyEvent)
	{
		if (!keyEvent.isAutoRepeat())
		{
			keyCodesPressed.add(keyEvent.getKeyCode());
		}
		stack.get().keyDown(keyEvent);
	}

	@Override
	public void keyReleased(KeyEvent keyEvent)
	{
		keyCodesPressed.removeAll(Collections.singletonList(keyEvent.getKeyCode()));
	}

	@Override
	public void handleDraw()
	{
		try
		{
			super.handleDraw();
		}
		catch (Exception ex)
		{
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

	public boolean isKeyPressed(int keyCode)
	{
		return keyCodesPressed.contains(keyCode);
	}

	protected MouseEvent scaleMouseEvent(MouseEvent mouseEvent)
	{
		int scale = pixelDensity;
		return new MouseEvent(mouseEvent.getNative(), mouseEvent.getMillis(), mouseEvent.getAction(), mouseEvent.getModifiers(), scale * mouseEvent.getX(), scale * mouseEvent.getY(), mouseEvent.getButton(), mouseEvent.getCount());
	}

	protected void push()
	{
		pushMatrix();
		pushStyle();
	}

	protected void pop()
	{
		popStyle();
		popMatrix();
	}

	protected void dontCloseOnExit()
	{
		closeOnExit = false;
	}

	@Override
	public void exitActual()
	{
		if (closeOnExit) super.exitActual();
	}
}
