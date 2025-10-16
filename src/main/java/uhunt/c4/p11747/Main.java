package uhunt.c4.p11747;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class Main {
    private static String line;
    private static String[] lines;

    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int[][] edges = new int[25000][3];

        while (true) {
            line = in.readLine();
            lines = line.split(" ", 2);

            int totalNode = Integer.parseInt(lines[0]);
            int totalEdge = Integer.parseInt(lines[1]);

            if (totalNode == 0 && totalEdge == 0) break;

            for (int i = 0; i < totalEdge; i++) {
                line = in.readLine();
                lines = line.split(" ", 3);

                int node1 = Integer.parseInt(lines[0]);
                int node2 = Integer.parseInt(lines[1]);
                int weight = Integer.parseInt(lines[2]);

                edges[i][0] = node1;
                edges[i][1] = node2;
                edges[i][2] = weight;
            }

            Solution solution = new Solution(totalNode, totalEdge, edges);
            int[] weights = solution.getHeaviestWeightInCycles();

            if (weights == null || weights.length == 0) {
                System.out.println("forest");
            } else {
                System.out.print(weights[0]);
                for (int i = 1; i < weights.length; i++) System.out.format(" %d", weights[i]);
                System.out.println();
            }
        }
    }
}

// find minimum edge that connect all nodes (minimum spanning tree) using kruskal algorithm
// edges other than the minimum spanning tree was a cycle edge that can be removed
// use disjoint set to store the spanning tree
// time complexity: O(T * E * m), T = number of cases, E = number of edges to loop, m = number of chain relation in disjoint set (0..E)
// space complexity: O(V), V = number of vertices stored in disjoint set
class Solution {
    private static final DisjointSet disjointSet = new DisjointSet(1_000);
    private final int totalNode;
    private final int totalEdge;
    private final int[][] edges;

    public Solution(int totalNode, int totalEdge, int[][] edges) {
        this.totalNode = totalNode;
        this.totalEdge = totalEdge;
        this.edges = edges;
    }

    public int[] getHeaviestWeightInCycles() {
        Arrays.sort(edges, 0, totalEdge, Comparator.comparingInt(e -> e[2]));

        LinkedList<Integer> weights = new LinkedList<>();
        disjointSet.reset();

        for (int i = 0; i < totalEdge; i++) {
            int[] edge = edges[i];

            boolean cycle = disjointSet.find(edge[0]) == disjointSet.find(edge[1]);
            if (cycle) weights.addLast(edge[2]);
            else disjointSet.union(edge[0], edge[1]);
        }

        return weights.stream().mapToInt(i -> i).toArray();
    }
}

class DisjointSet {
    private final int[] leaders;

    public DisjointSet(int size) {
        this.leaders = new int[size];
        reset();
    }

    public void union(int follower1, int follower2) {
        int leader1 = find(follower1);
        int leader2 = find(follower2);

        if (leader1 == leader2) return;
        leaders[leader2] = leader1;
    }

    public int find(int follower) {
        while (leaders[follower] != -1) follower = leaders[follower];
        return follower;
    }

    public void reset() {
        Arrays.fill(this.leaders, -1);
    }
}
