package cz.krejcar25.sudoku.game;

import cz.krejcar25.sudoku.SudokuApplet;
import cz.krejcar25.sudoku.ui.BaseView;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class GameView extends BaseView
{
	private final BaseGrid game;
	final GridProperties gridProperties;

	GameView(SudokuApplet sudokuApplet, GridProperties gridProperties)
	{
		super(sudokuApplet, gridProperties.getWidth(), gridProperties.getHeight());
		this.gridProperties = gridProperties;
		game = new BaseGrid(this, gridProperties);
		newGenerator();
	}

	@Override
	public void click(int mx, int my, boolean rmb)
	{
		if (overlay == null)
		{
			game.click(mx, my, parent.mouseButton == PApplet.RIGHT);
		}
		else
		{
			overlay.click(mx, my, rmb);
		}
	}

	@Override
	protected void draw()
	{
		if (game.shouldUpdateGrid) game.update();
		//image(game, game.x, game.y);

		if (overlay != null)
		{
			overlay.update();
			//image(overlay, overlay.x, overlay.y);
		}
	}

	@Override
	public void keyDown(KeyEvent keyEvent)
	{
		try
		{
			int key = Integer.parseInt(String.valueOf(keyEvent.getKey()));
			game.keyInput(key);
		}
		catch (NumberFormatException ex)
		{
			// We dont care, it may be another key...
		}
	}

	SudokuGenerator getGenerator()
	{
		return game.generator;
	}

	BaseGrid getGrid()
	{
		return game;
	}

	public GridProperties getGridProperties()
	{
		return gridProperties;
	}

	public void generate(GridDifficulty gridDifficulty, boolean async)
	{
		game.gridDifficulty = gridDifficulty;
		game.generator.generate(gridProperties.getClueCount(gridDifficulty), async);
	}

	private void newGenerator()
	{
		game.generator = new SudokuGenerator(game.getCore());
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
	public void mouseDrag(MouseEvent mouseEvent)
	{

	}

	@Override
	public void scroll(MouseEvent event)
	{

	}

}
