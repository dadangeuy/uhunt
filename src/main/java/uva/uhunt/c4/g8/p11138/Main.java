package uva.uhunt.c4.g8.p11138;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
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
        final FlowNetwork<String> flowNetwork = createFlowNetwork(input);
        final int maxTotalFits = flowNetwork.getMaximumFlow();
        return new Output(input.caseId, maxTotalFits);
    }

    private FlowNetwork<String> createFlowNetwork(final Input input) {
        final FlowNetwork<String> flowNetwork = new FlowNetwork<>(SOURCE, SINK);

        for (int bolt = 0; bolt < input.totalBolts; bolt++) {
            final String boltName = String.format("BOLT_%d", bolt);
            flowNetwork.increment(SOURCE, boltName, 1);
        }

        for (int nut = 0; nut < input.totalNuts; nut++) {
            final String nutName = String.format("NUT_%d", nut);
            flowNetwork.increment(nutName, SINK, 1);
        }

        for (int bolt = 0; bolt < input.totalBolts; bolt++) {
            final String boltName = String.format("BOLT_%d", bolt);
            for (int nut = 0; nut < input.totalNuts; nut++) {
                final String nutName = String.format("NUT_%d", nut);

                if (input.fits[bolt][nut] == 1) {
                    flowNetwork.increment(boltName, nutName, 1);
                }
            }
        }

        return flowNetwork;
    }
}

/**
 * Algorithm to compute maximal flow in a flow network, based on Edmonds-Karp algorithm.<br>
 * Time Complexity: O(V*E^2).<br>
 * Template: {@link uva.common.template.generic.FlowNetwork} (Revision 1).<br>
 * Reference: <a href="https://cp-algorithms.com/graph/edmonds_karp.html">Algorithms for Competitive Programming</a>.<br>
 */
class FlowNetwork<V> {
    private final V source;
    private final V sink;
    private final Map<V, Map<V, Integer>> pipes;

    public FlowNetwork(
        final V source,
        final V sink
    ) {
        this.source = source;
        this.sink = sink;
        this.pipes = new HashMap<>();
    }

    // O(1)
    public void increment(final V fromVertex, final V intoVertex, final int capacity) {
        pipes
            .computeIfAbsent(fromVertex, k -> new HashMap<>())
            .compute(intoVertex, (k, v) -> v == null ? capacity : capacity + v);
    }

    // O(1)
    public Set<V> get(final V fromVertex) {
        return pipes.getOrDefault(fromVertex, Collections.emptyMap()).keySet();
    }

    // O(1)
    public Optional<Integer> get(final V fromVertex, final V intoVertex) {
        return Optional.ofNullable(pipes.getOrDefault(fromVertex, Collections.emptyMap()).get(intoVertex));
    }

    // O(V*E^2)
    public int getMaximumFlow() {
        doMaximumFlow();
        final int maximumFlow = get(sink).stream()
            .mapToInt(vertex -> get(sink, vertex).orElse(0))
            .sum();
        return maximumFlow;
    }

    // O(V*E^2)
    public void doMaximumFlow() {
        doMaximumFlowWithEdmondsKarp();
    }

    // O(V*E^2)
    private void doMaximumFlowWithEdmondsKarp() {
        Collection<V> path;
        while ((path = getShortestPath()) != null) {
            final int bottleneck = getBottleneck(path);
            doAugmentFlow(path, bottleneck);
        }
    }

    // O(V+E)
    private Collection<V> getShortestPath() {
        return getShortestPathWithBreadthFirstSearch();
    }

    // O(V+E)
    private Collection<V> getShortestPathWithBreadthFirstSearch() {
        final Set<V> visitedVertices = new HashSet<>();
        final Queue<V> pendingVertices = new LinkedList<>();
        final Map<V, V> previousVertices = new HashMap<>();

        visitedVertices.add(source);
        pendingVertices.add(source);
        previousVertices.put(source, null);

        while (!pendingVertices.isEmpty()) {
            final V vertex = pendingVertices.remove();
            if (vertex == sink) {
                return getPath(previousVertices);
            }

            for (final V nextVertex : get(vertex)) {
                final boolean isVisited = visitedVertices.contains(nextVertex);
                if (isVisited) continue;

                final boolean hasPositiveFlow = get(vertex, nextVertex).orElse(0) > 0;
                if (!hasPositiveFlow) continue;

                visitedVertices.add(nextVertex);
                pendingVertices.add(nextVertex);
                previousVertices.put(nextVertex, vertex);
            }
        }

        return null;
    }

    // O(V)
    private Collection<V> getPath(final Map<V, V> previousVertices) {
        final LinkedList<V> path = new LinkedList<>();
        for (V currentVertex = sink; currentVertex != null; currentVertex = previousVertices.get(currentVertex)) {
            path.addFirst(currentVertex);
        }
        return path;
    }

    // O(V)
    private int getBottleneck(final Collection<V> path) {
        int bottleneck = Integer.MAX_VALUE;

        final Iterator<V> it = path.iterator();
        V from, into = it.next();
        while (it.hasNext()) {
            from = into;
            into = it.next();
            final int capacity = get(from, into).orElse(0);
            bottleneck = Math.min(bottleneck, capacity);
        }

        return bottleneck;
    }

    // O(V)
    private void doAugmentFlow(final Collection<V> path, final int flow) {
        final Iterator<V> it = path.iterator();
        V from, into = it.next();
        while (it.hasNext()) {
            from = into;
            into = it.next();
            increment(from, into, -flow);
            increment(into, from, +flow);
        }
    }
}
