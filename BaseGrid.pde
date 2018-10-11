public abstract class BaseGrid {
  int sizex;
  int sizey;

  int cols;
  int rows;
  int extraRows;
  int baseClues;

  int[][] game;
  boolean[][] baseGame;
  boolean[][][] notes;

  int selectedx = -1;
  int selectedy = -1;
  int selectedn = -1;

  private color gameFill = color(255);
  private color gameStroke = color(51);
  private color baseFill = color(220);
  private color neighbourFill = color(255, 255, 200);
  private color thisFill = color(255, 255, 100);
  private color buttonFill = color(51);
  private color buttonStroke = color(220);
  private color flashFill = color(255, 200, 200);
  private color darkBgFore = color(220);
  private color lightBgFore = color(51);
  private color blue = color(0, 0, 255);

  FlashSquareList flashSquares = new FlashSquareList();
  boolean smallNumbers = false;
  boolean numFirst = true;

  public BaseGrid(int sizex, int sizey, int extraRows) {
    this.sizex = sizex;
    this.sizey = sizey;
    this.extraRows = extraRows;
  }
  
  public abstract void show();
  public abstract void click(int x, int y, boolean right);
  public abstract void select(int x, int y);
  public abstract void placeNumber(int num, int x, int y);
  public abstract boolean canPlaceNumber(int num, int atx, int aty, int flashTime);
  public abstract void lockAsBase(boolean output);
  public abstract void keyInput(int k);
  public abstract BaseSolver getSolver();
}
