public abstract class BaseSolver {
  protected BaseGrid game;
  protected boolean used = false;
  protected StopWatch timer;
  ArrayList<ArrayList<ArrayList<Integer>>> numbers;

  int count;
  int x;
  int y;
  boolean tryNext;
  boolean cycleAllowed;
  protected boolean wentBack;

  public BaseSolver(BaseGrid game) {
    this.game = game;
    timer = new StopWatch("GridSolver");
    numbers = new ArrayList<ArrayList<ArrayList<Integer>>>();
    tryNext = true;
    cycleAllowed = false;
    wentBack = false;
  }

  public abstract int countSolutions();
  public abstract boolean prepare();
  public abstract void cycle();
  public abstract int finish();
}

public class Solver9x9 extends BaseSolver {

  public Solver9x9(Grid9x9 game) {
    super(game);
    for (int x = 0; x < game.cols; x++) {
      numbers.add(new ArrayList<ArrayList<Integer>>());
      for (int y = 0; y < game.rows; y++) {
        numbers.get(x).add(new ArrayList<Integer>());
        replenish(x, y);
      }
    }

    count = 0;
    x = 0;
    y = 0;
  }

  public int countSolutions() {
    timer.start();
    if (!prepare()) return -1;

    while (tryNext) {
      cycle();
    }

    timer.stop();
    return finish();
  }

  public boolean prepare() {
    if (used) return false;
    used = true;
    cycleAllowed = true;
    return true;
  }

  public void cycle() {
    if (!cycleAllowed) return;
    
    game.select(x,y);
    
    if (wentBack) {
      wentBack = false;
      numbers.get(x).get(y).remove(0);
    }
    
    if (available(x, y)) {
      int n = 0;
      if (game.canPlaceNumber(numbers.get(x).get(y).get(n), x, y, -1) || game.game[x][y] == numbers.get(x).get(y).get(n)) {
        game.game[x][y] = numbers.get(x).get(y).get(n);
        x++;
      } else {
        numbers.get(x).get(y).remove(n);
      }
    } else {
      replenish(x, y);
      if (!game.baseGame[x][y]) game.game[x][y] = -1;
      x--;
      wentBack = true;
    }


    if (x >= game.cols) {
      x -= game.cols;
      y++;
    } else if (x < 0) {
      x += game.cols;
      y--;
    }

    if (y >= game.rows) {
      count++;
      x += (game.cols - 2);
      y--;
      if (!game.baseGame[x][y]) game.game[8][8] = -1;
      wentBack = true;
    }
    if (y < 0) {
      tryNext = false;
      cycleAllowed = false;
    }
  }
  
  public int finish() {
    println("Solving finished in " + timer.getElapsedTimeSecs() + " seconds (" + timer.getElapsedTime() + " milliseconds, to be precise). Found " + count + " solutions.");
    return count;
  }

  private void replenish(int x, int y) {
    ArrayList<Integer> list = numbers.get(x).get(y);
    list.clear();
    if (game.baseGame[x][y]) {
      list.add(game.game[x][y]);
    } else {
      for (int i = 0; i < 9; i++) {
        list.add(i);
      }
    }
  }

  private boolean available(int x, int y) {
    return numbers.get(x).get(y).size() > 0;
  }
}
