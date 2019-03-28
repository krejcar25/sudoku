package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.game.GridDifficulty;
import cz.krejcar25.sudoku.game.GridProperties;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GeneratorSelectionDialog extends JDialog {
	private final DefaultComboBoxModel<GridProperties> sizeComboBoxModel;
	private final DefaultComboBoxModel<GridDifficulty> difficultyComboBoxModel;
	private final CreateGenerationViewAction createGenerationViewAction;
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JSpinner countSpinner;
	private JComboBox<GridProperties> sizeComboBox;
	private JComboBox<GridDifficulty> difficultyComboBox;
	private JSpinner clueCountSpinner;

	GeneratorSelectionDialog(CreateGenerationViewAction createGenerationViewAction) {
		this.createGenerationViewAction = createGenerationViewAction;
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);

		buttonOK.addActionListener(e -> onOK());

		buttonCancel.addActionListener(e -> onCancel());

		// call onCancel() when cross is clicked
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onCancel();
			}
		});

		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		setTitle("Generator selection");

		sizeComboBoxModel = new DefaultComboBoxModel<>();
		for (GridProperties gp : GridProperties.values()) sizeComboBoxModel.addElement(gp);
		sizeComboBox.setModel(sizeComboBoxModel);
		sizeComboBox.addActionListener(e ->
		{
			setClueLimit();
			setClueCount();
		});

		difficultyComboBoxModel = new DefaultComboBoxModel<>();
		for (GridDifficulty gd : GridDifficulty.values()) if (gd.isSelectable()) difficultyComboBoxModel.addElement(gd);
		difficultyComboBox.setModel(difficultyComboBoxModel);
		difficultyComboBox.addActionListener(e ->
		{
			clueCountSpinner.setEnabled(difficultyComboBox.getSelectedItem() == GridDifficulty.Custom);
			setClueCount();
		});

		countSpinner.setModel(new SpinnerNumberModel(10, 0, 100000, 1));

		setClueLimit();
		pack();
	}

	private void onOK() {
		createGenerationViewAction.create(sizeComboBoxModel.getElementAt(sizeComboBox.getSelectedIndex()), (int) clueCountSpinner.getValue(), Integer.parseInt(countSpinner.getValue().toString()));
		dispose();
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}

	private void setClueLimit() {
		GridProperties gp = sizeComboBoxModel.getElementAt(sizeComboBox.getSelectedIndex());
		int ncr = (int) Math.pow(gp.getSizea() * gp.getSizeb(), 2);
		int min = ncr / 3;
		int val = ncr / 2;
		clueCountSpinner.setModel(new SpinnerNumberModel(val, min, ncr, 1));
	}

	private void setClueCount() {
		if (difficultyComboBox.getSelectedItem() != GridDifficulty.Custom)
			this.clueCountSpinner.setValue(sizeComboBoxModel.getElementAt(sizeComboBox.getSelectedIndex()).getClueCount(difficultyComboBoxModel.getElementAt(difficultyComboBox.getSelectedIndex())));
	}
}
