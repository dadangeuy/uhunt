package uva.c4.g3.p11463;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int totalTest = in.nextInt();
        for (int test = 1; test <= totalTest; test++) {
            int totalBuilding = in.nextInt();
            int totalRoad = in.nextInt();
            int[][] roads = new int[totalRoad][];
            for (int i = 0; i < totalRoad; i++) {
                int building1 = in.nextInt();
                int building2 = in.nextInt();
                int[] road = new int[]{building1, building2};
                roads[i] = road;
            }
            int origin = in.nextInt();
            int destination = in.nextInt();

            Solution solution = new Solution(totalBuilding, totalRoad, roads, origin, destination);
            int time = solution.getMinimumTime();
            System.out.format("Case %d: %d\n", test, time);
        }
    }
}

class Solution {
    private final int totalBuilding;
    private final int totalRoad;
    private final int[][] roads;
    private final int origin;
    private final int destination;

    public Solution(int totalBuilding, int totalRoad, int[][] roads, int origin, int destination) {
        this.totalBuilding = totalBuilding;
        this.totalRoad = totalRoad;
        this.roads = roads;
        this.origin = origin;
        this.destination = destination;
    }

    public int getMinimumTime() {
        int[][] times = getAllPairTimes();

        int minFinishTime = times[origin][0] + times[0][destination];
        for (int i = 1; i < totalBuilding; i++) {
            int finishTime = times[origin][i] + times[i][destination];
            minFinishTime = Math.max(minFinishTime, finishTime);
        }

        return minFinishTime;
    }

    private int[][] getAllPairTimes() {
        int[][] times = new int[totalBuilding][totalBuilding];
        for (int i = 0; i < totalBuilding; i++) Arrays.fill(times[i], Integer.MAX_VALUE);
        for (int i = 0; i < totalBuilding; i++) times[i][i] = 0;

        for (int[] road : roads) {
            int building1 = road[0];
            int building2 = road[1];
            times[building1][building2] = times[building2][building1] = 1;
        }

        for (int i = 0; i < totalBuilding; i++) {
            for (int j = 0; j < totalBuilding; j++) {
                if (times[j][i] == Integer.MAX_VALUE) continue;
                for (int k = 0; k < totalBuilding; k++) {
                    if (times[i][k] == Integer.MAX_VALUE) continue;
                    times[j][k] = times[k][j] = Math.min(times[j][k], times[j][i] + times[i][k]);
                }
            }
        }

        return times;
    }
}
