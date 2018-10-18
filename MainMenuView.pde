public class MainMenuView extends BaseView {
  public MainMenuView() {
    super(800, 600);
  }

  public void show() {
    push();
    background(51);
    textSize(40);
    fill(220);
    text("Main menu", 280, 350);
    
    if (overlay != null) image(overlay.show(), overlay.x, overlay.y);
    pop();
  }

  public void click(int mx, int my) {
    if (mouseButton == LEFT) {
      Sudoku9x9View view = new Sudoku9x9View(desiredClues);
      view.generate();
      stack.push(view);
    }
  }
}
