package uva.uhunt.c4.g7.p12047;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Function;

/**
 * 12047 - Highest Paid Toll
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=3198
 * NOTE: Solution is correct, but has a strict time limit, thus leading to intermittent TLE.
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final FastReader in = new FastReader(System.in, 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        final int totalCases = in.nextInt();
        for (int i = 0; i < totalCases; i++) {
            final Input input = new Input();
            input.totalPlaces = in.nextInt();
            input.totalRoads = in.nextInt();
            input.startPlace = in.nextInt();
            input.finishPlace = in.nextInt();
            input.totalMoney = in.nextInt();

            input.roads = new int[input.totalRoads][3];
            for (int j = 0; j < input.totalRoads; j++) {
                input.roads[j][0] = in.nextInt();
                input.roads[j][1] = in.nextInt();
                input.roads[j][2] = in.nextInt();
            }

            final Output output = process.process(input);
            out.write(Integer.toString(output.maximumToll));
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    // 2..10^4
    public int totalPlaces;
    // 1..10^5
    public int totalRoads;
    public int startPlace;
    public int finishPlace;
    // 1..10^6
    public int totalMoney;
    // start, end, cost
    public int[][] roads;
}

class Output {
    public int maximumToll;
}

class Process {
    private static final Comparator<int[]> SORT_BY_DISTANCE_ASC = Comparator.comparingInt((int[] s) -> s[0]);

    public Output process(final Input input) {
        final Output output = new Output();

        final Map<Integer, Map<Integer, Integer>> startGraph = new HashMap<>();
        for (final int[] road : input.roads) {
            startGraph
                    .computeIfAbsent(road[0], k -> new LinkedHashMap<>())
                    .put(road[1], road[2]);
        }

        final Map<Integer, Integer> startCostPerVertex = getDistancePerVertexUsingDijkstra(
                startGraph,
                input.startPlace,
                input.totalMoney
        );

        final boolean isFinished = startCostPerVertex.containsKey(input.finishPlace);
        if (!isFinished) {
            output.maximumToll = -1;
            return output;
        }

        final Map<Integer, Map<Integer, Integer>> finishGraph = new HashMap<>();
        for (final int[] road : input.roads) {
            finishGraph
                    .computeIfAbsent(road[1], k -> new LinkedHashMap<>())
                    .put(road[0], road[2]);
        }

        final Map<Integer, Integer> finishCostPerVertex = getDistancePerVertexUsingDijkstra(
                finishGraph,
                input.finishPlace,
                input.totalMoney
        );

        for (final int[] road : input.roads) {
            final int startCost = startCostPerVertex.getOrDefault(road[0], Integer.MAX_VALUE);
            final int finishCost = finishCostPerVertex.getOrDefault(road[1], Integer.MAX_VALUE);
            final int roadCost = road[2];

            final boolean noRoute = startCost == Integer.MAX_VALUE || finishCost == Integer.MAX_VALUE;
            if (noRoute) continue;

            final int totalCost = startCost + roadCost + finishCost;
            final boolean isNotEnoughMoney = totalCost > input.totalMoney;
            if (isNotEnoughMoney) continue;

            output.maximumToll = Math.max(output.maximumToll, roadCost);
        }

        return output;
    }

    private Map<Integer, Integer> getDistancePerVertexUsingDijkstra(
            final Map<Integer, Map<Integer, Integer>> graph,
            final int startVertex,
            final int maxDistance
    ) {
        final PriorityQueue<int[]> pendingStates = new PriorityQueue<>(SORT_BY_DISTANCE_ASC);

        final Map<Integer, Integer> distancePerVertex = new HashMap<>();

        final int initialDistance = 0;
        final int[] initialState = new int[]{initialDistance, startVertex};
        pendingStates.add(initialState);
        distancePerVertex.put(startVertex, initialDistance);

        while (!pendingStates.isEmpty()) {
            final int[] state = pendingStates.remove();
            final int distance = state[0];
            final int vertex = state[1];

            final int oldDistance = distancePerVertex.getOrDefault(vertex, Integer.MAX_VALUE);
            final boolean isMoreExpensive = distance > oldDistance;
            if (isMoreExpensive) continue;

            final boolean noEdges = !graph.containsKey(vertex);
            if (noEdges) continue;

            for (Map.Entry<Integer, Integer> entry : graph.get(vertex).entrySet()) {
                final int nextVertex = entry.getKey();
                final int roadDistance = entry.getValue();

                final int nextDistance = distance + roadDistance;
                final int nextOldDistance = distancePerVertex.getOrDefault(nextVertex, Integer.MAX_VALUE);
                final boolean isMoreExpensiveNext = nextDistance >= nextOldDistance;
                if (isMoreExpensiveNext) continue;

                final boolean isTooExpensiveNext = nextDistance > maxDistance;
                if (isTooExpensiveNext) continue;

                final int[] nextState = new int[]{nextDistance, nextVertex};
                pendingStates.add(nextState);
                distancePerVertex.put(nextVertex, nextDistance);
            }
        }

        return distancePerVertex;
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
