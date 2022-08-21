package dev.rizaldi.uhunt.c2.p11495;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 11495 - Bubbles and Buckets
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2490
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int totalNumber = in.nextInt();
            if (totalNumber == 0) break;

            int[] numbers = new int[totalNumber];
            for (int i = 0; i < totalNumber; i++) numbers[i] = in.nextInt();

            Solution solution = new Solution(totalNumber, numbers);
            String winner = solution.findWinner();

            out.println(winner);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final String FIRST = "Marcelo";
    private static final String SECOND = "Carlos";
    private final int totalNumber;
    private final int[] numbers;

    public Solution(int totalNumber, int[] numbers) {
        this.totalNumber = totalNumber;
        this.numbers = numbers;
    }

    public String findWinner() {
        return findWinnerUsingCount();
    }

    private String findWinnerUsingCount() {
        int totalSwap = 0;
        for (int i = 0; i < totalNumber; i++) {
            int countGt = 0;
            for (int j = i + 1; j < totalNumber; j++) {
                if (numbers[i] > numbers[j]) {
                    countGt++;
                }
            }
            totalSwap += countGt;
        }

        return ((totalSwap & 1) == 0) ? SECOND : FIRST;
    }
}
