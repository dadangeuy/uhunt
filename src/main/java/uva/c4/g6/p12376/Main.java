package uva.c4.g6.p12376;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

/**
 * 12376 - As Long as I Learn, I Live
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3798
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        final int totalTestCases = in.nextInt();
        for (int caseId = 1; caseId <= totalTestCases; caseId++) {
            final Input input = new Input();
            input.caseId = caseId;
            input.totalNodes = in.nextInt();
            input.totalEdges = in.nextInt();

            input.nodes = new int[input.totalNodes];
            for (int i = 0; i < input.totalNodes; i++) {
                input.nodes[i] = in.nextInt();
            }

            input.edges = new int[input.totalEdges][];
            for (int i = 0; i < input.totalEdges; i++) {
                input.edges[i] = new int[]{
                    in.nextInt(),
                    in.nextInt()
                };
            }

            final Output output = process.process(input);
            out.format("Case %d: %d %d\n", output.caseId, output.totalLearningUnits, output.nodeId);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int caseId;
    public int totalNodes;
    public int totalEdges;
    public int[] nodes;
    public int[][] edges;
}

class Output {
    public int caseId;
    public int totalLearningUnits;
    public int nodeId;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.caseId = input.caseId;

        final Graph<Integer> graph = new Graph<>();
        for (final int[] edge : input.edges) {
            graph.add(edge[0], edge[1]);
        }

        int nodeId = 0;
        int totalLearningUnits = 0;
        Set<Integer> vertices = new HashSet<>();
        vertices.add(nodeId);

        final Comparator<Integer> compareByLearningUnits = Comparator
            .comparingInt(v -> input.nodes[v]);

        while (true) {
            final Optional<Integer> nextNodeId = graph.get(nodeId).stream()
                .filter(v -> !vertices.contains(v))
                .max(compareByLearningUnits);

            if (nextNodeId.isPresent()) {
                nodeId = nextNodeId.get();
                totalLearningUnits += input.nodes[nextNodeId.get()];
                vertices.add(nextNodeId.get());
            } else {
                break;
            }
        }

        output.nodeId = nodeId;
        output.totalLearningUnits = totalLearningUnits;
        return output;
    }
}

class Graph<V> {
    public final Map<V, Set<V>> edges = new HashMap<>();

    public void add(final V from, final V into) {
        edges.computeIfAbsent(from, k -> new HashSet<>()).add(into);
    }

    public Set<V> get(final V from) {
        return edges.getOrDefault(from, Collections.emptySet());
    }
}
