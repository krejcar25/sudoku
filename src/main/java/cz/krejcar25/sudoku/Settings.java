package cz.krejcar25.sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Settings {
    public static final String DEF_PATH = "config.yml";

    public static Settings loadSettings(String path) throws FileNotFoundException, IllegalArgumentException {
        File file = new File(path);
        if (!file.exists()) throw new FileNotFoundException("The path specified does not exist. You can use the prepare method to create the config file.");
        if (file.isDirectory()) throw new IllegalArgumentException("The path specified is a directory. loadSettings requires a file to be specified.");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            Settings settings = mapper.readValue(file, Settings.class);
            settings.path = path;
            return settings;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean prepare(String path) {
        Settings settings = new Settings();
        return settings.save();
    }

    public static void isValidConfig(String path) throws InvalidFormatException, IllegalArgumentException, IOException {
        File file = new File(path);
        if (!file.exists()) throw new FileNotFoundException("The path specified does not exist. You can use the prepare method to create the config file.");
        if (file.isDirectory()) throw new IllegalArgumentException("The path specified is a directory. isValidConfig requires a file to be specified.");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.readValue(file, Settings.class);
    }

    @JsonIgnore
    private String path;
    private boolean defaultNumberFirst;
    private boolean defaultNotes;

    private Settings() {
        this.path = DEF_PATH;
        this.defaultNumberFirst = true;
        this.defaultNotes = false;
    }

    public boolean save() {
        File file = new File(path);
        if (file.isDirectory()) throw new InvalidParameterException("The location specified is a directory. save requires a file to be specified.");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            mapper.writeValue(file, this);
            return true;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getPath() {
        return path;
    }

    public boolean isDefaultNumberFirst() {
        return defaultNumberFirst;
    }

    public void setDefaultNumberFirst(boolean defaultNumberFirst) {
        this.defaultNumberFirst = defaultNumberFirst;
    }

    public boolean isDefaultNotes() {
        return defaultNotes;
    }

    public void setDefaultNotes(boolean defaultNotes) {
        this.defaultNotes = defaultNotes;
    }
}

