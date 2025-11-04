package uhunt.c4.p11352;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 11352 - Crazy King
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2327
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 8);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 8);
        final Process process = new Process();

        final int totalTestCases = Integer.parseInt(readLine(in));
        for (int caseId = 1; caseId <= totalTestCases; caseId++) {
            final String[] l1 = readSplitLine(in);
            final int totalRows = Integer.parseInt(l1[0]);
            final int totalColumns = Integer.parseInt(l1[1]);

            final char[][] grid = new char[totalRows][];
            for (int row = 0; row < totalRows; row++) {
                grid[row] = readLine(in).toCharArray();
            }

            final Input input = new Input(caseId, totalRows, totalColumns, grid);
            final Output output = process.process(input);

            if (output.isPossible) {
                out.write(String.format("Minimal possible length of a trip is %d\n", output.minimumLength));
            } else {
                out.write("King Peter, you can't go now!\n");
            }
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String[] readSplitLine(final BufferedReader in) throws IOException {
        final String line = readLine(in);
        final String[] splitLine = line.split(" ");
        return splitLine;
    }

    private static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Input {
    public final int caseId;
    public final int totalRows;
    public final int totalColumns;
    public final char[][] board;

    public Input(final int caseId, final int totalRows, final int totalColumns, final char[][] board) {
        this.caseId = caseId;
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.board = board;
    }
}

class Output {
    public final int caseId;
    public final boolean isPossible;
    public final int minimumLength;

    public Output(final int caseId, final boolean isPossible, final int minimumLength) {
        this.caseId = caseId;
        this.isPossible = isPossible;
        this.minimumLength = minimumLength;
    }
}

class Process {
    private final Comparator<int[]> COMPARE_POSITION = Comparator
        .comparingInt((int[] p) -> p[0])
        .thenComparingInt((int[] p) -> p[1]);

    /**
     * algorithm:
     * - find position of every horse
     * - calculate attack positions (L pattern)
     * - mark attack positions on the grid as X
     * - find position of kingdom A
     * - find position of kingdom B
     * - bfs from A to B by following EMPTY cell path
     * - keep track of number of step during bfs
     * - mark visited cell as # to avoid revisit
     * - if on kingdom B, return step
     */
    public Output process(final Input input) {
        final int[][] horsePositions = findPositions(input.board, Cell.HORSE);
        final int[] kingdomAPositions = findPositions(input.board, Cell.KINGDOM_A)[0];
        final int[] kingdomBPositions = findPositions(input.board, Cell.KINGDOM_B)[0];

        for (final int[] horsePosition : horsePositions) {
            final int[][] horseAttackPositions = calculateHorseAttackPositions(input.board, horsePosition);
            mark(input.board, horseAttackPositions, Cell.ATTACK);
        }

        final LinkedList<int[]> positionq = new LinkedList<>();
        final LinkedList<Integer> stepq = new LinkedList<>();

        mark(input.board, kingdomAPositions, Cell.MOVE);
        positionq.addLast(kingdomAPositions);
        stepq.addLast(0);

        while (!positionq.isEmpty()) {
            final int[] position = positionq.removeFirst();
            final int step = stepq.removeFirst();

            final boolean isFinished = COMPARE_POSITION.compare(position, kingdomBPositions) == 0;
            if (isFinished) {
                return new Output(input.caseId, true, step);
            }

            final int[][] kingMovePositions = calculateKingMovePositions(input.board, position);
            for (final int[] kingMovePosition : kingMovePositions) {
                mark(input.board, kingMovePosition, Cell.MOVE);
                positionq.addLast(kingMovePosition);
                stepq.addLast(step + 1);
            }
        }

        return new Output(input.caseId, false, -1);
    }

    private int[][] findPositions(final char[][] board, final char cell) {
        final LinkedList<int[]> positions = new LinkedList<>();
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == cell) {
                    final int[] position = new int[]{row, column};
                    positions.addLast(position);
                }
            }
        }
        return positions.toArray(new int[0][0]);
    }

    private int[][] calculateHorseAttackPositions(final char[][] board, final int[] horsePosition) {
        final List<Character> allowlist = Collections.singletonList(Cell.EMPTY);

        final int row = horsePosition[0];
        final int column = horsePosition[1];

        final List<int[]> positions = Arrays.asList(
            new int[]{row - 2, column - 1},
            new int[]{row - 2, column + 1},
            new int[]{row - 1, column - 2},
            new int[]{row - 1, column + 2},
            new int[]{row + 1, column - 2},
            new int[]{row + 1, column + 2},
            new int[]{row + 2, column - 1},
            new int[]{row + 2, column + 1}
        );
        final List<int[]> validPositions = positions.stream()
            .filter(p -> 0 <= p[0] && p[0] < board.length)
            .filter(p -> 0 <= p[1] && p[1] < board[p[0]].length)
            .filter(p -> allowlist.contains(board[p[0]][p[1]]))
            .collect(Collectors.toList());

        return validPositions.toArray(new int[0][0]);
    }

    private void mark(final char[][] board, final int[][] positions, final char mark) {
        for (final int[] position : positions) {
            mark(board, position, mark);
        }
    }

    private void mark(final char[][] board, final int[] position, final char mark) {
        board[position[0]][position[1]] = mark;
    }

    private int[][] calculateKingMovePositions(final char[][] board, final int[] kingPosition) {
        final List<Character> allowlist = Arrays.asList(Cell.EMPTY, Cell.KINGDOM_A, Cell.KINGDOM_B);

        final int row = kingPosition[0];
        final int column = kingPosition[1];

        final List<int[]> positions = Arrays.asList(
            new int[]{row - 1, column - 1},
            new int[]{row - 1, column},
            new int[]{row - 1, column + 1},
            new int[]{row, column - 1},
            new int[]{row, column},
            new int[]{row, column + 1},
            new int[]{row + 1, column - 1},
            new int[]{row + 1, column},
            new int[]{row + 1, column + 1}
        );
        final List<int[]> validPositions = positions.stream()
            .filter(p -> 0 <= p[0] && p[0] < board.length)
            .filter(p -> 0 <= p[1] && p[1] < board[p[0]].length)
            .filter(p -> allowlist.contains(board[p[0]][p[1]]))
            .collect(Collectors.toList());

        return validPositions.toArray(new int[0][0]);
    }
}

class Cell {
    public static final char EMPTY = '.';
    public static final char HORSE = 'Z';
    public static final char ATTACK = 'X';
    public static final char KINGDOM_A = 'A';
    public static final char KINGDOM_B = 'B';
    public static final char MOVE = 'C';
}
