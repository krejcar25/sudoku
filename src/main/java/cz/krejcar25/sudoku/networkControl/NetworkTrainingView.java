package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.FileChooserFactory;
import cz.krejcar25.sudoku.event.ButtonEvents;
import cz.krejcar25.sudoku.game.GridCore;
import cz.krejcar25.sudoku.game.GridProperties;
import cz.krejcar25.sudoku.game.SudokuGenerator;
import cz.krejcar25.sudoku.neuralNetwork.NetworkTrainRunnable;
import cz.krejcar25.sudoku.neuralNetwork.NeuralNetwork;
import cz.krejcar25.sudoku.ui.*;
import cz.krejcar25.sudoku.ui.control.Button;
import cz.krejcar25.sudoku.ui.control.Control;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.io.*;
import java.util.ArrayList;

public class NetworkTrainingView extends BaseView
{
	private Stage stage;
	private final ArrayList<Control> controls;
	private final Button<Stage> currentButton;
	private final GridCoreTrainingDataSet dataSet;
	private NeuralNetwork network;
	private NetworkTrainRunnable trainRunnable;
	private Clock trainingClock;
	private Button pauseButton;
	private Button progressDemoButton;
	private final int buttonMargin = 75;

	NetworkTrainingView(Applet applet)
	{
		super(applet, 800, 800);
		stage = Stage.SelectSudokus;
		controls = new ArrayList<>();
		controls.add(Button.getStandardBackButton(this));
		dataSet = new GridCoreTrainingDataSet();
		currentButton = new Button<>(this, width / 2f, 600, width - 2 * buttonMargin, 2 * buttonMargin, "Select a Sudokus file", sender -> openDataSetFile());
	}

