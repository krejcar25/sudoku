public class SettingsView extends BaseView {
  public SettingsView() {
    super(800, 600);
  }
  
  public void show() {
    push();
    background(51);
    textSize(40);
    fill(220);
    text("Settings", 280, 350);
    
    if (overlay != null) image(overlay.show(), overlay.x, overlay.y);
    pop();
  }
  
  public void click(int x, int y) {
    if (mouseButton == RIGHT) {
      stack.pop();
    }
  }
}
