package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.game.GridDifficulty;
import cz.krejcar25.sudoku.game.GridProperties;

import javax.swing.*;
import java.awt.event.*;

public class GeneratorSelectionDialog extends JDialog
{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner countSpinner;
	private JComboBox<GridProperties> sizeComboBox;
	private DefaultComboBoxModel<GridProperties> sizeComboBoxModel;
	private JComboBox<GridDifficulty> difficultyComboBox;
	private DefaultComboBoxModel<GridDifficulty> difficultyComboBoxModel;
    private CreateGenerationViewAction createGenerationViewAction;

    public GeneratorSelectionDialog(CreateGenerationViewAction createGenerationViewAction) {
        this.createGenerationViewAction = createGenerationViewAction;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setTitle("Generator selection");

	    sizeComboBoxModel = new DefaultComboBoxModel<>();
	    for (GridProperties gp : GridProperties.values()) sizeComboBoxModel.addElement(gp);
	    sizeComboBox.setModel(sizeComboBoxModel);

	    difficultyComboBoxModel = new DefaultComboBoxModel<>();
	    for (GridDifficulty gd : GridDifficulty.values()) if (gd.isSelectable()) difficultyComboBoxModel.addElement(gd);
	    difficultyComboBox.setModel(difficultyComboBoxModel);

        countSpinner.setValue(10);

        pack();
    }

    private void onOK() {
	    createGenerationViewAction.create(sizeComboBoxModel.getElementAt(sizeComboBox.getSelectedIndex()), difficultyComboBoxModel.getElementAt(difficultyComboBox.getSelectedIndex()), Integer.parseInt(countSpinner.getValue().toString()));
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
