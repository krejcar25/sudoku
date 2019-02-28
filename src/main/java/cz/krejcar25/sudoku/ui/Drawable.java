package cz.krejcar25.sudoku.ui;

import cz.krejcar25.sudoku.SudokuApplet;
import org.intellij.lang.annotations.MagicConstant;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;

public abstract class Drawable implements PConstants
{
	public static final int APPLET = 0;
	public static final int OWN = 1;

	protected final Applet parent;
	protected final PGraphics g;
	@MagicConstant(intValues = {APPLET, OWN})
	private final int targetGraphics;
	public int x;
	public int y;
	public int width;
	public int height;
	private boolean resizable = false;
	private int widthLimit = -1;
	private int heightLimit = -1;

	public Drawable(Applet applet, int x, int y, int width, int height)
	{
		this(applet, x, y, width, height, APPLET);
	}

	public Drawable(Applet applet, int x, int y, int width, int height, @MagicConstant(intValues = {APPLET, OWN}) int targetGraphics)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.parent = applet;
		this.targetGraphics = targetGraphics;

		switch (targetGraphics)
		{
			case APPLET:
				this.g = applet.g;
				break;
			case OWN:
				this.g = applet.createGraphics(width, height);
				break;
			default:
				assert false;
				this.g = null;
				break;
		}
	}

	public final void update()
	{
		if (this.targetGraphics == APPLET)
		{
			this.parent.push();
			this.parent.translate(x, y);
		}
		else if (this.targetGraphics == OWN) this.g.beginDraw();

		fill(-1);
		stroke(0);
		background(0);

		draw();
		if (this.targetGraphics == APPLET) this.parent.pop();
		else if (this.targetGraphics == OWN) this.g.endDraw();
	}

	protected void beforeDraw()
	{
	}

	protected abstract void draw();

	public Applet getApplet()
	{
		return parent;
	}

	public SudokuApplet getRootApplet()
	{
		if (parent instanceof SudokuApplet) return (SudokuApplet) parent;
		else if (parent instanceof ChildApplet) return ((ChildApplet) parent).getRootOwner();
		else return null;
	}

	protected void setResizable(int limitx, int limity)
	{
		resizable = true;
		widthLimit = limitx;
		heightLimit = limity;
	}

	protected void setNonResizable()
	{
		resizable = false;
		widthLimit = -1;
		heightLimit = -1;
	}

	@MagicConstant(intValues = {APPLET, OWN})
	public int getTargetGraphics()
	{
		return targetGraphics;
	}

	// Mirroring methods from PGraphics

	public void setSize(int width, int height)
	{
		switch (targetGraphics)
		{
			case APPLET:
				this.parent.setSize(width, height);
				break;
			case OWN:
				this.g.setSize(width, height);
				break;
		}
		this.width = width;
		this.height = height;
	}

	protected void push()
	{
		//TODO Update to Processing 3.5
		this.g.pushMatrix();
		this.g.pushStyle();
	}

	protected void pop()
	{
		//TODO Update to Processing 3.5
		this.g.popStyle();
		this.g.popMatrix();
	}

	protected PShape createShape()
	{
		return this.g.createShape();
	}

	protected PShape createShape(@MagicConstant(intValues = {POINT, LINE, TRIANGLE, QUAD, RECT, ELLIPSE, ARC, BOX, SPHERE}) int type)
	{
		return this.g.createShape(type);
	}

	protected PShape createShape(@MagicConstant(intValues = {POINT, LINE, TRIANGLE, QUAD, RECT, ELLIPSE, ARC, BOX, SPHERE}) int kind, float[] p)
	{
		return this.g.createShape(kind, p);
	}

	protected void arc(float a, float b, float c, float d, float start, float stop)
	{
		this.g.arc(a, b, c, d, start, stop);
	}

	protected void arc(float a, float b, float c, float d, float start, float stop, @MagicConstant(intValues = {PIE, OPEN, CHORD}) int mode)
	{
		this.g.arc(a, b, c, d, start, stop, mode);
	}

	protected void circle(float x, float y, float extent)
	{
		//TODO Update to Processing 3.5
		this.g.ellipse(x, y, extent, extent);
	}

	protected void ellipse(float a, float b, float c, float d)
	{
		this.g.ellipse(a, b, c, d);
	}

	protected void line(float x1, float y1, float x2, float y2)
	{
		this.g.line(x1, y1, x2, y2);
	}

	protected void point(float x, float y)
	{
		this.g.point(x, y);
	}

	protected void quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
	{
		this.g.quad(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	protected void rect(float a, float b, float c, float d)
	{
		this.g.rect(a, b, c, d);
	}

	protected void rect(float a, float b, float c, float d, float r)
	{
		this.g.rect(a, b, c, d, r);
	}

	protected void rect(float a, float b, float c, float d, float tl, float tr, float br, float bl)
	{
		this.g.rect(a, b, c, d, tl, tr, br, bl);
	}

	protected void square(float x, float y, float extent)
	{
		//TODO Update to Processing 3.5
		this.g.rect(x, y, extent, extent);
	}

	protected void triangle(float x1, float y1, float x2, float y2, float x3, float y3)
	{
		this.g.triangle(x1, y1, x2, y2, x3, y3);
	}

	protected void bezier(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
	{
		this.g.bezier(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	protected void bezierDetail(int detail)
	{
		this.g.bezierDetail(detail);
	}

	protected float bezierPoint(float a, float b, float c, float d, float t)
	{
		return this.g.bezierPoint(a, b, c, d, t);
	}

	protected float bezierTangent(float a, float b, float c, float d, float t)
	{
		return this.g.bezierTangent(a, b, c, d, t);
	}

	protected void curve(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
	{
		this.g.curve(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	protected void curveDetail(int detail)
	{
		this.g.curveDetail(detail);
	}

	protected float curvePoint(float a, float b, float c, float d, float t)
	{
		return this.g.curvePoint(a, b, c, d, t);
	}

	protected float curveTangent(float a, float b, float c, float d, float t)
	{
		return this.g.curveTangent(a, b, c, d, t);
	}

	protected void curveTightness(float tightness)
	{
		this.g.curveTightness(tightness);
	}

	protected void ellipseMode(@MagicConstant(intValues = {RADIUS, CENTER, CORNER, CORNERS}) int mode)
	{
		this.g.ellipseMode(mode);
	}

	protected void rectMode(@MagicConstant(intValues = {CORNER, CORNERS, RADIUS, CENTER}) int mode)
	{
		this.g.rectMode(mode);
	}

	protected void strokeCap(@MagicConstant(intValues = {SQUARE, PROJECT, ROUND}) int cap)
	{
		this.g.strokeCap(cap);
	}

	protected void strokeJoin(@MagicConstant(intValues = {MITER, BEVEL, ROUND}) int join)
	{
		this.g.strokeJoin(join);
	}

	protected void strokeWeight(float weight)
	{
		this.g.strokeWeight(weight);
	}

	protected void beginContour()
	{
		this.g.beginContour();
	}

	protected void beginShape()
	{
		this.g.beginShape();
	}

	protected void bezierVertex(float x2, float y2, float x3, float y3, float x4, float y4)
	{
		this.g.bezierVertex(x2, y2, x3, y3, x4, y4);
	}

	protected void curveVertex(float x, float y)
	{
		this.g.curveVertex(x, y);
	}

	protected void endContour()
	{
		this.g.endContour();
	}

	protected void endShape()
	{
		this.g.endShape();
	}

	protected void endShape(@MagicConstant(intValues = {CLOSE}) int mode)
	{
		this.g.endShape(mode);
	}

	protected void quadraticVertex(float cx, float cy, float x3, float y3)
	{
		this.g.quadraticVertex(cx, cy, x3, y3);
	}

	protected void vertex(float x, float y)
	{
		this.g.vertex(x, y);
	}

	protected void vertex(float[] v)
	{
		this.g.vertex(v);
	}

	protected void vertex(float x, float y, float u, float v)
	{
		this.g.vertex(x, y, u, v);
	}

	protected void shape(PShape shape)
	{
		this.g.shape(shape);
	}

	protected void shape(PShape shape, float x, float y)
	{
		this.g.shape(shape, x, y);
	}

	protected void shape(PShape shape, float a, float b, float c, float d)
	{
		this.g.shape(shape, a, b, c, d);
	}

	protected void shapeMode(@MagicConstant(intValues = {CORNER, CORNERS, CENTER}) int mode)
	{
		this.g.shapeMode(mode);
	}

	protected void translate(float x, float y)
	{
		this.g.translate(x, y);
	}

	protected void background(int rgb)
	{
		push();
		noStroke();
		fill(rgb);
		rectMode(CORNER);
		rect(0, 0, this.width, this.height);
		pop();
	}

	protected void background(int rgb, int alpha)
	{
		push();
		noStroke();
		fill(rgb, alpha);
		rectMode(CORNER);
		rect(0, 0, this.width, this.height);
		pop();
	}

	protected void background(float gray)
	{
		push();
		noStroke();
		fill(gray);
		rectMode(CORNER);
		rect(0, 0, this.width, this.height);
		pop();
	}

	protected void backgroud(float gray, float alpha)
	{
		push();
		noStroke();
		fill(gray, alpha);
		rectMode(CORNER);
		rect(0, 0, this.width, this.height);
		pop();
	}

	protected void backgroud(float v1, float v2, float v3)
	{
		push();
		noStroke();
		fill(v1, v2, v3);
		rectMode(CORNER);
		rect(0, 0, this.width, this.height);
		pop();
	}

	protected void backgroud(float v1, float v2, float v3, float alpha)
	{
		push();
		noStroke();
		fill(v1, v2, v3, alpha);
		rectMode(CORNER);
		rect(0, 0, this.width, this.height);
		pop();
	}

	protected void background(PImage image)
	{
		this.g.image(image, 0, 0, width, height);
	}

	protected void colorMode(@MagicConstant(intValues = {RGB, HSB}) int mode)
	{
		this.g.colorMode(mode);
	}

	protected void colorMode(@MagicConstant(intValues = {RGB, HSB}) int mode, float max)
	{
		this.g.colorMode(mode, max);
	}

	protected void colorMode(@MagicConstant(intValues = {RGB, HSB}) int mode, float max1, float max2, float max3)
	{
		this.g.colorMode(mode, max1, max2, max3);
	}

	protected void colorMode(@MagicConstant(intValues = {RGB, HSB}) int mode, float max1, float max2, float max3, float maxA)
	{
		this.g.colorMode(mode, max1, max2, max3, maxA);
	}

	protected void fill(int rgb)
	{
		this.g.fill(rgb);
	}

	protected void fill(int rgb, float alpha)
	{
		this.g.fill(rgb, alpha);
	}

	protected void fill(float gray)
	{
		this.g.fill(gray);
	}

	protected void fill(float gray, float alpha)
	{
		this.g.fill(gray, alpha);
	}

	protected void fill(float v1, float v2, float v3)
	{
		this.g.fill(v1, v2, v3);
	}

	protected void fill(float v1, float v2, float v3, float alpha)
	{
		this.g.fill(v1, v2, v3, alpha);
	}

	protected void noFill()
	{
		this.g.noFill();
	}

	protected void noStroke()
	{
		this.g.noStroke();
	}

	protected void stroke(int rgb)
	{
		this.g.stroke(rgb);
	}

	protected void stroke(int rgb, float alpha)
	{
		this.g.stroke(rgb, alpha);
	}

	protected void stroke(float gray)
	{
		this.g.stroke(gray);
	}

	protected void stroke(float gray, float alpha)
	{
		this.g.stroke(gray, alpha);
	}

	protected void stroke(float v1, float v2, float v3)
	{
		this.g.stroke(v1, v2, v3);
	}

	protected void stroke(float v1, float v2, float v3, float alpha)
	{
		this.g.stroke(v1, v2, v3, alpha);
	}

	protected float alpha(int rgb)
	{
		return this.g.alpha(rgb);
	}

	protected float blue(int rgb)
	{
		return this.g.blue(rgb);
	}

	protected float brightnes(int rgb)
	{
		return this.g.brightness(rgb);
	}

	protected int color(int gray)
	{
		return this.g.color(gray);
	}

	protected int color(float fgray)
	{
		return this.g.color(fgray);
	}

	protected int color(int gray, int alpha)
	{
		return this.g.color(gray, alpha);
	}

	protected int color(float fgray, float falpha)
	{
		return this.g.color(fgray, falpha);
	}

	protected int color(int v1, int v2, int v3)
	{
		return this.g.color(v1, v2, v3);
	}

	protected int color(int v1, int v2, int v3, int alpha)
	{
		return this.g.color(v1, v2, v3, alpha);
	}

	protected int color(float v1, float v2, float v3)
	{
		return this.g.color(v1, v2, v3);
	}

	protected int color(float v1, float v2, float v3, float alpha)
	{
		return this.g.color(v1, v2, v3, alpha);
	}

	protected float green(int rgb)
	{
		return this.g.green(rgb);
	}

	protected float hue(int rgb)
	{
		return this.g.hue(rgb);
	}

	protected int lerpColor(int c1, int c2, float amt)
	{
		return this.g.lerpColor(c1, c2, amt);
	}

	protected float red(int rgb)
	{
		return this.g.red(rgb);
	}

	protected float saturation(int rgb)
	{
		return this.g.saturation(rgb);
	}

	protected PImage createImage(int w, int h, @MagicConstant(intValues = {RGB, ARGB, ALPHA}) int format)
	{
		return this.parent.createImage(w, h, format);
	}

	protected void image(PImage image, float a, float b)
	{
		this.g.image(image, a, b);
	}

	protected void image(PImage image, float a, float b, float c, float d)
	{
		this.g.image(image, a, b, c, d);
	}

	protected void image(Drawable drawable)
	{
		drawable.update();
		if (drawable.targetGraphics == OWN) image(drawable.g, drawable.x, drawable.y, drawable.width, drawable.height);
	}

	protected void image(Drawable drawable, float x, float y)
	{
		drawable.update();
		if (drawable.targetGraphics == OWN) image(drawable.g, x, y);
	}

	protected void image(Drawable drawable, float x, float y, float w, float h)
	{
		push();
		drawable.update();
		imageMode(CORNER);
		if (drawable.targetGraphics == OWN) image(drawable.g, x, y, w, h);
		pop();
	}

	protected PImage loadImage(String filename)
	{
		return this.parent.loadImage(filename);
	}

	protected void imageMode(@MagicConstant(intValues = {CORNER, CORNERS, CENTER}) int mode)
	{
		this.g.imageMode(mode);
	}

	protected PImage loadImage(String filename, String extension)
	{
		return this.parent.loadImage(filename, extension);
	}

	protected void text(String str, float x, float y)
	{
		this.g.text(str, x, y);
	}

	protected void text(String str, float x1, float y1, float x2, float y2)
	{
		this.g.text(str, x1, y1, x2, y2);
	}

	protected void text(Object obj, float x, float y)
	{
		text(obj.toString(), x, y);
	}

	protected void text(Object obj, float x1, float y1, float x2, float y2)
	{
		text(obj.toString(), x1, y1, x2, y2);
	}

	protected void textAlign(@MagicConstant(intValues = {LEFT, CENTER, RIGHT}) int alignX)
	{
		this.g.textAlign(alignX);
	}

	protected void textAlign(@MagicConstant(intValues = {LEFT, CENTER, RIGHT}) int alignX, @MagicConstant(intValues = {TOP, BOTTOM, CENTER, BASELINE}) int alignY)
	{
		this.g.textAlign(alignX, alignY);
	}

	protected void textLeading(float leading)
	{
		this.g.textLeading(leading);
	}

	protected void textMode(@MagicConstant(intValues = {MODEL, SHAPE}) int mode)
	{
		this.g.textMode(mode);
	}

	protected void textSize(float size)
	{
		this.g.textSize(size);
	}

	protected float textWidth(String str)
	{
		return this.g.textWidth(str);
	}

	public boolean isResizable()
	{
		return resizable;
	}

	public int getWidthLimit()
	{
		return widthLimit;
	}

	public int getHeightLimit()
	{
		return heightLimit;
	}
}
