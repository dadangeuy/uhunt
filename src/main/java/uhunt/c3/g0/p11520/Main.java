package uhunt.c3.g0.p11520;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

/**
 * 11520 - Fill the Square
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2515
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        final Process process = new Process();

        final int totalCases = Integer.parseInt(readLine(in));
        for (int i = 0; i < totalCases; i++) {
            final Input input = new Input();
            input.caseId = i + 1;
            input.dimension = Integer.parseInt(readLine(in));
            input.grid = new char[input.dimension][];
            for (int j = 0; j < input.dimension; j++) {
                input.grid[j] = readLine(in).toCharArray();
            }

            final Output output = process.process(input);
            out.write(String.format("Case %d:\n", output.caseId));
            for (char[] row : output.grid) {
                out.write(row);
                out.write('\n');
            }
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Input {
    public int caseId;
    public int dimension;
    public char[][] grid;
}

class Output {
    public int caseId;
    public char[][] grid;
}

class Process {
    private static final char EMPTY = '.';

    public Output process(final Input input) {
        final Output output = new Output();
        output.caseId = input.caseId;

        final int dimension = input.dimension;
        final char[][] grid = copy(input.grid);
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                final char central = grid[row][col];
                if (central != EMPTY) continue;

                final char up = getOrDefault(grid, row - 1, col, EMPTY);
                final char down = getOrDefault(grid, row + 1, col, EMPTY);
                final char left = getOrDefault(grid, row, col - 1, EMPTY);
                final char right = getOrDefault(grid, row, col + 1, EMPTY);
                final List<Character> adjacent = Arrays.asList(up, down, left, right);

                char candidate;
                for (candidate = 'A'; candidate <= 'Z'; candidate++) {
                    final boolean isValid = !adjacent.contains(candidate);
                    if (isValid) break;
                }

                grid[row][col] = candidate;
            }
        }

        output.grid = grid;
        return output;
    }

    private char[][] copy(final char[][] grid) {
        final char[][] copy = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) copy[i] = Arrays.copyOf(grid[i], grid[i].length);
        return copy;
    }

    private char getOrDefault(final char[][] grid, final int row, final int col, final char defaultValue) {
        final boolean validRow = 0 <= row && row < grid.length;
        final boolean validCol = 0 <= col && col < grid[0].length;
        return validRow && validCol? grid[row][col] : defaultValue;
    }
}
