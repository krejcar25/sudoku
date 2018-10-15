public abstract class BaseOverlay {
  public int x;
  public int y;
  public int sx;
  public int sy;
  public OverlayResult result;
  public OverlayType type;

  public BaseOverlay(int x, int y, int sx, int sy, OverlayType type) {
    this.x = x;
    this.y = y;
    this.sx = sx;
    this.sy = sy;
    this.result = OverlayResult.None;
    this.type = type;
  }

  public void drawButtons(PGraphics g) {
    g.rectMode(CENTER);
    g.pushStyle();
    g.fill(0);
    g.noStroke();
    g.pushMatrix();
    g.translate(sx / 2, sy - 40);

    switch (type.buttonCount) {
    case 1:
      drawButton(g, type.buttonLabels[0]);
      break;
    case 2:
      g.pushMatrix();
      g.translate(-sx / 2, 0);
      g.translate(sx / 3, 0);
      drawButton(g, type.buttonLabels[0]);
      g.translate(sx / 3, 0);
      drawButton(g, type.buttonLabels[1]);
      g.popMatrix();

      break;
    case 3:
      g.pushMatrix();
      g.translate(-sx / 4, 0);
      drawButton(g, type.buttonLabels[0]);
      g.popMatrix();

      drawButton(g, type.buttonLabels[1]);

      g.pushMatrix();
      g.translate(sx / 4, 0);
      drawButton(g, type.buttonLabels[2]);
      g.popMatrix();
      break;
    }
  }

  private void drawButton(PGraphics g, String buttonLabel) {
    g.rect(0, 0, 135, 40);
    g.pushStyle();
    g.fill(51);
    g.rect(0, 0, 131, 36);
    g.popStyle();
    g.pushStyle();
    g.fill(255);
    g.textSize(40);
    g.textAlign(CENTER, CENTER);
    g.text(buttonLabel, 0, -3);
    g.popStyle();
  }

  public abstract PGraphics show();
  public abstract OverlayResult getResult();
}

public class WinOverlay extends BaseOverlay {
  public WinOverlay(int x, int y, int sx, int sy) {
    super(x, y, sx, sy, OverlayType.YesNoCancel);
  }

  public PGraphics show() {
    PGraphics g = createGraphics(sx, sy);
    g.beginDraw();
    g.background(0);
    g.pushStyle();
    g.fill(200, 210, 200);
    g.rect(10, 10, sx-20, sy-20);
    g.popStyle();
    g.textSize(40);
    g.textAlign(CENTER, CENTER);
    g.text("You have won!", sx / 2, 60);
    drawButtons(g);
    g.endDraw();
    return g;
  }

  public OverlayResult getResult() {
    return OverlayResult.None;
  }
}
