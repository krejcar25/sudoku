BaseGrid game;
BaseSolver solver;
int desiredClues = 28;
long[] times;
int done;
boolean benchmark = false;
WinOverlay overlay;

void setup() {
  size(810, 990);
  times = new long[10000];
  done = 0;
  if (!benchmark) generateGame();
  overlay = new WinOverlay(55,350,700,200);
}

void draw() {
  if (benchmark) benchmark();
  else runGame();
  image(overlay.show(), overlay.x, overlay.y);
}

void benchmark() {
  times[done] = generateGame();
  done++;
  if (done >= times.length) {
    String[] t = new String[times.length];
    for (int i = 0; i < times.length; i++) {
      t[i] = String.valueOf(times[i]);
    }
    saveStrings("genTimes.txt", t);
    text("Finished", 80, 240);
    noLoop();
  }

  background(220);
  fill(51);
  noStroke();
  textSize(60);
  text("Finished cycles: " + done, 80, 80);
  long sum = 0;
  for (int i = 0; i < done; i++) {
    sum += times[i];
  }
  float avg = ((float)sum) / done;
  text("Average time: " + avg, 80, 160);
}

long generateGame() {
  SudokuGenerator gen = new Sudoku9x9Generator(desiredClues, width, height);
  game = gen.generate();
  return gen.timer.getElapsedTime();
}

void runGame() {
  if (solver != null && frameCount % 20 == 0) {
    if (solver.cycleAllowed) solver.cycle();
    else if (!solver.used) {
      if (!solver.prepare()) solver = null;
    } else {
      solver.finish();
      solver = null;
    }
  }
  game.show();
}

boolean xor(boolean a, boolean b) {
  return (a && !b) || (!a && b);
}

void mouseClicked() {
  int sizex = floor(width / game.cols);
  int sizey = floor(height / (game.rows + game.extraRows));

  int x = floor(mouseX / sizex);
  int y = floor(mouseY / sizey);

  game.click(x, y, mouseButton == RIGHT);
}

void keyPressed() {
  if (tryParseInt(String.valueOf(key))) game.keyInput(Integer.parseInt(String.valueOf(key)));
}

boolean tryParseInt(String value) {  
  try {  
    Integer.parseInt(value);  
    return true;
  } 
  catch (NumberFormatException e) {  
    return false;
  }
}

void newGame(BaseGrid ng) {
  game = ng;
}

void setSolver(BaseSolver s) {
  solver = s;
}

void endSolver() {
  solver = null;
}
