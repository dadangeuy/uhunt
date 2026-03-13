package uva.uhunt.c4.g4.p10544;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 10544 - Numbering the Paths
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1485
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        final int totalTestCases = in.nextInt();
        for (int i = 0; i < totalTestCases; i++) {
            final Input input = new Input();
            input.totalLetters = in.nextInt();
            input.totalRoads = in.nextInt();
            input.roads = new char[input.totalRoads][];
            for (int j = 0; j < input.totalRoads; j++) {
                input.roads[j] = in.next().toCharArray();
            }
            input.totalQueries = in.nextInt();
            input.queries = new char[input.totalQueries][];
            for (int j = 0; j < input.totalQueries; j++) {
                input.queries[j] = in.next().toCharArray();
            }

            final Output output = process.process(input);
            for (int j = 0; j < output.queries.length; j++) {
                final char[] query = output.queries[j];
                final int pathId = output.pathIdPerQuery[j];
                out.print(query);
                out.print(": ");
                out.println(pathId);
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalLetters;
    public int totalRoads;
    public char[][] roads;
    public int totalQueries;
    public char[][] queries;
}

class Output {
    public char[][] queries;
    public int[] pathIdPerQuery;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.queries = input.queries;
        output.pathIdPerQuery = new int[input.totalQueries];

        final int[][] edges = getEdges(input);
        final DirectedGraph graph = getGraph(edges);
        final int[][] queries = getQueries(input);

        final Memoization memoization = new Memoization();
        for (int i = 0; i < input.totalQueries; i++) {
            final int[] query = queries[i];
            final int totalLowerPaths = getTotalLowerPaths(memoization, graph, query, 0);
            final int pathId = totalLowerPaths + 1;
            output.pathIdPerQuery[i] = pathId;
        }

        return output;
    }

    private int[][] getEdges(final Input input) {
        final int[][] edges = new int[input.totalRoads][];
        for (int i = 0; i < input.totalRoads; i++) {
            final char[] road = input.roads[i];
            edges[i] = new int[]{road[0] - 'A', road[1] - 'A'};
        }
        return edges;
    }

    private DirectedGraph getGraph(final int[][] edges) {
        final DirectedGraph.Builder graph = new DirectedGraph.Builder(26);
        for (final int[] edge : edges) {
            graph.add(edge[0], edge[1]);
        }
        return graph.build();
    }

    private int[][] getQueries(final Input input) {
        final int[][] queries = new int[input.totalQueries][];
        for (int i = 0; i < input.totalQueries; i++) {
            queries[i] = new int[input.queries[i].length];
            for (int j = 0; j < input.queries[i].length; j++) {
                queries[i][j] = input.queries[i][j] - 'A';
            }
        }
        return queries;
    }

    private int getTotalLowerPaths(
        final Memoization memoization,
        final DirectedGraph graph,
        final int[] path,
        final int step
    ) {
        if (step == path.length) return 0;

        int totalLowerPaths = 0;
        final int vertex = path[step];
        for (final int next : graph.get(vertex)) {
            if (next == path[step + 1]) break;
            totalLowerPaths += getTotalPaths(memoization, graph, next);
        }

        return totalLowerPaths + getTotalLowerPaths(memoization, graph, path, step + 1);
    }

    private int getTotalPaths(
        final Memoization memoization,
        final DirectedGraph graph,
        final int current
    ) {
        if (graph.get(current).length == 0) return 1;
        if (memoization.contains(current)) return memoization.get(current);

        int totalPaths = 0;
        for (final int next : graph.get(current)) totalPaths += getTotalPaths(memoization, graph, next);
        memoization.set(current, totalPaths);

        return totalPaths;
    }
}

final class DirectedGraph {
    private final int[][] edges;

    private DirectedGraph(final Builder builder) {
        edges = new int[builder.edges.length][];
        for (int vertex = 0; vertex < builder.edges.length; vertex++) {
            final List<Integer> destinations = builder.edges[vertex] == null ? Collections.emptyList() : builder.edges[vertex];
            edges[vertex] = destinations.stream()
                .mapToInt(Integer::intValue)
                .distinct()
                .sorted()
                .toArray();
        }
    }

    public int[] get(final int from) {
        return edges[from];
    }

    static class Builder {
        private final LinkedList<Integer>[] edges;

        public Builder(final int maxVertex) {
            this.edges = new LinkedList[maxVertex + 1];
        }

        public void add(final int from, final int into) {
            if (edges[from] == null) edges[from] = new LinkedList<>();
            edges[from].add(into);
        }

        public DirectedGraph build() {
            return new DirectedGraph(this);
        }
    }
}

final class Memoization {
    private final int[] totalPathsPerVertex = new int[26];

    public void set(final int vertex, final int totalPaths) {
        totalPathsPerVertex[vertex] = totalPaths;
    }

    public int get(final int vertex) {
        return totalPathsPerVertex[vertex];
    }

    public boolean contains(final int vertex) {
        return totalPathsPerVertex[vertex] > 0;
    }
}
