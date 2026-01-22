package uhunt.c4.g0.p12950;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 12950 - Even Obsession
 * Time limit: 2.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4829
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        final Process process = new Process();

        while (true) {
            final Input input = new Input();

            final String[] l1 = readSplitLine(in);
            final boolean isEOF = l1 == null;
            if (isEOF) break;

            input.totalCities = Integer.parseInt(l1[0]);
            input.totalRoads = Integer.parseInt(l1[1]);

            input.roads = new int[input.totalRoads][];
            for (int i = 0; i < input.totalRoads; i++) {
                final String[] l2 = readSplitLine(in);
                final int[] road = new int[3];
                road[0] = Integer.parseInt(l2[0]);
                road[1] = Integer.parseInt(l2[1]);
                road[2] = Integer.parseInt(l2[2]);
                input.roads[i] = road;
            }

            final Output output = process.process(input);

            if (output.isPossible) out.write(Integer.toString(output.minimumTollValue));
            else out.write("-1");

            out.write('\n');
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
        final String line = in.readLine();
        return line == null || !line.isEmpty() ? line : readLine(in);
    }
}

class Input {
    public int totalCities;
    public int totalRoads;
    public int[][] roads;
}

class Output {
    public boolean isPossible;
    public int minimumTollValue;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final List<Road> roads = Arrays.stream(input.roads)
            .map(r -> {
                final Road road = new Road();
                road.city1 = r[0];
                road.city2 = r[1];
                road.tollValue = r[2];
                return road;
            })
            .collect(Collectors.toList());

        final Graph<Integer, Road> graph = new Graph<>();
        for (final Road road : roads) {
            graph.addBi(road.city1, road.city2, road);
        }

        final Map<Integer, Checkpoint> checkpointPerCity = findShortestPathUsingDijkstra(graph, 1);
        final Checkpoint finishCheckpoint = checkpointPerCity.get(input.totalCities);

        if (finishCheckpoint == null) {
            output.isPossible = false;
            output.minimumTollValue = -1;
        }
        else {
            output.isPossible = true;
            output.minimumTollValue = finishCheckpoint.totalTollValues;
        }

        return output;
    }

    private Map<Integer, Checkpoint> findShortestPathUsingDijkstra(
        final Graph<Integer, Road> graph,
        final int origin
    ) {
        final PriorityQueue<Checkpoint> queue = new PriorityQueue<>(Checkpoint.ORDER_BY_TOTAL_TOLL_VALUES);
        final Map<Integer, Checkpoint> oddCheckpointPerCity = new HashMap<>();
        final Map<Integer, Checkpoint> evenCheckpointPerCity = new HashMap<>();

        final Checkpoint startCheckpoint = new Checkpoint(origin, 0, 0);
        queue.add(startCheckpoint);
        evenCheckpointPerCity.put(origin, startCheckpoint);

        while (!queue.isEmpty()) {
            final Checkpoint currentCheckpoint = queue.remove();
            final int currentCity = currentCheckpoint.city;
            final int currentTotalRoads = currentCheckpoint.totalRoads;
            final int currentTotalTollValues = currentCheckpoint.totalTollValues;

            for (final int nextCity : graph.get(currentCity)) {
                final int nextTollValue = graph.get(currentCity, nextCity).get().tollValue;
                final Checkpoint nextCheckpoint = new Checkpoint(
                    nextCity,
                    currentTotalRoads + 1,
                    currentTotalTollValues + nextTollValue
                );

                final boolean isEvenNext = (nextCheckpoint.totalRoads & 1) == 0;
                final Map<Integer, Checkpoint> checkpointPerCity = isEvenNext ? evenCheckpointPerCity : oddCheckpointPerCity;

                final Checkpoint oldNextCheckpoint = checkpointPerCity.get(nextCity);
                if (oldNextCheckpoint == null || nextCheckpoint.isBetterThan(oldNextCheckpoint)) {
                    checkpointPerCity.put(nextCity, nextCheckpoint);
                    queue.add(nextCheckpoint);
                }
            }
        }

        return evenCheckpointPerCity;
    }
}

class Road {
    public int city1;
    public int city2;
    public int tollValue;
}

class Graph<V, E> {
    public Map<V, Map<V, E>> edges = new HashMap<>();

    public void addBi(final V vertex1, final V vertex2, final E edge) {
        addUni(vertex1, vertex2, edge);
        addUni(vertex2, vertex1, edge);
    }

    public void addUni(final V fromVertex, final V intoVertex, final E edge) {
        edges
            .computeIfAbsent(fromVertex, k -> new HashMap<>())
            .put(intoVertex, edge);
    }

    public Set<V> get(final V fromVertex) {
        return edges.getOrDefault(fromVertex, Collections.emptyMap()).keySet();
    }

    public Optional<E> get(final V fromVertex, final V intoVertex) {
        return Optional.ofNullable(edges.getOrDefault(fromVertex, Collections.emptyMap()).get(intoVertex));
    }
}

class Checkpoint {
    public static final Comparator<Checkpoint> ORDER_BY_TOTAL_TOLL_VALUES = Comparator
        .comparingInt((Checkpoint c) -> c.totalTollValues);

    public final int city;
    public final int totalRoads;
    public final int totalTollValues;

    public Checkpoint(final int city, final int totalRoads, final int totalTollValues) {
        this.city = city;
        this.totalRoads = totalRoads;
        this.totalTollValues = totalTollValues;
    }

    public boolean isBetterThan(final Checkpoint other) {
        return totalTollValues < other.totalTollValues;
    }
}
