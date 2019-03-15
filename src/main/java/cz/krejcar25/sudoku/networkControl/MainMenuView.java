package cz.krejcar25.sudoku.networkControl;

import com.sun.tools.javac.util.Pair;
import cz.krejcar25.sudoku.FileChooserFactory;
import cz.krejcar25.sudoku.event.ButtonEvents;
import cz.krejcar25.sudoku.game.GridCore;
import cz.krejcar25.sudoku.game.GridProperties;
import cz.krejcar25.sudoku.game.SudokuGenerator;
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

import java.util.ArrayList;

public class MainMenuView extends BaseView
{
	private final ArrayList<Control> controls;
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

		ArrayList<Pair<String, ButtonEvents<Object>>> buttonEvents = new ArrayList<>();

		buttonEvents.add(new Pair<>("XOR Test", sender -> viewStack.push(new NetworkLearningSimulationView(sender.getApplet(), NetworkLearningSimulatorScenario.XOR, sender.getApplet().isKeyPressed(PConstants.SHIFT)))));
		buttonEvents.add(new Pair<>("OR Test", sender -> viewStack.push(new NetworkLearningSimulationView(sender.getApplet(), NetworkLearningSimulatorScenario.OR, sender.getApplet().isKeyPressed(PConstants.SHIFT)))));
		buttonEvents.add(new Pair<>("RGB Test", sender -> viewStack.push(new NetworkLearningSimulationView(sender.getApplet(), NetworkLearningSimulatorScenario.RGB, sender.getApplet().isKeyPressed(PConstants.SHIFT)))));
		buttonEvents.add(new Pair<>("Create network", sender -> new NetworkCreationDialog(getApplet(), DeepLayer.class)));
		buttonEvents.add(new Pair<>("Generate sudokus", sender -> new GeneratorSelectionDialog(((gridProperties, clueCount, count) -> viewStack.push(new GenerateSudokuStringView(applet, gridProperties, clueCount, count)))).setVisible(true)));
		buttonEvents.add(new Pair<>("Train network", sender -> viewStack.push(new NetworkTrainingView(sender.getApplet()))));
		buttonEvents.add(new Pair<>("Test network", button1 -> testNetwork()));
		buttonEvents.add(new Pair<>("Open network file in viewer", sender1 -> openNetworkFileInViewer()));
		buttonEvents.add(new Pair<>("Open sudokus file in browser", sender ->
		{
			SudokuBrowserView view = new SudokuBrowserView(sender.getApplet());
			if (!view.shouldLeave) viewStack.push(view);
		}));

		for (Pair<String, ButtonEvents<Object>> buttonEvent : buttonEvents)
		{
			int bx = (2 * (index % 2) + 1) * width / 4;
			int by = baseY + ((index / 2) * yDelta);
			Button button = new Button<>(this, bx, by, bsx, bsy, buttonEvent.fst, buttonEvent.snd);
			controls.add(button);
			index++;
		}

		controls.add(Button.getStandardBackButton(this));
	}

	private void testNetwork()
	{
		String extension = NeuralNetwork.FILE_TYPE;
		new FileChooserFactory()
				.addFileType(NeuralNetwork.FILE_TYPE_DESC, extension)
				.setAllowAll(false)
				.setOkAction(file ->
				{
					NeuralNetwork network = NeuralNetwork.loadFromFile(file.getAbsolutePath());
					if (network == null) return;

					GridProperties properties = null;
					for (GridProperties gp : GridProperties.values())
						if (Math.pow(gp.getSizea() * gp.getSizeb(), 3) == network.getInputCount()) properties = gp;
					if (properties == null) return;

					GridCore testCore = new GridCore(properties);
					int clueCount = 23;//properties.getClueCount(GridDifficulty.Hard);
					new SudokuGenerator(testCore).generate(clueCount, false);
					viewStack.push(new NetworkEstimateView(getApplet(), testCore, network));
				})
				.setMode(FileChooserFactory.OPEN)
				.show();
	}

	private void openNetworkFileInViewer()
	{
		String extension = NeuralNetwork.FILE_TYPE;
		new FileChooserFactory()
				.addFileType(NeuralNetwork.FILE_TYPE_DESC, extension)
				.setAllowAll(false)
				.setOkAction(file -> viewStack.push(new NetworkChartView(getApplet(), NeuralNetwork.loadFromFile(file.getAbsolutePath()), true)))
				.setMode(FileChooserFactory.OPEN)
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
			//image(button, button.x, button.y);
		}

		if (overlay != null)
		{
			overlay.update();
			//image(overlay, overlay.x, overlay.y);
		}
		pop();
	}
}
