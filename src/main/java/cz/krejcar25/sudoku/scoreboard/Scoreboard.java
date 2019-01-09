package cz.krejcar25.sudoku.scoreboard;

import cz.krejcar25.sudoku.game.GridDifficulty;
import cz.krejcar25.sudoku.game.GridProperties;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Scoreboard implements Serializable {
    public static final String DEF_PATH = "score.dat";

    public static Scoreboard loadScoreboard(String path) throws IOException {
        FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);

        try {
            return (Scoreboard) objectIn.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void prepare(String path) {
        Scoreboard scoreboard = new Scoreboard(path);
        scoreboard.save();
    }

    public static boolean isValidScoreboard(String path) throws IOException {
        File file = new File(path);
        if (!file.exists())
            throw new FileNotFoundException("The path specified does not exist. You can use the prepare method to create the scoreboard file.");
        if (file.isDirectory())
            throw new IllegalArgumentException("The path specified is a directory. isValidConfig requires a file to be specified.");
        return loadScoreboard(path) != null;
    }

    private String path;
    public ArrayList<ScoreboardEntry> entries;

    private Scoreboard(String path) {
        this.path = path;
        this.entries = new ArrayList<>();

        // Testing data generation
        for (GridProperties properties : GridProperties.values()) {
            for (GridDifficulty difficulty : GridDifficulty.values()) {
                for (int i = 0; i < 50; i++) {
                    entries.add(new ScoreboardEntry(properties, difficulty));
                }
            }
        }
    }

    public ArrayList<ScoreboardEntry> getEntries(GridProperties gridSize, GridDifficulty difficulty, SortBy sortBy, int sortOrder) {
        ArrayList<ScoreboardEntry> selected = new ArrayList<>();
        for (ScoreboardEntry entry : entries)
            if (entry.getGridSize() == gridSize && entry.getDifficulty() == difficulty) selected.add(entry);

        selected.sort(new ScoreboardComparator(sortBy, sortOrder));
        return selected;
    }

    public void addEntry(GridProperties gridProperties, GridDifficulty gridDifficulty, Date dateStarted, long timeElapsed) {
        entries.add(new ScoreboardEntry(gridProperties, gridDifficulty, dateStarted, timeElapsed));
        save();
    }

    public void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.flush();
            objectOut.close();
        } catch (FileNotFoundException ex) {
            // We don`t care
        } catch (IOException ex) {
            // Do we care?
        }
    }

    public enum SortBy {
        Date, Time
    }
}
