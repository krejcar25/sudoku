public class StopWatch {

  private long startTime = 0;
  private long stopTime = 0;
  private boolean running = false;
  public String name;
  
  public StopWatch(String name) {
    this.name = name;
  }

  public void start() {
    this.startTime = System.currentTimeMillis();
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
}
