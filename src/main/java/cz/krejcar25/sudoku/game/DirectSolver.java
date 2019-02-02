package cz.krejcar25.sudoku.game;

import cz.krejcar25.sudoku.Timer;

import java.util.BitSet;

// Stupid sudoku solver with a limited set of solving rules
// useful for assessing the difficulty

public class DirectSolver {
    protected BaseGrid game;
    protected Rule[] rules;

    public DirectSolver(BaseGrid game) {
        this.game = game.clone();
        this.game.lockAsBase(false,false);
        this.rules = new Rule[]{new LastInBlockRule(), new LastInRowRule(), new LastInColumnRule(), new OnlyRule()};
    }

    public int getDifficulty() {
      Timer timer = new Timer("DirectSolver");
      timer.start();
      int maxDifficulty = 0;
      while (!isDone()) {
        for (int i=0; i<maxDifficulty || ++maxDifficulty <= rules.length; ++i) {
          if (rules[i].apply(game)) {
            break;
          }
        }
        if (maxDifficulty > rules.length) {
          break;
        }
      }
      timer.stop();
      return maxDifficulty;
    }

  private boolean isDone() {
    final int size = game.sizea * game.sizeb;
    for (int x=0; x<size; ++x) {
      for (int y=0; y<size; ++y) {
        if (game.game[x][y] < 0) {
          return false;
        }
      }
    }
    return true;
  }
}

abstract class Rule {
  abstract boolean apply(BaseGrid game);
  static void set(BitSet bs, int element) {
    if (element >= 0) {
      bs.set(element);
    }
  }
}

/**
 * Fill in the only number not used in a row, column and block
 */
class OnlyRule extends Rule {
  @Override
  public boolean apply(BaseGrid game) {
    BitSet occupied = new BitSet();
    final int size = game.sizea * game.sizeb;
    for (int x=0; x<size; ++x) {
      for (int y=0; y<size; ++y) {
        if (game.game[x][y] >= 0) {
          continue;
        }
        occupied.clear();
        for (int i=0; i<size; ++i) {
          set(occupied, game.game[i][y]);
          set(occupied, game.game[x][i]);
          set(occupied, game.game[x - (x % game.sizea) + i % game.sizea][y - (y % game.sizeb) + i / game.sizea]);
        }
        if (occupied.cardinality() == size - 1) {
          game.game[x][y] = occupied.nextClearBit(0);
          return true;
        }
      }
    }
    return false;
  }
}

abstract class LastRule extends Rule {
  abstract boolean canPlace(BaseGrid game, int number, int i, int j);
  abstract void set(BaseGrid game, int number, int i, int j);
  @Override
  boolean apply(BaseGrid game) {
    BitSet available = new BitSet();
    final int size = game.sizea * game.sizeb;
    for (int i=0; i<size; ++i) {
      for (int num=0; num<size; ++num) {
        available.clear();
        for (int j=0; j<size; ++j) {
          if (canPlace(game, num, i, j)) {
            available.set(j);
          }
          if (available.cardinality() > 1) {
            break;
          }
        }
        if (available.cardinality() == 1) {
          int j = available.nextSetBit(0);
          set(game, num, i, j);
          return true;
        }
      }
    }
    return false;
  }
}

class LastInColumnRule extends LastRule {
  @Override
  boolean canPlace(BaseGrid game, int number, int x, int y) {
    return game.game[x][y] < 0 && game.canPlaceNumber(number, x, y, -1);
  }

  @Override
  void set(BaseGrid game, int number, int x, int y) {
    game.game[x][y] = number;
  }
}

class LastInRowRule extends LastRule {
  @Override
  boolean canPlace(BaseGrid game, int number, int y, int x) {
    return game.game[x][y] < 0 && game.canPlaceNumber(number, x, y, -1);
  }

  @Override
  void set(BaseGrid game, int number, int y, int x) {
    game.game[x][y] = number;
  }
}

class LastInBlockRule extends LastRule {
  @Override
  boolean canPlace(BaseGrid game, int number, int block, int cell) {
    int x = getX(game, block, cell);
    int y = getY(game, block, cell);
    return game.game[x][y] < 0 && game.canPlaceNumber(number, x, y,-1);
  }

  @Override
  void set(BaseGrid game, int number, int block, int cell) {
    int x = getX(game, block, cell);
    int y = getY(game, block, cell);
    game.game[x][y] = number;
  }

  private int getY(BaseGrid game, int block, int cell) {
    return game.sizeb * (block / game.sizeb) + (cell / game.sizea);
  }

  private int getX(BaseGrid game, int block, int cell) {
    return game.sizea * (block % game.sizeb) + (cell % game.sizea);
  }
}

