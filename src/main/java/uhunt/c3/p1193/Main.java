package uhunt.c3.p1193;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 1193 - Radar Installation
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=3634
 */
public class Main {
    private static final String EOF_LINE = "0 0";
    private static final String SEPARATOR = " ";

    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        for (int caseId = 1; ; caseId++) {
            final String line1 = readNonEmpty(in);
            final boolean isEOF = EOF_LINE.equals(line1);
            if (isEOF) break;

            final int totalIslands = Integer.parseInt(line1.split(SEPARATOR)[0]);
            final int radarDistance = Integer.parseInt(line1.split(SEPARATOR)[1]);
            final Coordinate[] islandCoordinates = new Coordinate[totalIslands];
            for (int i = 0; i < totalIslands; i++) {
                final String line2 = readNonEmpty(in);
                final Coordinate islandCoordinate = new Coordinate(
                        Integer.parseInt(line2.split(SEPARATOR)[0]),
                        Integer.parseInt(line2.split(SEPARATOR)[1])
                );
                islandCoordinates[i] = islandCoordinate;
            }

            final Input input = new Input(
                    caseId,
                    totalIslands,
                    radarDistance,
                    islandCoordinates
            );
            final Output output = process.process(input);
            out.write(String.format("Case %d: %d\n", output.caseId, output.minimumTotalRadar));
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String readNonEmpty(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Input {
    public final int caseId;
    public final int totalIslands;
    public final int radarDistance;
    public final Coordinate[] islandCoordinates;

    public Input(
            final int caseId,
            final int totalIslands,
            final int radarDistance,
            final Coordinate[] islandCoordinates
    ) {
        this.caseId = caseId;
        this.totalIslands = totalIslands;
        this.radarDistance = radarDistance;
        this.islandCoordinates = islandCoordinates;
    }
}

class Output {
    public final int caseId;
    public final int minimumTotalRadar;

    public Output(
            final int caseId,
            final int minimumTotalRadar
    ) {
        this.caseId = caseId;
        this.minimumTotalRadar = minimumTotalRadar;
    }
}

class Process {
    private static final int IMPOSSIBLE_TO_COVER = -1;
    private static final Comparator<Range> SORT_BY_FROM_UNTIL = Comparator
            .comparingDouble((Range r) -> r.from)
            .thenComparingDouble((Range r) -> r.until);

    public Output process(final Input input) {
        final List<Range> radarRanges = Arrays.stream(input.islandCoordinates)
                .map(c -> getRangeOfRadarLocation(c, input.radarDistance))
                .collect(Collectors.toList());

        final boolean canBeCovered = radarRanges.stream().allMatch(Range::isValid);
        if (!canBeCovered) {
            return new Output(input.caseId, IMPOSSIBLE_TO_COVER);
        }

        final List<Range> sortedRadarRanges = radarRanges.stream()
                .sorted(SORT_BY_FROM_UNTIL)
                .collect(Collectors.toList());

        // sliding window radar range
        int minimumTotalRadar = 0;
        Range currentRange = null;
        for (final Range nextRange : sortedRadarRanges) {
            final boolean isCovered = currentRange != null && currentRange.isOverlap(nextRange);
            if (isCovered) {
                currentRange = currentRange.merge(nextRange);
            } else {
                currentRange = nextRange;
                minimumTotalRadar++;
            }

        }

        return new Output(input.caseId, minimumTotalRadar);
    }

    // range is calculated using pytagoras formula: h^2 = a^2 + o^2
    // h is radar coverage, a is the vertical distance, o is the left/right horizontal range
    private Range getRangeOfRadarLocation(final Coordinate island, final double radarDistance) {
        final double hypotenuse = radarDistance;
        final double adjacent = island.y;
        final double opposite = Math.sqrt(hypotenuse * hypotenuse - adjacent * adjacent);
        return new Range(island.x - opposite, island.x + opposite);
    }
}

class Range {
    public final double from;
    public final double until;

    public Range(final double from, final double until) {
        this.from = from;
        this.until = until;
    }

    public boolean isValid() {
        return from <= until;
    }

    public boolean isOverlap(final Range other) {
        final boolean overlap1 = between(from, other.from, until);
        final boolean overlap2 = between(from, other.until, until);
        final boolean overlap3 = between(other.from, from, other.until);
        final boolean overlap4 = between(other.from, until, other.until);

        return overlap1 || overlap2 || overlap3 || overlap4;
    }

    private boolean between(final double lower, final double value, final double upper) {
        return lower <= value && value <= upper;
    }

    public Range merge(final Range other) {
        return new Range(
                Math.max(from, other.from),
                Math.min(until, other.until)
        );
    }
}

class Coordinate {
    public double x;
    public double y;

    public Coordinate(
            final double x,
            final double y
    ) {
        this.x = x;
        this.y = y;
    }
}
