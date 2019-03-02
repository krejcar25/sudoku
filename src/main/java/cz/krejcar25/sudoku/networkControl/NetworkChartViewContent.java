package cz.krejcar25.sudoku.networkControl;

import cz.krejcar25.sudoku.neuralNetwork.DeepLayer;
import cz.krejcar25.sudoku.neuralNetwork.NeuralNetwork;
import cz.krejcar25.sudoku.ui.Applet;
import cz.krejcar25.sudoku.ui.ScrollView;
import cz.krejcar25.sudoku.ui.ScrollViewContent;
import processing.core.PVector;

import java.util.ArrayList;

public class NetworkChartViewContent extends ScrollViewContent
{
	private final NeuralNetwork network;

	private final ArrayList<ArrayList<PVector>> positions;
	private final float d;

	NetworkChartViewContent(ScrollView scrollView, NeuralNetwork network)
	{
		super(scrollView, scrollView.width, scrollView.height);
		this.network = network;

		positions = new ArrayList<>();
		float mx = 30;
		float my = 30;
		d = 30;

		int highestCount = network.getInputCount();

		// Separated in it's own block of code to ease further variable naming
		{
			ArrayList<PVector> layer = new ArrayList<>();
			int count = network.getLayers().get(0).getInCount();
			if (count > highestCount) highestCount = count;
			for (int i = 0; i < count; i++)
			{
				layer.add(new PVector(d, i * my + (i + 1) * d));
			}
			positions.add(layer);
		}

		for (int l = 0; l < network.getLayers().size(); l++)
		{
			ArrayList<PVector> layer = new ArrayList<>();
			int count = network.getLayers().get(l).getNodes();
			if (count > highestCount) highestCount = count;
			for (int i = 0; i < count; i++)
			{
				layer.add(new PVector((l + 2) * mx + (l + 1) * d, i * my + (i + 1) * d));
			}
			positions.add(layer);
		}

		float width = (positions.size() - 1) * mx + (positions.size() + 1) * d;
		float highestHeight = (highestCount - 1) * my + (highestCount + 1) * d;

		for (ArrayList<PVector> layer : positions)
		{
			float height = (layer.size() - 1) * my + (layer.size() + 1) * d;
			float offset = (highestHeight - height) / 2f;

			for (PVector pos : layer)
			{
				pos.y += offset;
			}
		}

		int dw = (int) Math.ceil(width);
		int dh = (int) Math.ceil(highestHeight + 100);

		this.width = dw < scrollView.width ? scrollView.width : dw;
		this.height = dh < scrollView.height ? scrollView.height : dh;
	}

	@Override
	public void click(int mx, int my, boolean rmb)
	{

	}

	@Override
	protected void draw()
	{
		background(220);
		stroke(51);
		fill(51);
		textAlign(CENTER, CENTER);

		for (int l = 1; l < positions.size(); l++)
		{
			for (int i = 0; i < positions.get(l).size(); i++)
			{
				PVector to = positions.get(l).get(i);
				for (int p = 0; p < positions.get(l - 1).size(); p++)
				{
					double w = ((DeepLayer) network.getLayers().get(l - 1)).getWeights().get(i, p);
					strokeWeight(Applet.map((float) Math.abs(w), 0, 10, 0, 5));
					stroke(w > 0 ? 0 : 255, 0, w > 0 ? 255 : 0);
					PVector from = positions.get(l - 1).get(p);
					line(from.x, from.y, to.x, to.y);
				}
			}
		}

		noStroke();

		for (int l = 1; l < positions.size(); l++)
		{
			for (int i = 0; i < positions.get(l).size(); i++)
			{
				PVector to = positions.get(l).get(i);
				fill(150);
				ellipse(to.x, to.y, d, d);
				fill(51);
				textSize(d / 2);
				text(String.valueOf((int) Math.floor(((DeepLayer) network.getLayers().get(l - 1)).getBias().get(i, 0) * 10)), to.x, to.y);
			}
		}

		for (int i = 0; i < positions.get(0).size(); i++)
		{
			ellipse(positions.get(0).get(i).x, positions.get(0).get(i).y, d, d);
		}
	}
}
