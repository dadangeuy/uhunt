package uva.c4.g4.p10054;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * 10054 - The Necklace
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=995
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 8);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 8);
        final Process process = new Process();

        final int totalTestCases = Integer.parseInt(readLine(in));
        for (int testCase = 1; testCase <= totalTestCases; testCase++) {
            final Input input = new Input();
            input.testCase = testCase;
            input.totalBeads = Integer.parseInt(readLine(in));
            input.beads = new int[input.totalBeads][];
            for (int i = 0; i < input.totalBeads; i++) {
                final String[] l1 = readSplitLine(in);
                final int[] bead = new int[]{Integer.parseInt(l1[0]), Integer.parseInt(l1[1])};
                input.beads[i] = bead;
            }

            final Output output = process.process(input);
            if (testCase > 1) out.write('\n');
            out.write(String.format("Case #%d\n", output.testCase));
            if (output.isLost) {
                out.write("some beads may be lost\n");
            } else {
                for (final int[] bead : output.necklace) {
                    out.write(String.format("%d %d\n", bead[0], bead[1]));
                }
            }
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String[] readSplitLine(final BufferedReader in) throws IOException {
        final String line = readLine(in);
        return line.split(" ");
    }

    private static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Input {
    public int testCase;
    public int totalBeads;
    public int[][] beads;
}

class Output {
    public int testCase;
    public boolean isLost;
    public int[][] necklace;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.testCase = input.testCase;

        final EulerianBiGraph<Integer, Integer> eulerianGraph = new EulerianBiGraph<>();
        for (int beadIdx = 0; beadIdx < input.totalBeads; beadIdx++) {
            final int[] bead = input.beads[beadIdx];
            eulerianGraph.add(bead[0], bead[1], beadIdx);
        }

        final int firstColor = input.beads[0][0];
        final LinkedList<Integer> cycle = eulerianGraph.getEulerianCycle(firstColor);

        if (cycle == null) {
            output.isLost = true;
        } else {
            output.isLost = false;

            Iterator<Integer> it1 = cycle.iterator(), it2 = cycle.iterator();
            it2.next();

            final int[][] necklace = new int[input.totalBeads][];
            for (int i = 0; i < input.totalBeads; i++) {
                final int[] bead = new int[]{it1.next(), it2.next()};
                necklace[i] = bead;
            }

            output.necklace = necklace;
        }

        return output;
    }
}

class EulerianBiGraph<V, E> {
    private final BiGraph<V, E> graph = new BiGraph<>();

    public void add(final V vertex1, final V vertex2, final E edge) {
        graph.add(vertex1, vertex2, edge);
    }

    public LinkedList<V> getEulerianCycle(final V fromVertex) {
        return getEulerianCycleWithHierholzerAlgorithm(fromVertex);
    }

    private LinkedList<V> getEulerianCycleWithHierholzerAlgorithm(final V fromVertex) {
        if (!hasEvenDegrees()) return null;

        final LinkedList<V> cycle = new LinkedList<>();
        doDepthFirstSearch(fromVertex, cycle);

        return isValidEulerianCycle(cycle) ? cycle : null;
    }

    private boolean hasEvenDegrees() {
        final IntStream degrees = graph.getVertices().stream().mapToInt(graph::getDegrees);
        return degrees.allMatch(degree -> degree % 2 == 0);
    }

    private void doDepthFirstSearch(final V fromVertex, final LinkedList<V> path) {
        while (!graph.getVertices(fromVertex).isEmpty()) {
            final V intoVertex = graph.getVertices(fromVertex).iterator().next();
            while (!graph.getEdges(fromVertex, intoVertex).isEmpty()) {
                final E edge = graph.getEdges(fromVertex, intoVertex).iterator().next();
                graph.remove(fromVertex, intoVertex, edge);
                doDepthFirstSearch(intoVertex, path);
            }
        }
        path.addFirst(fromVertex);
    }

    private boolean isValidEulerianCycle(final LinkedList<V> cycle) {
        final boolean hasEveryEdge = (cycle.size() - 1) == graph.getEdges().size();
        final boolean isCircular = cycle.getFirst() == cycle.getLast();
        return hasEveryEdge && isCircular;
    }
}

class BiGraph<V, E> {
    private final Set<V> vertices = new HashSet<>();
    private final Set<E> edges = new HashSet<>();
    private final Map<V, Map<V, Set<E>>> relationships = new HashMap<>();
    private final Map<V, Integer> degrees = new HashMap<>();

    public void add(final V vertex1, final V vertex2, final E edge) {
        vertices.add(vertex1);
        vertices.add(vertex2);
        edges.add(edge);

        doAddRelationship(vertex1, vertex2, edge);
        doAddRelationship(vertex2, vertex1, edge);

        doIncrementDegrees(vertex1, 1);
        doIncrementDegrees(vertex2, 1);
    }

    private void doAddRelationship(final V fromVertex, final V intoVertex, final E edge) {
        relationships
            .computeIfAbsent(fromVertex, k -> new HashMap<>())
            .computeIfAbsent(intoVertex, k -> new HashSet<>()).add(edge);
    }

    public void remove(final V vertex1, final V vertex2, final E edge) {
        doRemoveRelationship(vertex1, vertex2, edge);
        doRemoveRelationship(vertex2, vertex1, edge);

        doIncrementDegrees(vertex1, -1);
        doIncrementDegrees(vertex2, -1);
    }

    private void doRemoveRelationship(final V fromVertex, final V intoVertex, final E edge) {
        final Map<V, Set<E>> intoVertices = relationships.getOrDefault(fromVertex, Collections.emptyMap());
        final Set<E> edges = intoVertices.getOrDefault(intoVertex, Collections.emptySet());

        edges.remove(edge);
        if (edges.isEmpty()) intoVertices.remove(intoVertex);
        if (intoVertices.isEmpty()) relationships.remove(fromVertex);
    }

    private void doIncrementDegrees(final V fromVertex, final int amount) {
        degrees.compute(fromVertex, (k, v) -> v == null ? amount : v + amount);
    }

    public Set<V> getVertices() {
        return vertices;
    }

    public Set<V> getVertices(final V vertex1) {
        return relationships
            .getOrDefault(vertex1, Collections.emptyMap())
            .keySet();
    }

    public Set<E> getEdges() {
        return edges;
    }

    public Set<E> getEdges(final V vertex1, final V vertex2) {
        return relationships
            .getOrDefault(vertex1, Collections.emptyMap())
            .getOrDefault(vertex2, Collections.emptySet());
    }

    public int getDegrees(final V vertex) {
        return degrees.getOrDefault(vertex, 0);
    }
}
