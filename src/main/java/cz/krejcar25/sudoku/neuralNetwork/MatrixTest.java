package cz.krejcar25.sudoku.neuralNetwork;

// TODO This is a debug file and should be removed before the final version

public class MatrixTest {
    public static void main(String[] args) {
        DoubleMatrix a = new DoubleMatrix(2,3);
        a.set(0, 0, 0);
        a.set(0, 1, 1);
        a.set(0, 2, 2);
        a.set(1, 0, 3);
        a.set(1, 1, 4);
        a.set(1, 2, 5);

        DoubleMatrix b = new DoubleMatrix(3,4);
        b.set(0, 0, 0);
        b.set(0, 1, 1);
        b.set(0, 2, 2);
        b.set(0, 3, 3);
        b.set(1, 0, 4);
        b.set(1, 1, 5);
        b.set(1, 2, 6);
        b.set(1, 3, 7);
        b.set(2, 0, 8);
        b.set(2, 1, 9);
        b.set(2, 2, 10);
        b.set(2, 3, 11);

        DoubleMatrix product = a.matmult(b);
        System.out.println(product);
        product.add(product);

        System.out.println(a);
        System.out.println(b);
        System.out.println(product);
    }
}
