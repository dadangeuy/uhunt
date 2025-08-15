package dev.rizaldi.uhunt.c4.p260;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 260 - Il Gioco dell'X
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=196
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        for (int gameId = 1; ; gameId++) {
            final Input input = new Input();
            input.gameId = gameId;
            input.boardSize = Integer.parseInt(in.readLine());
            if (input.boardSize == 0) break;
            input.board = new char[input.boardSize][];
            for (int i = 0; i < input.boardSize; i++) {
                input.board[i] = in.readLine().toCharArray();
            }

            final Output output = process.process(input);
            out.write(String.format("%d %c\n", output.gameId, output.winner));
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int boardSize;
    public char[][] board;
    public int gameId;
}

class Output {
    public int gameId;
    public char winner;
}

class Process {
    private static final char U_WHITE = 'W';
    private static final char L_WHITE = 'w';
    private static final char U_BLACK = 'B';
    private static final char L_BLACK = 'b';

    public Output process(final Input input) {
        final Output output = new Output();
        output.gameId = input.gameId;

        for (int row = 0; row < input.boardSize; row++) {
            for (int col = 0; col < input.boardSize; col++) {
                final char cell = input.board[row][col];
                switch (cell) {
                    case L_WHITE: {
                        final int[] stats = floodFill(input.board, row, col, 'X');
                        final boolean win = stats[2] == 0 && stats[3] == input.boardSize - 1;
                        if (win) {
                            output.winner = U_WHITE;
                            return output;
                        }
                        break;
                    }
                    case L_BLACK: {
                        final int[] stats = floodFill(input.board, row, col, 'X');
                        final boolean win = stats[0] == 0 && stats[1] == input.boardSize - 1;
                        if (win) {
                            output.winner = U_BLACK;
                            return output;
                        }
                        break;
                    }
                }
            }
        }

        return output;
    }

    private int[] floodFill(char[][] board, int row, int col, char mark) {
        final char target = board[row][col];

        int minRow = row;
        int maxRow = row;
        int minCol = col;
        int maxCol = col;

        final LinkedList<int[]> pendingCells = new LinkedList<>();

        board[row][col] = mark;
        pendingCells.addLast(new int[]{row, col});

        while (!pendingCells.isEmpty()) {
            final int[] cell = pendingCells.removeFirst();
            minRow = Math.min(minRow, cell[0]);
            maxRow = Math.max(maxRow, cell[0]);
            minCol = Math.min(minCol, cell[1]);
            maxCol = Math.max(maxCol, cell[1]);

            final List<int[]> neighborCells = getNeighborCells(cell[0], cell[1], board.length);
            for (final int[] neighborCell : neighborCells) {
                if (board[neighborCell[0]][neighborCell[1]] != target) continue;

                board[neighborCell[0]][neighborCell[1]] = mark;
                pendingCells.addLast(neighborCell);
            }
        }

        return new int[]{minRow, maxRow, minCol, maxCol};
    }

    private List<int[]> getNeighborCells(int row, int col, int size) {
        final List<int[]> neighborCells = getNeighborCells(row, col);
        return neighborCells.stream()
                .filter(cell -> 0 <= cell[0] && cell[0] < size)
                .filter(cell -> 0 <= cell[1] && cell[1] < size)
                .collect(Collectors.toList());
    }

    private List<int[]> getNeighborCells(int row, int col) {
        return Arrays.asList(
                new int[]{row - 1, col - 1},
                new int[]{row - 1, col},
                new int[]{row, col - 1},
                new int[]{row, col + 1},
                new int[]{row + 1, col},
                new int[]{row + 1, col + 1}
        );
    }
}
