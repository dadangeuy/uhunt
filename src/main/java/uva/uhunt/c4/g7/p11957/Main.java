package uva.uhunt.c4.g7.p11957;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 11957 - Checkers
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3108
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 8);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 8);
        final Process process = new Process();

        final int totalTestCases = Integer.parseInt(readLine(in));
        for (int testCase = 1; testCase <= totalTestCases; testCase++) {
            final Input input = new Input();
            input.testCase = testCase;
            input.boardSize = Integer.parseInt(readLine(in));
            input.board = new char[input.boardSize][];
            for (int row = 0; row < input.boardSize; row++) {
                input.board[row] = readLine(in).toCharArray();
            }

            final Output output = process.process(input);
            out.write(String.format("Case %d: %d\n", output.testCase, output.totalPaths));
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
    public int testCase;
    public int boardSize;
    public char[][] board;
}

class Output {
    public int testCase;
    public int totalPaths;
}

class Process {
    private static final int MODULO = 1000007;
    private static final char FREE = '.';
    private static final char BLACK = 'B';
    private static final char WHITE = 'W';

    public Output process(final Input input) {
        final Output output = new Output();
        output.testCase = input.testCase;

        final Location whiteLocation = findLocation(input.board, WHITE);
        final Integer[][] totalPathsPerLocation = new Integer[input.boardSize][input.boardSize];
        output.totalPaths = getTotalPaths(input.board, whiteLocation, totalPathsPerLocation);

        return output;
    }

    private Location findLocation(final char[][] board, final char piece) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == piece) {
                    final Location location = new Location();
                    location.row = row;
                    location.column = column;
                    return location;
                }
            }
        }
        return null;
    }

    private int getTotalPaths(
        final char[][] board,
        final Location location,
        final Integer[][] totalPathsPerLocation
    ) {
        if (totalPathsPerLocation[location.row][location.column] != null) {
            return totalPathsPerLocation[location.row][location.column];
        }

        if (isTopLocation(location)) {
            return totalPathsPerLocation[location.row][location.column] = 1;
        }

        int totalPaths = 0;

        // case 1 - free spaces
        {
            if (isValid(board, location.diagonalUL(), FREE)) {
                totalPaths += getTotalPaths(board, location.diagonalUL(), totalPathsPerLocation);
                totalPaths %= MODULO;
            }
            if (isValid(board, location.diagonalUR(), FREE)) {
                totalPaths += getTotalPaths(board, location.diagonalUR(), totalPathsPerLocation);
                totalPaths %= MODULO;
            }
        }

        // case 2 - jump over
        {
            if (
                isValid(board, location.diagonalUL(), BLACK) &&
                isValid(board, location.diagonalUL().diagonalUL(), FREE)
            ) {
                totalPaths += getTotalPaths(board, location.diagonalUL().diagonalUL(), totalPathsPerLocation);
                totalPaths %= MODULO;
            }
            if (
                isValid(board, location.diagonalUR(), BLACK) &&
                isValid(board, location.diagonalUR().diagonalUR(), FREE)
            ) {
                totalPaths += getTotalPaths(board, location.diagonalUR().diagonalUR(), totalPathsPerLocation);
                totalPaths %= MODULO;
            }
        }

        return totalPathsPerLocation[location.row][location.column] = totalPaths;
    }

    private boolean isValid(final char[][] board, final Location location, final char piece) {
        return isValid(board, location) && board[location.row][location.column] == piece;
    }

    private boolean isValid(final char[][] board, final Location location) {
        final boolean isValidRow = 0 <= location.row && location.row < board.length;
        final boolean isValidRowColumn = isValidRow && 0 <= location.column && location.column < board[location.row].length;
        return isValidRowColumn;
    }

    private boolean isTopLocation(final Location location) {
        return location.row == 0;
    }
}

class Location {
    public int row;
    public int column;

    public Location diagonalUL() {
        final Location location = new Location();
        location.row = row - 1;
        location.column = column - 1;
        return location;
    }

    public Location diagonalUR() {
        final Location location = new Location();
        location.row = row - 1;
        location.column = column + 1;
        return location;
    }
}
