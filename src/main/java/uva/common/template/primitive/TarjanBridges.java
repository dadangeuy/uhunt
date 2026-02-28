package uva.common.template.primitive;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Find Bridges in an Undirected Graph using Tarjan's Algorithm in O(totalVertices + totalEdges).
 * Bridge is an edge whose removal increases the number of connected components of the graph.
 * In @rizaldi term, bridge is an edge and a single point of failure in a graph.
 */
@SuppressWarnings("unused")
final class TarjanBridges {
    private static final int NULL = -1;

    private final UndirectedGraph graph;
    private int[] discovery;
    private int[] low;
    private int[] parent;
    private LinkedList<int[]> bridgeList;
    private int[][] bridges;
    private int time;

    public TarjanBridges(final UndirectedGraph graph) {
        this.graph = graph;
        initArrays();
        initBridges();
    }

    private void initArrays() {
        this.discovery = new int[graph.get().length];
        this.low = new int[graph.get().length];
        this.parent = new int[graph.get().length];

        Arrays.fill(discovery, NULL);
        Arrays.fill(low, NULL);
        Arrays.fill(parent, NULL);
    }

    private void initBridges() {
        this.bridgeList = new LinkedList<>();
        for (int vertex = 0; vertex < graph.get().length; vertex++) {
            if (discovery[vertex] == NULL) {
                depthFirstSearch(vertex);
            }
        }
        bridges = bridgeList.toArray(new int[0][0]);
    }

    private void depthFirstSearch(final int vertex) {
        discovery[vertex] = low[vertex] = ++time;
        for (int nextVertex : graph.get(vertex)) {
            if (discovery[nextVertex] == NULL) {
                parent[nextVertex] = vertex;
                depthFirstSearch(nextVertex);
                low[vertex] = Math.min(low[vertex], low[nextVertex]);

                if (low[nextVertex] > discovery[vertex]) {
                    foundBridge(vertex, nextVertex);
                    foundBridge(nextVertex, vertex);
                }
            } else if (nextVertex != parent[vertex]) {
                low[vertex] = Math.min(low[vertex], discovery[nextVertex]);
            }
        }
    }

    private void foundBridge(final int fromVertex, final int intoVertex) {
        bridgeList.addLast(new int[]{fromVertex, intoVertex});
    }

    public int[][] getBridges() {
        return bridges;
    }
}