	private void openDataSetFile()
	{
		String extension = GridCore.FILE_TYPE;
		new FileChooserFactory()
				.addFileType(GridCore.FILE_TYPE_DESC, extension)
				.setAllowAll(false)
				.setOkAction(file -> {
					try (FileInputStream in = new FileInputStream(file))
					{
						BufferedReader reader = new BufferedReader(new InputStreamReader(in));
						reader.lines().forEach(line -> dataSet.addCore(line));
						stage = Stage.SelectNetwork;
						currentButton.setLabel("Select a Neural Network file");
						currentButton.setClick(sender -> openNetworkFile());
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				})
				.setMode(FileChooserFactory.OPEN)
				.show();
	}

	private void openNetworkFile()
	{
		String extension = NeuralNetwork.FILE_TYPE;
		new FileChooserFactory()
				.addFileType(NeuralNetwork.FILE_TYPE_DESC, extension)
				.setAllowAll(false)
				.setOkAction(file -> {
					network = NeuralNetwork.loadFromFile(file.getAbsolutePath());
					if (network != null)
					{
						if (dataSet.checkDataDimensions(network.getInputCount(), network.getOutputCount()))
						{
							int width = 700;
							int height = 200;
							overlay = new BaseOverlay(this, ((this.width) - width) / 2f, ((this.height) - height) / 2f, width, height, OverlayType.OK, (ButtonEvents) sender1 -> removeFromViewStack())
							{

								@Override
								public void click(int mx, int my, boolean rmb)
								{
									for (Button button : buttons) if (button.isClick(mx - this.x, my - this.y)) button.click();
								}

								@Override
								protected void draw()
								{
									background(51);
									push();
									fill(200, 210, 200);
									rect(10, 10, width - 20, height - 20);
									pop();
									textSize(40);
									textAlign(PApplet.CENTER, PApplet.CENTER);
									text("The network has wrong dimensions for this dataset", width / 2f, 60);
									textSize(20);
									textAlign(CENTER, TOP);
									text("You will be returned to the Main Menu", width / 2f, 90);
									drawButtons();
								}
							};
						}
						stage = Stage.StartTraining;
						currentButton.setLabel("Begin the training process");
						currentButton.setClick(sender -> startTraining());
					}
				})
				.setMode(FileChooserFactory.OPEN)
				.show();
	}

	private void startTraining()
	{
		stage = Stage.Training;
		this.trainRunnable = new NetworkTrainRunnable(this.network, this.dataSet);
		this.trainRunnable.startTrain();
		this.currentButton.setLabel("Finish training");
		this.currentButton.setClick(sender -> finishTraining());
		this.trainingClock = new Clock(getApplet(), 0, 0, "Training time");
		this.trainingClock.setDisplayWidthWithHeight(80);
		this.trainingClock.x = width - this.trainingClock.getDisplayWidth();
		this.trainingClock.start();
		this.pauseButton = new Button<>(this, (width - 2 * buttonMargin) / 4f + buttonMargin, currentButton.y - buttonMargin, (width - 2 * buttonMargin) / 2, 2 * buttonMargin, "Pause", (Button<Stage> button) -> pauseButton_click());
		this.progressDemoButton = new Button<>(this, 3 * (width - 2 * buttonMargin) / 4f + buttonMargin, currentButton.y - buttonMargin, (width - 2 * buttonMargin) / 2, 2 * buttonMargin, "Current", (Button<Stage> button) -> progressDemoButton_click());
		this.controls.add(this.pauseButton);
	}

	private void pauseButton_click()
	{
		if (trainRunnable.isPaused())
		{
			this.pauseButton.setLabel("Pause");
			this.controls.remove(progressDemoButton);
			this.trainRunnable.resume();
			this.trainingClock.start();
		}
		else
		{
			this.trainingClock.pause();
			this.trainRunnable.pause();
			this.pauseButton.setLabel("Resume");
			this.controls.add(progressDemoButton);
		}
	}

	private void progressDemoButton_click() throws IllegalArgumentException
	{
		GridCore training = this.dataSet.getRandomPair();
		GridProperties properties = training.gridProperties;
		GridCore testCore = new GridCore(properties);
		new SudokuGenerator(testCore).generate(training.getClueCount(), false);
		viewStack.push(new NetworkEstimateView(getApplet(), testCore, network));
	}

	private void finishTraining()
	{
		this.trainRunnable.stop();
		this.trainingClock.stop();
		this.stage = Stage.TrainingFinished;
		this.currentButton.setLabel("Save trained network");
		this.currentButton.setClick(sender -> saveNetwork());
	}

	private void saveNetwork()
	{
		String extension = NeuralNetwork.FILE_TYPE;
		new FileChooserFactory()
				.addFileType(NeuralNetwork.FILE_TYPE_DESC, extension)
				.setAllowAll(false)
				.setOkAction(file -> {
					String path = file.getAbsolutePath();
					if (!path.endsWith(extension))
					{
						file = new File(path + "." + extension);
					}
					if (this.network.saveToFile(file.getAbsolutePath())) removeFromViewStack();
				})
				.setMode(FileChooserFactory.SAVE)
				.show();
	}

	@Override
	public void mouseDown(int mx, int my, boolean rmb)
	{

	}

	@Override
	public void mouseUp(int mx, int my, boolean rmb)
	{

	}

	@Override
	public void click(int mx, int my, boolean rmb)
	{
		if (overlay != null) overlay.click(mx, my, rmb);
		if (currentButton != null && currentButton.isClick(mx, my)) currentButton.click();
		else
			for (int i = controls.size() - 1; i >= 0; i--) if (controls.get(i).isClick(mx, my)) controls.get(i).click();
	}

	@Override
	public void mouseDrag(MouseEvent mouseEvent)
	{

	}

	@Override
	public void scroll(MouseEvent event)
	{

	}

	@Override
	public void keyDown(KeyEvent keyEvent)
	{

	}

	@Override
	protected void draw()
	{
		background(220);

		textAlign(CENTER, CENTER);
		rectMode(CENTER);
		fill(51);
		strokeWeight(0);

		if (stage == Stage.Training)
		{
			textSize(40);
			text(String.format("Training iterations: %d", network.getTrainCycles()), width / 2f, 200, 700, 100);
			text(String.format("Loss: %e", trainRunnable.getLastError()), width / 2f, 250, 700, 100);
			trainingClock.update();
			image(trainingClock, trainingClock.x, trainingClock.y, trainingClock.getDisplayWidth(), trainingClock.getDisplayHeight());

			push();
			textAlign(LEFT, TOP);
			fill(255, 0, 0);
			text(getApplet().frameCount, 0, 0);
			pop();
		}

		if (currentButton != null)
		{
			currentButton.update();
			//image(currentButton, currentButton.x, currentButton.y);
		}

		for (Control control : controls)
		{
			control.update();
			//image(control, control.x, control.y);
		}

		if (overlay != null)
		{
			overlay.update();
			//image(overlay, overlay.x, overlay.y);
		}
	}

	private enum Stage
	{
		SelectSudokus, SelectNetwork, StartTraining, Training, TrainingFinished
	}
}
