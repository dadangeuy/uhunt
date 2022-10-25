package dev.rizaldi.uhunt.c3.p11900;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 11900 - Boiled Eggs
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3051
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCases = in.nextInt();
        for (int i = 0; i < totalCases; i++) {
            int total = in.nextInt();
            int maxItem = in.nextInt();
            int maxWeight = in.nextInt();
            int[] weights = new int[total];
            for (int j = 0; j < total; j++) weights[j] = in.nextInt();

            Solution solution = new Solution(total, maxItem, maxWeight, weights);
            int max = solution.getMax();
            out.format("Case %d: %d\n", i + 1, max);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int total;
    private final int maxItem;
    private final int maxWeight;
    private final int[] weights;


    public Solution(int total, int maxItem, int maxWeight, int[] weights) {
        this.total = total;
        this.maxItem = maxItem;
        this.maxWeight = maxWeight;
        this.weights = weights;
    }

    public int getMax() {
        Arrays.sort(weights);

        int totalItem = 0, totalWeight = 0;
        for (int weight : weights) {
            if (totalItem + 1 > maxItem) break;
            if (totalWeight + weight > maxWeight) break;

            totalItem++;
            totalWeight += weight;
        }

        return totalItem;
    }
}
