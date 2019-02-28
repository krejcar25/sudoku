package cz.krejcar25.sudoku.ui;

import cz.krejcar25.sudoku.Timer;
import processing.core.PApplet;

public class Clock extends Drawable
{
	private static final int baseWidth = 975;
	private static final int baseHeight = 370;

	private final Timer timer;
	private Digit minD;
	private Digit minU;
	private Digit secD;
	private Digit secU;
	private float displayWidth = baseWidth;
	private float displayHeight = baseHeight;

	public Clock(Applet applet, float x, float y, String name)
	{
		super(applet, x, y, baseWidth, baseHeight, OWN);
		this.timer = new Timer(name);
		this.minD = new Digit(getApplet(), 0, 0);
        this.minU = new Digit(getApplet(), minD.width, 0);
        this.secD = new Digit(getApplet(), minD.width + minU.width + 50, 0);
        this.secU = new Digit(getApplet(), minD.width + minU.width + 50 + secD.width, 0);
    }

    public Clock(Applet applet, float x, float y, Timer timer) {
        super(applet, x, y, baseWidth, baseHeight);
        this.timer = timer;
        this.minD = new Digit(getApplet(), 0, 0);
		this.minU = new Digit(getApplet(), minD.width, 0);
		this.secD = new Digit(getApplet(), minD.width + minU.width + 50, 0);
		this.secU = new Digit(getApplet(), minD.width + minU.width + 50 + secD.width, 0);
	}

	public void start()
	{
		timer.start();
	}

	public void pause()
	{
		timer.pause();
	}

	public void stop()
	{
		timer.stop();
	}

	public boolean isPaused()
	{
		return timer.isPaused();
	}

	public boolean isRunning()
	{
		return timer.isRunning();
	}

	public void setDisplayWidthWithHeight(float height)
	{
		this.displayWidth = getWidthFromHeight(height);
		this.displayHeight = height;
	}

	public void setDisplayHeightWithWidth(float width)
	{
		this.displayWidth = width;
		this.displayHeight = getHeightFromWidth(width);
	}

	public float getDisplayWidth()
	{
		return displayWidth;
	}

	public float getDisplayHeight()
	{
		return displayHeight;
	}

	private static float getWidthFromHeight(float height)
	{
		return height * baseWidth / baseHeight;
    }

	private static float getHeightFromWidth(float width)
	{
		return width * baseHeight / baseWidth;
    }

    public Timer getTimer() {
        return timer;
    }

	@Override
	protected void draw()
	{
		long time = timer.getElapsedTimeSecs();
		minD.setDigit(PApplet.abs(PApplet.floor(PApplet.floor(time / 60f) / 10f)), false);
		minD.update();
		minU.setDigit(PApplet.abs(PApplet.floor(time / 60f) % 10), false);
		minU.update();
		secD.setDigit(PApplet.abs(PApplet.floor(PApplet.floor(time % 60) / 10f)), false);
		secD.update();
		secU.setDigit(PApplet.abs(PApplet.floor(time % 60) % 10), false);
		secU.update();

		background(51);

		image(minD);
		image(minU);
		push();
		fill((timer.getElapsedTime() % 1000 < 500) ? minD.on : minD.off);
		ellipse(minD.width + minU.width + 30, 120, 30, 30);
		ellipse(minD.width + minU.width + 20, 250, 30, 30);
		pop();
		image(secD);
		image(secU);
	}
}
