package dev.rizaldi.uhunt.c3.p12965;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 12965 - Angry Bids
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4844
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCases = in.nextInt();
        for (int i = 0; i < totalCases; i++) {
            int totalProducers = in.nextInt();
            int totalConsumers = in.nextInt();

            int[] producers = new int[totalProducers];
            for (int j = 0; j < totalProducers; j++) producers[j] = in.nextInt();

            int[] consumers = new int[totalConsumers];
            for (int j = 0; j < totalConsumers; j++) consumers[j] = in.nextInt();

            Solution solution = new Solution(producers, consumers);
            int[] priceAndTotal = solution.getPriceAndTotal();

            out.format("%d %d\n", priceAndTotal[0], priceAndTotal[1]);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int[] producers;  // price must be >=
    private final int[] consumers;  // price must be <=

    public Solution(int[] producers, int[] consumers) {
        this.producers = producers;
        this.consumers = consumers;
    }

    public int[] getPriceAndTotal() {
        Arrays.sort(producers);
        Arrays.sort(consumers);

        int[] bestPriceAndTotal = new int[]{0, getTotalAngryPeoples(0)};

        for (int price : producers) {
            int totalAngryPeoples = getTotalAngryPeoples(price);
            bestPriceAndTotal = max(bestPriceAndTotal, new int[]{price, totalAngryPeoples});
        }

        return bestPriceAndTotal;
    }

    private int getTotalAngryPeoples(int price) {
        return getTotalAngryProducers(price) + getTotalAngryConsumers(price);
    }

    private int getTotalAngryProducers(int price) {
        int index = greater(producers, price);
        boolean none = index == producers.length;
        return none ? 0 : producers.length - index;
    }

    private int getTotalAngryConsumers(int price) {
        int index = lower(consumers, price);
        boolean none = index == consumers.length;
        return none ? 0 : index + 1;
    }

    private int greater(int[] values, int target) {
        int index = values.length, left = 0, right = values.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int val = values[mid];

            if (val <= target) {
                left = mid + 1;
            } else {
                index = mid;
                right = mid - 1;
            }
        }

        return index;
    }

    private int lower(int[] values, int target) {
        int index = values.length, left = 0, right = values.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int val = values[mid];

            if (val >= target) {
                right = mid - 1;
            } else {
                index = mid;
                left = mid + 1;
            }
        }

        return index;
    }

    private int[] max(int[] first, int[] second) {
        if (first[1] < second[1]) return first;
        if (second[1] < first[1]) return second;
        if (first[0] < second[0]) return first;
        return second;
    }
}
