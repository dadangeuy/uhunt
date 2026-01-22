package uhunt.c4.g8.p11228;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Main {
    private static BufferedReader in;
    private static StringBuilder out;
    private static String[] lines;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        int totalTest = Integer.parseInt(in.readLine());
        for (int i = 0; i < totalTest; i++) {
            lines = in.readLine().split(" ", 2);
            int totalCity = Integer.parseInt(lines[0]);
            int threshold = Integer.parseInt(lines[1]);

            int[][] coordinates = new int[totalCity][2];
            for (int j = 0; j < totalCity; j++) {
                lines = in.readLine().split(" ", 2);
                coordinates[j][0] = Integer.parseInt(lines[0]);
                coordinates[j][1] = Integer.parseInt(lines[1]);
            }

            Solution solution = new Solution(totalCity, threshold, coordinates);
            int[] totalStateAndRoadAndRailroad = solution.getTotalStateAndRoadAndRailroad();
            int totalState = totalStateAndRoadAndRailroad[0];
            int totalRoad = totalStateAndRoadAndRailroad[1];
            int totalRailroad = totalStateAndRoadAndRailroad[2];

            out
                .append("Case #").append(i + 1).append(": ")
                .append(totalState).append(' ').append(totalRoad).append(' ').append(totalRailroad).append('\n');
        }

        System.out.print(out);
    }
}

/**
 * quesstion to answer:
 * 1. how to get list of city in each state
 * 2. how to get total road between city
 * 3. how to get total railroad between state
 * <p>
 * 1) to get list of city in each state, we can compare each city (city A and city B), if it's within the threshold,
 * then those city is connected. we can use disjoint set to store the connection, and each set will represent our state
 * time complexity: O(n^2), n = total city
 * <p>
 * 2 + 3) first, we need to calculate distance between each city (edges). starting from smallest distance,
 * if the cities isn't connected, then we build a road (if on a same state) or a railroad (if on a different state).
 * use disjoint set to store list of connected cities
 * time complexity: O(m*log(m)), m = total edge = total city * total city
 * <p>
 * addition
 * - implement compression to improve find performance on disjointset
 * - use pythagoras formula to determine if two city was between threshold, i.e. delta(x)^2 + delta(y)^2 <= threshold^2
 */
class Solution {
    private final int totalCity;
    private final int threshold;
    private final int[][] coordinates;

    public Solution(int totalCity, int threshold, int[][] coordinates) {
        this.totalCity = totalCity;
        this.threshold = threshold;
        this.coordinates = coordinates;
    }

    public int[] getTotalStateAndRoadAndRailroad() {
        // get list of city in each state
        DisjointSet stateDs = new DisjointSet(totalCity);
        for (int city1 = 0; city1 < totalCity; city1++) {
            for (int city2 = city1 + 1; city2 < totalCity; city2++) {
                boolean withinThreshold = withinThreshold(coordinates[city1], coordinates[city2]);
                if (withinThreshold) stateDs.union(city1, city2);
            }
        }

        // calculate distance between city
        ArrayList<int[]> distances = new ArrayList<>();
        for (int city1 = 0; city1 < totalCity; city1++) {
            for (int city2 = city1 + 1; city2 < totalCity; city2++) {
                int distance = distance(coordinates[city1], coordinates[city2]);
                distances.add(new int[]{city1, city2, distance});
            }
        }

        // process from shortest road/railroad, and connect the city
        DisjointSet connectDs = new DisjointSet(totalCity);
        double totalRoad = 0;
        double totalRailroad = 0;

        Comparator<int[]> compareByDistance = Comparator.comparingInt(s -> s[2]);
        distances.sort(compareByDistance);
        for (int[] state : distances) {
            int city1 = state[0];
            int city2 = state[1];
            int distance = state[2];

            boolean merged = connectDs.union(city1, city2);
            if (!merged) continue;

            double actualDistance = Math.sqrt(distance);
            int state1 = stateDs.find(city1);
            int state2 = stateDs.find(city2);
            if (state1 == state2) totalRoad += actualDistance;
            else totalRailroad += actualDistance;
        }

        return new int[]{stateDs.count(), (int) Math.round(totalRoad), (int) Math.round(totalRailroad)};
    }

    private boolean withinThreshold(int[] coordinate1, int[] coordinate2) {
        return distance(coordinate1, coordinate2) <= threshold * threshold;
    }

    private int distance(int[] coordinate1, int[] coordinate2) {
        int dx = Math.abs(coordinate1[0] - coordinate2[0]);
        int dy = Math.abs(coordinate1[1] - coordinate2[1]);
        return dx * dx + dy * dy;
    }
}

class DisjointSet {
    private final int[] parents;

    public DisjointSet(int size) {
        this.parents = new int[size];
        Arrays.fill(parents, -1);
    }

    public boolean union(int element1, int element2) {
        int root1 = find(element1);
        int root2 = find(element2);
        if (root1 == root2) return false;

        this.parents[root1] = root2;
        return true;
    }

    public int find(int element) {
        int root = element;
        while (parents[root] != -1) root = parents[root];
        compress(element, root);
        return root;
    }

    public int count() {
        int count = 0;
        for (int parent : parents) if (parent == -1) count++;
        return count;
    }

    private void compress(int element, int root) {
        while (element != root && parents[element] != root) {
            int parent = parents[element];
            parents[element] = root;
            element = parent;
        }
    }
}
