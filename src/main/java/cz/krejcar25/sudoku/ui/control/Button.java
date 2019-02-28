package cz.krejcar25.sudoku.ui.control;

import cz.krejcar25.sudoku.event.ButtonEvents;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.style.ButtonStyle;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;
import processing.core.PImage;

public class Button<O> extends Control<O>
{
	public ButtonStyle style;
	private ButtonEvents<O> click;
	private PImage content;
	private float labelSize;

	public Button(BaseView baseView, float centerX, float centerY, int width, int height, String label, ButtonEvents<O> click)
	{
		super(baseView, centerX - width / 2f, centerY - height / 2f, width, height);
		this.style = new ButtonStyle();
		setLabel(label);
		this.click = click;
	}

	public Button(BaseView baseView, float centerX, float centerY, int width, int height, PImage image, ButtonEvents<O> click)
	{
		super(baseView, centerX - width / 2f, centerY - height / 2f, width, height);
		this.style = new ButtonStyle();
		setLabel("");
		this.click = click;
		setLabel(image);
	}

	public Button(Applet applet, int centerX, int centerY, int width, int height, String label, ButtonEvents<O> click)
	{
		super(applet, centerX - width / 2f, centerY - height / 2f, width, height);
		this.style = new ButtonStyle();
		setLabel(label);
		this.click = click;
	}

	@NotNull
	@Contract("_ -> new")
	public static Button getStandardBackButton(BaseView baseView)
	{
		return new Button<>(baseView, 30, 15, 60, 30, "Back", sender -> sender.baseView.removeFromViewStack());
	}

	@Override
	protected void draw()
	{
		push();
		strokeWeight(0);
		push();
		fill(style.border.r, style.border.g, style.border.b);
		push();
		rect(0, 0, width, height);
		pop();
		if (enabled) fill(style.background.r, style.background.g, style.background.b);
		else fill(style.disabledBackground.r, style.disabledBackground.g, style.disabledBackground.b);
		rect(style.borderThickness.left, style.borderThickness.top, width - style.borderThickness.totalRl(), height - style.borderThickness.totalTb());
		pop();

		push();
		if (enabled) fill(style.foreground.r, style.foreground.g, style.foreground.b);
		else fill(style.disabledForeground.r, style.disabledForeground.g, style.disabledForeground.b);
		if (!label.isEmpty())
		{
			textSize(labelSize);
			textAlign(PApplet.CENTER, PApplet.CENTER);
			translate(width / 2f, height / 2f);
			text(label, 0, -3);
		}
		else if (content != null)
		{
			push();
			imageMode(CENTER);
			image(content, width / 2f, height / 2f);
			pop();
		}
		pop();
		pop();
	}

	@Override
	public boolean isClick(int mx, int my)
	{
		if (!enabled) return false;
		float right = (this.x + width);
		boolean hor = this.x < mx && mx < right;
		float bottom = (this.y + this.height);
		boolean ver = this.y < my && my < bottom;
		return hor && ver;
	}

	@Override
	public void click()
	{
		click.click(this);
	}

	public void setClick(ButtonEvents<O> click)
	{
		this.click = click;
	}

	@Override
	public void setLabel(String label)
	{
		float Hb = height - style.borderThickness.totalTb();
		float Wb = width - style.borderThickness.totalRl();
		textSize(Hb);
		float Wt = textWidth(label);
		if (Wt > Wb) labelSize = Wb * Hb / Wt;
		else labelSize = Hb;
		this.label = label;
	}

	public void setLabel(PImage image)
	{
		content = image.copy();
		int Hb = (int) (height - style.borderThickness.totalTb());
		int Wb = (int) (width - style.borderThickness.totalRl());
		if (Wb > Hb) content.resize(0, Hb);
		else content.resize(Wb, 0);
	}
}

