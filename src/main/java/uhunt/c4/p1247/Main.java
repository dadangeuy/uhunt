package uhunt.c4.p1247;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

/**
 * 1247 - Interstar Transport
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3688
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        final Input input = new Input();
        input.totalPlanets = in.nextInt();

        input.totalTransports = in.nextInt();
        input.transports = new Transport[input.totalTransports];
        for (int i = 0; i < input.totalTransports; i++) {
            final Transport transport = new Transport();
            transport.planet1 = in.next().charAt(0);
            transport.planet2 = in.next().charAt(0);
            transport.cost = in.nextInt();
            input.transports[i] = transport;
        }

        input.totalQueries = in.nextInt();
        input.queries = new Query[input.totalQueries];
        for (int i = 0; i < input.totalQueries; i++) {
            final Query query = new Query();
            query.origin = in.next().charAt(0);
            query.destination = in.next().charAt(0);
            input.queries[i] = query;
        }

        final Output output = process.process(input);
        for (final QueryResult result : output.results) {
            out.write(result.path[0]);
            for (int i = 1; i < result.path.length; i++) {
                out.write(' ');
                out.write(result.path[i]);
            }
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalPlanets;
    public int totalTransports;
    public Transport[] transports;
    public int totalQueries;
    public Query[] queries;
}

class Output {
    public QueryResult[] results;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.results = new QueryResult[input.totalQueries];

        final Graph<Character, Transport> graph = new Graph<>();
        for (final Transport transport : input.transports) {
            graph.addBi(transport.planet1, transport.planet2, transport);
        }

        for (int i = 0; i < input.totalQueries; i++) {
            final Query query = input.queries[i];
            final QueryResult result = new QueryResult();

            result.path = findShortestPathUsingDijkstra(
                graph,
                query.origin,
                query.destination
            );
            output.results[i] = result;
        }

        return output;
    }

    private char[] findShortestPathUsingDijkstra(
        final Graph<Character, Transport> graph,
        final char origin,
        final char destination
    ) {
        final Checkpoint start = new Checkpoint().addLast(origin, 0);
        final Map<Character, Checkpoint> checkpointPerPlanet = new HashMap<>();
        checkpointPerPlanet.put(origin, start);

        findShortestPathUsingDijkstra(graph, start, checkpointPerPlanet);

        final Checkpoint finish = checkpointPerPlanet.get(destination);
        return finish.path;
    }

    private void findShortestPathUsingDijkstra(
        final Graph<Character, Transport> graph,
        final Checkpoint checkpoint,
        final Map<Character, Checkpoint> checkpointPerPlanet
    ) {
        final PriorityQueue<Checkpoint> queue = new PriorityQueue<>(Checkpoint.ORDER_BY_TOTAL_COSTS_AND_PATH);
        queue.add(checkpoint);

        while (!queue.isEmpty()) {
            final Checkpoint currentCheckpoint = queue.remove();
            final char currentPlanet = currentCheckpoint.getLastPlanet();
            for (final char nextPlanet : graph.get(currentPlanet)) {
                final int cost = graph.get(currentPlanet, nextPlanet).get().cost;
                final Checkpoint oldCheckpoint = checkpointPerPlanet.get(nextPlanet);

                final Checkpoint nextCheckpoint = currentCheckpoint.addLast(nextPlanet, cost);
                if (oldCheckpoint == null || nextCheckpoint.isBetterThan(oldCheckpoint)) {
                    checkpointPerPlanet.put(nextPlanet, nextCheckpoint);
                    queue.add(nextCheckpoint);
                }
            }
        }
    }
}

class Transport {
    public char planet1;
    public char planet2;
    public int cost;
}

class Query {
    public char origin;
    public char destination;
}

class QueryResult {
    public char[] path;
}

class Graph<V, E> {
    public final Map<V, Map<V, E>> edges = new HashMap<>();

    public void addBi(final V vertex1, final V vertex2, final E edge) {
        addUni(vertex1, vertex2, edge);
        addUni(vertex2, vertex1, edge);
    }

    public void addUni(final V fromVertex, final V intoVertex, final E edge) {
        edges.computeIfAbsent(fromVertex, k -> new HashMap<>()).put(intoVertex, edge);
    }

    public Set<V> get(final V fromVertex) {
        return edges.getOrDefault(fromVertex, Collections.emptyMap()).keySet();
    }

    public Optional<E> get(final V fromVertex, final V intoVertex) {
        return Optional.ofNullable(edges.getOrDefault(fromVertex, Collections.emptyMap()).get(intoVertex));
    }
}

class Checkpoint {
    public static final Comparator<Checkpoint> ORDER_BY_TOTAL_COSTS_AND_PATH = Comparator
        .comparingInt((Checkpoint c) -> c.totalCosts)
        .thenComparing((Checkpoint c) -> new String(c.path));
    public final char[] path;
    public final int totalCosts;

    public Checkpoint() {
        this(new char[0], 0);
    }

    public Checkpoint(final char[] path, final int totalCosts) {
        this.path = path;
        this.totalCosts = totalCosts;
    }

    public Checkpoint addLast(final char planet, final int cost) {
        final char[] newPath = Arrays.copyOf(path, path.length + 1);
        newPath[newPath.length - 1] = planet;
        final int newTotalCosts = totalCosts + cost;
        return new Checkpoint(newPath, newTotalCosts);
    }

    public Checkpoint removeLast(final int cost) {
        final char[] newPath = Arrays.copyOf(path, path.length - 1);
        final int newTotalCosts = totalCosts - cost;
        return new Checkpoint(newPath, newTotalCosts);
    }

    public boolean isBetterThan(final Checkpoint other) {
        if (totalCosts < other.totalCosts) return true;
        if (totalCosts == other.totalCosts && path.length < other.path.length) return true;
        return false;
    }

    public char getLastPlanet() {
        return path[path.length - 1];
    }
}
