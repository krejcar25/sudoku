package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.neuralNetwork.NeuralNetworkLayer;
import cz.krejcar25.sudoku.neuralNetwork.NeuralNetworkLayerProperties;

import javax.swing.*;
import java.awt.*;
import java.lang.annotation.Annotation;

class NetworkLayerListItemRenderer implements ListCellRenderer<NeuralNetworkLayer> {
	@Override
	public Component getListCellRendererComponent(JList list, NeuralNetworkLayer value, int index, boolean isSelected, boolean hasFocus) {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());

		String label = "";
		for (Annotation annotation : value.getClass().getAnnotations()) {
			if (annotation instanceof NeuralNetworkLayerProperties) {
				label = ((NeuralNetworkLayerProperties) annotation).label();
				break;
			}
		}

		JTextArea ta = new JTextArea();
		ta.setLineWrap(true);
		ta.setText(String.format("%s (%d out)", label, value.getNodes()));
		if (isSelected) {
			ta.setBackground(new Color(180, 180, 255));
			ta.setFont(new Font(ta.getFont().getName(), Font.BOLD, ta.getFont().getSize()));
		}
		p.add(ta, BorderLayout.CENTER);
		int width = list.getWidth();
		// this is just to lure the ta's internal sizing mechanism into action
		if (width > 0) {
			ta.setSize(width, Short.MAX_VALUE);
		}
		return p;
	}
}
