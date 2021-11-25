package dev.rizaldi.uhunt.p11997;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

// TODO: Time limit exceeded
public class Main {
    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;

        int totalArray;
        int[][] arrays = new int[751][751];

        while ((line = in.readLine()) != null && !line.isEmpty()) {
            totalArray = Integer.parseInt(line);
            for (int i = 0; i < totalArray; i++) {
                line = in.readLine();
                String[] numberString = line.split(" ");
                for (int j = 0; j < totalArray; j++) {
                    arrays[i][j] = Integer.parseInt(numberString[j]);
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
