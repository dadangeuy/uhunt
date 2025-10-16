package uhunt.c1.p12608;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 12608 - Garbage Collection
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4286
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            int weightLimit = in.nextInt();
            int totalPickup = in.nextInt();

            // [distance, weight]
            int[][] pickups = new int[totalPickup][2];
            for (int j = 0; j < totalPickup; j++) {
                pickups[j][0] = in.nextInt();
                pickups[j][1] = in.nextInt();
            }

            Solution solution = new Solution(weightLimit, totalPickup, pickups);
            int travelDistance = solution.calculateTravelDistance();

            out.println(travelDistance);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int weightLimit;
    private final int totalPickup;
    private final int[][] pickups;

    public Solution(int weightLimit, int totalPickup, int[][] pickups) {
        this.weightLimit = weightLimit;
        this.totalPickup = totalPickup;
        this.pickups = pickups;
    }

    public int calculateTravelDistance() {
        int totalDistance = 0, currentLocation = 0, currentWeight = 0;
        for (int i = 0; i < totalPickup; i++) {
            int[] pickup = pickups[i];
            int pickupLocation = pickup[0], pickupWeight = pickup[1];

            totalDistance += pickupLocation - currentLocation;
            currentLocation = pickupLocation;

            boolean overweight = currentWeight + pickupWeight > weightLimit;
            if (overweight) {
                totalDistance += pickupLocation + pickupLocation;
                currentWeight = 0;
            }
            currentWeight += pickupWeight;

            boolean full = currentWeight >= weightLimit;
            boolean last = i == totalPickup - 1;
            if (full || last) {
                totalDistance += pickupLocation;
                currentLocation = 0;
                currentWeight = 0;
            }
        }

        return totalDistance;
    }
}
