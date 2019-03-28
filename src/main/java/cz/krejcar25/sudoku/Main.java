package cz.krejcar25.sudoku;

import com.besaba.revonline.pastebinapi.Pastebin;
import com.besaba.revonline.pastebinapi.impl.factory.PastebinFactory;
import cz.krejcar25.sudoku.game.GridCore;
import cz.krejcar25.sudoku.game.GridDifficulty;
import cz.krejcar25.sudoku.game.GridProperties;
import cz.krejcar25.sudoku.game.SudokuGenerator;
import cz.krejcar25.sudoku.networkControl.GridCoreTrainingDataSet;
import cz.krejcar25.sudoku.neuralNetwork.NetworkTrainRunnable;
import cz.krejcar25.sudoku.neuralNetwork.NeuralNetwork;
import org.apache.commons.cli.*;
import processing.core.PApplet;

import java.io.*;

@SuppressWarnings("SpellCheckingInspection")
public class Main {
	public static final PastebinFactory pastebinFactory = new PastebinFactory();
	public static final Pastebin pastebin = pastebinFactory.createPastebin("b05b784a8076c6b40a846c50688adfd1");

	public static void main(String[] args) {
		Options options = new Options();

		// Option declarations
		{
			StringBuilder sizeDesc = new StringBuilder();
			sizeDesc.append("Specify Sudokus size. Valid are: ");
			for (int i = 0; i < GridProperties.values().length; i++) {
				if (i > 0) sizeDesc.append(", ");
				sizeDesc.append(GridProperties.values()[i].getShortName());
			}
			sizeDesc.append(".");
			Option size = new Option(null, "size", true, sizeDesc.toString());
			size.setArgName("size");

			Option clueCount = new Option("q", "clueCount", true, "Specify clue count");
			clueCount.setArgName("count");
			clueCount.setType(int.class);

			Option count = new Option("c", "count", true, "How many sudokus to generate");
			count.setArgName("count");
			count.setType(Integer.class);

			Option sudokus = new Option("s", "sudokus", true, "The sudokus file");
			sudokus.setArgName("path");

			Option network = new Option("n", "network", true, "The network file");
			network.setArgName("path");

			Option output = new Option("o", "output", true, "The output file");
			output.setArgName("path");

			Option help = new Option("h", "help", false, "Show this help");

			options
					.addOptionGroup(
							new OptionGroup()
									.addOption(new Option(null, "game", false, "Start program as game. This is default if nothing is specified"))
									.addOption(new Option(null, "generator", false, "Start program as headless generator. Specify difficulty or clues, size and output file."))
									.addOption(new Option(null, "classroom", false, "Start program as headless classroom. Specify input neural network and sudoku files and output neural network file. If no output file is specified the input neural network will be overwritten!"))
					)
					.addOption(size)
					.addOptionGroup(
							new OptionGroup()
									.addOption(new Option(null, "easy", false, "Generate Easy sudokus"))
									.addOption(new Option(null, "medium", false, "Generate Medium sudokus"))
									.addOption(new Option(null, "hard", false, "Generate Hard sudokus"))
									.addOption(new Option(null, "extreme", false, "Generate Extreme sudokus"))
									.addOption(clueCount)
					)
					.addOption(count)
					.addOption(sudokus)
					.addOption(network)
					.addOption(output)
					.addOption(help);
		}

		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			if (cmd.hasOption('h')) printHelp(options);
			else {
				if (cmd.hasOption("game")) PApplet.main("cz.krejcar25.sudoku.SudokuApplet");
				else if (cmd.hasOption("generator")) {
					if (cmd.hasOption("size")) {
						GridProperties properties = null;
						for (GridProperties testGp : GridProperties.values())
							if (testGp.getShortName().equals(cmd.getOptionValue("size"))) properties = testGp;
						if (properties == null)
							System.out.println("The size parameter could not be understood. See help for valid options.");
						else {
							int difficultyIndex = onlyOneBoolean(
									cmd.hasOption("easy"),
									cmd.hasOption("medium"),
									cmd.hasOption("hard"),
									cmd.hasOption("extreme"),
									cmd.hasOption("clueCount")
							);

							if (difficultyIndex == -2) System.out.println("Only one size parameter can be specified.");
							else if (difficultyIndex == -1) System.out.println("No size argument was specified.");
							else {
								int clueCount;
								if (difficultyIndex == 4) clueCount = Integer.parseInt(cmd.getOptionValue("clueCount"));
								else clueCount = properties.getClueCount(GridDifficulty.fromLevel(difficultyIndex));
								if (cmd.hasOption("count"))
									if (cmd.hasOption("output"))
										try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cmd.getOptionValue("output"))))) {
											int count = Integer.parseInt(cmd.getOptionValue("count"));
											for (int i = 0; i < count; i++) {
												GridCore core = new GridCore(properties);
												new SudokuGenerator(core).generate(clueCount, false);
												writer.write(core.getGridString());
												writer.newLine();
											}
										}
										catch (IOException ex) {
											ex.printStackTrace(System.out);
										}
									else System.out.println("No output path was specified.");
								else System.out.println("No count parameter was specified.");
							}
						}
					}
					else System.out.println("No size parameter was not specified. See help for usage.");
				}
				else if (cmd.hasOption("classroom")) {
					if (cmd.hasOption("sudokus")) {
						if (cmd.hasOption("network")) {
							File sudokusFile = new File(cmd.getOptionValue("sudokus"));
							File outputFile = new File(cmd.getOptionValue(cmd.hasOption("output") ? "output" : "network"));

							try {
								NeuralNetwork network = NeuralNetwork.loadFromFile(cmd.getOptionValue("network"));
								if (network != null) {
									GridCoreTrainingDataSet dataSet = new GridCoreTrainingDataSet();
									new BufferedReader(new InputStreamReader(new FileInputStream(sudokusFile))).lines().forEach(dataSet::addCore);
									NetworkTrainRunnable runnable = new NetworkTrainRunnable(network, dataSet);
									Runtime.getRuntime().addShutdownHook(new Thread(() ->
									{
										runnable.stop();
										network.saveToFile(outputFile.getAbsolutePath());
										System.out.println("Neural network successfully saved");
									}));
									runnable.setTrainCycleFinishedCallback(() -> System.out.println(String.format("Cycle %d finished, last error is %e", network.getTrainCycles(), runnable.getLastError())));
									runnable.startTrain();
								}
								else
									System.out.println("Neural network could not be loaded from the specified file. Details above.");
							}
							catch (IOException ex) {
								ex.printStackTrace(System.out);
							}
						}
						else System.out.println("No network file was specified. Seee help for usage.");
					}
					else System.out.println("No sudokus file was specified. See help for usage.");
				}
				else PApplet.main("cz.krejcar25.sudoku.SudokuApplet");
			}
		}
		catch (ParseException ex) {
			System.out.println("A ParseException occured.");
			printHelp(options);
		}
	}

	private static void printHelp(Options options) {
		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.printHelp(
				80,
				"java -jar Sudoku.jar",
				String.format(
						"%n%nUse the -generator option to geneerate sudokus. Specify size, difficulty, count and output path.%n" +
								"Use the -classroom option to train network. Specify the sudokus and network files and the optional output path.%n%n%n"),
				options,
				"",
				true);
	}

	private static int onlyOneBoolean(boolean... booleans) {
		int index = -1;
		int count = 0;
		for (int i = 0; i < booleans.length; i++)
			if (booleans[i]) {
				index = i;
				count++;
			}

		if (count == 0) return -1;
		else if (count == 1) return index;
		else return -2;
	}
}
