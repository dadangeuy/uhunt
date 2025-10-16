package uhunt.c4.p12878;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String line;
        String[] lines;

        while (true) {
            line = in.readLine();
            if (line == null || line.isEmpty()) break;

            lines = line.split(" ", 2);
            int totalPoint = Integer.parseInt(lines[0]);
            int totalTrail = Integer.parseInt(lines[1]);

            int[][] trails = new int[totalTrail][];
            for (int i = 0; i < totalTrail; i++) {
                line = in.readLine();
                lines = line.split(" ", 3);

                int point1 = Integer.parseInt(lines[0]);
                int point2 = Integer.parseInt(lines[1]);
                int length = Integer.parseInt(lines[2]);
                int[] trail = new int[]{point1, point2, length};
                trails[i] = trail;
            }

            Solution solution = new Solution(totalPoint, totalTrail, trails);
            int totalFlower = solution.totalFlowerToCoverPopularTrails();

            System.out.println(totalFlower);
        }
    }
}

class Solution {
    private final int totalPoint;
    private final int totalTrail;
    private final int[][] trails;

    private final Map<Integer, Map<Integer, Integer>> graph;

    public Solution(int totalPoint, int totalTrail, int[][] trails) {
        this.totalPoint = totalPoint;
        this.totalTrail = totalTrail;
        this.trails = trails;

        this.graph = createWeightedBidirectionalGraph(trails);
    }

    public int totalFlowerToCoverPopularTrails() {
        int[] distancesFromEntrance = calculateMinimumDistanceFromVertex(0, totalPoint - 1);
        int[] distancesFromExit = calculateMinimumDistanceFromVertex(totalPoint - 1, 0);

        int minimumDistance = distancesFromEntrance[totalPoint - 1];
        int totalFlower = 0;
        for (int[] trail : trails) {
            int point1 = trail[0];
            int point2 = trail[1];
            int length = trail[2];

            int distance1 = distancesFromEntrance[point1] + length + distancesFromExit[point2];
            int distance2 = distancesFromEntrance[point2] + length + distancesFromExit[point1];
            boolean isShortestPath = distance1 == minimumDistance || distance2 == minimumDistance;

            if (isShortestPath) totalFlower += length;
        }

        return totalFlower * 2;
    }

    private Map<Integer, Map<Integer, Integer>> createWeightedBidirectionalGraph(int[][] edges) {
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>(totalPoint * 2);
        for (int[] edge : edges) {
            int vertex1 = edge[0];
            int vertex2 = edge[1];
            int weight = edge[2];

            graph
                    .computeIfAbsent(vertex1, k -> new HashMap<>())
                    .compute(vertex2, (k, v) -> v == null ? weight : Math.min(v, weight));

            graph
                    .computeIfAbsent(vertex2, k -> new HashMap<>())
                    .compute(vertex1, (k, v) -> v == null ? weight : Math.min(v, weight));
        }

        return graph;
    }

    private int[] calculateMinimumDistanceFromVertex(int source, int destination) {
        // state: distance, current vertex
        Comparator<int[]> compareDistanceAsc = Comparator.comparingInt(p -> p[0]);
        PriorityQueue<int[]> traverseq = new PriorityQueue<>(compareDistanceAsc);

        int[] distances = new int[totalPoint];
        Arrays.fill(distances, Integer.MAX_VALUE);

        traverseq.add(new int[]{0, source});
        distances[source] = 0;

        while (!traverseq.isEmpty()) {
            int[] traverse = traverseq.remove();
            int currDistance = traverse[0];
            int currVertex = traverse[1];

            if (currDistance > distances[destination]) break;

            Map<Integer, Integer> neighbors = graph.getOrDefault(currVertex, Collections.emptyMap());
            for (Map.Entry<Integer, Integer> entry : neighbors.entrySet()) {
                int nextVertex = entry.getKey();
                int length = entry.getValue();

                int newDistance = currDistance + length;
                if (newDistance < distances[nextVertex]) {
                    distances[nextVertex] = newDistance;
                    traverseq.add(new int[]{newDistance, nextVertex});
                }
            }
        }

        return distances;
    }
}
