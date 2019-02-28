public class Digit {
  int x;
  int y;
  float sx;
  float sy;
  final byte[] digits = new byte[] { 0x7E, 0x30, 0x6D, 0x79, 0x33, 0x5B, 0x5F, 0x70, 0x7F, 0x7B, 0x77, 0x1F, 0x4E, 0x3D, 0x4F, 0x47 };

  final color on = color(255, 0, 0);
  final color off = color(51,0,0);

  public Digit(int x, int y) {
    this.x = x;
    this.y = y;
    this.sx = 230;
    this.sy = 370;
  }

  public PGraphics show(int digit, boolean decimal) {
    return show((decimal) ? (byte)(digits[digit] + 0x80) : digits[digit]);
  }

  public float getx(float y) {
    return y * (sx / sy);
  }

  public float gety(float x) {
    return x * (sy / sx);
  }

  public PGraphics show(byte segments) {
    noStroke();
    PGraphics g = createGraphics((int)sx, (int)sy);
    PShape h = createShape();
    PShape value = createShape();

    h.beginShape();
    h.vertex(25, 0);
    h.vertex(125, 0);
    h.vertex(150, 25);
    h.vertex(125, 50);
    h.vertex(25, 50);
    h.vertex(0, 25);
    h.endShape(CLOSE);

    value.beginShape();
    value.vertex(35, 0);
    value.vertex(60, 25);
    value.vertex(50, 125);
    value.vertex(25, 150);
    value.vertex(0, 125);
    value.vertex(10, 25);
    value.endShape(CLOSE);

    g.beginDraw(); 
    g.noStroke();

    g.fill(isBitOn(segments, 0));
    g.ellipse(215, 355, 30, 30);

    // A
    h.setFill(isBitOn(segments, 1));
    g.shape(h, 50, 0);
    // B
    value.setFill(isBitOn(segments, 2));
    g.shape(value, 170, 30);
    // C
    value.setFill(isBitOn(segments, 3));
    g.shape(value, 160, 190);
    // D
    h.setFill(isBitOn(segments, 4));
    g.shape(h, 30, 320);
    // E
    value.setFill(isBitOn(segments, 5));
    g.shape(value, 0, 190);
    // F
    value.setFill(isBitOn(segments, 6));
    g.shape(value, 10, 30);
    // G
    h.setFill(isBitOn(segments, 7));
    g.shape(h, 40, 160);

    g.endDraw();
    return g;
  }
  
  private color isBitOn(byte segments, int segment) {
    return (((segments >> (byte)(7 - segment)) & 0x1) == 1) ? on : off;
  }
}
