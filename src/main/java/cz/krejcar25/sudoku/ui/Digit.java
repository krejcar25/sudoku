package cz.krejcar25.sudoku.ui;

import org.intellij.lang.annotations.MagicConstant;

public class Digit extends Drawable
{
	public static final int POINT = 0;
	public static final int A = 1;
	public static final int B = 2;
	public static final int C = 3;
	public static final int D = 4;
	public static final int E = 5;
	public static final int F = 6;
	public static final int G = 7;

	public final int on;
	public final int off;
	private final byte[] digits = new byte[]{0x7E, 0x30, 0x6D, 0x79, 0x33, 0x5B, 0x5F, 0x70, 0x7F, 0x7B, 0x77, 0x1F, 0x4E, 0x3D, 0x4F, 0x47};
	private byte segments;

	Digit(Applet applet, int x, int y)
	{
		super(applet, x, y, 230, 370, OWN);

		this.on = color(255, 0, 0);
		this.off = color(51, 0, 0);
	}

	public void setDigit(int digit, boolean decimal)
	{
		segments = (decimal) ? (byte) (digits[digit] + 0x80) : digits[digit];
	}

	@Override
	protected void draw()
	{
		fill(isBitOn(0));
		ellipse(215, 355, 30, 30);

		drawHorizontal(50, 0, A);
		drawVertical(170, 30, B);
		drawVertical(160, 190, C);
		drawHorizontal(30, 320, D);
		drawVertical(0, 190, E);
		drawVertical(10, 30, F);
		drawHorizontal(40, 160, G);
	}

	private int isBitOn(int segment)
	{
		return (((segments >> (byte) (7 - segment)) & 0x1) == 1) ? on : off;
	}

	private void drawHorizontal(int x, int y, @MagicConstant(intValues = {A, D, G}) int segment)
	{
		push();
		noStroke();
		fill(isBitOn(segment));
		translate(x, y);
		beginShape();
		vertex(25, 0);
		vertex(125, 0);
		vertex(150, 25);
		vertex(125, 50);
		vertex(25, 50);
		vertex(0, 25);
		endShape(CLOSE);
		pop();
	}

	private void drawVertical(int x, int y, @MagicConstant(intValues = {B, C, E, F}) int segment)
	{
		push();
		noStroke();
		fill(isBitOn(segment));
		translate(x, y);
		beginShape();
		vertex(35, 0);
		vertex(60, 25);
		vertex(50, 125);
		vertex(25, 150);
		vertex(0, 125);
		vertex(10, 25);
		endShape(CLOSE);
		pop();
	}
}
