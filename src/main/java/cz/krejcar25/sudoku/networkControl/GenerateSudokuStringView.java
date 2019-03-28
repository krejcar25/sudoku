package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.FileChooserFactory;
import cz.krejcar25.sudoku.game.GridCore;
import cz.krejcar25.sudoku.game.GridProperties;
import cz.krejcar25.sudoku.game.SudokuGenerator;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.BaseView;
import cz.krejcar25.sudoku.ui.control.Button;
import cz.krejcar25.sudoku.ui.control.Control;
import cz.krejcar25.sudoku.ui.style.Color;
import cz.krejcar25.sudoku.ui.style.Thickness;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GenerateSudokuStringView extends BaseView implements Runnable {
	private final GridProperties gridProperties;
	private final int clueCount;
	private final int count;
	private final AtomicInteger cycle = new AtomicInteger(0);
	private final ArrayList<String> sudokus;
	private final ArrayList<Control> controls;

	GenerateSudokuStringView(Applet applet, GridProperties gridProperties, int clueCount, int count) {
		super(applet, 800, 600);
		this.gridProperties = gridProperties;
		this.clueCount = clueCount;
		this.count = count;
		this.sudokus = new ArrayList<>();
		Thread thread = new Thread(this);
		thread.start();
		this.controls = new ArrayList<>();
		this.controls.add(Button.getStandardBackButton(this));
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
		push();
		background(220);
		textSize(50);
		fill(51);
		textAlign(CENTER, CENTER);

		if (cycle.get() < count) {
			text("Generating", width / 2f, 100);
			fill(150);
			noStroke();
			rect(100, 400, 600, 100);
			fill(0, 200, 0);
			rect(105, 405, Applet.map(cycle.floatValue() / count, 0, 1, 0, 590), 90);
			fill(51);
			text(String.format("%d / %d", cycle.get(), count), 400, 450);
		}
		else {
			text("Generation finished", width / 2f, 100);
		}
		pop();

		for (int i = controls.size() - 1; i >= 0; i--) {
			controls.get(i).update();
		}
	}

	@Override
	public void run() {
		ArrayList<GridCore> cores = new ArrayList<>();
		for (cycle.set(0); cycle.get() < count; cycle.getAndIncrement()) {
			GridCore core = new GridCore(gridProperties);
			new SudokuGenerator(core).generate(clueCount, false);
			sudokus.add(core.getGridString());
			cores.add(core);
		}

		for (GridCore core : cores) System.out.println(core);
		System.out.println("Generation finished...");

		Button button = new Button<>(this, width / 2f, 450, 600, 100, "Save to file", sender ->
		{
			String extension = GridCore.FILE_TYPE;
			new FileChooserFactory()
					.addFileType(GridCore.FILE_TYPE_DESC, extension)
					.setAllowAll(false)
					.setOkAction((file) ->
					{
						String path = file.getAbsolutePath();

						if (!path.endsWith(extension)) {
							file = new File(path + "." + extension);
						}
						try (FileOutputStream out = new FileOutputStream(file)) {
							BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
							for (String line : sudokus) {
								writer.write(line);
								writer.newLine();
							}
							writer.close();
						}
						catch (IOException e) {
							e.printStackTrace();
						}
					})
					.setMode(FileChooserFactory.SAVE)
					.show();
		});
		button.style.borderThickness = new Thickness(5);
		button.style.border = new Color(150);
		button.style.background = new Color(0, 200, 0);
		button.style.foreground = new Color(51);

		this.controls.add(button);
	}
}
