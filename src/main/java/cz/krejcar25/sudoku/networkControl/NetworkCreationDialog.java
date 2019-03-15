package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.FileChooserFactory;
import cz.krejcar25.sudoku.neuralNetwork.*;
import cz.krejcar25.sudoku.ui.Applet;
import processing.awt.PSurfaceAWT;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NetworkCreationDialog extends JDialog
{
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JComboBox<LayerTypeDescription> newLayerTypeComboBox;
	private JSpinner newLayerSizeSpinner;
	private JButton propertiesButton;
	private JList<NeuralNetworkLayer> layersList;
	private final DefaultListModel<NeuralNetworkLayer> layersListModel;
	private JSpinner inputLayerSizeSpinner;
	private JButton addLayerButton;
	private JButton removeLastButton;
	private JPanel networkDisplayPanel;
	private JButton chartButton;

	private NeuralNetwork network;
	private NetworkChartApplet chartApplet;

	NetworkCreationDialog(Applet owner, Class... deepLayerClasses)
	{
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);

		buttonOK.addActionListener(e -> onOK());

		buttonCancel.addActionListener(e -> onCancel());

		// call onCancel() when cross is clicked
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				onCancel();
			}
		});

		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		newLayerTypeComboBox.addActionListener(e -> propertiesButton.setEnabled(newLayerTypeComboBox.getItemAt(newLayerTypeComboBox.getSelectedIndex()).needsPropertiesButton));

		layersListModel = new DefaultListModel<>();
		layersListModel.addListDataListener(new ListDataListener()
		{
			@Override
			public void intervalAdded(ListDataEvent e)
			{
				inputLayerSizeSpinner.setEnabled(layersListModel.isEmpty());
				removeLastButton.setEnabled(!layersListModel.isEmpty());
				buttonOK.setEnabled(!layersListModel.isEmpty());
				chartButton.setEnabled(!layersListModel.isEmpty());
			}

			@Override
			public void intervalRemoved(ListDataEvent e)
			{
				inputLayerSizeSpinner.setEnabled(layersListModel.isEmpty());
				removeLastButton.setEnabled(!layersListModel.isEmpty());
				buttonOK.setEnabled(!layersListModel.isEmpty());
				chartButton.setEnabled(!layersListModel.isEmpty());
			}

			@Override
			public void contentsChanged(ListDataEvent e)
			{

			}
		});
		layersList.setCellRenderer(new NetworkLayerListItemRenderer());
		layersList.setModel(layersListModel);

		addLayerButton.addActionListener(e3 -> addLayerButton_actionPerformed());

		inputLayerSizeSpinner.addChangeListener(e2 -> sizeSpinnerStatesChanged());
		newLayerSizeSpinner.addChangeListener(e1 -> sizeSpinnerStatesChanged());

		addLayerButton.setEnabled(false);
		removeLastButton.setEnabled(false);
		buttonOK.setEnabled(false);
		chartButton.setEnabled(false);

		removeLastButton.addActionListener(e ->
		{
			if (!layersListModel.isEmpty()) layersListModel.removeElementAt(layersListModel.size() - 1);
		});

		for (Class layer : deepLayerClasses)
			if (NeuralNetworkLayer.class.isAssignableFrom(layer)) for (Annotation annotation : layer.getAnnotations())
				if (annotation instanceof NeuralNetworkLayerProperties)
					//noinspection unchecked
					newLayerTypeComboBox.addItem(new LayerTypeDescription((Class<? extends NeuralNetworkLayer>) layer, ((NeuralNetworkLayerProperties) annotation).needsProperties(), ((NeuralNetworkLayerProperties) annotation).label()));

		chartButton.addActionListener(e ->
		{
			if (e.getActionCommand().equals("SHOW"))
			{
				network = new NeuralNetwork(new DeepLayer(layersListModel.get(0).getInCount(), layersListModel.get(0).getNodes(), ActivationFunction.SIGMOID));
				for (int i = 1; i < layersListModel.size(); i++)
				{
					network.addLayer(layersListModel.get(i));
				}
				chartApplet = new NetworkChartApplet(owner, network, closedApplet ->
				{

				});
				Applet.runSketch(new String[]{""}, chartApplet);
				PSurfaceAWT surface = (PSurfaceAWT) chartApplet.getSurface();
				Canvas canvas = (Canvas) surface.getNative();
				chartApplet.frame.remove(canvas);
				chartApplet.frame.setVisible(false);
				surface.startThread();
				networkDisplayPanel.add(canvas, BorderLayout.CENTER);
				chartButton.setActionCommand("HIDE");
				chartButton.setText("Chart <<");
				pack();
			}
			else if (e.getActionCommand().equals("HIDE"))
			{
				network = null;
				chartApplet = null;
				networkDisplayPanel.remove(0);
				chartButton.setActionCommand("SHOW");
				chartButton.setText("Chart >>");
				pack();
			}
		});
		chartButton.setActionCommand("SHOW");

		pack();
		setVisible(true);
	}

	private void onOK()
	{
		NeuralNetwork network = new NeuralNetwork(layersListModel.elementAt(0));
		for (int i = 1; i < layersListModel.size(); i++) network.addLayer(layersListModel.elementAt(i));

		String extension = NeuralNetwork.FILE_TYPE;
		new FileChooserFactory()
				.addFileType(NeuralNetwork.FILE_TYPE_DESC, extension)
				.setAllowAll(false)
				.setOkAction(file ->
				{
					String path = file.getAbsolutePath();
					if (!path.endsWith(extension))
					{
						file = new File(path + "." + extension);
					}
					if (network.saveToFile(file.getAbsolutePath())) dispose();
				})
				.setMode(FileChooserFactory.SAVE)
				.show(false);
	}

	private void onCancel()
	{
		// add your code here if necessary
		dispose();
	}

	private void addLayerButton_actionPerformed()
	{
		LayerTypeDescription layerType = newLayerTypeComboBox.getItemAt(newLayerTypeComboBox.getSelectedIndex());
		int lastOutput = (layersListModel.isEmpty()) ? (int) inputLayerSizeSpinner.getValue() : layersListModel.getElementAt(layersListModel.getSize() - 1).getNodes();
		try
		{
			Method createMethod = layerType.type.getMethod("create", int.class, int.class);
			@SuppressWarnings("JavaReflectionInvocation")
			NeuralNetworkLayer layer = (NeuralNetworkLayer) createMethod.invoke(null, lastOutput, newLayerSizeSpinner.getValue());
			layersListModel.addElement(layer);
			if (network != null) network.addLayer(layer);
		}
		catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassCastException e1)
		{
			e1.printStackTrace();
		}
	}

	private void sizeSpinnerStatesChanged()
	{
		int input = (int) inputLayerSizeSpinner.getValue();
		int newLayer = (int) newLayerSizeSpinner.getValue();
		if (input < 0) inputLayerSizeSpinner.setValue(0);
		if (newLayer < 0) newLayerSizeSpinner.setValue(0);
		addLayerButton.setEnabled(input > 0 && newLayer > 0);
	}

	private class LayerTypeDescription
	{
		final Class<? extends NeuralNetworkLayer> type;
		final boolean needsPropertiesButton;
		final String label;

		LayerTypeDescription(Class<? extends NeuralNetworkLayer> type, boolean needsPropertiesButton, String label)
		{
			this.type = type;
			this.needsPropertiesButton = needsPropertiesButton;
			this.label = label;
		}

		public String toString()
		{
			return label;
		}
	}
}
