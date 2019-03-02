package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.game.GridCore;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.control.Button;
import cz.krejcar25.sudoku.ui.control.Control;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SudokuBrowserView extends BaseView
{
	private static final Logger log = Logger.getLogger(SudokuBrowserView.class.getName());
	private ArrayList<String> cores;
	private ArrayList<Control> controls;
	private DrawableGridCore core;
	private int coreIndex = 0;
	private int rVal;

	public final boolean shouldLeave;

	public SudokuBrowserView(Applet applet)
	{
		super(applet, 800, 800);
		cores = new ArrayList<>();
		controls = new ArrayList<>();
		controls.add(Button.getStandardBackButton(this));

		JFileChooser c = new JFileChooser();
		String extension = GridCore.FILETYPE;
		c.addChoosableFileFilter(new FileNameExtensionFilter(GridCore.FILETYPE_DESC, extension));
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
			try (FileInputStream in = new FileInputStream(file))
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				reader.lines().forEach(line -> cores.add(line));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			core = new DrawableGridCore(getApplet(), 0, 0, width, height, GridCore.fromGridString(cores.get(coreIndex)));
			core.update();
			shouldLeave = false;
		}
		else shouldLeave = true;
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
		if (!rmb) for (Control control : controls) if (control.isClick(mx, my)) control.click();
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
		boolean changed = false;
		if (keyEvent.getKeyCode() == LEFT && coreIndex > 0)
		{
			coreIndex--;
			changed = true;
		}
		else if (keyEvent.getKeyCode() == RIGHT && coreIndex + 1 < cores.size())
		{
			coreIndex++;
			changed = true;
		}
		else if (keyEvent.getKeyCode() == ENTER || keyEvent.getKeyCode() == RETURN)
			core.setShowSolved(!core.isShowingSolved());

		if (changed)
			core = new DrawableGridCore(getApplet(), 0, 0, width, height, GridCore.fromGridString(cores.get(coreIndex)));
	}

	@Override
	public void keyUp(KeyEvent keyEvent)
	{

	}

	@Override
	protected void draw()
	{
		core.update();
		image(core, core.x, core.y, core.width, core.height);

		for (Control control : controls)
		{
			control.update();
			//image(control, control.x, control.y);
		}
	}
}
