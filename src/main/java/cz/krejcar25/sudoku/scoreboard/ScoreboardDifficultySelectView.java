package cz.krejcar25.sudoku.scoreboard;

import cz.krejcar25.sudoku.game.GridDifficulty;
import cz.krejcar25.sudoku.game.GridProperties;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.control.Button;
import cz.krejcar25.sudoku.ui.control.Control;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class ScoreboardDifficultySelectView extends BaseView
{
	private final GridProperties gridProperties;
	private final ArrayList<Control> controls;

	ScoreboardDifficultySelectView(Applet sudokuApplet, GridProperties gridProperties)
	{
		super(sudokuApplet, 800, 600);
		this.gridProperties = gridProperties;
		this.controls = new ArrayList<>();

		int bsx = 340;
		int bsy = 40;
		controls.add(new Button<>(this, width / 4f, 200, bsx, bsy, "Easy", sender -> viewStack.push(new ScoreboardDisplayView(sudokuApplet, gridProperties, GridDifficulty.Easy))));
		controls.add(new Button<>(this, 3 * width / 4f, 200, bsx, bsy, "Medium", sender -> viewStack.push(new ScoreboardDisplayView(sudokuApplet, gridProperties, GridDifficulty.Medium))));
		controls.add(new Button<>(this, width / 4f, 280, bsx, bsy, "Hard", sender -> viewStack.push(new ScoreboardDisplayView(sudokuApplet, gridProperties, GridDifficulty.Hard))));
		controls.add(new Button<>(this, 3 * width / 4f, 280, bsx, bsy, "Extreme", sender -> viewStack.push(new ScoreboardDisplayView(sudokuApplet, gridProperties, GridDifficulty.Extreme))));
		controls.add(Button.getStandardBackButton(this));

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
		if (rmb) removeFromViewStack();
		else for (Control control : controls)
		{
			if (control.isClick(mx, my)) control.click();
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
		textAlign(CENTER, CENTER);
		text("Display results from \r\n" + gridProperties.getName() + " of difficulty:", 400, 100);

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
		pop();
	}
}
