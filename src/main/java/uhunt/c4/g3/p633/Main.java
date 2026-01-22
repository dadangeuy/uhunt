package uhunt.c4.g3.p633;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * 633 - A Chess Knight
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=574
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 8);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 8);
        final Process process = new Process();

        while (true) {
            final String l1 = readLine(in);
            final int boardSize = Integer.parseInt(l1);
            if (boardSize == 0) break;

            final String[] l2 = readSplitLine(in);
            final int[] fromPoint = new int[2];
            fromPoint[0] = Integer.parseInt(l2[0]);
            fromPoint[1] = Integer.parseInt(l2[1]);

            final String[] l3 = readSplitLine(in);
            final int[] intoPoint = new int[2];
            intoPoint[0] = Integer.parseInt(l3[0]);
            intoPoint[1] = Integer.parseInt(l3[1]);

            final LinkedList<int[]> obstacleList = new LinkedList<>();
            while (true) {
                final String[] l4 = readSplitLine(in);
                final int[] obstacle = new int[2];
                obstacle[0] = Integer.parseInt(l4[0]);
                obstacle[1] = Integer.parseInt(l4[1]);

                if (obstacle[0] == 0 && obstacle[1] == 0) break;
                obstacleList.add(obstacle);
            }

            final int[][] obstacles = obstacleList.toArray(new int[0][0]);

            final Input input = new Input(boardSize, fromPoint, intoPoint, obstacles);
            final Output output = process.process(input);

            if (output.minimumTotalMoves.isPresent()) {
                out.write(String.format("Result : %d\n", output.minimumTotalMoves.get()));
            } else {
                out.write("Solution doesn't exist\n");
            }
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String[] readSplitLine(final BufferedReader in) throws IOException {
        final String line = readLine(in);
        return line == null ? null : line.split(" ");
    }

    private static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Input {
    public final int boardSize;
    public final int[] fromPoint;
    public final int[] intoPoint;
    public final int[][] obstacles;

    public Input(
            final int boardSize,
            final int[] fromPoint,
            final int[] intoPoint,
            final int[][] obstacles
    ) {
        this.boardSize = boardSize;
        this.fromPoint = fromPoint;
        this.intoPoint = intoPoint;
        this.obstacles = obstacles;
    }
}

class Output {
    public final Optional<Integer> minimumTotalMoves;

    public Output(final Optional<Integer> minimumTotalMoves) {
        this.minimumTotalMoves = minimumTotalMoves;
    }
}

class Process {
    private static final char OBSTACLE = 'O';
    private static final char EMPTY = 'E';

    public Output process(final Input input) {
        convertBaseOneToZero(input.fromPoint);
        convertBaseOneToZero(input.intoPoint);
        convertBaseOneToZero(input.obstacles);

        final char[][] board = createBoard(input.boardSize, input.obstacles);

        final Queue<int[]> pointQueue = new LinkedList<>();
        final Queue<Integer> stepQueue = new LinkedList<>();
        final Queue<Integer> typeQueue = new LinkedList<>();
        final boolean[][][] visited = new boolean[2 * input.boardSize][2 * input.boardSize][3];

        pointQueue.add(input.fromPoint);
        stepQueue.add(0);
        typeQueue.add(-1);

        while (!pointQueue.isEmpty()) {
            final int[] point = pointQueue.remove();
            final int step = stepQueue.remove();
            final int type = typeQueue.remove();

            final boolean isFinished = point[0] == input.intoPoint[0] && point[1] == input.intoPoint[1];
            if (isFinished) {
                return new Output(Optional.of(step));
            }

            final List<MoveSet> moveSets = Arrays.stream(MoveSet.TYPES)
                    .mapToObj(t -> new MoveSet(t, point, input.boardSize))
                    .collect(Collectors.toList());

            for (final MoveSet set : moveSets) {
                final boolean isEqualType = type == set.type;
                if (isEqualType) continue;

                for (final int[] nextPoint : set.intoPoints) {
                    final boolean validRow = 0 <= nextPoint[0] && nextPoint[0] < 2 * input.boardSize;
                    final boolean validCol = 0 <= nextPoint[1] && nextPoint[1] < 2 * input.boardSize;
                    if (!(validRow && validCol)) continue;

                    final boolean isObstacle = board[nextPoint[0]][nextPoint[1]] == OBSTACLE;
                    if (isObstacle) continue;

                    final boolean isVisited = visited[nextPoint[0]][nextPoint[1]][set.type];
                    if (isVisited) continue;

                    pointQueue.add(nextPoint);
                    stepQueue.add(step + 1);
                    typeQueue.add(set.type);
                    visited[nextPoint[0]][nextPoint[1]][set.type] = true;
                }
            }
        }

        return new Output(Optional.empty());
    }

    private char[][] createBoard(final int boardSize, final int[][] obstacles) {
        final char[][] board = new char[2 * boardSize][2 * boardSize];

        for (int i = 0; i < 2 * boardSize; i++) {
            for (int j = 0; j < 2 * boardSize; j++) {
                board[i][j] = EMPTY;
            }
        }

        for (final int[] obstacle : obstacles) {
            board[obstacle[0]][obstacle[1]] = OBSTACLE;
        }

        return board;
    }

    private void convertBaseOneToZero(final int[]... points) {
        for (int[] point : points) {
            point[0]--;
            point[1]--;
        }
    }

}

class MoveSet {
    public static final int TYPE_K = 0;
    public static final int TYPE_B = 1;
    public static final int TYPE_T = 2;
    public static final int[] TYPES = new int[]{TYPE_K, TYPE_B, TYPE_T};

    public final int type;
    public final int[] fromPoint;
    public final int[][] intoPoints;

    public MoveSet(final int type, final int[] point, final int boardSize) {
        this.type = type;
        this.fromPoint = point;
        this.intoPoints = getNextPoints(point, type, boardSize);
    }

    private int[][] getNextPoints(final int[] point, final int type, final int boardSize) {
        switch (type) {
            case TYPE_K:
                return getNextPointsForTypeK(point);
            case TYPE_B:
                return getNextPointsForTypeB(point);
            case TYPE_T:
                return getNextPointsForTypeT(point, boardSize);
        }
        return new int[0][0];
    }

    private int[][] getNextPointsForTypeK(final int[] point) {
        return new int[][]{
                new int[]{point[0] - 2, point[1] - 1},
                new int[]{point[0] - 2, point[1] + 1},
                new int[]{point[0] - 1, point[1] - 2},
                new int[]{point[0] - 1, point[1] + 2},
                new int[]{point[0] + 1, point[1] - 2},
                new int[]{point[0] + 1, point[1] + 2},
                new int[]{point[0] + 2, point[1] - 1},
                new int[]{point[0] + 2, point[1] - 1},
        };
    }

    private int[][] getNextPointsForTypeB(final int[] point) {
        return new int[][]{
                new int[]{point[0] - 2, point[1] - 2},
                new int[]{point[0] - 2, point[1] + 2},
                new int[]{point[0] + 2, point[1] - 2},
                new int[]{point[0] + 2, point[1] + 2},
        };
    }

    private int[][] getNextPointsForTypeT(final int[] point, final int boardSize) {
        final int border = 2 * boardSize - 1;
        return new int[][]{
                new int[]{border - point[0], point[1]},
                new int[]{point[0], border - point[1]},
        };
    }
}
