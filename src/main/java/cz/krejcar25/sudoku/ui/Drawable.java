package cz.krejcar25.sudoku.ui;

import cz.krejcar25.sudoku.SudokuApplet;
import cz.krejcar25.sudoku.ui.style.Color;
import org.intellij.lang.annotations.MagicConstant;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;

@SuppressWarnings({"unused", "WeakerAccess", "SameParameterValue"})
public abstract class Drawable implements PConstants {
	protected final Applet parent;
	public float x;
	public float y;
	public int width;
	public int height;
	protected float scale = 1;

	public Drawable(Applet applet, float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.parent = applet;
	}

	public final void update() {
		this.parent.push();
		this.parent.translate(x, y);

		fill(-1);
		stroke(0);
		this.parent.scale(scale);

		draw();
		this.parent.pop();
	}

	protected void beforeDraw() {
	}

	protected abstract void draw();

	public Applet getApplet() {
		return parent;
	}

	public SudokuApplet getRootApplet() {
		if (parent instanceof SudokuApplet) return (SudokuApplet) parent;
		else if (parent instanceof ChildApplet) return ((ChildApplet) parent).getRootOwner();
		else return null;
	}

	// Mirroring methods from PGraphics

	protected void push() {
		//TODO Update to Processing 3.5
		this.parent.pushMatrix();
		this.parent.pushStyle();
	}

	protected void pop() {
		//TODO Update to Processing 3.5
		this.parent.popStyle();
		this.parent.popMatrix();
	}

	protected PShape createShape() {
		return this.parent.createShape();
	}

	protected PShape createShape(@MagicConstant(intValues = {POINT, LINE, TRIANGLE, QUAD, RECT, ELLIPSE, ARC, BOX, SPHERE}) int type) {
		return this.parent.createShape(type);
	}

	protected PShape createShape(@MagicConstant(intValues = {POINT, LINE, TRIANGLE, QUAD, RECT, ELLIPSE, ARC, BOX, SPHERE}) int kind, float[] p) {
		return this.parent.createShape(kind, p);
	}

	protected void arc(float a, float b, float c, float d, float start, float stop) {
		this.parent.arc(a, b, c, d, start, stop);
	}

	protected void arc(float a, float b, float c, float d, float start, float stop, @MagicConstant(intValues = {PIE, OPEN, CHORD}) int mode) {
		this.parent.arc(a, b, c, d, start, stop, mode);
	}

	protected void circle(float x, float y, float extent) {
		//TODO Update to Processing 3.5
		this.parent.ellipse(x, y, extent, extent);
	}

	protected void ellipse(float a, float b, float c, float d) {
		this.parent.ellipse(a, b, c, d);
	}

	protected void line(float x1, float y1, float x2, float y2) {
		this.parent.line(x1, y1, x2, y2);
	}

	protected void point(float x, float y) {
		this.parent.point(x, y);
	}

	protected void quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		this.parent.quad(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	protected void rect(float a, float b, float c, float d) {
		this.parent.rect(a, b, c, d);
	}

	protected void rect(float a, float b, float c, float d, float r) {
		this.parent.rect(a, b, c, d, r);
	}

	protected void rect(float a, float b, float c, float d, float tl, float tr, float br, float bl) {
		this.parent.rect(a, b, c, d, tl, tr, br, bl);
	}

	protected void square(float x, float y, float extent) {
		//TODO Update to Processing 3.5
		this.parent.rect(x, y, extent, extent);
	}

	protected void triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
		this.parent.triangle(x1, y1, x2, y2, x3, y3);
	}

