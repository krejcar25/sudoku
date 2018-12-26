package cz.krejcar25.sudoku;

public enum GridDifficulty {
    Easy(0), Medium(1), Hard(2), Extreme(3);

    private final int level;

    GridDifficulty(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
