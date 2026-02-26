package uva.c4.g4.p784;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 784 - Maze Exploration
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=725
 */
public class Main {
    private static final char DIVIDER = '_';

    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        final Process process = new Process();

        final int totalTestCases = Integer.parseInt(readLine(in));
        for (int i = 0; i < totalTestCases; i++) {
            final List<String> rows = new LinkedList<>();
            while (true) {
                final String row = readLine(in);
                rows.add(row);
                if (row.charAt(0) == DIVIDER) break;
            }

            final Input input = new Input();
            input.maze = rows.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);

            final Output output = process.process(input);
            for (final char[] row : output.paintedMaze) {
                out.write(row);
                out.write('\n');
            }
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String readLine(final BufferedReader in) throws IOException {
        final String line = in.readLine();
        return line == null || !line.isEmpty() ? line : readLine(in);
    }
}

class Input {
    public char[][] maze;
}

class Output {
    public char[][] paintedMaze;
}

class Process {
    private static final char START_CELL = '*';
    private static final char EMPTY_CELL = ' ';
    private static final char PAINTED_CELL = '#';

    public Output process(final Input input) {
        final Output output = new Output();

        final char[][] paintedMaze = copy(input.maze);
        final int[] startPosition = find(paintedMaze, START_CELL);

        final Queue<int[]> queue = new LinkedList<>();
        queue.add(startPosition);
        paintedMaze[startPosition[0]][startPosition[1]] = PAINTED_CELL;

        while (!queue.isEmpty()) {
            final int[] position = queue.remove();
            final int[][] adjacentPositions = getAdjacentPositions(position);

            for (final int[] adjacentPosition : adjacentPositions) {
                final boolean isValid = isValid(paintedMaze, adjacentPosition, EMPTY_CELL);
                if (!isValid) continue;

                queue.add(adjacentPosition);
                paintedMaze[adjacentPosition[0]][adjacentPosition[1]] = PAINTED_CELL;
            }
        }

        output.paintedMaze = paintedMaze;
        return output;
    }

    private char[][] copy(final char[][] original) {
        final char[][] copy = new char[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    private int[] find(final char[][] maze, final char target) {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                if (maze[row][col] == target) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    private int[][] getAdjacentPositions(final int[] position) {
        return new int[][]{
            new int[]{position[0] - 1, position[1]},
            new int[]{position[0] + 1, position[1]},
            new int[]{position[0], position[1] - 1},
            new int[]{position[0], position[1] + 1},
        };
    }

    private boolean isValid(final char[][] maze, final int[] position, final char target) {
        final boolean validRow = 0 <= position[0] && position[0] < maze.length;
        final boolean validRowCol = validRow && 0 <= position[1] && position[1] < maze[position[0]].length;
        final boolean validTarget = validRowCol && maze[position[0]][position[1]] == target;
        return validTarget;
    }
}
