package uva.uhunt.c4.g8.p10048;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 10048 - Audiophobia
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=989
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        for (int caseId = 1; ; caseId++) {
            final String[] l1 = readSplitLine(in);
            final int totalCrossings = Integer.parseInt(l1[0]);
            final int totalStreets = Integer.parseInt(l1[1]);
            final int totalQueries = Integer.parseInt(l1[2]);

            final boolean isEOF = totalCrossings == 0 && totalStreets == 0 && totalQueries == 0;
            if (isEOF) break;

            final Street[] streets = new Street[totalStreets];
            for (int i = 0; i < totalStreets; i++) {
                final String[] l2 = readSplitLine(in);
                final int crossing1 = Integer.parseInt(l2[0]);
                final int crossing2 = Integer.parseInt(l2[1]);
                final int soundIntensity = Integer.parseInt(l2[2]);
                final Street street = new Street(new int[]{crossing1, crossing2}, soundIntensity);
                streets[i] = street;
            }

            final Query[] queries = new Query[totalQueries];
            for (int i = 0; i < totalQueries; i++) {
                final String[] l3 = readSplitLine(in);
                final int fromCrossing = Integer.parseInt(l3[0]);
                final int intoCrossing = Integer.parseInt(l3[1]);
                final Query query = new Query(fromCrossing, intoCrossing);
                queries[i] = query;
            }

            final Input input = new Input(caseId, totalCrossings, totalStreets, totalQueries, streets, queries);
            final Output output = process.process(input);

            if (output.caseId > 1) out.write('\n');
            out.write(String.format("Case #%d\n", output.caseId));
            for (final Query.Result result : output.queryResults) {
                if (result.hasPath) {
                    out.write(Integer.toString(result.minimumSoundIntensity));
                    out.write('\n');
                } else {
                    out.write("no path\n");
                }
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

class Street {
    public final int[] crossings;
    public final int soundIntensity;

    public Street(final int[] crossings, final int soundIntensity) {
        this.crossings = crossings;
        this.soundIntensity = soundIntensity;
    }
}

class Query {
    public final int fromCrossing;
    public final int intoCrossing;

    public Query(final int fromCrossing, final int intoCrossing) {
        this.fromCrossing = fromCrossing;
        this.intoCrossing = intoCrossing;
    }

    public static class Result {
        public final boolean hasPath;
        public final int minimumSoundIntensity;

        public Result(
                final boolean hasPath,
                final int minimumSoundIntensity
        ) {
            this.hasPath = hasPath;
            this.minimumSoundIntensity = minimumSoundIntensity;
        }
    }
}

class Input {
    public final int caseId;
    public final int totalCrossings;
    public final int totalStreets;
    public final int totalQueries;
    public final Street[] streets;
    public final Query[] queries;

    public Input(
            final int caseId,
            final int totalCrossings,
            final int totalStreets,
            final int totalQueries,
            final Street[] streets,
            final Query[] queries
    ) {
        this.caseId = caseId;
        this.totalCrossings = totalCrossings;
        this.totalStreets = totalStreets;
        this.totalQueries = totalQueries;
        this.streets = streets;
        this.queries = queries;
    }
}

class Output {
    public final int caseId;
    public final Query.Result[] queryResults;

    public Output(
            final int caseId,
            final Query.Result[] queryResults
    ) {
        this.caseId = caseId;
        this.queryResults = queryResults;
    }
}

/**
 * 1. create a minimum spanning tree
 * 2. dfs origin to destination
 * 3. calculate maximum sound intensity as we go
 */
class Process {
    public Output process(final Input input) {
        final Graph<Integer, Street> minimumSpanningTree = buildMinimumSpanningTreeWithKruskal(input.streets);
        final Query.Result[] results = new Query.Result[input.totalQueries];

        for (int i = 0; i < input.totalQueries; i++) {
            final Query query = input.queries[i];
            final List<Street> path = depthFirstSearch(minimumSpanningTree, query.fromCrossing, query.intoCrossing);

            if (path == null) {
                results[i] = new Query.Result(false, -1);
            } else {
                final int maximumSoundIntensity = path.stream().mapToInt(s -> s.soundIntensity).max().orElse(-1);
                results[i] = new Query.Result(true, maximumSoundIntensity);
            }
        }

        return new Output(input.caseId, results);
    }

    private Graph<Integer, Street> buildMinimumSpanningTreeWithKruskal(final Street[] streets) {
        final Graph<Integer, Street> minimumSpanningTree = new Graph<>();
        final DisjointSet<Integer> disjointSet = new DisjointSet<>();

        final Comparator<Street> orderBySoundIntensity = Comparator.comparingInt(s -> s.soundIntensity);
        final PriorityQueue<Street> queue = new PriorityQueue<>(orderBySoundIntensity);
        queue.addAll(Arrays.asList(streets));

        while (!queue.isEmpty()) {
            final Street street = queue.remove();

            final int group1 = disjointSet.find(street.crossings[0]);
            final int group2 = disjointSet.find(street.crossings[1]);
            final boolean isConnected = group1 == group2;
            if (isConnected) continue;

            minimumSpanningTree.addBiDirection(street.crossings[0], street.crossings[1], street);
            disjointSet.union(street.crossings[0], street.crossings[1]);
        }

        return minimumSpanningTree;
    }

    private List<Street> depthFirstSearch(
            final Graph<Integer, Street> graph,
            final int fromCrossing,
            final int intoCrossing
    ) {
        final Set<Integer> visited = new HashSet<>();
        visited.add(fromCrossing);

        return doDepthFirstSearch(graph, fromCrossing, intoCrossing, visited, new LinkedList<>());
    }

    private List<Street> doDepthFirstSearch(
            final Graph<Integer, Street> graph,
            final int fromCrossing,
            final int intoCrossing,
            final Set<Integer> visited,
            final LinkedList<Street> path
    ) {
        if (fromCrossing == intoCrossing) {
            return new ArrayList<>(path);
        }

        for (final Street road : graph.get(fromCrossing)) {
            for (int nextCrossing : road.crossings) {
                if (visited.contains(nextCrossing)) continue;

                visited.add(nextCrossing);
                path.addLast(road);
                final List<Street> completedPath = doDepthFirstSearch(graph, nextCrossing, intoCrossing, visited, path);
                path.removeLast();

                if (completedPath != null) return completedPath;
            }
        }

        return null;
    }
}

class Graph<V, E> {
    private final Map<V, Map<V, E>> edges = new HashMap<>();

    public void addBiDirection(final V between1, final V between2, final E edge) {
        addUniDirection(between1, between2, edge);
        addUniDirection(between2, between1, edge);
    }

    public void addUniDirection(final V from, final V into, final E edge) {
        edges.computeIfAbsent(from, k -> new HashMap<>()).put(into, edge);
    }

    public Collection<E> get(final V from) {
        return edges.getOrDefault(from, Collections.emptyMap()).values();
    }

    public void remove(final V from, final V into) {
        edges.getOrDefault(from, Collections.emptyMap()).remove(into);
    }

    public boolean contains(final V from) {
        return edges.containsKey(from);
    }
}

class DisjointSet<V> {
    private final Map<V, V> parents = new HashMap<>();

    public V find(final V item) {
        final V parent = parents.getOrDefault(item, item);
        return parent == item ? item : find(parent);
    }

    public void union(final V item1, final V item2) {
        final V parent1 = find(item1);
        final V parent2 = find(item2);
        parents.put(parent2, parent1);
    }
}
