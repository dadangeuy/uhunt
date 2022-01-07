package dev.rizaldi.uhunt.c4.p13127;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static BufferedReader in;
    private static StringBuilder out;
    private static LinkedList<Integer> buffer;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        fillBuffer();

        while (!buffer.isEmpty()) {
            int totalSite = buffer.removeFirst();
            int totalRoad = buffer.removeFirst();
            int totalBank = buffer.removeFirst();
            int totalPolice = buffer.removeFirst();

            int[][] roads = new int[totalRoad][3];
            for (int i = 0; i < totalRoad; i++) {
                int site1 = buffer.removeFirst();
                int site2 = buffer.removeFirst();
                int duration = buffer.removeFirst();

                roads[i][0] = site1;
                roads[i][1] = site2;
                roads[i][2] = duration;
            }

            int[] bankSites = new int[totalBank];
            for (int i = 0; i < totalBank; i++) {
                int bankSite = buffer.removeFirst();
                bankSites[i] = bankSite;
            }

            int[] policeSites = new int[totalPolice];
            for (int i = 0; i < totalPolice; i++) {
                int policeSite = buffer.removeFirst();
                policeSites[i] = policeSite;
            }

            Solution solution = new Solution(
                totalSite, totalRoad, totalBank, totalPolice,
                roads, bankSites, policeSites
            );
            Answer answer = solution.getTotalAndDurationOfTheFurthestBank();

            out.append(answer.count).append(' ');
            if (answer.duration == Integer.MAX_VALUE) out.append("*\n");
            else out.append(answer.duration).append('\n');

            for (int i = 0; i < answer.sites.length; i++) {
                int site = answer.sites[i];
                out.append(site);
                if (i == answer.sites.length - 1) out.append('\n');
                else out.append(' ');
            }
        }

        System.out.print(out);
    }

    private static void fillBuffer() throws IOException {
        buffer = new LinkedList<>();

        while (true) {
            String line = in.readLine();
            if (line == null) break;
            if (line.isEmpty()) continue;

            String[] lines = line.split(" ");
            for (String text : lines) {
                buffer.add(Integer.parseInt(text));
            }
        }
    }
}

// 1. get shortest path from police sites to all sites using dijkstra
// 2. get maximum duration from each bank site
// 3. count bank site with maximum duration
// time complexity: O(total-site * log(total-site)) -> complexity of dijkstra algorithm
class Solution {
    private final int totalSite;
    private final int totalRoad;
    private final int totalBank;
    private final int totalPolice;
    private final int[][] roads;
    private final int[] bankSites;
    private final int[] policeSites;

    public Solution(
        int totalSite, int totalRoad, int totalBank, int totalPolice,
        int[][] roads, int[] bankSites, int[] policeSites
    ) {
        this.totalSite = totalSite;
        this.totalRoad = totalRoad;
        this.totalBank = totalBank;
        this.totalPolice = totalPolice;
        this.roads = roads;
        this.bankSites = bankSites;
        this.policeSites = policeSites;
    }

    public Answer getTotalAndDurationOfTheFurthestBank() {
        Map<Integer, List<int[]>> graph = createGraph();

        int[] durations = new int[totalSite];
        Arrays.fill(durations, Integer.MAX_VALUE);

        Comparator<int[]> compareByDuration = Comparator.comparingInt(v -> v[1]);
        PriorityQueue<int[]> traverseq = new PriorityQueue<>(compareByDuration);
        for (int policeSite : policeSites) {
            durations[policeSite] = 0;
            traverseq.add(new int[]{policeSite, 0});
        }

        while (!traverseq.isEmpty()) {
            int[] traverse = traverseq.remove();
            int site = traverse[0];
            int duration = traverse[1];

            for (int[] entry : graph.getOrDefault(site, Collections.emptyList())) {
                int nextSite = entry[0];
                int nextDuration = duration + entry[1];

                boolean optimal = nextDuration < durations[nextSite];
                if (!optimal) continue;

                durations[nextSite] = nextDuration;
                traverseq.add(new int[]{nextSite, nextDuration});
            }
        }

        int maxDuration = max(durations, bankSites);
        int[] sites = filter(durations, bankSites, maxDuration);
        Arrays.sort(sites);

        return new Answer(sites.length, maxDuration, sites);
    }

    private Map<Integer, List<int[]>> createGraph() {
        Map<Integer, List<int[]>> graph = new HashMap<>(2 * totalSite);

        for (int[] road : roads) {
            int site1 = road[0];
            int site2 = road[1];
            int duration = road[2];

            graph
                .computeIfAbsent(site1, k -> new LinkedList<>())
                .add(new int[]{site2, duration});
            graph
                .computeIfAbsent(site2, k -> new LinkedList<>())
                .add(new int[]{site1, duration});
        }

        return graph;
    }

    private int max(int[] values, int[] indexes) {
        int max = values[indexes[0]];
        for (int index : indexes) max = Math.max(max, values[index]);
        return max;
    }

    private int[] filter(int[] values, int[] indexes, int targetValue) {
        int count = count(values, indexes, targetValue);
        int[] newValues = new int[count];
        int length = 0;
        for (int index : indexes) if (values[index] == targetValue) newValues[length++] = index;
        return newValues;
    }

    private int count(int[] values, int[] indexes, int targetValue) {
        int count = 0;
        for (int index : indexes) if (values[index] == targetValue) count++;
        return count;
    }
}

class Answer {
    // count bank with the farthest distance from police
    public final int count;
    // fastest time for police to reach the farthest bank
    public final int duration;
    // sites of the farthest bank
    public final int[] sites;

    public Answer(int count, int duration, int[] sites) {
        this.count = count;
        this.duration = duration;
        this.sites = sites;
    }
}
