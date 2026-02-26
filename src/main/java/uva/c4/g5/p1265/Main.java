package uva.c4.g5.p1265;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 1265 - Tour Belt
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=3706
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        final int totalTestCases = in.nextInt();
        for (int i = 0; i < totalTestCases; i++) {
            final Input input = new Input();
            input.totalVertices = in.nextInt();
            input.totalEdges = in.nextInt();
            input.edges = new int[input.totalEdges][];
            for (int j = 0; j < input.totalEdges; j++) {
                final int[] edge = new int[3];
                edge[0] = in.nextInt();
                edge[1] = in.nextInt();
                edge[2] = in.nextInt();
                input.edges[j] = edge;
            }

            final Output output = process.process(input);
            out.write(Integer.toString(output.totalCandidateVertices));
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalVertices;
    public int totalEdges;
    public int[][] edges;
}

class Output {
    public int totalCandidateVertices;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final List<Edge> edges = getEdges(input);
        final List<Edge> minimumSpanningTreeEdges = getMinimumSpanningTreeOfMaximumWeight(edges);
        final DisjointSet<Integer> sccSet = new DisjointSet<>();
        final Map<Integer, Set<Integer>> verticesPerSCC = new HashMap<>();

        // loop edge from the greatest weight
        // this will create subgraph consisting of greatest edge at every iteration
        for (final Edge edge : minimumSpanningTreeEdges) {
            // find scc subgraph of vertex1 and vertex2
            final int scc1 = sccSet.find(edge.vertex1);
            final int scc2 = sccSet.find(edge.vertex2);
            final Set<Integer> vertices1 = verticesPerSCC.getOrDefault(scc1, Collections.singleton(scc1));
            final Set<Integer> vertices2 = verticesPerSCC.getOrDefault(scc2, Collections.singleton(scc2));

            // combine scc subgraph of vertex1 and vertex2
            sccSet.union(edge.vertex1, edge.vertex2);
            final int scc = sccSet.find(scc1);
            final Set<Integer> vertices = Stream
                .concat(vertices1.stream(), vertices2.stream())
                .collect(Collectors.toSet());
            verticesPerSCC.put(scc, vertices);

            // validate constraint
            if (isValidSubGraph(edges, vertices)) {
                output.totalCandidateVertices += vertices.size();
            }
        }

        return output;
    }

    private List<Edge> getEdges(final Input input) {
        return Arrays.stream(input.edges)
            .map(edge -> new Edge(edge[0], edge[1], edge[2]))
            .collect(Collectors.toList());
    }

    private List<Edge> getMinimumSpanningTreeOfMaximumWeight(final List<Edge> edges) {
        edges.sort(Edge.ORDER_BY_WEIGHT_DESC);

        final List<Edge> minimumSpanningTree = new LinkedList<>();
        final DisjointSet<Integer> set = new DisjointSet<>();

        for (final Edge edge : edges) {
            final int parent1 = set.find(edge.vertex1);
            final int parent2 = set.find(edge.vertex2);
            if (parent1 != parent2) {
                minimumSpanningTree.add(edge);
                set.union(edge.vertex1, edge.vertex2);
            }
        }

        return minimumSpanningTree;
    }

    private boolean isValidSubGraph(final List<Edge> edges, final Set<Integer> vertices) {
        final int minimumInnerWeight = getMinimumInnerWeight(edges, vertices);
        final int maximumBorderWeight = getMaximumBorderWeight(edges, vertices);
        return minimumInnerWeight > maximumBorderWeight;
    }

    private int getMinimumInnerWeight(final List<Edge> edges, final Set<Integer> vertices) {
        return edges.stream()
            .filter(e -> vertices.contains(e.vertex1) & vertices.contains(e.vertex2))
            .mapToInt(e -> e.weight)
            .min()
            .orElse(Integer.MAX_VALUE);
    }

    private int getMaximumBorderWeight(final List<Edge> edges, final Set<Integer> vertices) {
        return edges.stream()
            .filter(e -> vertices.contains(e.vertex1) ^ vertices.contains(e.vertex2))
            .mapToInt(e -> e.weight)
            .max()
            .orElse(Integer.MIN_VALUE);
    }
}

class Edge {
    public static final Comparator<Edge> ORDER_BY_WEIGHT_DESC = Comparator.comparingInt(e -> -e.weight);
    public final int vertex1;
    public final int vertex2;
    public final int weight;

    public Edge(final int vertex1, final int vertex2, final int weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }
}

class DisjointSet<V> {
    private final Map<V, V> parentMap = new HashMap<>();

    public V find(final V child) {
        final V parent = parentMap.getOrDefault(child, child);
        if (parent == child) {
            return parent;
        } else {
            final V grandparent = find(parent);
            parentMap.put(child, grandparent);
            return grandparent;
        }
    }

    public void union(final V child1, final V child2) {
        final V parent1 = find(child1);
        final V parent2 = find(child2);
        parentMap.put(parent2, parent1);
    }
}
