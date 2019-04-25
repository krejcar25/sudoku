package cz.krejcar25.sudoku.scoreboard;

import cz.krejcar25.sudoku.game.GridDifficulty;
import cz.krejcar25.sudoku.game.GridProperties;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Scoreboard {
	public static final String DEF_PATH = "scoreboard.dat";
	private static final MessageDigest SHA256;

	// I need this static shit because I need a try...catch block around the getInstance() call
	static {
		MessageDigest sha256 = null;
		try {
			sha256 = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// Won't happen unless someone fiddles with the String above :)
		}
		SHA256 = sha256;
	}

	final ArrayList<ScoreboardEntry> entries;

	private Scoreboard() {
		this.entries = new ArrayList<>();
	}

	public void randomize() {
		for (GridProperties properties : GridProperties.values()) {
			for (GridDifficulty difficulty : GridDifficulty.values()) {
				for (int i = 0; i < 50; i++) {
					entries.add(new ScoreboardEntry(properties, difficulty));
				}
			}
		}
	}

	@Nullable
	public static Scoreboard loadScoreboard() throws IOException, ScoreboardWasMessedAroundWithException {
		try (final ZipFile zip = new ZipFile(DEF_PATH)) {
			BufferedInputStream data = new BufferedInputStream(zip.getInputStream(zip.getEntry("scoreboard.data")));
			data.mark(data.available());
			BufferedInputStream hash = new BufferedInputStream(zip.getInputStream(zip.getEntry("scoreboard.sha256")));

			byte[] dataBytes = new byte[data.available()];
			data.read(dataBytes);
			byte[] storedSha256 = new byte[hash.available()];
			hash.read(storedSha256);
			byte[] computedSha256 = SHA256.digest(dataBytes);

			if (Arrays.equals(storedSha256, computedSha256)) {
				Scoreboard sb = new Scoreboard();
				data.reset();
				BufferedReader reader = new BufferedReader(new InputStreamReader(data));
				reader.lines().forEach(sb::addEntry);

				return sb;
			} else {
				throw new ScoreboardWasMessedAroundWithException();
			}
		}
	}

	public static void prepare() {
		Scoreboard scoreboard = new Scoreboard();
		scoreboard.save();
	}

	public static void isValidScoreboard() throws IllegalArgumentException, IOException, ScoreboardWasMessedAroundWithException {
		File file = new File(DEF_PATH);
		if (!file.exists())
			throw new FileNotFoundException("The path specified does not exist. You can use the prepare method to create the scoreboard file.");
		if (file.isDirectory())
			throw new IllegalArgumentException("The path specified is a directory. isValidConfig requires a file to be specified.");
		loadScoreboard();
	}

	ArrayList<ScoreboardEntry> getEntries(GridProperties gridSize, GridDifficulty difficulty, SortBy sortBy, int sortOrder) {
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

	private void addEntry(String line) {
		try {
			ScoreboardEntry entry = ScoreboardEntry.deserialize(line);
			if (entry != null) entries.add(entry);
		} catch (ParseException e) {
			// Whatever, one doesn't get added...
		}
	}

	void save() {
		File file = new File(DEF_PATH);
		try (FileOutputStream fileStream = new FileOutputStream(file)) {
			try (ZipOutputStream zip = new ZipOutputStream(fileStream)) {
				StringBuilder sb = new StringBuilder();
				sb.append("# Sudoku Scoreboard entries. This file is verified upon read. Unless you reeeeeeeally know what you are doing, I recommend you don't fiddle with it.");
				sb.append(System.lineSeparator());

				for (ScoreboardEntry entry : entries) {
					sb.append(entry.serialize());
					sb.append(System.lineSeparator());
				}

				String serialized = sb.toString();

				ZipEntry data = new ZipEntry("scoreboard.data");
				zip.putNextEntry(data);
				zip.write(serialized.getBytes(StandardCharsets.UTF_8));
				zip.closeEntry();

				ZipEntry hash = new ZipEntry("scoreboard.sha256");
				zip.putNextEntry(hash);
				zip.write(SHA256.digest(serialized.getBytes(StandardCharsets.UTF_8)));
				zip.closeEntry();
			} catch (FileNotFoundException ex) {
				// We don`t care
			} catch (IOException ex) {
				// Do we care?
			}
		} catch (FileNotFoundException e) {
			if (file.mkdir()) save();
			else System.out.println("Something bad happened :)");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public enum SortBy {
		Date, Time
	}
}
