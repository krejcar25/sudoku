public class Grid9x9 extends BaseGrid {
  FlashSquareList flashSquares = new FlashSquareList();
  boolean smallNumbers = false;
  boolean numFirst = true;

  Grid9x9(IGameView baseView) {
    super(baseView, 2);

    cols = 9;
    rows = 9;

    game = new int[cols][rows];
    baseGame = new boolean[cols][rows];
    notes = new boolean[cols][rows][9];

    for (int y = 0; y < (rows); y++) 
      for (int x = 0; x < cols; x++) 
        game[x][y] = -1;
  }

  void show() {
    int sx = ((BaseView)baseView).sizex / cols;
    int sy = ((BaseView)baseView).sizey / (rows + controlRows);

    if (timer.isPaused()) timer.start();

    int[] counts = new int[9];

    for (int y = 0; y < (rows + controlRows); y++) {
      for (int x = 0; x < cols; x++) {
        push();
        stroke((y > 8) ? buttonStroke : gameStroke);
        fill(cellBg(x, y));

        rect(x*sx, y*sy, sx, sy);
        pop();

        if (y < 9 && game[x][y] > -1) {
          drawNumber(x, y, game[x][y], baseGame[x][y]);
          counts[game[x][y]]++;
        }
        BaseSolver solver = baseView.getSolver();
        boolean solverRunning = solver != null && solver.used && solver.cycleAllowed;
        if (y < 9 && (solverRunning || game[x][y] < 0)) {
          push();
          translate(x * sx, y * sy);
          int sxs = sx / 3;
          int sys = sy / 3;
          translate(sxs / 2, sys / 2 - 3);
          textSize(sys);
          textAlign(CENTER, CENTER);
          fill(0);
          strokeWeight(0);
          for (int i = 0; i < 9; i++) {
            if ((solverRunning) ? solver.numbers.get(x).get(y).contains(i) : notes[x][y][i]) text(i + 1, (i % 3) * sxs, floor(i / 3) * sys);
          }
          pop();
        }
      }
    }

    push();
    stroke(0);
    strokeWeight(3);
    line(3 * sx, 0, 3 * sx, height - controlRows * sy);
    line(6 * sx, 0, 6 * sx, height - controlRows * sy);
    line(0, 3 * sy, width, 3 * sy);
    line(0, 6 * sy, width, 6 * sy);
    line(0, 9 * sy, width, 9 * sy);
    pop();

    boolean allNine = true;

    for (int i = 0; i < 9; i++) {
      drawNumber(i, 9, i, selectedn == i);
      push();
      translate(i * sx, 9 * sy);
      int sxs = sx / 3;
      int sys = sy / 3;
      translate(sxs / 2, sys / 2 - 3);
      textSize(sys);
      textAlign(CENTER, CENTER);
      fill((selectedn == i) ? lightBgFore : darkBgFore);
      strokeWeight(0);
      text(counts[i], 2 * sxs, 0);
      if (counts[i] < 9) allNine = false;
      pop();
    }

    if (allNine && timer.running) {
      timer.stop();
      println("Solved");
    }

    push();
    fill(darkBgFore);
    drawText(0, 10, "*");
    pop();

    push();
    fill((flashSquares.contains(1, 10)) ? lightBgFore : darkBgFore);
    drawText(1, 10, "?");
    pop();

    if (smallNumbers) {
      push();
      translate(4 * sx, 10 * sy);
      strokeWeight(1);
      stroke(220);
      line(sx / 3, 0, sx / 3, sy);
      line(2 * sx / 3, 0, 2 * sx / 3, sy);
      line(0, sy / 3, sx, sy / 3);
      line(0, 2 * sy / 3, sx, 2 * sy / 3);
      pop();
    } else {
      push();
      fill(220);
      strokeWeight(0);
      drawText(4, 10, "#");
      pop();
    }

    push();
    fill((selectedn == -2) ? lightBgFore : darkBgFore);
    drawText(3, 10, "x");
    pop();

    if (numFirst) {
      push();
      fill(220);
      strokeWeight(0);
      drawText(2, 10, "N");
      pop();
    } else {
      push();
      fill(220);
      strokeWeight(0);
      drawText(2, 10, "C");
      pop();
    }

    push();
    translate(5 * sx, 10 * sy);
    translate(sx / 2, sy / 2);
    fill(darkBgFore);
    ellipse(0, 0, sx / 2, sy / 2);
    fill(buttonFill);
    ellipse(0, 0, sx / 3, sy / 3);
    rectMode(CENTER);
    fill(darkBgFore);

    for (int i = 0; i < 8; i++) {
      push();
      rotate(PI * i / 4);
      translate(sx / 4, 0);
      rect(0, 0, sx / 8, sx / 8);
      pop();
    }
    pop();

    push();
    translate(6 * sx, 10 * sy);
    image(door, 10, 10, sx - 20, sy - 20);    
    pop();

    push();
    fill(buttonFill);
    stroke(buttonStroke);
    strokeWeight(1);
    rect(7 * sx, 10 * sy, 2 * sx, sy);
    pop();

    image(timer.show(), 7 * sx + 10, 10 * sy + (sy - timer.gety(2 * sx - 20)) / 2, 2 * sx - 20, timer.gety(2 * sx - 20));
  }

  private void drawNumber(int x, int y, int num, boolean black) {
    if (num > -1) {
      push();
      if (black) fill(lightBgFore);
      else if (y > 8) fill(darkBgFore);
      else fill(blue);
      drawText(x, y, Integer.toString(num + 1));
      pop();
    }
  }

  private void drawText(int x, int y, String text) {
    push();
    int sx = ((BaseView)baseView).sizex / cols;
    int sy = ((BaseView)baseView).sizey / (rows+controlRows);
    translate(x * sx, y * sy);
    translate(sx / 2, sy / 2);
    translate(0, -7);
    strokeWeight(0);
    textAlign(CENTER, CENTER);
    textSize(sy);
    text(text, 0, 0);
    pop();
  }

  private color cellBg(int x, int y) {
    if (flashSquares.contains(x, y)) return flashSquares.colorOf(x, y);
    else if (x == selectedn && y == 9) return thisFill;
    else if (x == 3 && y == 10 && selectedn == -2) return thisFill;
    else if (y < 9 && selectedn != -1 && selectedn!=-2 && (selectedn == game[x][y] || (game[x][y] == -1 && notes[x][y][selectedn]))) return neighbourFill;
    else if (y > 8) return buttonFill;
    else if (x == selectedx && y == selectedy) return thisFill;
    else if (x < 9 && selectedx > -1 && selectedy > -1 && isRowColSc(x, y)) return neighbourFill;
    else if (y < 9 && baseGame[x][y]) return baseFill;
    else return gameFill;
  }

  boolean isRowCol(int x, int y) {
    return isRowCol(x, y, -1, -1);
  }
  boolean isRowCol(int x, int y, int ofx, int ofy) {
    return xor(x == ((ofx != -1) ? ofx : selectedx), y == ((ofy != -1) ? ofy : selectedy));
  } 

  boolean isSc(int x, int y) {
    return isSc(x, y, -1, -1);
  }
  boolean isSc(int x, int y, int ofx, int ofy) {
    boolean isScX = floor(x / 3) == floor(((ofx != -1) ? ofx : selectedx) / 3);
    boolean isScY = floor(y / 3) == floor(((ofy != -1) ? ofy : selectedy) / 3);

    return isScX && isScY;
  }

  boolean isRowColSc(int x, int y) {
    return isRowColSc(x, y, -1, -1);
  }
  boolean isRowColSc(int x, int y, int ofx, int ofy) {
    return isRowCol(x, y, ofx, ofy) || isSc(x, y, ofx, ofy);
  }

  public void click(int x, int y, boolean right) {
    if (right) {
      if (x < 9 && y < 9) {
        placeNumber(-2, x, y);
        return;
      }
    }
    if (y == 9) {
      if (numFirst && x < 9) selectedn = (selectedn == x) ? -1 : x;
      else if (x < 9) placeNumber(x, selectedx, selectedy);
    } else if (y == 10) {
      switch (x) {
      case 0:
        baseView.newGenerator();
        baseView.generate();
        break;
      case 1:
        int count = getSolver().countSolutions();
        if (count < 1) flashSquares.newNow(1, 10, 40, flashFillBad);
        else if (count > 1) flashSquares.newNow(1, 10, 40, neighbourFill);
        else flashSquares.newNow(1, 10, 40, flashFillGood);
        break;
      case 2:
        numFirst = !numFirst;
        selectedx = -1;
        selectedy = -1;
        selectedn = -1;
        break;
      case 3:
        if (numFirst) selectedn = (selectedn == -2) ? -1 : -2;
        else placeNumber(-2, selectedx, selectedy);
        break;
      case 4:
        smallNumbers = !smallNumbers;
        break;
      case 5:
        timer.pause();
        stack.push(new SettingsView());
        break;
      case 6:
        stack.pop();
        break;
      }
    }
    if (x == selectedx && y == selectedy) {
      selectedx = -1;
      selectedy = -1;
    } else if (x < 9 && y < 9) {
      if (numFirst) {
        if (selectedn > -1 || selectedn == -2) placeNumber(selectedn, x, y);
      } else {
        selectedx = x;
        selectedy = y;
      }
    }
  }

  public void select(int x, int y) {
    selectedx = x;
    selectedy = y;
  }

  public void placeNumber(int num, int x, int y) {
    if (x > -1 && y > -1 && !baseGame[x][y]) {
      if (num > -1 && canPlaceNumber(num, x, y, 40)) {
        if (smallNumbers) {
          notes[x][y][num] = !notes[x][y][num];
        } else {
          game[x][y] = num;
          for (int ry = 0; ry < rows; ry++) {
            for (int rx = 0; rx < cols; rx++) {
              if (isRowColSc(rx, ry, x, y)) notes[rx][ry][num] = false;
            }
          }
        }
      } else if (num == -2) {
        if (smallNumbers) {
          for (int i = 0; i < 9; i++) {
            notes[x][y][i] = false;
          }
        } else {
          game[x][y] = -1;
        }
      }
    }
  }

  public boolean canPlaceNumber(int num, int atx, int aty, int flashTime) {
    boolean can = true;
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < cols; x++) {
        if (isRowColSc(x, y, atx, aty) && game[x][y] == num) {
          can = false;
          if (flashTime > -1) flashSquares.newNow(x, y, flashTime, flashFillBad);
        }
      }
    }
    return can;
  }

  public void lockAsBase(boolean output, boolean finalise) {
    baseClues = 0;
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < cols; x++) {
        if (game[x][y] > -1) {
          baseGame[x][y] = true;
          baseClues++;
        } else {
          baseGame[x][y] = false;
        }
      }
    }
    if (output) println("Clues locked: " + baseClues);
  }

  public void keyInput(int k) {
    if (k > 0) {
      if (numFirst) {
        selectedn = (selectedn == (k - 1)) ? -1 : (k - 1);
      } else {
        placeNumber(k, selectedx, selectedy);
      }
    }
  }

  public BaseSolver getSolver() {
    return new Sudoku9x9Solver(this);
  }

  public BaseGrid clone() {
    Grid9x9 clone = new Grid9x9(baseView);

    clone.baseClues = baseClues;
    clone.game = new int[clone.cols][clone.rows];
    for (int y = 0; y < clone.rows; y++) {
      for (int x = 0; x < clone.cols; x++) {
        clone.game[x][y] = game[x][y];
        clone.baseGame[x][y] = baseGame[x][y];
        for (int n = 0; n < 9; n++) {
          clone.notes[x][y][n] = notes[x][y][n];
        }
      }
    }
    clone.selectedx = selectedx;
    clone.selectedy = selectedy;
    clone.selectedn = selectedn;

    clone.flashSquares = flashSquares.clone();

    clone.smallNumbers = smallNumbers;
    clone.numFirst = numFirst;
    clone.finalised = finalised;

    return clone;
  }
}

void push() {
  pushMatrix();
  pushStyle();
}

void pop() {
  popStyle();
  popMatrix();
}
