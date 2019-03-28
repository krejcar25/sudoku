package cz.krejcar25.sudoku;

import org.intellij.lang.annotations.MagicConstant;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

public class FileChooserFactory {
	public static final int OPEN = 0;
	public static final int SAVE = 1;
	private final JFileChooser fileChooser;
	private Consumer<File> okAction = null;
	private Runnable cancelAction = null;
	@MagicConstant(intValues = {OPEN, SAVE})
	private int mode;
	private int result = -1;

	public FileChooserFactory() {
		this.fileChooser = new JFileChooser();
	}

	public FileChooserFactory setOkAction(Consumer<File> action) {
		this.okAction = action;
		return this;
	}

	public FileChooserFactory setCancelAction(Runnable action) {
		this.cancelAction = action;
		return this;
	}

	public FileChooserFactory addFileType(String description, String... validExtensions) {
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(description, validExtensions));
		return this;
	}

	public FileChooserFactory setAllowAll(boolean allow) {
		fileChooser.setAcceptAllFileFilterUsed(allow);
		return this;
	}

	public FileChooserFactory setMode(@MagicConstant(intValues = {OPEN, SAVE}) int mode) {
		this.mode = mode;
		return this;
	}

	public void show() {
		show(true);
	}

	public void show(boolean invokeAndWait) {
		switch (mode) {
			case OPEN:
				try {
					if (invokeAndWait) EventQueue.invokeAndWait(this::open);
					else open();
				}
				catch (InterruptedException | InvocationTargetException e) {
					e.printStackTrace();
				}
				break;
			case SAVE:
				try {
					if (invokeAndWait) EventQueue.invokeAndWait(this::save);
					else save();
				}
				catch (InterruptedException | InvocationTargetException e) {
					e.printStackTrace();
				}
				break;
		}

		if (result == JFileChooser.APPROVE_OPTION && okAction != null) okAction.accept(fileChooser.getSelectedFile());
		else if (result == JFileChooser.CANCEL_OPTION && cancelAction != null) cancelAction.run();
	}

	private void open() {
		result = fileChooser.showOpenDialog(null);
	}

	private void save() {
		result = fileChooser.showSaveDialog(null);
	}
}
