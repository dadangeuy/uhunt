package uva.c3.g1.p12321;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * 12321 - Gas Stations
 * Time limit: 2.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=3743
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        while (true) {
            final String[] l1 = Util.readNonEmptyStrings(in);
            final int roadLength = Integer.parseInt(l1[0]);
            final int totalStations = Integer.parseInt(l1[1]);

            final boolean isEOF = roadLength == 0 && totalStations == 0;
            if (isEOF) break;

            final int[] locationPerStation = new int[totalStations];
            final int[] radiusPerStation = new int[totalStations];

            for (int stationId = 0; stationId < totalStations; stationId++) {
                final String[] l2 = Util.readNonEmptyStrings(in);
                final int location = Integer.parseInt(l2[0]);
                final int radius = Integer.parseInt(l2[1]);

                locationPerStation[stationId] = location;
                radiusPerStation[stationId] = radius;
            }

            final Input input = new Input(roadLength, totalStations, locationPerStation, radiusPerStation);
            final Output output = process.process(input);
            out.write(String.format("%d\n", output.maximumTotalEliminatedStations));
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public final int roadLength;
    public final int totalStations;
    public final int[] locationPerStation;
    public final int[] radiusPerStation;

    public Input(
            final int roadLength,
            final int totalStations,
            final int[] locationPerStation,
            final int[] radiusPerStation
    ) {
        this.roadLength = roadLength;
        this.totalStations = totalStations;
        this.locationPerStation = locationPerStation;
        this.radiusPerStation = radiusPerStation;
    }
}

class Output {
    public static final int IMPOSSIBLE = -1;
    public final int maximumTotalEliminatedStations;

    public Output(
            final int maximumTotalEliminatedStations
    ) {
        this.maximumTotalEliminatedStations = maximumTotalEliminatedStations;
    }
}

class Process {
    private static final Comparator<Interval> SORT_BY_FROM_UNTIL = Comparator
            .comparingInt((Interval i) -> i.from)
            .thenComparingInt((Interval i) -> i.until);
    private static final Comparator<Interval> SORT_BY_UNTIL = Comparator
            .comparingInt((Interval i) -> i.until);

    public Output process(final Input input) {
        final List<Interval> intervals = getIntervalPerStation(input);
        intervals.sort(SORT_BY_FROM_UNTIL);

        final Interval roadCoverage = new Interval(0, input.roadLength);
        Interval stationCoverage = new Interval(0, 0);
        int totalStations = 0;

        int from = 0, until = 0;
        while (from < intervals.size() && !stationCoverage.contain(roadCoverage)) {
            // find intervals that overlap with current coverage
            while (until < intervals.size() && stationCoverage.overlap(intervals.get(until))) until++;
            final List<Interval> overlapIntervals = intervals.subList(from, until);

            // find interval with the farthest coverage (maximum until)
            final Optional<Interval> farthestIntervalOp = overlapIntervals.stream().max(SORT_BY_UNTIL);
            if (!farthestIntervalOp.isPresent()) break;
            final Interval farthestInterval = farthestIntervalOp.get();

            // update coverage, counter, and loop
            stationCoverage = stationCoverage.combine(farthestInterval);
            totalStations++;
            from = until;
        }

        if (stationCoverage.contain(roadCoverage)) {
            return new Output(input.totalStations - totalStations);
        }

        return new Output(Output.IMPOSSIBLE);
    }

    private List<Interval> getIntervalPerStation(final Input input) {
        final List<Interval> intervals = new ArrayList<>(input.totalStations);

        for (int stationId = 0; stationId < input.totalStations; stationId++) {
            final int location = input.locationPerStation[stationId];
            final int radius = input.radiusPerStation[stationId];

            final Interval interval = new Interval(
                    Math.max(0, location - radius),
                    Math.min(input.roadLength, location + radius)
            );
            intervals.add(interval);
        }

        return intervals;
    }
}

class Interval {
    public final int from;
    public final int until;

    public Interval(final int from, final int until) {
        this.from = from;
        this.until = until;
    }

    public boolean contain(final Interval other) {
        return from <= other.from && other.until <= until;
    }

    public boolean overlap(final Interval other) {
        final boolean overlap1 = between(from, other.from, until);
        final boolean overlap2 = between(from, other.until, until);
        final boolean overlap3 = between(other.from, from, other.until);
        final boolean overlap4 = between(other.from, until, other.until);

        return overlap1 || overlap2 || overlap3 || overlap4;
    }

    public Interval combine(final Interval other) {
        return new Interval(
                Math.min(from, other.from),
                Math.max(until, other.until)
        );
    }

    private boolean between(final int lower, final int value, final int upper) {
        return lower <= value && value <= upper;
    }
}

class Util {
    private static final String SEPARATOR = " ";

    public static String[] readNonEmptyStrings(final BufferedReader in) throws IOException {
        final String line = readNonEmptyString(in);
        return line.split(SEPARATOR);
    }

    public static String readNonEmptyString(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line.isEmpty()) line = in.readLine();
        return line;
    }
}
