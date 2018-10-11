abstract class SudokuGenerator {
  int targetCount;
  int sizex;
  int sizey;
  ArrayList<ArrayList<ArrayList<Integer>>> numbers;
  StopWatch timer;
  protected boolean used = false;
  
  SudokuGenerator(int targetCount, int sizex, int sizey) {
    this.targetCount = targetCount;
    this.sizex = sizex;
    this.sizey = sizey;
    timer = new StopWatch("GridGenerator");
    numbers = new ArrayList<ArrayList<ArrayList<Integer>>>();
  }

  abstract BaseGrid generate();
}

public class Sudoku9x9Generator extends SudokuGenerator {
  Sudoku9x9Generator(int targetCount, int sizex, int sizey) {
    super(targetCount, sizex, sizey);
    for (int x = 0; x < 9; x++) {
      numbers.add(new ArrayList<ArrayList<Integer>>());
      for (int y = 0; y < 9; y++) {
        numbers.get(x).add(new ArrayList<Integer>());
        replenish(x, y);
      }
    }
  }

  public Grid9x9 generate() {
    timer.start();
    if (used) return null;
    used = true;
    Grid9x9 game = new Grid9x9(sizex, sizey, 2);

    int gx = 0;
    int gy = 0;

    while (true) {
      if (available(gx, gy)) {
        int n = floor(random(numbers.get(gx).get(gy).size()));
        if (game.canPlaceNumber(numbers.get(gx).get(gy).get(n), gx, gy, -1)) {
          game.game[gx][gy] = numbers.get(gx).get(gy).get(n);
          gx++;
        } else {
          numbers.get(gx).get(gy).remove(n);
        }
      } else {
        replenish(gx, gy);
        game.game[gx][gy] = -1;
        gx--;
      }

      if (gx < 0) {
        gx += 9;
        gy--;
      } else if (gx >= 9) {
        gx -= 9;
        gy++;
      }

      if (gy < 0 || gy >= 9) break;
    }

    PVector[] cells = new PVector[81];

    for (int y = 0; y < 9; y++) {
      for (int x = 0; x < 9; x++) {
        cells[9 * y + x] = new PVector(x, y);
      }
    }

    cells = shuffle(cells);
    int removed = 0;
    
    for (int i = 80; removed < (80 - targetCount) && i >= 0; i--) {
      PVector v = cells[i];
      int x = floor(v.x);
      int y = floor(v.y);
      
      if (game.game[x][y] > -1) {
        int cell = game.game[x][y];
        game.game[x][y] = -1;
        game.lockAsBase(false);
        int solutions = game.getSolver().countSolutions();
        if (solutions > 1) {
          game.game[x][y] = cell;
        } else removed++;
      }
    }
    
    game.select(-1, -1);
    
    println("removal target: " + (80 - targetCount) + ", removed: " + removed);
    game.lockAsBase(true);
    timer.stop();
    println("Generation finished in " + timer.getElapsedTimeSecs() + " seconds (" + timer.getElapsedTime() + " milliseconds, to be precise)");
    return game;
  }

  private void replenish(int x, int y) {
    ArrayList<Integer> list = numbers.get(x).get(y);
    list.clear();
    for (int i = 0; i < 9; i++) {
      list.add(i);
    }
  }

  private boolean available(int x, int y) {
    return numbers.get(x).get(y).size() > 0;
  }
}

PVector[] shuffle(PVector[] input) {
  int m = input.length;
  int i;
  PVector t;
  
  while (m > 0) {
    i = floor(random(m--));
    t = input[m];
    input[m] = input[i];
    input[i] = t;
  }
  
  return input;
}
