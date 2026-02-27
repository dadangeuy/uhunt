package uva.uhunt.c4.g9.p459;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int totalTest = in.nextInt();
        in.nextLine();
        in.nextLine();

        for (int test = 0; test < totalTest; test++) {
            char maxVertice = in.nextLine().charAt(0);
            LinkedList<char[]> edgesLL = new LinkedList<>();

            String edgeText;
            while (in.hasNextLine() && !(edgeText = in.nextLine()).isEmpty()) {
                char vertice1 = edgeText.charAt(0);
                char vertice2 = edgeText.charAt(1);
                char[] edge = new char[]{vertice1, vertice2};
                edgesLL.addLast(edge);
            }

            char[][] edges = edgesLL.toArray(new char[0][0]);

            Solution solution = new Solution(maxVertice, edges);
            int total = solution.getTotalSubgraph();
            System.out.println(total);
            if (test < totalTest - 1) System.out.println();
        }
    }
}

class Solution {
    private final int totalVertice;
    private final char[][] edges;

    public Solution(char maxVertice, char[][] edges) {
        this.totalVertice = maxVertice - 'A' + 1;
        this.edges = edges;
    }

    public int getTotalSubgraph() {
        boolean[][] graph = createGraph();
        boolean[] visited = new boolean[totalVertice];

        int total = 0;
        for (int initialVertice = 0; initialVertice < totalVertice; initialVertice++) {
            if (visited[initialVertice]) continue;

            total++;
            LinkedList<Integer> verticeq = new LinkedList<>();
            verticeq.addLast(initialVertice);
            visited[initialVertice] = true;

            while (!verticeq.isEmpty()) {
                int currentVertice = verticeq.removeFirst();
                for (int nextVertice = 0; nextVertice < totalVertice; nextVertice++) {
                    if (!graph[currentVertice][nextVertice]) continue;
                    if (visited[nextVertice]) continue;
                    verticeq.addLast(nextVertice);
                    visited[nextVertice] = true;
                }
            }
        }

        return total;
    }

    private boolean[][] createGraph() {
        boolean[][] graph = new boolean[totalVertice][totalVertice];
        for (char[] edge : edges) {
            int vertice1 = edge[0] - 'A';
            int vertice2 = edge[1] - 'A';
            graph[vertice1][vertice2] = graph[vertice2][vertice1] = true;
        }
        return graph;
    }
}
