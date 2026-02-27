package uva.uhunt.c4.g8.p11838;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * 11838 - Come and Go
 * Time limit: 2.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2938
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        while (true) {
            final int totalIntersections = in.nextInt();
            final int totalStreets = in.nextInt();

            final boolean isEOF = totalIntersections == 0 && totalStreets == 0;
            if (isEOF) break;

            final int[][] streets = new int[totalStreets][];
            for (int i = 0; i < totalStreets; i++) {
                final int intersection1 = in.nextInt();
                final int intersection2 = in.nextInt();
                final int direction = in.nextInt();

                final int[] street = new int[]{intersection1, intersection2, direction};
                streets[i] = street;
            }

            final Input input = new Input(totalIntersections, totalStreets, streets);
            final Output output = process.process(input);

            if (output.isConnectedCity) {
                out.println(1);
            } else {
                out.println(0);
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public final int totalIntersections;
    public final int totalStreets;
    public final int[][] streets;

    public Input(final int totalIntersections, final int totalStreets, final int[][] streets) {
        this.totalIntersections = totalIntersections;
        this.totalStreets = totalStreets;
        this.streets = streets;
    }
}

class Output {
    public final boolean isConnectedCity;

    public Output(final boolean isConnectedCity) {
        this.isConnectedCity = isConnectedCity;
    }
}

class Process {
    public Output process(final Input input) {
        final Graph<Integer> graph = createGraph(input.streets);
        final Graph<Integer> transposeGraph = createTransposeGraph(input.streets);

        final int[] vertices = IntStream.rangeClosed(1, input.totalIntersections).toArray();
        final LinkedList<LinkedList<Integer>> listSCC = findSCCWithKosaraju(vertices, graph, transposeGraph);

        final boolean isStronglyConnectedGraph = listSCC.size() == 1;
        return new Output(isStronglyConnectedGraph);
    }

    private Graph<Integer> createTransposeGraph(final int[][] streets) {
        final int[][] transposeStreets = Arrays.stream(streets)
            .map(street -> new int[]{street[1], street[0], street[2]})
            .toArray(int[][]::new);
        return createGraph(transposeStreets);
    }

    private Graph<Integer> createGraph(final int[][] streets) {
        final Graph<Integer> graph = new Graph<>();

        for (final int[] street : streets) {
            final int junction1 = street[0];
            final int junction2 = street[1];
            final int direction = street[2];

            if (direction == 1) {
                graph.addUni(junction1, junction2);
            } else if (direction == 2) {
                graph.addBi(junction1, junction2);
            }
        }

        return graph;
    }

    private LinkedList<LinkedList<Integer>> findSCCWithKosaraju(
        final int[] vertices,
        final Graph<Integer> graph,
        final Graph<Integer> transposeGraph
    ) {
        final Set<Integer> visited = new HashSet<>();
        final LinkedList<Integer> sequence = new LinkedList<>();

        for (final int vertex : vertices) {
            if (visited.contains(vertex)) continue;
            dfs(graph, vertex, visited, sequence);
        }

        final LinkedList<LinkedList<Integer>> listSCC = new LinkedList<>();
        visited.clear();
        for (final Iterator<Integer> it = sequence.descendingIterator(); it.hasNext(); ) {
            final int intersection = it.next();
            if (visited.contains(intersection)) continue;

            final LinkedList<Integer> scc = new LinkedList<>();
            dfs(transposeGraph, intersection, visited, scc);
            listSCC.add(scc);
        }

        return listSCC;
    }

    private void dfs(
        final Graph<Integer> graph,
        final int vertex,
        final Set<Integer> visited,
        final LinkedList<Integer> sequence
    ) {
        if (visited.contains(vertex)) return;

        visited.add(vertex);
        graph.get(vertex).forEach(nextVertex -> dfs(graph, nextVertex, visited, sequence));
        sequence.addLast(vertex);
    }
}

class Graph<V> {
    private final Map<V, Set<V>> edges = new HashMap<>();

    public void addBi(final V first, final V second) {
        addUni(first, second);
        addUni(second, first);
    }

    public void addUni(final V from, final V into) {
        edges.computeIfAbsent(from, k -> new HashSet<>()).add(into);
    }

    public Set<V> get(final V from) {
        return edges.getOrDefault(from, Collections.emptySet());
    }
}