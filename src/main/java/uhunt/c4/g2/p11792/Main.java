package uhunt.c4.g2.p11792;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        final Set<Integer> importantStations = findImportantStations(input.lines, input.totalStations);
        final Graph<Integer, Integer> railwayGraph = createRailwayGraph(input.lines, importantStations);
        output.location = getOptimalStation(railwayGraph, importantStations);

        return output;
    }

    private Set<Integer> findImportantStations(final int[][] lines, final int totalStations) {
        final int[] counts = counts(lines, totalStations);
        return IntStream.rangeClosed(1, totalStations)
            .filter(station -> counts[station] > 1)
            .boxed()
            .collect(Collectors.toSet());
    }

    private int[] counts(final int[][] lines, final int totalStations) {
        final int[] counts = new int[totalStations + 1];
        for (final int[] line : lines) {
            Arrays.stream(line)
                .distinct()
                .forEach(station -> counts[station]++);
        }
        return counts;
    }

    // Sliding Window
    private Graph<Integer, Integer> createRailwayGraph(final int[][] lines, final Set<Integer> importantStations) {
        final Graph<Integer, Integer> railwayGraph = new Graph<>();

        for (final int[] line : lines) {
            final int[] importantIndexes = IntStream.range(0, line.length)
                .filter(index -> importantStations.contains(line[index]))
                .toArray();

            for (int i = 0, j = 1; j < importantIndexes.length; i++, j++) {
                final int prevIndex = importantIndexes[i];
                final int nextIndex = importantIndexes[j];

                final int prevStation = line[prevIndex];
                final int nextStation = line[nextIndex];

                final int oldDistance = railwayGraph.get(prevStation, nextStation).orElse(Integer.MAX_VALUE);
                final int newDistance = 2 * (nextIndex - prevIndex);
                final int minDistance = Math.min(oldDistance, newDistance);
                railwayGraph.addBi(prevStation, nextStation, minDistance);
            }
        }

        return railwayGraph;
    }

    private int getOptimalStation(final Graph<Integer, Integer> railwayGraph, final Set<Integer> importantStations) {
        int minStation = -1;
        int minTotalDistances = Integer.MAX_VALUE;

        for (final int station : importantStations) {
            final int totalDistances = getTotalDistances(railwayGraph, station);
            final boolean better1 = totalDistances < minTotalDistances;
            final boolean better2 = totalDistances == minTotalDistances && station < minStation;

            if (better1 || better2) {
                minStation = station;
                minTotalDistances = totalDistances;
            }
        }

        return minStation;
    }

    // Dijkstra
    private int getTotalDistances(final Graph<Integer, Integer> railwayGraph, final int originStation) {
        int totalDistances = 0;
        final Set<Integer> visited = new HashSet<>();
        final PriorityQueue<Progress> queue = new PriorityQueue<>(Progress.ORDER_BY_DISTANCE);

        final Progress initialProgress = new Progress(originStation, 0);
        queue.add(initialProgress);

        while (!queue.isEmpty()) {
            final Progress progress = queue.remove();

            if (visited.contains(progress.station)) continue;

            visited.add(progress.station);
            totalDistances += progress.distance;

            for (final int nextStation : railwayGraph.get(progress.station)) {
                if (visited.contains(nextStation)) continue;

                final int nextDistance = railwayGraph.get(progress.station, nextStation).get();
                final Progress nextProgress = new Progress(nextStation, progress.distance + nextDistance);
                queue.add(nextProgress);
            }
        }

        return totalDistances;
    }
}

class Graph<V, E> {
    private final Map<V, Map<V, E>> edges = new HashMap<>();

    public void addBi(final V first, final V second, final E edge) {
        addUni(first, second, edge);
        addUni(second, first, edge);
    }

    public void addUni(final V from, final V into, final E edge) {
        edges.computeIfAbsent(from, k -> new HashMap<>()).put(into, edge);
    }

    public Set<V> get() {
        return edges.keySet();
    }

    public Set<V> get(final V from) {
        return edges.getOrDefault(from, Collections.emptyMap()).keySet();
    }

    public Optional<E> get(final V from, final V into) {
        return Optional.ofNullable(edges.getOrDefault(from, Collections.emptyMap()).get(into));
    }
}

class Progress {
    public static final Comparator<Progress> ORDER_BY_DISTANCE = Comparator.comparingInt(p -> p.distance);

    public final int station;
    public final int distance;

    public Progress(final int station, final int distance) {
        this.station = station;
        this.distance = distance;
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