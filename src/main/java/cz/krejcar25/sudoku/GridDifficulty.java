package cz.krejcar25.sudoku;

public enum GridDifficulty {
    Debug(-1), Easy(0), Medium(1), Hard(2), Extreme(3);

    private final int level;

    GridDifficulty(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        switch (this) {
            case Debug:
                return "Debug";
            case Easy:
                return "Easy";
            case Medium:
                return "Medium";
            case Hard:
                return "Hard";
            case Extreme:
                return "Extreme";
            default:
                return "Nonsense";
        }
    }
}
