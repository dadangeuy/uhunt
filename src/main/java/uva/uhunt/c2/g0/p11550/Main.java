package uva.uhunt.c2.g0.p11550;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Main {
    private static BufferedReader in;
    private static StringBuilder out;
    private static Iterator<Integer> nextInts;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();
        nextInts = in.lines()
            .filter(line -> !line.isEmpty())
            .flatMap(line -> Arrays.stream(line.split(" ")))
            .mapToInt(Integer::parseInt)
            .iterator();

        int totalCase = nextInts.next();
        for (int i = 0; i < totalCase; i++) {
            int totalRow = nextInts.next();
            int totalCol = nextInts.next();
            boolean[][] matrix = new boolean[totalRow][totalCol];
            for (int j = 0; j < totalRow; j++) {
                for (int k = 0; k < totalCol; k++) {
                    matrix[j][k] = nextInts.next() == 1;
                }
            }

            Solution solution = new Solution(totalRow, totalCol, matrix);
            boolean can = solution.canBeIncidenceMatrixOfUndirectedGraph();
            if (can) out.append("Yes\n");
            else out.append("No\n");
        }

        System.out.print(out);
    }
}


/**
 * 1. initialize undirected graph
 * 2. for each edge in matrix, add edge to the graph if matrix value is true
 * 3. if conflicted (edge existed in graph), it's not possible, return false
 * 4. if there are no conflict, then it's possible, return true
 * <p>
 * what data structure we can use to represent undirected graph? use 2d boolean
 * time complexity: O(V*E)
 */
class Solution {
    private final int totalVertice;
    private final int totalEdge;
    private final boolean[][] matrix;

    public Solution(int totalVertice, int totalEdge, boolean[][] matrix) {
        this.totalVertice = totalVertice;
        this.totalEdge = totalEdge;
        this.matrix = matrix;
    }

    public boolean canBeIncidenceMatrixOfUndirectedGraph() {
        boolean[][] graph = new boolean[totalVertice][totalVertice];

        for (int edge = 0; edge < totalEdge; edge++) {
            LinkedList<Integer> vertices = new LinkedList<>();
            for (int vertice = 0; vertice < totalVertice; vertice++) {
                if (matrix[vertice][edge]) {
                    vertices.add(vertice);
                }
            }

            if (vertices.size() != 2) return false;
            int v1 = vertices.getFirst();
            int v2 = vertices.getLast();

            boolean conflict = graph[v1][v2] == true;
            if (conflict) return false;

            graph[v1][v2] = graph[v2][v1] = true;
        }

        return true;
    }
}
