public abstract class BaseOverlay {
  public int x;
  public int y;
  public int sx;
  public int sy;
  public OverlayResult result;
  public OverlayType type;
  ArrayList<Button> buttons;

  public BaseOverlay(int x, int y, int sx, int sy, OverlayType type) {
    this.x = x;
    this.y = y;
    this.sx = sx;
    this.sy = sy;
    this.result = OverlayResult.None;
    this.type = type;
    this.buttons = new ArrayList<Button>();
    
    int bsx = 135;
    int bsy = 40;
    int bbx = 2;
    int bby = 2;
    switch (type.buttonCount) {
    case 1:
      buttons.add(new Button(sx / 2, sy - 40, bsx, bsy, bbx, bby, type.buttonLabels[0]));
      break;
    case 2:
      buttons.add(new Button(sx / 3, sy - 40, bsx, bsy, bbx, bby, type.buttonLabels[0]));
      buttons.add(new Button(2 * sx / 3, sy - 40, bsx, bsy, bbx, bby, type.buttonLabels[1]));
      break;
    case 3:
      buttons.add(new Button(sx / 4, sy - 40, bsx, bsy, bbx, bby, type.buttonLabels[0]));
      buttons.add(new Button(sx / 2, sy - 40, bsx, bsy, bbx, bby, type.buttonLabels[1]));
      buttons.add(new Button(3 * sx / 4, sy - 40, bsx, bsy, bbx, bby, type.buttonLabels[2]));
      break;
    }
  }

  protected void drawButtons(PGraphics g) {
    for (int i = 0; i < buttons.size(); i++) {
      buttons.get(i).show(g);
    }
  }

  public abstract PGraphics show();
  public abstract OverlayResult getResult();
}

public class WinOverlay extends BaseOverlay {
  public WinOverlay() {
    super(105, 300, 600, 200, OverlayType.OK);
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
