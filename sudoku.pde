BaseGrid game;
BaseSolver solver;
int desiredClues = 28;
WinOverlay overlay;

void setup() {
  size(810, 990);
  SudokuGenerator gen = new Sudoku9x9Generator(desiredClues, width, height);
  game = gen.generate();
  overlay = new WinOverlay(55, 350, 700, 300);
}

void draw() {
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
  image(overlay.show(), overlay.x, overlay.y);
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
