public abstract class BaseGrid {
  IGameView baseView;

  int cols;
  int rows;
  int controlRows;
  int baseClues;

  int[][] game;
  boolean[][] baseGame;
  boolean[][][] notes;

  int selectedx = -1;
  int selectedy = -1;
  int selectedn = -1;

  protected color gameFill = color(255);
  protected color gameStroke = color(51);
  protected color baseFill = color(220);
  protected color neighbourFill = color(255, 255, 200);
  protected color thisFill = color(255, 255, 100);
  protected color buttonFill = color(51);
  protected color buttonStroke = color(220);
  protected color flashFillBad = color(255, 200, 200);
  protected color flashFillGood = color(200, 255, 200);
  protected color darkBgFore = color(220);
  protected color lightBgFore = color(51);
  protected color blue = color(0, 0, 255);

  FlashSquareList flashSquares;
  boolean smallNumbers;
  boolean numFirst;

  boolean finalised;

  StopWatch timer;

  public BaseGrid(IGameView baseView, int controlRows) {
    this.baseView = baseView;
    this.controlRows = controlRows;
    this.flashSquares = new FlashSquareList();
    smallNumbers = false;
    numFirst = true;
    finalised = false;
    timer = new StopWatch("9x9 Grid");
  }

  public abstract void show();
  public abstract void click(int x, int y, boolean right);
  public abstract void select(int x, int y);
  public abstract void placeNumber(int num, int x, int y);
  public abstract boolean canPlaceNumber(int num, int atx, int aty, int flashTime);
  public abstract void lockAsBase(boolean output, boolean finalise);
  public abstract void keyInput(int k);
  public abstract BaseSolver getSolver();
  public abstract BaseGrid clone();
}
