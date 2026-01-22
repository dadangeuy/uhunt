package uhunt.c4.g4.p11504;

import java.util.*;

public class Main {
    public static final int MAX = 100_000;
    private static final int[][] falls = new int[MAX][2];

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);

        int totalTest = in.nextInt();
        for (int i = 0; i < totalTest; i++) {
            int totalDomino = in.nextInt();
            int totalFall = in.nextInt();

            for (int j = 0; j < totalFall; j++) {
                falls[j][0] = in.nextInt() - 1;
                falls[j][1] = in.nextInt() - 1;
            }

            Solution solution = new Solution(totalDomino, totalFall, falls);
            int totalKnock = solution.getTotalKnock();

            System.out.println(totalKnock);
        }
    }
}

class Solution {
    private static final Map<Integer, List<Integer>> graph = new HashMap<>(Main.MAX);
    private final int totalDomino;
    private final int totalFall;
    private final int[][] falls;

    public Solution(int totalDomino, int totalFall, int[][] falls) {
        this.totalDomino = totalDomino;
        this.totalFall = totalFall;
        this.falls = falls;
    }

    public int getTotalKnock() {
        fillGraph();
        LinkedList<Integer> sequences = topologicalSort();

        int totalKnock = 0;
        boolean[] visited = new boolean[totalDomino];
        boolean[] explored = new boolean[totalDomino];

        for (int domino : sequences) {
            if (visited[domino]) continue;

            explored[domino] = true;
            topologicalSort(domino, explored, visited, new LinkedList<>());
            totalKnock++;
        }

        return totalKnock;
    }

    private void fillGraph() {
        graph.clear();
        for (int i = 0; i < totalFall; i++) {
            int[] fall = falls[i];
            int domino1 = fall[0];
            int domino2 = fall[1];
            graph.computeIfAbsent(domino1, k -> new LinkedList<>()).add(domino2);
        }
    }

    private LinkedList<Integer> topologicalSort() {
        LinkedList<Integer> sequences = new LinkedList<>();
        boolean[] explored = new boolean[totalDomino];
        boolean[] visited = new boolean[totalDomino];

        for (int sourceVertice = 0; sourceVertice < totalDomino; sourceVertice++) {
            if (visited[sourceVertice]) continue;
            topologicalSort(sourceVertice, explored, visited, sequences);
        }

        return sequences;
    }

    private void topologicalSort(int sourceVertice, boolean[] explored, boolean[] visited, LinkedList<Integer> sequences) {
        LinkedList<Integer> verticeq = new LinkedList<>();
        explored[sourceVertice] = true;
        verticeq.addFirst(sourceVertice);

        while (!verticeq.isEmpty()) {
            int vertice = verticeq.getFirst();

            if (visited[vertice]) {
                verticeq.removeFirst();
                sequences.addFirst(vertice);
                continue;
            }
            visited[vertice] = true;

            for (int nextVertice : graph.getOrDefault(vertice, Collections.emptyList())) {
                if (explored[nextVertice]) continue;
                explored[nextVertice] = true;
                verticeq.addFirst(nextVertice);
            }
        }
    }
}
