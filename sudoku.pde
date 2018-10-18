int desiredClues = 28;

ViewStack stack;
PImage door;

void setup() {
  size(810, 990);
  stack = new ViewStack(new MainMenuView());
  BaseView v = stack.get();
  loadImages();
}

void draw() {
  BaseView view = stack.get();
  surface.setSize(view.sizex, view.sizey);
  view.show();
}

void mouseClicked() {
  stack.get().click(mouseX, mouseY);
}

void keyPressed() {
  if (stack.get() instanceof IGameView) {
    BaseGrid game = ((IGameView)stack.get()).getGrid();
    if (tryParseInt(String.valueOf(key))) game.keyInput(Integer.parseInt(String.valueOf(key)));
  }
}

void loadImages() {
  door = loadImage("media/door.png");
  door.loadPixels();
  for (int i = 0; i < door.width * door.height; i++) {
    door.pixels[i] = (door.pixels[i] == 0) ? color(51) : color(220);
  }
  door.updatePixels();
} 

boolean xor(boolean a, boolean b) {
  return (a && !b) || (!a && b);
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
