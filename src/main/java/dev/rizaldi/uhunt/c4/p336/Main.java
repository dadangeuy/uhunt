package dev.rizaldi.uhunt.c4.p336;

import java.io.IOException;
import java.util.*;

public class Main {
    private static Scanner in;
    private static StringBuilder out;

    public static void main(String... args) throws IOException {
        in = new Scanner(System.in);
        out = new StringBuilder();

        int testCase = 0;
        while (true) {
            int totalConnection = in.nextInt();
            if (totalConnection == 0) break;

            Set<Integer> vertices = new TreeSet<>();
            Map<Integer, Set<Integer>> graph = new HashMap<>(2 * totalConnection);
            for (int i = 0; i < totalConnection; i++) {
                int node1 = in.nextInt();
                int node2 = in.nextInt();
                graph.computeIfAbsent(node1, k -> new TreeSet<>()).add(node2);
                graph.computeIfAbsent(node2, k -> new TreeSet<>()).add(node1);
                vertices.add(node1);
                vertices.add(node2);
            }

            while (true) {
                int startNode = in.nextInt();
                int ttl = in.nextInt();
                if (startNode == 0 && ttl == 0) break;

                testCase++;

                Solution solution = new Solution(vertices, graph, startNode, ttl);
                int total = solution.getTotalUnreachableNode();

                out
                    .append("Case ").append(testCase).append(": ").append(total)
                    .append(" nodes not reachable from node ").append(startNode).append(" with TTL = ").append(ttl)
                    .append(".\n");
            }
        }

        System.out.print(out);
    }
}

// use BFS to traverse graph
// store the amount of step taken in each iteration
// if step == ttl, stop traversing
class Solution {
    private final Set<Integer> vertices;
    private final Map<Integer, Set<Integer>> graph;
    private final int startNode;
    private final int ttl;

    public Solution(Set<Integer> vertices, Map<Integer, Set<Integer>> graph, int startNode, int ttl) {
        this.vertices = vertices;
        this.graph = graph;
        this.startNode = startNode;
        this.ttl = ttl;
    }

    public int getTotalUnreachableNode() {
        int totalUnreachableNode = vertices.size();
        Set<Integer> visited = new TreeSet<>();
        LinkedList<int[]> progressq = new LinkedList<>();

        visited.add(startNode);
        progressq.addLast(new int[]{startNode, 0});

        while (!progressq.isEmpty()) {
            int[] progress = progressq.removeFirst();
            int node = progress[0];
            int step = progress[1];

            if (vertices.contains(node)) totalUnreachableNode--;

            if (step == ttl) continue;

            for (int nextNode : graph.getOrDefault(node, Collections.emptySet())) {
                if (visited.contains(nextNode)) continue;

                visited.add(nextNode);
                progressq.addLast(new int[]{nextNode, step + 1});
            }
        }

        return totalUnreachableNode;
    }
}
