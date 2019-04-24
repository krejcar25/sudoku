package cz.krejcar25.sudoku.neuralNetwork;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class NeuralNetwork implements Serializable {
	public static final String FILE_TYPE = "nn";
	public static final String FILE_TYPE_DESC = "Neural Network files";
	static final long serialVersionUID = -7317338358506558555L;

	private final int inputCount;
	private final ArrayList<NeuralNetworkLayer> layers;
	private final double learningRate;
	private int outputCount;
	private int trainCycles = 0;

	@JsonCreator
	private NeuralNetwork() {
		inputCount = -1;
		layers = new ArrayList<>();
		learningRate = 0;
	}

	public NeuralNetwork(@NotNull NeuralNetworkLayer layer) {
		this.inputCount = layer.getInCount();
		this.outputCount = layer.getNodes();
		this.layers = new ArrayList<>();
		layers.add(layer);
		layer.setNetwork(this);

		this.learningRate = 0.1;
	}

	@Nullable
	public static NeuralNetwork loadFromFile(@NotNull String path) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		File file = new File(path);
		try (FileInputStream fileStream = new FileInputStream(file)) {
			try {
				ZipFile zip = new ZipFile(file);
				NeuralNetwork network = mapper.readValue(zip.getInputStream(zip.getEntry("network.json")), NeuralNetwork.class);
				for (NeuralNetworkLayer layer : network.layers) layer.setNetwork(network);
				return network;
			} catch (ZipException ex) {
				System.out.println("A ZipException occurred. Perhaps the file is in the old format and wasn't yet converted. Attempting old deserialization now...");
				ObjectInputStream stream = new ObjectInputStream(fileStream);
				NeuralNetwork neuralNetwork = (NeuralNetwork) stream.readObject();
				stream.close();
				return neuralNetwork;
			}
		} catch (FileNotFoundException ex) {
			System.out.println("The specified file could not be found...");
			return null;
		} catch (IOException ex) {
			System.out.println("An exception occurred: " + ex.getMessage());
			return null;
		} catch (ClassCastException | ClassNotFoundException ex) {
			System.out.println("This is probably not a NeuralNetwork file.");
			return null;
		}
	}

	public double[] estimate(double[] input) {
		DoubleMatrix intermediate = DoubleMatrix.fromArray(input);
		for (NeuralNetworkLayer layer : layers) {
			intermediate = layer.estimate(intermediate);
		}
		double[] output = new double[outputCount];
		for (int i = 0; i < outputCount; i++) {
			output[i] = intermediate.get(i, 0);
		}
		return output;
	}

	double train(@NotNull TrainingDataPair pair) {
		return train(pair.getInput(), pair.getDesiredOutput());
	}

	private double train(double[] input, double[] desiredOutput) {
		DoubleMatrix in = DoubleMatrix.fromArray(input);
		ArrayList<DoubleMatrix> estimates = new ArrayList<>();
		DoubleMatrix estimate = in;
		for (NeuralNetworkLayer layer : layers) {
			estimate = layer.estimate(estimate);
			estimates.add(estimate);
		}

		DoubleMatrix output = estimates.get(estimates.size() - 1);
		DoubleMatrix absMat = estimate.copy().map(((value, i, j) -> Math.abs(value)));

		DoubleMatrix errors = DoubleMatrix.fromArray(desiredOutput).sub(output);
		for (int i = estimates.size() - 1; i >= 0; i--) {
			errors = layers.get(i).train(estimates.get(i), i == 0 ? in : estimates.get(i - 1), errors);
		}

		trainCycles++;

		double totalError = 0;

		for (int i = 0; i < absMat.getRows(); i++) {
			for (int j = 0; j < absMat.getCols(); j++) {
				totalError += absMat.get(i, j);
			}
		}

		return totalError;
	}

	public void addLayer(@NotNull NeuralNetworkLayer layer) {
		NeuralNetworkLayer last = layers.get(layers.size() - 1);
		if (last.getNodes() == layer.getInCount()) {
			layers.add(layer);
			outputCount = layer.getNodes();
			layer.setNetwork(this);
		} else
			throw new IllegalArgumentException(String.format("Input node count of the added layer (%d) must match the output node count of the last added layer (%d).", layer.getInCount(), last.getNodes()));
	}

	public int getInputCount() {
		return inputCount;
	}

	public int getOutputCount() {
		return outputCount;
	}

	public ArrayList<NeuralNetworkLayer> getLayers() {
		return layers;
	}

	double getLearningRate() {
		return learningRate;
	}

	public int getTrainCycles() {
		return trainCycles;
	}

	public boolean saveToFile(@NotNull String path) {
		boolean ret = false;
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		try (ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream(new File(path)))) {
			zipFile.putNextEntry(new ZipEntry("network.json"));
			mapper.writerWithDefaultPrettyPrinter().writeValue(zipFile, this);
			// zipFile.closeEntry();
			ret = true;
		} catch (FileNotFoundException ex) {
			System.out.println("The specified file could not be found...");
		} catch (IOException ex) {
			System.out.println("An exception occurred: " + ex.getMessage());
		}
		return ret;
	}
}
