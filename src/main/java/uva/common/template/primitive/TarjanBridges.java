package uva.common.template.primitive;

import java.util.Arrays;
import java.util.LinkedList;

@SuppressWarnings("unused")
final class TarjanBridges {
    private final UndirectedGraph graph;
    private final boolean[] visited;
    private final int[] discovery;
    private final int[] low;
    private LinkedList<int[]> bridges;
    private int timer;

    public TarjanBridges(final UndirectedGraph graph) {
        this.graph = graph;
        this.visited = new boolean[graph.get().length];
        this.discovery = new int[graph.get().length];
        this.low = new int[graph.get().length];
        this.bridges = null;
        this.timer = 0;

        Arrays.fill(discovery, -1);
        Arrays.fill(low, -1);
    }

    public int[][] getBridges() {
        if (bridges == null) {
            bridges = new LinkedList<>();
            for (int vertex = 0; vertex < graph.get().length; vertex++) {
                if (!visited[vertex]) dfs(-1, vertex);
            }
        }

        return bridges.toArray(new int[0][0]);
    }

    private void dfs(final int prevVertex, final int vertex) {
        visited[vertex] = true;
        discovery[vertex] = low[vertex] = timer++;
        boolean parent_skipped = false;

        for (int nextVertex : graph.get(vertex)) {
            if (prevVertex == nextVertex && !parent_skipped) {
                parent_skipped = true;
            } else if (visited[nextVertex]) {
                low[vertex] = Math.min(low[vertex], discovery[nextVertex]);
            } else {
                dfs(vertex, nextVertex);
                low[vertex] = Math.min(low[vertex], low[nextVertex]);
                if (low[nextVertex] > discovery[vertex]) {
                    foundBridge(vertex, nextVertex);
                    foundBridge(nextVertex, vertex);
                }
            }
        }
    }

    private void foundBridge(final int fromVertex, final int intoVertex) {
        bridges.addLast(new int[]{fromVertex, intoVertex});
    }
}
