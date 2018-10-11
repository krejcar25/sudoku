class FlashSquare {
  int x, y, timestamp, lifespan;
  FlashSquare(int x, int y, int lifespan) {
    this.x = x;
    this.y = y;
    this.timestamp = frameCount;
    this.lifespan = lifespan;
  }

  boolean isValid() {
    return frameCount < timestamp + lifespan;
  }
}

class FlashSquareList {
  ArrayList<FlashSquare> squares;
  FlashSquareList() {
    squares = new ArrayList<FlashSquare>();
  }

  void newNow(int x, int y, int lifespan) {
    push(new FlashSquare(x, y, lifespan));
  } 

  void push(FlashSquare square) {
    squares.add(square);
  }

  boolean contains(int x, int y) {
    for (int i = squares.size() - 1; i >= 0; i--) {
      FlashSquare square = squares.get(i);
      if (!(square.isValid())) {
        squares.remove(square);
      } else if (square.x==x&&square.y==y) return true;
    }
    return false;
  }
}
