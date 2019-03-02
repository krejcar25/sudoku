package cz.krejcar25.sudoku.neuralNetwork;

public class NetworkTrainRunnable implements Runnable
{
	private volatile boolean runAllowed;
	private volatile boolean shouldPause;
	private volatile boolean isPaused;
	private volatile boolean running;
	private final NeuralNetwork network;
	private Thread thread;
	private final TrainingDataSet trainingData;
	private volatile double lastError;

	public NetworkTrainRunnable(NeuralNetwork network, TrainingDataSet trainingData)
	{
		this.runAllowed = false;
		this.shouldPause = false;
		this.isPaused = false;
		this.running = false;
		this.network = network;
		this.trainingData = trainingData;

		if (trainingData.checkDataDimensions(network.getInputCount(), network.getOutputCount()))
			throw new IllegalArgumentException(
					String.format("The input data's length and output data's length must match the network input (%d) and output (%d) node count respectively.",
							network.getInputCount(),
							network.getOutputCount()
					)
			);

		this.thread = new Thread(this);
		this.thread.setName("NeuralNetwork-TrainThread");
	}

	@Override
	public void run()
	{
		this.runAllowed = true;
		this.shouldPause = false;
		this.isPaused = false;
		this.running = true;

		while (runAllowed)
		{
			if (shouldPause)
			{
				isPaused = true;
				running = false;
				while (shouldPause) Thread.yield();
				isPaused = false;
				running = true;
			}
			else
			{
				lastError = network.train(trainingData.getRandomPair());
			}
		}

		this.isPaused = false;
		this.running = false;
	}

	public void startTrain()
	{
		thread.start();
	}

	public boolean isPaused()
	{
		return isPaused;
	}

	public boolean pause()
	{
		boolean pausedBefore = isPaused;
		if (runAllowed) shouldPause = true;
		else return false;
		while (!isPaused) Thread.yield();
		return !pausedBefore;
	}

	public void resume()
	{
		if (runAllowed) shouldPause = false;
	}

	public void stop()
	{
		this.runAllowed = false;
		this.shouldPause = false;
		while (running) Thread.yield();
	}

	public double getLastError()
	{
		return lastError;
	}
}