	protected void bezier(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		this.parent.bezier(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	protected void bezierDetail(int detail) {
		this.parent.bezierDetail(detail);
	}

	protected float bezierPoint(float a, float b, float c, float d, float t) {
		return this.parent.bezierPoint(a, b, c, d, t);
	}

	protected float bezierTangent(float a, float b, float c, float d, float t) {
		return this.parent.bezierTangent(a, b, c, d, t);
	}

	protected void curve(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		this.parent.curve(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	protected void curveDetail(int detail) {
		this.parent.curveDetail(detail);
	}

	protected float curvePoint(float a, float b, float c, float d, float t) {
		return this.parent.curvePoint(a, b, c, d, t);
	}

	protected float curveTangent(float a, float b, float c, float d, float t) {
		return this.parent.curveTangent(a, b, c, d, t);
	}

	protected void curveTightness(float tightness) {
		this.parent.curveTightness(tightness);
	}

	protected void ellipseMode(@MagicConstant(intValues = {RADIUS, CENTER, CORNER, CORNERS}) int mode) {
		this.parent.ellipseMode(mode);
	}

	protected void rectMode(@MagicConstant(intValues = {CORNER, CORNERS, RADIUS, CENTER}) int mode) {
		this.parent.rectMode(mode);
	}

	protected void strokeCap(@MagicConstant(intValues = {SQUARE, PROJECT, ROUND}) int cap) {
		this.parent.strokeCap(cap);
	}

	protected void strokeJoin(@MagicConstant(intValues = {MITER, BEVEL, ROUND}) int join) {
		this.parent.strokeJoin(join);
	}

	protected void strokeWeight(float weight) {
		this.parent.strokeWeight(weight);
	}

	protected void beginContour() {
		this.parent.beginContour();
	}

	protected void beginShape() {
		this.parent.beginShape();
	}

	protected void bezierVertex(float x2, float y2, float x3, float y3, float x4, float y4) {
		this.parent.bezierVertex(x2, y2, x3, y3, x4, y4);
	}

	protected void curveVertex(float x, float y) {
		this.parent.curveVertex(x, y);
	}

	protected void endContour() {
		this.parent.endContour();
	}

	protected void endShape() {
		this.parent.endShape();
	}

	protected void endShape(@MagicConstant(intValues = {CLOSE}) int mode) {
		this.parent.endShape(mode);
	}

	protected void quadraticVertex(float cx, float cy, float x3, float y3) {
		this.parent.quadraticVertex(cx, cy, x3, y3);
	}

	protected void vertex(float x, float y) {
		this.parent.vertex(x, y);
	}

	protected void vertex(float[] v) {
		this.parent.vertex(v);
	}

	protected void vertex(float x, float y, float u, float v) {
		this.parent.vertex(x, y, u, v);
	}

	protected void shape(PShape shape) {
		this.parent.shape(shape);
	}

	protected void shape(PShape shape, float x, float y) {
		this.parent.shape(shape, x, y);
	}

	protected void shape(PShape shape, float a, float b, float c, float d) {
		this.parent.shape(shape, a, b, c, d);
	}

	protected void shapeMode(@MagicConstant(intValues = {CORNER, CORNERS, CENTER}) int mode) {
		this.parent.shapeMode(mode);
	}

	protected void translate(float x, float y) {
		this.parent.translate(x, y);
	}

	protected void background(float gray) {
		push();
		noStroke();
		fill(gray);
		rectMode(CORNER);
		rect(0, 0, this.width, this.height);
		pop();
	}

	protected void background(float gray, float alpha) {
		push();
		noStroke();
		fill(gray, alpha);
		rectMode(CORNER);
		rect(0, 0, this.width, this.height);
		pop();
	}

	protected void background(float v1, float v2, float v3) {
		push();
		noStroke();
		fill(v1, v2, v3);
		rectMode(CORNER);
		rect(0, 0, this.width, this.height);
		pop();
	}

	protected void background(float v1, float v2, float v3, float alpha) {
		push();
		noStroke();
		fill(v1, v2, v3, alpha);
		rectMode(CORNER);
		rect(0, 0, this.width, this.height);
		pop();
	}

	protected void background(PImage image) {
		this.parent.image(image, 0, 0, width, height);
	}

	protected void colorMode(@MagicConstant(intValues = {RGB, HSB}) int mode) {
		this.parent.colorMode(mode);
	}

	protected void colorMode(@MagicConstant(intValues = {RGB, HSB}) int mode, float max) {
		this.parent.colorMode(mode, max);
	}

	protected void colorMode(@MagicConstant(intValues = {RGB, HSB}) int mode, float max1, float max2, float max3) {
		this.parent.colorMode(mode, max1, max2, max3);
	}

	protected void colorMode(@MagicConstant(intValues = {RGB, HSB}) int mode, float max1, float max2, float max3, float maxA) {
		this.parent.colorMode(mode, max1, max2, max3, maxA);
	}

	protected void fill(int rgb) {
		this.parent.fill(rgb);
	}

	protected void fill(int rgb, float alpha) {
		this.parent.fill(rgb, alpha);
	}

	protected void fill(float gray) {
		this.parent.fill(gray);
	}

	protected void fill(float gray, float alpha) {
		this.parent.fill(gray, alpha);
	}

	protected void fill(float v1, float v2, float v3) {
		this.parent.fill(v1, v2, v3);
	}

	protected void fill(float v1, float v2, float v3, float alpha) {
		this.parent.fill(v1, v2, v3, alpha);
	}

	protected void fill(Color color) {
		this.parent.fill(color.r, color.g, color.b);
	}

	protected void noFill() {
		this.parent.noFill();
	}

	protected void noStroke() {
		this.parent.noStroke();
	}

	protected void stroke(int rgb) {
		this.parent.stroke(rgb);
	}

	protected void stroke(int rgb, float alpha) {
		this.parent.stroke(rgb, alpha);
	}

	protected void stroke(float gray) {
		this.parent.stroke(gray);
	}

	protected void stroke(float gray, float alpha) {
		this.parent.stroke(gray, alpha);
	}

	protected void stroke(float v1, float v2, float v3) {
		this.parent.stroke(v1, v2, v3);
	}

	protected void stroke(float v1, float v2, float v3, float alpha) {
		this.parent.stroke(v1, v2, v3, alpha);
	}

	protected float alpha(int rgb) {
		return this.parent.alpha(rgb);
	}

	protected float blue(int rgb) {
		return this.parent.blue(rgb);
	}

	protected float brightness(int rgb) {
		return this.parent.brightness(rgb);
	}

	protected int color(int gray) {
		return this.parent.color(gray);
	}

	protected int color(float gray) {
		return this.parent.color(gray);
	}

	protected int color(int gray, int alpha) {
		return this.parent.color(gray, alpha);
	}

	protected int color(float gray, float alpha) {
		return this.parent.color(gray, alpha);
	}

	protected int color(int v1, int v2, int v3) {
		return this.parent.color(v1, v2, v3);
	}

	protected int color(int v1, int v2, int v3, int alpha) {
		return this.parent.color(v1, v2, v3, alpha);
	}

	protected int color(float v1, float v2, float v3) {
		return this.parent.color(v1, v2, v3);
	}

	protected int color(float v1, float v2, float v3, float alpha) {
		return this.parent.color(v1, v2, v3, alpha);
	}

	protected float green(int rgb) {
		return this.parent.green(rgb);
	}

	protected float hue(int rgb) {
		return this.parent.hue(rgb);
	}

	protected int lerpColor(int c1, int c2, float amt) {
		return this.parent.lerpColor(c1, c2, amt);
	}

	protected float red(int rgb) {
		return this.parent.red(rgb);
	}

	protected float saturation(int rgb) {
		return this.parent.saturation(rgb);
	}

	protected PImage createImage(int w, int h, @MagicConstant(intValues = {RGB, ARGB, ALPHA}) int format) {
		return this.parent.createImage(w, h, format);
	}

	protected void image(PImage image, float a, float b) {
		this.parent.image(image, a, b);
	}

	protected void image(PImage image, float a, float b, float c, float d) {
		this.parent.image(image, a, b, c, d);
	}

	protected void image(Drawable drawable) {
		drawable.update();
	}

	protected void image(Drawable drawable, float x, float y) {
		drawable.update();
	}

	protected void image(Drawable drawable, float x, float y, float w, float h) {
		push();
		drawable.update();
		imageMode(CORNER);
		pop();
	}

	protected PImage loadImage(String filename) {
		return this.parent.loadImage(filename);
	}

	protected void imageMode(@MagicConstant(intValues = {CORNER, CORNERS, CENTER}) int mode) {
		this.parent.imageMode(mode);
	}

	protected PImage loadImage(String filename, String extension) {
		return this.parent.loadImage(filename, extension);
	}

	protected void text(String str, float x, float y) {
		this.parent.text(str, x, y);
	}

	protected void text(String str, float x1, float y1, float x2, float y2) {
		this.parent.text(str, x1, y1, x2, y2);
	}

	protected void text(Object obj, float x, float y) {
		text(obj.toString(), x, y);
	}

	protected void text(Object obj, float x1, float y1, float x2, float y2) {
		text(obj.toString(), x1, y1, x2, y2);
	}

	protected void textAlign(@MagicConstant(intValues = {LEFT, CENTER, RIGHT}) int alignX) {
		this.parent.textAlign(alignX);
	}

	protected void textAlign(@MagicConstant(intValues = {LEFT, CENTER, RIGHT}) int alignX, @MagicConstant(intValues = {TOP, BOTTOM, CENTER, BASELINE}) int alignY) {
		this.parent.textAlign(alignX, alignY);
	}

	protected void textLeading(float leading) {
		this.parent.textLeading(leading);
	}

	protected void textMode(@MagicConstant(intValues = {MODEL, SHAPE}) int mode) {
		this.parent.textMode(mode);
	}

	protected void textSize(float size) {
		this.parent.textSize(size);
	}

	protected float textWidth(String str) {
		return this.parent.textWidth(str);
	}
}
