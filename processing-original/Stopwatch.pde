public class StopWatch {

  private long startTime = 0;
  private long stopTime = 0;
  private long pauseStart = -1;
  private long pauseTotal = 0;
  private boolean running = false;
  private boolean finished = false;
  public String name;

  public StopWatch(String name) {
    this.name = name;
  }

  public void start() {
    start(0);
  }
  public void start(long offset) {
    if (finished) return;
    long startTime = System.currentTimeMillis() - offset;
    if (running && pauseStart > -1) {
      pauseTotal += (startTime - pauseStart);
      pauseStart = -1;
      println("StopWatch " + name + " resumed at " + startTime);
    } else if (!running) { 
      this.startTime = startTime;
      println("StopWatch " + name + " started at " + startTime);
      this.running = true;
    }
  }

  public void pause() {
    pause(0);
  }
  public void pause(long offset) {
    if (running && pauseStart == -1) {
      long pauseStart = System.currentTimeMillis() - offset;
      this.pauseStart = pauseStart;
      println("StopWatch " + name + " paused at " + pauseStart);
    }
  }

  public void stop() {
    if (!running) return;
    this.stopTime = System.currentTimeMillis();
    println("StopWatch " + name + " stopped at " + stopTime);
    this.running = false;
    this.finished = true;
  }


  //elaspsed time in milliseconds
  public long getElapsedTime() {
    long elapsed = -pauseTotal;
    if (running) {
      elapsed += (System.currentTimeMillis() - startTime);
    } else {
      elapsed += (stopTime - startTime);
    }
    return elapsed;
  }


  //elaspsed time in seconds
  public long getElapsedTimeSecs() {
    return getElapsedTime() / 1000;
  }
  
  public boolean isPaused() {
    return pauseStart > -1;
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
    return x * sy / sx;
  }
}
