package cz.krejcar25.sudoku.game;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class DeserialisationTest {
    public static void main(String[] args) {
        String def = "/Users/krejcar25/Desktop/test.scs";
        System.out.print("Enter file name [" + def + "]: ");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        File file = new File(path.isEmpty() ? def : path);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            reader.lines().forEach(s -> {
                System.out.println(GridCore.fromGridString(s));
                System.out.println(Arrays.toString(GridCore.fromGridString(s).getInput()));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
