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
        this.rules = new Rule[]{new OnlyRule()};
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
}

/**
 * Fill in the only number not used in a row, column and block
 */
class OnlyRule extends Rule {
  static void set(BitSet bs, int element) {
    if (element >= 0) {
      bs.set(element);
    }
  }

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
        if (occupied.cardinality() == game.numbers() - 1) {
          game.game[x][y] = occupied.nextClearBit(0);
          return true;
        }
      }
    }
    return false;
  }
}
