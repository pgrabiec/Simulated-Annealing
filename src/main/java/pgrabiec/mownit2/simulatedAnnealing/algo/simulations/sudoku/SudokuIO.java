package pgrabiec.mownit2.simulatedAnnealing.algo.simulations.sudoku;

import java.io.*;

public class SudokuIO {
    public static Sudoku readSudoku(String fileName, String separator) throws IOException {
        File input = new File(fileName);
        BufferedReader reader = null;

        int[][] S = new int[9][9];

        try {
            reader = new BufferedReader(
                    new FileReader(
                            input
                    )
            );

            String line;
            String[] data;
            int row = 0;
            while (reader.ready()) {
                line = reader.readLine();

                data = line.split(separator);

                if (data.length != 9) {
                    throw new IllegalStateException("Sudoku row in input file cannot be of length: " + data.length);
                }

                for (int column=0; column<9; column++) {
                    if (data[column].equalsIgnoreCase("x")) {
                        S[row][column] = 0;
                    } else {
                        S[row][column] = Integer.parseInt(data[column]);
                    }
                }

                row++;
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return new Sudoku(S);
    }
}
