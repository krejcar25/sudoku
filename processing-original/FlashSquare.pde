class FlashSquare {
  int x, y, timestamp, lifespan;
  color c;
  FlashSquare(int x, int y, int lifespan, color c) {
    this.x = x;
    this.y = y;
    this.timestamp = frameCount;
    this.lifespan = lifespan;
    this.c = c;
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

  void newNow(int x, int y, int lifespan, color c) {
    push(new FlashSquare(x, y, lifespan, c));
  } 

  void push(FlashSquare square) {
    squares.add(square);
  }

  boolean contains(int x, int y) {
    return colorOf(x, y) != -1;
  }
  
  color colorOf(int x, int y) {
    for (int i = squares.size() - 1; i >= 0; i--) {
      FlashSquare square = squares.get(i);
      if (!(square.isValid())) {
        squares.remove(square);
      } else if (square.x == x && square.y == y) return square.c;
    }
    return -1;
  }
  
  FlashSquareList clone() {
    FlashSquareList clone = new FlashSquareList();
    for (FlashSquare s : squares) {
      clone.squares.add(s);
    }
    return clone;
  }
}
