package uhunt.c4.g0.p260;

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
            input.boardSize = Integer.parseInt(in.readLine());
            if (input.boardSize == 0) break;
            input.board = new char[input.boardSize][];
            for (int i = 0; i < input.boardSize; i++) {
                input.board[i] = in.readLine().toCharArray();
            }

            final Output output = process.process(input);
            out.write(String.format("%d %c\n", gameId, output.winner));
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int boardSize;
    public char[][] board;
}

class Output {
    public char winner;
}

class Process {
    private static final char U_WHITE = 'W';
    private static final char L_WHITE = 'w';
    private static final char U_BLACK = 'B';
    private static final char L_BLACK = 'b';
    private static final char VISITED_MARK = 'X';

    public Output process(final Input input) {
        final Output output = new Output();

        for (int row = 0; row < input.boardSize; row++) {
            for (int col = 0; col < input.boardSize; col++) {
                final char cell = input.board[row][col];

                switch (cell) {
                    case L_WHITE: {
                        final FloodFillStats stats = floodFill(input.board, row, col, VISITED_MARK);
                        final boolean win = stats.minCol == 0 && stats.maxCol == input.boardSize - 1;
                        if (win) {
                            output.winner = U_WHITE;
                            return output;
                        }
                        break;
                    }

                    case L_BLACK: {
                        final FloodFillStats stats = floodFill(input.board, row, col, VISITED_MARK);
                        final boolean win = stats.minRow == 0 && stats.maxRow == input.boardSize - 1;
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

    private FloodFillStats floodFill(char[][] board, int row, int col, char mark) {
        final char target = board[row][col];

        final FloodFillStats stats = new FloodFillStats();
        stats.minRow = row;
        stats.maxRow = row;
        stats.minCol = col;
        stats.maxCol = col;

        final LinkedList<int[]> pendingCells = new LinkedList<>();

        board[row][col] = mark;
        pendingCells.addLast(new int[]{row, col});

        while (!pendingCells.isEmpty()) {
            final int[] cell = pendingCells.removeFirst();
            stats.minRow = Math.min(stats.minRow, cell[0]);
            stats.maxRow = Math.max(stats.maxRow, cell[0]);
            stats.minCol = Math.min(stats.minCol, cell[1]);
            stats.maxCol = Math.max(stats.maxCol, cell[1]);

            final List<int[]> neighborCells = getNeighborCells(cell[0], cell[1], board, target);
            for (final int[] neighborCell : neighborCells) {
                board[neighborCell[0]][neighborCell[1]] = mark;
                pendingCells.addLast(neighborCell);
            }
        }

        return stats;
    }

    private List<int[]> getNeighborCells(int row, int col, char[][] board, char target) {
        final List<int[]> neighborCells = getNeighborCells(row, col);
        return neighborCells.stream()
                .filter(cell -> 0 <= cell[0] && cell[0] < board.length)
                .filter(cell -> 0 <= cell[1] && cell[1] < board.length)
                .filter(cell -> board[cell[0]][cell[1]] == target)
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

class FloodFillStats {
    public int minRow;
    public int maxRow;
    public int minCol;
    public int maxCol;
}
