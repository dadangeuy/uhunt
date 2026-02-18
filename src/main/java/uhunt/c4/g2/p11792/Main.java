package uhunt.c4.g2.p11792;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;

/**
 * 11792 - Krochanska is Here!
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2892
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final FastReader in = new FastReader(System.in, 1 << 8);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 8);
        final Process process = new Process();

        final int totalTestCases = in.nextInt();
        for (int i = 0; i < totalTestCases; i++) {
            final Input input = new Input();
            input.totalStations = in.nextInt();
            input.totalLines = in.nextInt();
            input.lines = new int[input.totalLines][];
            for (int j = 0; j < input.totalLines; j++) {
                final LinkedList<Integer> line = new LinkedList<>();
                for (int station = in.nextInt(); station != 0; station = in.nextInt()) {
                    line.addLast(station);
                }
                input.lines[j] = line.stream().mapToInt(s -> s).toArray();
            }

            final Output output = process.process(input);
            out.write(String.format("Krochanska is in: %d\n", output.location));
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalStations;
    public int totalLines;
    public int[][] lines;
}

class Output {
    public int location;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final Graph railwayGraph = getRailwayGraph(input.lines, input.totalStations);
        final boolean[] isTransit = getIsTransit(input.lines, input.totalStations);
        output.location = getTransitStationWithMinimumTransitDistance(railwayGraph, isTransit, input.totalStations);

        return output;
    }

    private Graph getRailwayGraph(final int[][] lines, final int totalStations) {
        final Graph.Builder builder = new Graph.Builder(totalStations + 1);
        for (final int[] line : lines) {
            for (int i = 0, j = 1; j < line.length; i++, j++) {
                final int prevStation = line[i];
                final int nextStation = line[j];
                builder.addBi(prevStation, nextStation);
            }
        }
        return builder.build();
    }

    private boolean[] getIsTransit(final int[][] lines, final int totalStations) {
        final int[] totalTransits = getTotalTransits(lines, totalStations);
        final boolean[] isTransit = new boolean[totalStations + 1];
        for (int station = 1; station <= totalStations; station++) {
            isTransit[station] = totalTransits[station] > 1;
        }
        return isTransit;
    }

    private int[] getTotalTransits(final int[][] lines, final int totalStations) {
        final int[] counts = new int[totalStations + 1];
        for (final int[] line : lines) {
            for (final int station : line) {
                counts[station]++;
            }
        }
        return counts;
    }

    // Find transit station with minimum total distances to other transit stations
    private int getTransitStationWithMinimumTransitDistance(
        final Graph railwayGraph,
        final boolean[] isTransit,
        final int totalStations
    ) {
        int minStation = -1;
        int minTotalDistances = Integer.MAX_VALUE;

        for (int station = 1; station <= totalStations; station++) {
            if (isTransit[station]) {
                final int totalDistances = getTotalDistances(railwayGraph, station, isTransit, totalStations);
                final boolean better1 = totalDistances < minTotalDistances;
                final boolean better2 = totalDistances == minTotalDistances && station < minStation;

                if (better1 || better2) {
                    minStation = station;
                    minTotalDistances = totalDistances;
                }
            }
        }

        return minStation;
    }

    // Get total distance using breadth-first Search
    private int getTotalDistances(
        final Graph railwayGraph,
        final int origin,
        final boolean[] isTransit,
        final int totalStations
    ) {
        int totalDistances = 0;

        final boolean[] isVisited = new boolean[totalStations + 1];
        final Queue<Integer> stationq = new LinkedList<>();
        final Queue<Integer> distanceq = new LinkedList<>();

        isVisited[origin] = true;
        stationq.add(origin);
        distanceq.add(0);

        while (!stationq.isEmpty()) {
            final int station = stationq.remove();
            final int distance = distanceq.remove();

            for (final int nextStation : railwayGraph.get(station)) {
                if (!isVisited[nextStation]) {
                    isVisited[nextStation] = true;
                    totalDistances += isTransit[nextStation] ? distance : 0;

                    stationq.add(nextStation);
                    distanceq.add(distance + 1);
                }
            }
        }

        return totalDistances;
    }
}

class Graph {
    private final int[][] edges;

    public Graph(Builder builder) {
        edges = new int[builder.edges.length][];
        for (int i = 0; i < builder.edges.length; i++) {
            if (builder.edges[i] == null) {
                edges[i] = new int[0];
            } else {
                edges[i] = builder.edges[i].stream().mapToInt(v -> v).toArray();
            }
        }
    }

    public int[] get(final int from) {
        return edges[from];
    }

    static class Builder {
        private final LinkedList<Integer>[] edges;

        public Builder(final int totalVertices) {
            this.edges = new LinkedList[totalVertices];
        }

        public void addBi(final int first, final int second) {
            addUni(first, second);
            addUni(second, first);
        }

        public void addUni(final int from, final int into) {
            if (edges[from] == null) edges[from] = new LinkedList<>();
            edges[from].add(into);
        }

        public Graph build() {
            return new Graph(this);
        }
    }
}

class FastReader {
    private final InputStream stream;
    private final byte[] buffer;
    private int length = 0;
    private int offset = 0;
    private final byte[] readBuffer;

    public FastReader(InputStream stream, int bufferSize) {
        this.stream = stream;
        this.buffer = new byte[bufferSize];
        this.readBuffer = new byte[bufferSize];
    }

    public boolean hasNextInt() throws IOException {
        skipUntil(b -> !Character.isWhitespace(b));
        return !end() && (buffer[offset] == '-' || Character.isDigit(buffer[offset]));
    }

    public int nextInt() throws IOException {
        if (!hasNextInt()) return 0;

        boolean negative = buffer[offset] == '-';
        if (negative) offset++;

        int length = readWhile(Character::isDigit, readBuffer);
        int value = 0;
        for (int i = 0; i < length; i++) value = value * 10 + readBuffer[i] - '0';
        return negative ? -value : value;
    }

    public boolean hasNext() throws IOException {
        skipUntil(b -> !Character.isWhitespace(b));
        return !end();
    }

    public String next() throws IOException {
        if (!hasNext()) return "";

        int length = readWhile(b -> !Character.isWhitespace(b), readBuffer);
        return new String(readBuffer, 0, length);
    }

    private void skipUntil(Function<Byte, Boolean> criteria) throws IOException {
        while (true) {
            if (exhausted()) read();
            if (end()) return;
            if (criteria.apply(buffer[offset])) return;
            ++offset;
        }
    }

    private int readWhile(Function<Byte, Boolean> criteria, byte[] output) throws IOException {
        for (int i = 0; ; i++) {
            if (exhausted()) read();
            if (end()) return i;
            if (!criteria.apply(buffer[offset])) return i;
            output[i] = buffer[offset];
            offset++;
        }
    }

    private boolean end() {
        return length == -1;
    }

    private boolean exhausted() {
        return offset >= length;
    }

    private void read() throws IOException {
        length = stream.read(buffer);
        offset = 0;
    }

    public void close() throws IOException {
        stream.close();
    }
}