package dev.rizaldi.uhunt.c4.p825;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * 825 - Walking on the Safe Side
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=766
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        final int totalCases = Integer.parseInt(readLine(in));
        for (int i = 0; i < totalCases; i++) {
            final String[] l1 = readSplitLine(in);
            final int horizontalLength = Integer.parseInt(l1[0]);
            final int verticalLength = Integer.parseInt(l1[1]);

            final List<Coordinate> unsafeCoordinates = new ArrayList<>();
            for (int j = 0; j < horizontalLength; j++) {
                final String[] l2 = readSplitLine(in);
                for (int k = 1; k < l2.length; k++) {
                    final int x = Integer.parseInt(l2[0]);
                    final int y = Integer.parseInt(l2[k]);
                    final Coordinate coordinate = new Coordinate(x, y);
                    unsafeCoordinates.add(coordinate);
                }
            }

            final Input input = new Input(horizontalLength, verticalLength, unsafeCoordinates);
            final Output output = process.process(input);

            if (i > 0) out.write('\n');
            out.write(String.format("%d\n", output.totalMinimumPaths));
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
        if (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Coordinate {
    public final int x;
    public final int y;

    public Coordinate(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public List<Coordinate> neighbours() {
        return Arrays.asList(east(), south());
    }

    public Coordinate east() {
        return new Coordinate(x + 1, y);
    }

    public Coordinate south() {
        return new Coordinate(x, y + 1);
    }

    public boolean equals(final Coordinate o) {
        return x == o.x && y == o.y;
    }
}

class Input {
    public final int horizontalLength;
    public final int verticalLength;
    public final List<Coordinate> unsafeCoordinates;

    public Input(
            final int horizontalLength,
            final int verticalLength,
            final List<Coordinate> unsafeCoordinates
    ) {
        this.horizontalLength = horizontalLength;
        this.verticalLength = verticalLength;
        this.unsafeCoordinates = unsafeCoordinates;
    }
}

class Output {
    public final int totalMinimumPaths;

    public Output(final int totalMinimumPaths) {
        this.totalMinimumPaths = totalMinimumPaths;
    }
}

class Process {
    public Output process(final Input input) {
        final boolean[][] isUnsafeByXAndY = new boolean[input.horizontalLength + 1][input.verticalLength + 1];
        for (final Coordinate coordinate : input.unsafeCoordinates) isUnsafeByXAndY[coordinate.x][coordinate.y] = true;

        final Coordinate entry = new Coordinate(1, 1);
        final Coordinate station = new Coordinate(input.horizontalLength, input.verticalLength);

        final Queue<Coordinate> coordinateQueue = new LinkedList<>();
        final int[][] stepPerXAndY = new int[input.horizontalLength + 1][input.verticalLength + 1];
        fill(stepPerXAndY, Integer.MAX_VALUE);

        coordinateQueue.add(entry);
        stepPerXAndY[entry.x][entry.y] = 0;

        int minStep = Integer.MAX_VALUE;
        int countMinStep = 0;

        while (!coordinateQueue.isEmpty()) {
            final Coordinate current = coordinateQueue.remove();
            final int step = stepPerXAndY[current.x][current.y];

            if (current.equals(station)) {
                if (step < minStep) {
                    minStep = step;
                    countMinStep = 1;
                } else if (step == minStep) {
                    countMinStep++;
                }
            } else {
                final List<Coordinate> neighbours = current.neighbours().stream()
                        // is within the horizontal & vertical boundaries
                        .filter(c -> 1 <= c.x && c.x <= input.horizontalLength && 1 <= c.y && c.y <= input.verticalLength)
                        // is optimal step
                        .filter(c -> step + 1 <= stepPerXAndY[c.x][c.y])
                        // is safe
                        .filter(c -> !isUnsafeByXAndY[c.x][c.y])
                        .collect(Collectors.toList());
                for (final Coordinate neighbour : neighbours) {
                    coordinateQueue.add(neighbour);
                    stepPerXAndY[neighbour.x][neighbour.y] = step + 1;
                }
            }
        }

        return new Output(countMinStep);
    }

    private void fill(final int[][] array, final int fill) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = fill;
            }
        }
    }
}
