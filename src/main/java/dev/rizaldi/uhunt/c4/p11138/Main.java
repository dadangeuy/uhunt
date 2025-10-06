package dev.rizaldi.uhunt.c4.p11138;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * 11138 - Nuts and Bolts
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=2079
 */
public class Main {
    public static void main(final String... agrs) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        final int totalCases = Integer.parseInt(readLine(in));
        for (int caseId = 1; caseId <= totalCases; caseId++) {
            final String[] l1 = readSplitLine(in);
            final int totalBolts = Integer.parseInt(l1[0]);
            final int totalNuts = Integer.parseInt(l1[1]);

            final int[][] fits = new int[totalBolts][totalNuts];
            for (int bolt = 0; bolt < totalBolts; bolt++) {
                final String[] l2 = readSplitLine(in);
                for (int nut = 0; nut < totalNuts; nut++) {
                    fits[bolt][nut] = Integer.parseInt(l2[nut]);
                }
            }

            final Input input = new Input(caseId, totalBolts, totalNuts, fits);
            final Output output = process.process(input);

            out.write(String.format(
                    "Case %d: a maximum of %d nuts and bolts can be fitted together\n",
                    caseId,
                    output.maxTotalFits
            ));
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

class Input {
    public final int caseId;
    public final int totalBolts;
    public final int totalNuts;
    public final int[][] fits;

    public Input(final int caseId, final int totalBolts, final int totalNuts, final int[][] fits) {
        this.caseId = caseId;
        this.totalBolts = totalBolts;
        this.totalNuts = totalNuts;
        this.fits = fits;
    }
}

class Output {
    public final int caseId;
    public final int maxTotalFits;

    public Output(final int caseId, final int maxTotalFits) {
        this.caseId = caseId;
        this.maxTotalFits = maxTotalFits;
    }
}

/**
 * the goal is to find maximum number of fitted bolts & nuts.
 * relationship between bolts & nuts could be depicted as bipartite graph.
 * we could use max flow algorithm (e.g. edmonds-karp) to find maximum pair in a bipartite graph.
 * the network flow graph could be depicted as SOURCE -> BOLTS -> NUTS -> SINK, with each edge having 1 capacity.
 * the complexity of edmonds-karp is assumed to be O(V*E^2), with V = 1.000 and E = 250.000, however, since all edges
 * have exactly 1 capacity, the time complexity becomes O(SQRT(V)*E).
 */
class Process {
    private static final String SOURCE = "SOURCE";
    private static final String SINK = "SINK";

    public Output process(final Input input) {
        final Graph<String> graph = new Graph<>();
        for (int bolt = 0; bolt < input.totalBolts; bolt++) {
            final String boltName = String.format("BOLT_%d", bolt);
            for (int nut = 0; nut < input.totalNuts; nut++) {
                final String nutName = String.format("NUT_%d", nut);

                if (input.fits[bolt][nut] == 0) continue;
                graph.increment(boltName, nutName, 1);
            }
        }
        for (int bolt = 0; bolt < input.totalBolts; bolt++) {
            final String boltName = String.format("BOLT_%d", bolt);
            graph.increment(SOURCE, boltName, 1);
        }
        for (int nut = 0; nut < input.totalNuts; nut++) {
            final String nutName = String.format("NUT_%d", nut);
            graph.increment(nutName, SINK, 1);
        }

        while (true) {
            final List<String> path = findPath(graph, SOURCE, SINK);
            if (path == null) break;
            final int bottleneck = findBottleneck(graph, path);
            updateFlow(graph, path, bottleneck);
        }

        final int maxTotalFits = getFlow(graph, SINK);
        return new Output(input.caseId, maxTotalFits);
    }

    private List<String> findPath(
            final Graph<String> graph,
            final String originVertex,
            final String destinationVertex
    ) {
        final Set<String> visitedVertices = new HashSet<>();
        final Queue<String> queueVertices = new LinkedList<>();
        final Map<String, String> previousVertexPerVertex = new HashMap<>();

        queueVertices.add(originVertex);
        visitedVertices.add(originVertex);
        previousVertexPerVertex.put(originVertex, null);

        while (!queueVertices.isEmpty()) {
            final String currentVertex = queueVertices.remove();
            if (currentVertex.equals(destinationVertex)) {
                return buildPath(previousVertexPerVertex, destinationVertex);
            }

            for (final String nextVertex : graph.getVertices(currentVertex)) {
                if (visitedVertices.contains(nextVertex)) continue;
                if (graph.getWeight(currentVertex, nextVertex) <= 0) continue;

                queueVertices.add(nextVertex);
                visitedVertices.add(nextVertex);
                previousVertexPerVertex.put(nextVertex, currentVertex);
            }
        }

        return null;
    }

    private List<String> buildPath(
            final Map<String, String> previousVertexPerVertex,
            final String destinationVertex
    ) {
        final LinkedList<String> path = new LinkedList<>();
        for (
                String currentVertex = destinationVertex;
                currentVertex != null;
                currentVertex = previousVertexPerVertex.get(currentVertex)
        ) {
            path.addFirst(currentVertex);
        }
        return path;
    }

    private int findBottleneck(
            final Graph<String> graph,
            final List<String> path
    ) {
        int bottleneck = Integer.MAX_VALUE;
        for (int i = 0; i < path.size() - 1; i++) {
            final String from = path.get(i);
            final String into = path.get(i + 1);
            final int capacity = graph.getWeight(from, into);
            bottleneck = Math.min(bottleneck, capacity);
        }
        return bottleneck;
    }

    private void updateFlow(
            final Graph<String> graph,
            final List<String> path,
            final int flow
    ) {
        for (int i = 0; i < path.size() - 1; i++) {
            final String from = path.get(i);
            final String into = path.get(i + 1);

            graph.increment(from, into, -flow);
            graph.increment(into, from, flow);
        }
    }

    private int getFlow(final Graph<String> graph, final String vertex) {
        int flow = 0;
        for (final String next : graph.getVertices(vertex)) {
            final int residual = graph.getWeight(vertex, next);
            flow += residual;
        }
        return flow;
    }
}

class Graph<V> {
    private final Map<V, Map<V, Integer>> edges = new HashMap<>();

    public void increment(final V from, final V into, final int weight) {
        edges
                .computeIfAbsent(from, k -> new HashMap<>())
                .compute(into, (k, v) -> v == null ? weight : v + weight);
    }

    public Set<V> getVertices(final V from) {
        return edges.getOrDefault(from, Collections.emptyMap()).keySet();
    }

    public int getWeight(final V from, final V into) {
        return edges
                .getOrDefault(from, Collections.emptyMap())
                .getOrDefault(into, 0);
    }
}
