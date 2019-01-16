package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.game.GridProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GeneratorSelectionDialog extends JDialog implements ActionListener {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner countSpinner;
    private JPanel selectionPanel;
    private GridProperties selectedProp;
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
        selectionPanel.setLayout(new GridLayout(GridProperties.values().length, 1));
        ButtonGroup group = new ButtonGroup();

        for (GridProperties gp : GridProperties.values()) {
            JRadioButton radio = new JRadioButton(gp.getName());
            radio.setActionCommand(gp.getName());
            radio.addActionListener(this);
            selectionPanel.add(radio);
            group.add(radio);
        }

        pack();
    }

    private void onOK() {
        createGenerationViewAction.create(selectedProp, Integer.parseInt(countSpinner.getValue().toString()));
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (GridProperties gp : GridProperties.values()) {
            if (e.getActionCommand().equals(gp.getName())) {
                selectedProp = gp;
                return;
            }
        }
    }
}
