package cz.krejcar25.sudoku;

import com.sun.tools.javac.util.Pair;
import cz.krejcar25.sudoku.event.ButtonEvents;
import cz.krejcar25.sudoku.game.DifficultySelectView;
import cz.krejcar25.sudoku.game.GridProperties;
import cz.krejcar25.sudoku.scoreboard.ScoreboardSizeSelectView;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.control.Button;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class MainMenuView extends BaseView
{
	private final ArrayList<Button<?>> buttons;

	MainMenuView(SudokuApplet applet)
	{
		super(applet, 800, 600);
		buttons = new ArrayList<>();
		int bsx = 340;
		int bsy = 40;
		int baseY = 200;
		int yDelta = 80;
		int index = 0;
		for (GridProperties gridProperties : GridProperties.values())
		{
			int bx = (2 * (index % 2) + 1) * width / 4;
			int by = baseY + ((index / 2) * yDelta);
			Button<Object> button = new Button<>(this, bx, by, bsx, bsy, gridProperties.getName(), sender -> viewStack.push(new DifficultySelectView(getRootApplet(), gridProperties)));
			buttons.add(button);
			index++;
		}

		ArrayList<Pair<String, ButtonEvents<Object>>> buttonEvents = new ArrayList<>();
		buttonEvents.add(new Pair<>("Settings", sender -> showSettingsView()));
		buttonEvents.add(new Pair<>("Scoreboard", sender -> showScoreboardSizeSelectView()));
		buttonEvents.add(new Pair<>("Help", sender -> showHelpView()));
		buttonEvents.add(new Pair<>("Network Control", sender -> showNetworkControl()));
		for (Pair<String, ButtonEvents<Object>> buttonEvent : buttonEvents)
		{
			int bx = (2 * (index % 2) + 1) * width / 4;
			int by = baseY + ((index / 2) * yDelta);
			Button<Object> button = new Button<>(this, bx, by, bsx, bsy, buttonEvent.fst, buttonEvent.snd);
			buttons.add(button);
			index++;
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
	protected void draw()
	{
		push();
		background(220);
		textSize(40);
		fill(51);
		textAlign(PApplet.CENTER, PApplet.CENTER);
		text("Main menu", 400, 100);

		for (Button button : buttons)
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

	@Override
	public void click(int mx, int my, boolean rmb)
	{
		if (!rmb)
		{
			for (Button button : buttons) if (button.isClick(mx - x, my - y)) button.click();
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
		if (keyEvent.getKeyCode() == ESC) getApplet().exit();
	}

	private void showSettingsView()
	{
		viewStack.push(new SettingsView(parent));
	}

	private void showScoreboardSizeSelectView()
	{
		viewStack.push(new ScoreboardSizeSelectView(getApplet()));
	}

	private void showHelpView()
	{
		viewStack.push(new HelpView(getApplet()));
	}

	private void showNetworkControl()
	{
		viewStack.push(new cz.krejcar25.sudoku.networkControl.MainMenuView(parent));
	}
}
