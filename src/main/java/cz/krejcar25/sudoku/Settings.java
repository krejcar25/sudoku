package cz.krejcar25.sudoku;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;

public class Settings
{
	private static final String DEF_PATH = "config.yml";
	@JsonIgnore
	private String path;
	private boolean defaultNumberFirst;
	private boolean defaultNotes;

	private Settings(String path)
	{
		this.path = path;
		this.defaultNumberFirst = true;
		this.defaultNotes = false;
	}

	static Settings loadSettings() throws FileNotFoundException, IllegalArgumentException
	{
		File file = new File(Settings.DEF_PATH);
		if (!file.exists())
			throw new FileNotFoundException("The path specified does not exist. You can use the prepare method to create the config file.");
		if (file.isDirectory())
			throw new IllegalArgumentException("The path specified is a directory. loadSettings requires a file to be specified.");
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try
		{
			Settings settings = mapper.readValue(file, Settings.class);
			settings.path = Settings.DEF_PATH;
			return settings;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	static void prepare()
	{
		Settings settings = new Settings(Settings.DEF_PATH);
		settings.save();
	}

	static void isValidConfig() throws IllegalArgumentException, IOException
	{
		File file = new File(Settings.DEF_PATH);
		if (!file.exists())
			throw new FileNotFoundException("The path specified does not exist. You can use the prepare method to create the config file.");
		if (file.isDirectory())
			throw new IllegalArgumentException("The path specified is a directory. isValidConfig requires a file to be specified.");
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		mapper.readValue(file, Settings.class);
	}

	void save()
	{
		File file = new File(path);
		if (file.isDirectory())
			throw new InvalidParameterException("The location specified is a directory. save requires a file to be specified.");
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try
		{
			mapper.writeValue(file, this);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isDefaultNumberFirst()
	{
		return defaultNumberFirst;
	}

	void setDefaultNumberFirst(boolean defaultNumberFirst)
	{
		this.defaultNumberFirst = defaultNumberFirst;
	}

	public boolean isDefaultNotes()
	{
		return defaultNotes;
	}

	void setDefaultNotes(boolean defaultNotes)
	{
		this.defaultNotes = defaultNotes;
	}
}

