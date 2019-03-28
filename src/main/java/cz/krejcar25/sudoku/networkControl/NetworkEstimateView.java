package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.event.ToggleEvents;
import cz.krejcar25.sudoku.game.GridCore;
import cz.krejcar25.sudoku.neuralNetwork.NeuralNetwork;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.control.Button;
import cz.krejcar25.sudoku.ui.control.Control;
import cz.krejcar25.sudoku.ui.control.ControlLabel;
import cz.krejcar25.sudoku.ui.control.Toggle;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetworkEstimateView extends BaseView {
	private static final int gridWidth = 800;
	private static final int gridHeight = 800;
	private static final int topBarHeight = 50;
	private static final int bottomBarHeight = 50;
	private final DrawableGridCore base;
	private final ArrayList<DrawableGridCore> guesses;
	private final ArrayList<Control> controls;
	private final Map<NavigationDirection, Button<NavigationDirection>> navigationButtons;
	private int guessIndex;

	NetworkEstimateView(Applet applet, GridCore base, NeuralNetwork network) {
		super(applet, 2 * gridWidth, topBarHeight + gridHeight + bottomBarHeight);
		this.base = new DrawableGridCore(applet, 0, topBarHeight, gridWidth, gridHeight, base);
		this.guesses = new ArrayList<>();
		this.controls = new ArrayList<>();
		this.navigationButtons = new HashMap<>();

		this.guesses.add(new DrawableGridCore(applet, gridWidth, topBarHeight, gridWidth, gridHeight, base, base));
		this.guessIndex = 0;

		float bc = NavigationDirection.values().length; // button count
		int bs = bottomBarHeight - 5; // button size
		float bxs = gridWidth / bc; // single button x space
		float byb = gridHeight + topBarHeight; // button y base
		int bi = 0; // button index

		for (NavigationDirection direction : NavigationDirection.values()) {
			float bx = (float) gridWidth + bi * bxs + bxs / 2f;
			float by = byb + bottomBarHeight / 2f;
			try {
				InputStream resourceStream = NetworkEstimateView.class.getResourceAsStream(String.format("%s.png", direction.getImageName()));
				BufferedImage image = ImageIO.read(resourceStream);
				PImage icon = new PImage(image);
				Button<NavigationDirection> b = new Button<>(this, bx, by, bs, bs, icon, this::navigationButtons_click);
				b.userObject = direction;
				navigationButtons.put(direction, b);
				controls.add(b);
				bi++;
			}
			catch (Exception ex) {
				Applet.println("Can't create button: " + ex.getMessage());
			}
		}

		GridCore intermediate = base.copy();

		while (!intermediate.isFinished()) {
			NetworkOutputStore store = new NetworkOutputStore(network.estimate(intermediate.getInput()));
			int placeIndex = 0;
			NetworkOutputStore.GridCell top;
			boolean canPlaceNumber;
			boolean isEmpty;
			top = store.getTop(placeIndex);
			canPlaceNumber = intermediate.canPlaceNumber(top.n, top.x, top.y, -1);
			isEmpty = intermediate.get(top.x, top.y) == -1;
			placeIndex++;

			while ((!canPlaceNumber || !isEmpty) && placeIndex < store.size()) {
				top = store.getTop(placeIndex);
				canPlaceNumber = intermediate.canPlaceNumber(top.n, top.x, top.y, -1);
				isEmpty = intermediate.get(top.x, top.y) == -1;
				placeIndex++;
			}

			if (placeIndex == store.size()) break;
			intermediate.set(top.x, top.y, top.n);
			DrawableGridCore newGuess = new DrawableGridCore(getApplet(), gridWidth, topBarHeight, gridWidth, gridHeight, intermediate.copy(), base);
			this.guesses.add(newGuess);
		}

		int solvedToggleSize = bottomBarHeight;
		ControlLabel solvedToggleLabel = new ControlLabel(new Toggle(this, (gridWidth - solvedToggleSize) / 2, topBarHeight + gridHeight, 2 * solvedToggleSize, solvedToggleSize, new ToggleEvents() {
			@Override
			public void toggled(Control sender) {

			}

			@Override
			public void switchedOn(Control sender) {
				((NetworkEstimateView) sender.getBaseView()).base.setShowSolved(true);
			}

			@Override
			public void switchedOff(Control sender) {
				((NetworkEstimateView) sender.getBaseView()).base.setShowSolved(false);
			}
		}), ControlLabel.CONTROL_LEFT, "Show solved grid");
		solvedToggleLabel.centerOnX(gridWidth / 2);
		this.controls.add(solvedToggleLabel);

		this.controls.add(Button.getStandardBackButton(this));
	}

	private void navigationButtons_click(@NotNull Button<NavigationDirection> sender) {
		navigate(sender.userObject);
	}

	private void navigate(@NotNull NavigationDirection direction) {
		switch (direction) {
			case First:
				this.guessIndex = 0;
				this.navigationButtons.get(NavigationDirection.Previous).setEnabled(false);
				this.navigationButtons.get(NavigationDirection.Next).setEnabled(true);
				break;
			case Previous:
				if (guessIndex > 0) {
					if (--this.guessIndex <= 0)
						this.navigationButtons.get(NavigationDirection.Previous).setEnabled(false);
					this.navigationButtons.get(NavigationDirection.Next).setEnabled(true);
				}
				break;
			case Next:
				if (this.guessIndex + 1 < guesses.size()) {
					if (++this.guessIndex + 1 >= guesses.size())
						this.navigationButtons.get(NavigationDirection.Next).setEnabled(false);
					this.navigationButtons.get(NavigationDirection.Previous).setEnabled(true);
				}
				break;
			case Last:
				this.guessIndex = guesses.size() - 1;
				this.navigationButtons.get(NavigationDirection.Next).setEnabled(false);
				this.navigationButtons.get(NavigationDirection.Previous).setEnabled(true);
				break;
			default:
				assert false;
		}
	}

	@Override
	public void mouseDown(int mx, int my, boolean rmb) {

	}

	@Override
	public void mouseUp(int mx, int my, boolean rmb) {

	}

	@Override
	public void click(int mx, int my, boolean rmb) {
		if (!rmb) for (Control control : controls) if (control.isClick(mx, my)) control.click();
	}

	@Override
	public void mouseDrag(MouseEvent mouseEvent) {

	}

	@Override
	public void scroll(MouseEvent event) {

	}

	@Override
	public void keyDown(KeyEvent keyEvent) {

	}

	@Override
	protected void draw() {
		background(51);
		base.update();

		DrawableGridCore guess = guesses.get(guessIndex);
		guess.update();

		push();
		fill(255, 0, 0);
		noStroke();
		textAlign(RIGHT, TOP);
		textSize(topBarHeight);
		text(getApplet().frameRate, width, 0);
		pop();

		push();
		stroke(0);
		strokeWeight(5);
		line(gridWidth, topBarHeight, gridWidth, topBarHeight + gridHeight);
		pop();

		for (Control control : controls) {
			control.update();
		}
	}

	private enum NavigationDirection {
		First("rw"), Previous("prev"), Next("next"), Last("ff");

		private final String imageName;

		NavigationDirection(String imageName) {
			this.imageName = imageName;
		}

		@Contract(pure = true)
		String getImageName() {
			return imageName;
		}
	}

	private static class NetworkOutputStore {
		private ArrayList<GridCell> cells;

		NetworkOutputStore(@NotNull double[] doubles) {
			double cbrt = Math.cbrt(doubles.length);
			if (cbrt == Math.floor(cbrt) && !Double.isInfinite(cbrt)) {
				this.cells = new ArrayList<>();
				int ncr = (int) cbrt;

				for (int x = 0; x < ncr; x++)
					for (int y = 0; y < ncr; y++)
						for (int n = 0; n < ncr; n++)
							this.cells.add(new GridCell(x, y, n, doubles[n + ncr * (y + ncr * x)]));
				cells.sort(((o1, o2) -> (int) Math.signum(o1.value - o2.value)));

			}
			else throw new IllegalArgumentException("The size of the doubles array is not an integer cube.");
		}

		GridCell getTop(int offset) {
			return cells.get(offset);
		}

		int size() {
			return cells.size();
		}

		private class GridCell {
			private final int x, y, n;
			private final double value;

			GridCell(int x, int y, int n, double value) {
				this.x = x;
				this.y = y;
				this.n = n;
				this.value = value;
			}
		}
	}
}
