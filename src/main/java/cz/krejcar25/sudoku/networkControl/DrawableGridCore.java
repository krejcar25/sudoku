package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.game.GridCore;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.Drawable;
import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;

public class DrawableGridCore extends Drawable
{
	private final GridCore core;
	private final GridCore solvedCore;
	private final float sx;
	private final float sy;
	private boolean showSolved;

	DrawableGridCore(Applet applet, float x, float y, int width, int height, @NotNull GridCore core)
	{
		this(applet, x, y, width, height, core, null);
	}

	DrawableGridCore(Applet applet, float x, float y, int width, int height, @NotNull GridCore core, GridCore solvedCore)
	{
		super(applet, x, y, width, height);
		this.core = core;
		this.solvedCore = solvedCore;
		this.sx = (float) width / core.ncr;
		this.sy = (float) height / core.ncr;
		this.showSolved = false;
	}

	void setShowSolved(boolean showSolved)
	{
		this.showSolved = showSolved;
	}

	boolean isShowingSolved()
	{
		return this.showSolved;
	}

	public GridCore getCore()
	{
		return core;
	}

	@Override
	protected void draw()
	{
		for (int y = 0; y < core.ncr; y++)
		{
			for (int x = 0; x < core.ncr; x++)
			{
				push();
				stroke(51);
				fill(core.isBaseGame(x, y) ? 220 : 255);

				rect(x * sx, y * sy, sx, sy);
				pop();


				int thisNum = showSolved ? core.getSolved(x, y) : core.get(x, y);
				if (thisNum > -1)
				{
					push();
					if (core.isBaseGame(x, y)) fill(51);
					else if (solvedCore != null)
					{
						if (core.get(x, y) == solvedCore.getSolved(x, y)) fill(0, 255, 0);
						else fill(255, 0, 0);
					}
					else fill(0, 0, 255);

					translate(x * sx, y * sy);
					translate(sx / 2f, sy / 2f);
					translate(0, -7);
					strokeWeight(0);
					textAlign(PApplet.CENTER, PApplet.CENTER);
					textSize(sy);
					text(thisNum + core.gridProperties.getDrawNumberOffset(), 0, 0);
					pop();
				}

			}
		}

		push();
		stroke(0);
		strokeWeight(3);
		for (int i = 1; i < core.sizeb; i++)
		{
			int linex = Applet.floor(i * core.sizea * sx);
			line(linex, 0, linex, height);
		}

		for (int i = 1; i <= core.sizea; i++)
		{
			int liney = Applet.floor(i * core.sizeb * sy);
			line(0, liney, width, liney);
		}
		pop();
	}
}
