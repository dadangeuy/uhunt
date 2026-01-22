package uhunt.c4.g0.p11080;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 11080 - Place the Guards
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=2021
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        final int totalTestCases = Integer.parseInt(Util.readLine(in));
        for (int i = 0; i < totalTestCases; i++) {
            final String[] l1 = Util.readSplitLine(in);
            final int totalJunctions = Integer.parseInt(l1[0]);
            final int totalStreets = Integer.parseInt(l1[1]);
            final int[][] streets = new int[totalStreets][];
            for (int j = 0; j < totalStreets; j++) {
                final String[] l2 = Util.readSplitLine(in);
                final int fromJunction = Integer.parseInt(l2[0]);
                final int intoJunction = Integer.parseInt(l2[1]);
                streets[j] = new int[]{fromJunction, intoJunction};
            }

            final Input input = new Input(totalJunctions, totalStreets, streets);
            final Output output = process.process(input);

            out.write(String.format("%d\n", output.optionalMinimumTotalGuards.orElse(-1)));
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Util {
    public static String[] readSplitLine(final BufferedReader in) throws IOException {
        final String line = readLine(in);
        return line.split(" ");
    }

    public static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Input {
    public final int totalJunctions;
    public final int totalStreets;
    public final int[][] streets;

    public Input(final int totalJunctions, final int totalStreets, final int[][] streets) {
        this.totalJunctions = totalJunctions;
        this.totalStreets = totalStreets;
        this.streets = streets;
    }
}

class Output {
    public final Optional<Integer> optionalMinimumTotalGuards;

    public Output(final Optional<Integer> optionalMinimumTotalGuards) {
        this.optionalMinimumTotalGuards = optionalMinimumTotalGuards;
    }
}

class Process {
    public Output process(final Input input) {
        final Graph<Integer> graph = new Graph<>();
        for (int[] street : input.streets) {
            final int fromJunction = street[0];
            final int intoJunction = street[1];
            graph.add(fromJunction, intoJunction);
            graph.add(intoJunction, fromJunction);
        }

        final List<Integer> vertices = IntStream.range(0, input.totalJunctions)
                .boxed()
                .collect(Collectors.toList());

        final List<Set<Integer>> connectedComponents = findStronglyConnectedComponents(graph, vertices);

        int minimumTotalGuards = 0;
        for (final Set<Integer> connectedComponent : connectedComponents) {
            final int initialVertex = connectedComponent.iterator().next();
            final List<Set<Integer>> twoPartiteSets = findTwoPartiteSets(graph, initialVertex);
            if (twoPartiteSets.isEmpty()) {
                return new Output(Optional.empty());
            }
            final int minimumTotalPartiteVertices = twoPartiteSets.stream()
                    .filter(partiteSet -> !partiteSet.isEmpty())
                    .mapToInt(Set::size)
                    .min()
                    .orElse(1);
            minimumTotalGuards += minimumTotalPartiteVertices;
        }

        return new Output(Optional.of(minimumTotalGuards));
    }

    private List<Set<Integer>> findStronglyConnectedComponents(
            final Graph<Integer> graph,
            final List<Integer> vertices
    ) {
        final List<Set<Integer>> connectedComponents = new ArrayList<>();
        final Set<Integer> visitedVertices = new HashSet<>();

        for (final int vertex : vertices) {
            if (visitedVertices.contains(vertex)) continue;

            final Set<Integer> connectedVertices = breadthFirstSearch(graph, vertex);
            visitedVertices.addAll(connectedVertices);
            connectedComponents.add(connectedVertices);
        }

        return connectedComponents;
    }

    private Set<Integer> breadthFirstSearch(
            final Graph<Integer> graph,
            final int initialVertex
    ) {
        final Set<Integer> visitedVertices = new HashSet<>();
        final Queue<Integer> queueVertices = new LinkedList<>();

        visitedVertices.add(initialVertex);
        queueVertices.add(initialVertex);

        while (!queueVertices.isEmpty()) {
            final int currentVertex = queueVertices.remove();
            final List<Integer> unvisitedNextVertices = graph.get(currentVertex).stream()
                    .filter(n -> !visitedVertices.contains(n))
                    .collect(Collectors.toList());
            visitedVertices.addAll(unvisitedNextVertices);
            queueVertices.addAll(unvisitedNextVertices);
        }

        return visitedVertices;
    }

    private List<Set<Integer>> findTwoPartiteSets(
            final Graph<Integer> graph,
            final int initialVertex
    ) {
        final Queue<Integer> queueVertices = new LinkedList<>();
        final Set<Integer> partition1 = new HashSet<>();
        final Set<Integer> partition2 = new HashSet<>();

        queueVertices.add(initialVertex);
        partition1.add(initialVertex);

        while (!queueVertices.isEmpty()) {
            final int currentVertex = queueVertices.remove();
            final boolean isPartition1 = partition1.contains(currentVertex);
            final boolean isPartition2 = partition2.contains(currentVertex);

            final boolean isConflicted = isPartition1 && isPartition2;
            if (isConflicted) return Collections.emptyList();

            final Set<Integer> nextJunctions = graph.get(currentVertex);
            final Set<Integer> unvisitedNextJunctions = nextJunctions.stream()
                    .filter(nextJunction -> !partition1.contains(nextJunction))
                    .filter(nextJunction -> !partition2.contains(nextJunction))
                    .collect(Collectors.toSet());

            queueVertices.addAll(unvisitedNextJunctions);
            if (isPartition1) partition2.addAll(nextJunctions);
            if (isPartition2) partition1.addAll(nextJunctions);
        }

        return Arrays.asList(partition1, partition2);
    }
}

class Graph<V> {
    private final Map<V, Set<V>> edges = new HashMap<>();

    public void add(final V from, final V into) {
        edges.computeIfAbsent(from, k -> new HashSet<>()).add(into);
    }

    public Set<V> get(final V from) {
        return edges.getOrDefault(from, Collections.emptySet());
    }

    public void remove(final V from, final V into) {
        get(from).remove(into);
    }
}
