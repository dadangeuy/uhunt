package dev.rizaldi.uhunt.p11997;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

// TODO: Time limit exceeded
public class Main {
    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);

        int totalArray;
        int[][] arrays = new int[750][750];

        while (scanner.hasNextInt()) {
            totalArray = scanner.nextInt();
            for (int i = 0; i < totalArray; i++) {
                for (int j = 0; j < totalArray; j++) {
                    arrays[i][j] = scanner.nextInt();
                }
            }

            Solution solution = new Solution(totalArray, arrays);
            PriorityQueue<Integer> topSmallest = solution.topSmallest();

            for (int i = 0; i < totalArray; i++) {
                if (i == totalArray - 1) System.out.println(topSmallest.remove());
                else System.out.format("%d ", topSmallest.remove());
            }
        }
    }
}

class Solution {
    private final int totalArray;
    private final int[][] arrays;
    private static final PriorityQueue<Integer> sumq = new PriorityQueue<>();
    private static final PriorityQueue<Integer> nextSumq = new PriorityQueue<>(Comparator.reverseOrder());

    public Solution(int totalArray, int[][] arrays) {
        this.totalArray = totalArray;
        this.arrays = arrays;

        sumq.clear();
        nextSumq.clear();
        sumq.add(0);
    }

    public PriorityQueue<Integer> topSmallest() {
        for (int i = 0; i < totalArray; i++) Arrays.sort(arrays[i], 0, totalArray);

        for (int i = 0; i < totalArray; i++) {
            while (!sumq.isEmpty()) {
                int prevSum = sumq.remove();
                if (nextSumq.size() == totalArray && prevSum + arrays[i][0] >= nextSumq.peek()) break;

                for (int j = 0; j < totalArray; j++) {
                    int nextSum = prevSum + arrays[i][j];
                    nextSumq.add(nextSum);
                    while (nextSumq.size() > totalArray) nextSumq.remove();
                    if (nextSumq.size() == totalArray && nextSum >= nextSumq.peek()) break;
                }
            }

            sumq.clear();
            sumq.addAll(nextSumq);
            nextSumq.clear();
        }

        return sumq;
    }
}
