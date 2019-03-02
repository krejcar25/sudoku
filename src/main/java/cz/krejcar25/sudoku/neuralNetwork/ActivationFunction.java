package cz.krejcar25.sudoku.neuralNetwork;

import org.jetbrains.annotations.Contract;

import java.io.Serializable;

@SuppressWarnings("unused")
public interface ActivationFunction extends Serializable
{
	ActivationFunction SIGMOID = new ActivationFunction()
	{
		@Contract(pure = true)
		@Override
		public double func(double value, int x, int y)
		{
			return 1 / (1 + Math.exp(-value));
		}

		@Contract(pure = true)
		@Override
		public double derivative(double value, int x, int y)
		{
			return value * (1 - value);
		}
	};
	ActivationFunction TANH = new ActivationFunction()
	{
		@Contract(pure = true)
		@Override
		public double func(double value, int x, int y)
		{
			return Math.tanh(value);
		}

		@Contract(pure = true)
		@Override
		public double derivative(double value, int x, int y)
		{
			return 1 - Math.pow(value, 2);
		}
	};

	double func(double value, int x, int y);

	double derivative(double value, int x, int y);
}
