package uva.c4.g4.p10354;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    private static BufferedReader in;
    private static StringBuilder out;
    private static String line;
    private static String[] lines;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        while (true) {
            line = in.readLine();
            if (line == null || line.isEmpty()) break;
            lines = line.split(" ", 6);

            int totalPlace = Integer.parseInt(lines[0]);
            int totalRoad = Integer.parseInt(lines[1]);
            int bossHome = Integer.parseInt(lines[2]);
            int bossOffice = Integer.parseInt(lines[3]);
            int myHome = Integer.parseInt(lines[4]);
            int market = Integer.parseInt(lines[5]);

            int[][] roads = new int[totalRoad][3];
            for (int i = 0; i < totalRoad; i++) {
                lines = in.readLine().split(" ", 3);
                int place1 = Integer.parseInt(lines[0]);
                int place2 = Integer.parseInt(lines[1]);
                int cost = Integer.parseInt(lines[2]);

                roads[i][0] = place1;
                roads[i][1] = place2;
                roads[i][2] = cost;
            }

            Solution solution = new Solution(totalPlace, totalRoad, bossHome, bossOffice, myHome, market, roads);
            int cost = solution.getCostToMarket();
            out.append(cost == Solution.NO_ROUTE ? "MISSION IMPOSSIBLE." : cost).append('\n');
        }

        System.out.print(out);
    }
}

/**
 * 1. how to get minimum cost from myHome-market? traverse with bfs/dfs/dijkstra with cost state
 * 2. how to avoid boss? use all-pair shortest path (floyd-warshall), make sure in step 1 we didn't visit node with
 * cost[bossHome][node] + cost[node][bossOffice] == cost[bossHome][bossOffice]
 * <p>
 * addition:
 * 1. place is 1-based (not 0-based)
 * 2. time complexity: O(place^3) + O(place^2)
 */
class Solution {
    public static final int NO_ROUTE = Integer.MAX_VALUE;
    private final int totalPlace;
    private final int totalRoad;
    private final int bossHome;
    private final int bossOffice;
    private final int myHome;
    private final int market;
    private final int[][] roads;

    public Solution(int totalPlace, int totalRoad, int bossHome, int bossOffice, int myHome, int market, int[][] roads) {
        this.totalPlace = totalPlace;
        this.totalRoad = totalRoad;
        this.bossHome = bossHome;
        this.bossOffice = bossOffice;
        this.myHome = myHome;
        this.market = market;
        this.roads = roads;
    }

    public int getCostToMarket() {
        boolean[] visitedByBoss = findPlacesVisitedByBoss();
        if (visitedByBoss[myHome] || visitedByBoss[market]) return NO_ROUTE;

        int[] costFromHome = new int[totalPlace + 1];
        Arrays.fill(costFromHome, NO_ROUTE);

        // shortest path using bfs, time complexity: O(P^2)
        int[][] graph = createDirectPathGraph();
        // state: place, totalCost
        LinkedList<int[]> nodeq = new LinkedList<>();
        costFromHome[myHome] = 0;
        nodeq.addLast(new int[]{myHome, 0});

        while (!nodeq.isEmpty()) {
            int[] node = nodeq.removeFirst();
            int place = node[0];
            int totalCost = node[1];

            for (int nextPlace = 1; nextPlace <= totalPlace; nextPlace++) {
                if (visitedByBoss[nextPlace]) continue;

                boolean hasNoRoute = graph[place][nextPlace] == NO_ROUTE;
                if (hasNoRoute) continue;

                int nextTotalCost = totalCost + graph[place][nextPlace];
                boolean higherTotalCost = nextTotalCost >= costFromHome[nextPlace];
                if (higherTotalCost) continue;

                costFromHome[nextPlace] = nextTotalCost;
                nodeq.addLast(new int[]{nextPlace, nextTotalCost});
            }
        }

        return costFromHome[market];
    }

    private int[][] createDirectPathGraph() {
        int[][] graph = new int[totalPlace + 1][totalPlace + 1];
        fill(graph, NO_ROUTE);

        for (int place = 1; place <= totalPlace; place++)
            graph[place][place] = 0;

        for (int[] road : roads) {
            int place1 = road[0];
            int place2 = road[1];
            int cost = road[2];

            graph[place1][place2] = Math.min(graph[place1][place2], cost);
            graph[place2][place1] = Math.min(graph[place2][place1], cost);
        }

        return graph;
    }

    // all-pair shortest path using floyd-warshall, time complexity: O(P^3)
    private int[][] createAllPairShortestPathGraph() {
        int[][] graph = createDirectPathGraph();

        for (int alt = 1; alt <= totalPlace; alt++) {
            for (int src = 1; src <= totalPlace; src++) {
                for (int dst = 1; dst <= totalPlace; dst++) {
                    boolean noAlternative = graph[src][alt] == NO_ROUTE || graph[alt][dst] == NO_ROUTE;
                    if (noAlternative) continue;

                    int altCost = graph[src][alt] + graph[alt][dst];
                    graph[src][dst] = Math.min(graph[src][dst], altCost);
                }
            }
        }

        return graph;
    }

    private boolean[] findPlacesVisitedByBoss() {
        boolean[] visited = new boolean[totalPlace + 1];
        int[][] graph = createAllPairShortestPathGraph();
        for (int place = 1; place <= totalPlace; place++) {
            visited[place] = inBossRoute(place, graph);
        }

        return visited;
    }

    private boolean inBossRoute(int place, int[][] allPairGraph) {
        int bossHomeToOfficeCost = allPairGraph[bossHome][bossOffice];
        int bossHomeToThisToOfficeCost = allPairGraph[bossHome][place] + allPairGraph[place][bossOffice];

        return bossHomeToOfficeCost == bossHomeToThisToOfficeCost;
    }

    private void fill(int[][] array2, int value) {
        for (int[] array1 : array2) Arrays.fill(array1, value);
    }
}
