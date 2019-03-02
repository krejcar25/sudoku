package cz.krejcar25.sudoku.neuralNetwork;

@FunctionalInterface
interface MapFunction<Data>
{
	Data map(Data value, int i, int j);
}
