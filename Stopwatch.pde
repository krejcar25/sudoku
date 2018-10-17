public class StopWatch {

  private long startTime = 0;
  private long stopTime = 0;
  private boolean running = false;
  public String name;

  public StopWatch(String name) {
    this.name = name;
  }

  public void start() {
    start(0);
  }
  public void start(long offset) {
    this.startTime = System.currentTimeMillis() - offset;
    println("StopWatch " + name + " started at " + startTime);
    this.running = true;
  }

  public void stop() {
    this.stopTime = System.currentTimeMillis();
    println("StopWatch " + name + " stopped at " + stopTime);
    this.running = false;
  }


  //elaspsed time in milliseconds
  public long getElapsedTime() {
    long elapsed;
    if (running) {
      elapsed = (System.currentTimeMillis() - startTime);
    } else {
      elapsed = (stopTime - startTime);
    }
    return elapsed;
  }


  //elaspsed time in seconds
  public long getElapsedTimeSecs() {
    long elapsed;
    if (running) {
      elapsed = ((System.currentTimeMillis() - startTime) / 1000);
    } else {
      elapsed = ((stopTime - startTime) / 1000);
    }
    return elapsed;
  }

  private final int sx = 975;
  private final int sy = 370;

  public PGraphics show() {
    long time = getElapsedTimeSecs();
    int minD = abs(floor(floor(time / 60) / 10));
    int minU = abs(floor(time / 60) % 10);
    int secD = abs(floor(floor(time % 60) / 10));
    int secU = abs(floor(time % 60) % 10);
    Digit d = new Digit(0, 0);
    PGraphics g = createGraphics(sx, sy);

    g.beginDraw();
    g.background(51);
    g.image(d.show(minD, false), 0, 0);
    g.image(d.show(minU, false), d.sx, 0);
    g.pushStyle();
    g.fill((getElapsedTime() % 1000 < 500) ? d.on : d.off);
    g.ellipse(2 * d.sx + 30, 120, 30, 30);
    g.ellipse(2 * d.sx + 20, 250, 30, 30);
    g.popStyle();
    g.image(d.show(secD, false), 2 * d.sx + 55, 0);
    g.image(d.show(secU, false), 3 * d.sx + 55, 0);
    g.endDraw();

    return g;
  }

  public float getx(float y) {
    return y * sx / sy;
  }

  public float gety(float x) {
    return x * sx / sy;
  }
}
