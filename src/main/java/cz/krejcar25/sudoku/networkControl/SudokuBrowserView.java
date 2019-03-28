package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.FileChooserFactory;
import cz.krejcar25.sudoku.game.GridCore;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.control.Button;
import cz.krejcar25.sudoku.ui.control.Control;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SudokuBrowserView extends BaseView {
	private final ArrayList<String> cores;
	private final ArrayList<Control> controls;
	boolean shouldLeave;
	private DrawableGridCore core;
	private int coreIndex = 0;

	SudokuBrowserView(Applet applet) {
		super(applet, 800, 800);
		cores = new ArrayList<>();
		controls = new ArrayList<>();
		controls.add(Button.getStandardBackButton(this));

		String extension = GridCore.FILE_TYPE;
		new FileChooserFactory()
				.addFileType(GridCore.FILE_TYPE_DESC, extension)
				.setAllowAll(false)
				.setOkAction(file ->
				{
					try (FileInputStream in = new FileInputStream(file)) {
						BufferedReader reader = new BufferedReader(new InputStreamReader(in));
						reader.lines().forEach(line -> cores.add(line));
					}
					catch (IOException e) {
						e.printStackTrace();
					}

					core = new DrawableGridCore(getApplet(), 0, 0, width, height, GridCore.fromGridString(cores.get(coreIndex)));
					core.update();
					shouldLeave = false;
				})
				.setCancelAction(() -> shouldLeave = true)
				.setMode(FileChooserFactory.OPEN)
				.show();
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
		boolean changed = false;
		if (keyEvent.getKeyCode() == LEFT && coreIndex > 0) {
			coreIndex--;
			changed = true;
		}
		else if (keyEvent.getKeyCode() == RIGHT && coreIndex + 1 < cores.size()) {
			coreIndex++;
			changed = true;
		}
		else if (keyEvent.getKeyCode() == ENTER || keyEvent.getKeyCode() == RETURN)
			core.setShowSolved(!core.isShowingSolved());

		if (changed)
			core = new DrawableGridCore(getApplet(), 0, 0, width, height, GridCore.fromGridString(cores.get(coreIndex)));
	}

	@Override
	protected void draw() {
		core.update();
		image(core, core.x, core.y, core.width, core.height);

		for (Control control : controls) {
			control.update();
		}
	}
}
