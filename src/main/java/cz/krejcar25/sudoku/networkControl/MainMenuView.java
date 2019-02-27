package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.event.ButtonEvents;
import cz.krejcar25.sudoku.game.BaseGenerator;
import cz.krejcar25.sudoku.game.GridCore;
import cz.krejcar25.sudoku.game.GridDifficulty;
import cz.krejcar25.sudoku.game.GridProperties;
import cz.krejcar25.sudoku.neuralNetwork.DeepLayer;
import cz.krejcar25.sudoku.neuralNetwork.NeuralNetwork;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.control.Button;
import cz.krejcar25.sudoku.ui.control.Control;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class MainMenuView extends BaseView
{
	ArrayList<Control> controls;
	private int rVal;

	public MainMenuView(Applet applet)
	{
		super(applet, 800, 600);
		controls = new ArrayList<>();

		int bsx = 340;
		int bsy = 40;
		int baseY = 200;
		int yDelta = 80;
		int index = 0;

		String[] labels = {"XOR Test", "OR Test", "RGB Test", "Create network", "Generate sudokus", "Train network", "Test network", "Open network file in viewer", "Open sudokus file in browser"};
		ButtonEvents[] buttonEvents = {
				sender -> viewStack.push(new NetworkLearningSimulationView(sender.getApplet(), NetworkLearningSimulatorScenario.XOR, sender.getApplet().isKeyPressed(PConstants.SHIFT))),
				sender -> viewStack.push(new NetworkLearningSimulationView(sender.getApplet(), NetworkLearningSimulatorScenario.OR, sender.getApplet().isKeyPressed(PConstants.SHIFT))),
				sender -> viewStack.push(new NetworkLearningSimulationView(sender.getApplet(), NetworkLearningSimulatorScenario.RGB, sender.getApplet().isKeyPressed(PConstants.SHIFT))),
				sender -> new NetworkCreationDialog(getApplet(), DeepLayer.class),
				sender -> new GeneratorSelectionDialog(((gridProperties, gridDifficulty, count) -> viewStack.push(new GenerateSudokuStringView(applet, gridProperties, gridDifficulty, count)))).setVisible(true),
				sender -> viewStack.push(new NetworkTrainingView(sender.getApplet())),
				this::testNetwork,
				this::openNetworkFileInViewer,
				sender ->
				{
					SudokuBrowserView view = new SudokuBrowserView(sender.getApplet());
					if (!view.shouldLeave) viewStack.push(view);
				}
		};

		for (int i = 0; i < labels.length && labels.length == buttonEvents.length; i++)
		{
			int bx = (2 * (index % 2) + 1) * width / 4;
			int by = baseY + ((index / 2) * yDelta);
			Button button = new Button<>(this, bx, by, bsx, bsy, labels[i], buttonEvents[i]);
			controls.add(button);
			index++;
		}
	}

	private void testNetwork(Button button)
	{
		JFileChooser c = new JFileChooser();
		String extension = NeuralNetwork.FILETYPE;
		c.addChoosableFileFilter(new FileNameExtensionFilter(NeuralNetwork.FILETYPE_DESC, extension));
		c.setAcceptAllFileFilterUsed(false);
		try
		{
			EventQueue.invokeAndWait(() -> rVal = c.showOpenDialog(null));
		}
		catch (InterruptedException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
		if (rVal == JFileChooser.APPROVE_OPTION)
		{
			File file = c.getSelectedFile();
			NeuralNetwork network = NeuralNetwork.loadFromFile(file.getAbsolutePath());
			if (network == null) return;

			GridProperties properties = null;
			for (GridProperties gp : GridProperties.values())
				if (Math.pow(gp.getSizea() * gp.getSizeb(), 3) == network.getInputCount()) properties = gp;
			if (properties == null) return;

			GridCore testCore = new GridCore(properties);
			new BaseGenerator(testCore).generate(properties.getClueCount(GridDifficulty.Hard), false);
			viewStack.push(new NetworkEstimateView(getApplet(), testCore, network));
		}
	}

	private void openNetworkFileInViewer(Button sender)
	{
		JFileChooser c = new JFileChooser();
		String extension = NeuralNetwork.FILETYPE;
		c.addChoosableFileFilter(new FileNameExtensionFilter(NeuralNetwork.FILETYPE_DESC, extension));
		c.setAcceptAllFileFilterUsed(false);
		try
		{
			EventQueue.invokeAndWait(() -> rVal = c.showOpenDialog(null));
		}
		catch (InterruptedException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
		if (rVal == JFileChooser.APPROVE_OPTION)
		{
			File file = c.getSelectedFile();
			viewStack.push(new NetworkChartView(getApplet(), NeuralNetwork.loadFromFile(file.getAbsolutePath()), true));
		}
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
		if (!rmb)
		{
			for (Control control : controls) if (control.isClick(mx - x, my - y)) control.click();
		}
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
	public void keyUp(KeyEvent keyEvent)
	{

	}

	@Override
	protected void draw()
	{
		push();
		background(220);
		textSize(40);
		fill(51);
		textAlign(PApplet.CENTER, PApplet.CENTER);
		text("Network Control", 400, 100);

		for (Control button : controls)
		{
			button.update();
			image(button, button.x, button.y);
		}

		if (overlay != null)
		{
			overlay.update();
			image(overlay, overlay.x, overlay.y);
		}
		pop();
	}
}
