package uva.common.template;

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
 * Algorithm to compute maximal flow in a flow network, based on Edmonds-Karp algorithm.<br>
 * Time Complexity: O(V*E^2).<br>
 * Template: {@link FlowNetwork} (Revision 1).<br>
 * Reference: <a href="https://cp-algorithms.com/graph/edmonds_karp.html">Algorithms for Competitive Programming</a>.<br>
 */
public class FlowNetwork<V> {
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
