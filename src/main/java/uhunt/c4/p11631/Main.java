package uhunt.c4.p11631;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Main {
    private static BufferedReader in;
    private static String line;
    private static String[] lines;
    private static int totalJunction;
    private static int totalRoad;
    private static int[][] roads = new int[200_000][3];

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            line = in.readLine();
            lines = line.split(" ", 2);

            totalJunction = Integer.parseInt(lines[0]);
            totalRoad = Integer.parseInt(lines[1]);
            if (totalJunction == 0 && totalRoad == 0) break;

            for (int i = 0; i < totalRoad; i++) {
                line = in.readLine();
                lines = line.split(" ", 3);

                roads[i][0] = Integer.parseInt(lines[0]);
                roads[i][1] = Integer.parseInt(lines[1]);
                roads[i][2] = Integer.parseInt(lines[2]);
            }

            Solution solution = new Solution(totalJunction, totalRoad, roads);
            int saving = solution.getMaximumSaving();

            System.out.println(saving);
        }

    }
}

// use kruskal to get minimum spanning tree
// kruskal: sort road by cost ascending, use disjoint set to store the spanning tree, use road if it's connected with
// an unvisited junction
// saving = total all weight - total spanning tree weight
class Solution {
    private static final Comparator<int[]> sortByCost = Comparator.comparing(road -> road[2]);
    private static final DisjointSet spanningTree = new DisjointSet(200_000);
    private final int totalJunction;
    private final int totalRoad;
    private final int[][] roads;

    public Solution(int totalJunction, int totalRoad, int[][] roads) {
        this.totalJunction = totalJunction;
        this.totalRoad = totalRoad;
        this.roads = roads;
    }

    public int getMaximumSaving() {
        Arrays.sort(roads, 0, totalRoad, sortByCost);

        int originalCost = findOriginalCost();
        int minimumCost = findMinimumCost();

        return originalCost - minimumCost;
    }

    private int findMinimumCost() {
        spanningTree.clear(0, totalJunction);

        int totalCost = 0;
        int totalRoadTaken = 0;
        for (int i = 0; i < totalRoad && totalRoadTaken < totalJunction - 1; i++) {
            int[] road = roads[i];
            int junction1 = road[0];
            int junction2 = road[1];
            int roadCost = road[2];

            boolean merged = spanningTree.union(junction1, junction2);
            if (!merged) continue;

            totalCost += roadCost;
            totalRoadTaken++;
        }

        return totalCost;
    }

    private int findOriginalCost() {
        int totalCost = 0;
        for (int i = 0; i < totalRoad; i++) totalCost += roads[i][2];
        return totalCost;
    }
}

class DisjointSet {
    private final int[] parents;

    public DisjointSet(int size) {
        this.parents = new int[size];
        clear(0, size);
    }

    public boolean union(int element1, int element2) {
        int root1 = find(element1);
        int root2 = find(element2);

        if (root1 == root2) return false;
        parents[root2] = root1;

        return true;
    }

    public int find(int element) {
        int root = element;
        while (parents[root] != -1) root = parents[root];
        return root;
    }

    public void clear(int from, int to) {
        Arrays.fill(parents, from, to, -1);
    }
}
