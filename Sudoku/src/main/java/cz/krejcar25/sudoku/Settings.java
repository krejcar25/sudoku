package cz.krejcar25.sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;

public class Settings {
    public static final String DEF_PATH = "config.yml";

    public static Settings loadSettings(String location) throws FileNotFoundException, InvalidParameterException {
        File config = new File(location);
        if (!config.exists()) throw new FileNotFoundException("The location specified does not exist. You can user the prepare method to create the config file.");
        if (config.isDirectory()) throw new InvalidParameterException("The location specified is a directory. loadSettings requires a file to be specified.");
    }

    public static boolean prepare(String location) {

    }

    public String path;
    public boolean defaultNumberFirst;
    public boolean defaultNotesFirst;

    public boolean save() {

    }
}
