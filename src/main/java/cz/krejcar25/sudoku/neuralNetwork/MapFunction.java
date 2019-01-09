package cz.krejcar25.sudoku.neuralNetwork;

@FunctionalInterface
public interface MapFunction<Data> {
    Data map(Data value, int i, int j);
}
