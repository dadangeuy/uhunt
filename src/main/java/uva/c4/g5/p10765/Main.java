package uva.c4.g5.p10765;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final StringBuilder out = new StringBuilder();
        String[] lines;

        while (true) {
            lines = in.readLine().split(" ", 2);
            int totalStation = Integer.parseInt(lines[0]);
            int totalTarget = Integer.parseInt(lines[1]);

            if (totalStation == 0 && totalTarget == 0) break;

            LinkedList<int[]> railwayq = new LinkedList<>();
            while (true) {
                lines = in.readLine().split(" ", 2);
                int station1 = Integer.parseInt(lines[0]);
                int station2 = Integer.parseInt(lines[1]);

                if (station1 == -1 && station2 == -1) break;

                railwayq.add(new int[]{station1, station2});
            }

            int[][] railways = railwayq.toArray(new int[0][]);

            Solution solution = new Solution(totalStation, totalTarget, railways);
            int[][] desirableStations = solution.getMostDesirableStations();

            for (int[] stationAndPigeon : desirableStations) {
                out.append(stationAndPigeon[0]).append(' ').append(stationAndPigeon[1]).append('\n');
            }
            out.append('\n');
        }

        System.out.print(out);
    }
}

/**
 * 1) store station-[railway]-station in a graph (map of set)
 * 2) find articulation point (critical station, that will split the graph if removed)
 * 3) calculate pigeon value for each station
 * - if not articulation point, pigeon value = 0
 * - if articulation point, pigeon value => flood fill neighbour => total initiated flood fill
 * 4) sort by pigeon value DESC and station ASC
 * <p>
 * tags: graph, articulation-point
 */
class Solution {
    private final int totalStation;
    private final int totalTarget;
    private final int[][] railways;

    public Solution(int totalStation, int totalTarget, int[][] railways) {
        this.totalStation = totalStation;
        this.totalTarget = totalTarget;
        this.railways = railways;
    }

    public int[][] getMostDesirableStations() {
        int[][] targets = new int[totalStation][2];

        final UndirectedGraph graph = new UndirectedGraph(totalStation, railways);
        boolean[] isArticulationPoints = graph.findArticulationPoints();
        for (int station = 0; station < totalStation; station++) {
            int pigeon = isArticulationPoints[station] ? graph.countChildSubgraph(station) : 1;
            targets[station] = new int[]{station, pigeon};
        }

        Comparator<int[]> compareByPigeonAndStation = Comparator
            .<int[]>comparingInt(v -> -v[1])
            .thenComparingInt(v -> v[0]);
        Arrays.sort(targets, compareByPigeonAndStation);

        return Arrays.copyOf(targets, totalTarget);
    }
}


class UndirectedGraph {
    private final Map<Integer, Set<Integer>> graph;

    public UndirectedGraph(int totalVertex, int[][] edges) {
        graph = new HashMap<>(2 * totalVertex);
        for (int i = 0; i < totalVertex; i++) graph.put(i, new TreeSet<>());
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
    }

    public boolean[] findArticulationPoints() {
        ArticulationPoint p = new ArticulationPoint(graph);
        return p.findArticulationPoint();
    }

    public int countChildSubgraph(int vertex) {
        FloodFill p = new FloodFill(graph);
        int count = 0;
        for (int childVertex : graph.get(vertex)) {
            if (p.visited[childVertex]) continue;
            count++;
            p.floodFill(childVertex, vertex);
        }

        return count;
    }
}


class ArticulationPoint {
    public final Map<Integer, Set<Integer>> graph;
    public final int[] dfsTimes;
    public final int[] dfsMinTimes;
    public final boolean[] isArticulationPoints;
    public int time;

    public ArticulationPoint(Map<Integer, Set<Integer>> graph) {
        this.graph = graph;
        this.time = 1;
        this.dfsTimes = new int[graph.size()];
        this.dfsMinTimes = new int[graph.size()];
        this.isArticulationPoints = new boolean[graph.size()];
    }

    public boolean[] findArticulationPoint() {
        for (int vertex : graph.keySet()) {
            boolean unvisited = dfsTimes[vertex] == 0;
            if (unvisited) dfsArticulationPoint(vertex, vertex);
        }

        return isArticulationPoints;
    }

    private void dfsArticulationPoint(int parentVertex, int vertex) {
        boolean visited = dfsTimes[vertex] != 0;
        if (visited) return;

        dfsTimes[vertex] = dfsMinTimes[vertex] = ++time;

        for (int childVertex : graph.get(vertex)) {
            if (childVertex == parentVertex) continue;
            dfsArticulationPoint(vertex, childVertex);
            dfsMinTimes[vertex] = Math.min(dfsMinTimes[vertex], dfsMinTimes[childVertex]);

            boolean isRoot = vertex == parentVertex;
            boolean isRootArticulationPoint = isRoot && graph.get(vertex).size() > 1;
            boolean isNonRootArticulationPoint = !isRoot && dfsTimes[vertex] <= dfsMinTimes[childVertex];
            boolean isArticulationPoint = isRootArticulationPoint || isNonRootArticulationPoint;
            isArticulationPoints[vertex] |= isArticulationPoint;
        }
    }
}

class FloodFill {
    public final Map<Integer, Set<Integer>> graph;
    public final boolean[] visited;

    public FloodFill(Map<Integer, Set<Integer>> graph) {
        this.graph = graph;
        this.visited = new boolean[graph.size()];
    }

    public void floodFill(int vertex, int excludeVertex) {
        for (int childVertex : graph.get(vertex)) {
            boolean visited = this.visited[childVertex];
            boolean excluded = childVertex == excludeVertex;
            if (visited || excluded) continue;

            this.visited[childVertex] = true;
            floodFill(childVertex, excludeVertex);
        }
    }
}
