package uva.c4.g8.p11518;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static int[][] falls = new int[10000][2];
    private static int[] knocks = new int[10000];

    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        String[] lines;

        line = in.readLine();
        int totalTest = Integer.parseInt(line);
        while (totalTest-- > 0) {
            line = in.readLine();
            lines = line.split(" ");

            int totalDomino = Integer.parseInt(lines[0]);
            int totalFall = Integer.parseInt(lines[1]);
            int totalKnock = Integer.parseInt(lines[2]);

            for (int i = 0; i < totalFall; i++) {
                line = in.readLine();
                lines = line.split(" ");

                falls[i][0] = Integer.parseInt(lines[0]);
                falls[i][1] = Integer.parseInt(lines[1]);
            }

            for (int i = 0; i < totalKnock; i++) {
                line = in.readLine();

                knocks[i] = Integer.parseInt(line);
            }

            Solution solution = new Solution(totalDomino, totalFall, totalKnock, falls, knocks);
            int total = solution.getTotalFallOver();

            System.out.println(total);
        }
    }
}

// create uni-directional graph, containing domino fall relation
// for each knocked domino, do flood fill
// return total visited domino
class Solution {
    private final int totalDomino;
    private final int totalFall;
    private final int totalKnock;
    private final int[][] falls;
    private final int[] knocks;

    public Solution(int totalDomino, int totalFall, int totalKnock, int[][] falls, int[] knocks) {
        this.totalDomino = totalDomino;
        this.totalFall = totalFall;
        this.totalKnock = totalKnock;
        this.falls = falls;
        this.knocks = knocks;
    }

    public int getTotalFallOver() {
        Map<Integer, Set<Integer>> graph = createGraph(falls, totalFall);
        Set<Integer> fallOver = new TreeSet<>();
        LinkedList<Integer> dominoq = new LinkedList<>();
        for (int i = 0; i < totalKnock; i++) {
            int knock = knocks[i];

            fallOver.add(knock);
            dominoq.add(knock);

            while (!dominoq.isEmpty()) {
                int domino = dominoq.remove();
                for (int nextDomino : graph.getOrDefault(domino, Collections.emptySet())) {
                    if (fallOver.contains(nextDomino)) continue;

                    fallOver.add(nextDomino);
                    dominoq.add(nextDomino);
                }
            }
        }

        return fallOver.size();
    }

    private Map<Integer, Set<Integer>> createGraph(int[][] edges, int totalEdge) {
        Map<Integer, Set<Integer>> graph = new TreeMap<>();
        for (int i = 0; i < totalEdge; i++) {
            int[] edge = edges[i];
            int from = edge[0];
            int to = edge[1];

            graph.computeIfAbsent(from, k -> new TreeSet<>()).add(to);
        }

        return graph;
    }
}
