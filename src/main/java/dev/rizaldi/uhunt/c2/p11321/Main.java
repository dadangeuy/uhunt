package dev.rizaldi.uhunt.c2.p11321;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * 11321 - Sort! Sort!! and Sort!!!
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2296
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int totalNumber = in.nextInt();
            int modulo = in.nextInt();

            out.format("%d %d\n", totalNumber, modulo);
            if (totalNumber == 0 && modulo == 0) break;

            int[] numbers = new int[totalNumber];
            for (int i = 0; i < totalNumber; i++) numbers[i] = in.nextInt();

            Solution solution = new Solution(totalNumber, modulo, numbers);
            int[] sortedNumbers = solution.getSortedNumbers();
            for (int number : sortedNumbers) out.println(number);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int totalNumber;
    private final int modulo;
    private final int[] numbers;

    public Solution(int totalNumber, int modulo, int[] numbers) {
        this.totalNumber = totalNumber;
        this.modulo = modulo;
        this.numbers = numbers;
    }

    public int[] getSortedNumbers() {
        return Arrays.stream(numbers)
                .boxed()
                .sorted(getSortCriteria())
                .mapToInt(i -> i)
                .toArray();
    }

    /**
     * sort ascending by:
     * 1. smaller value % modulo
     * 2. is odd
     * 3. larger odd OR smaller even
     */
    private Comparator<Integer> getSortCriteria() {
        return Comparator
                .<Integer>comparingInt(v -> v % modulo)
                .thenComparingInt(v -> even(v) ? 1 : 0)
                .thenComparingInt(v -> even(v) ? v : -v);
    }

    private boolean even(int value) {
        return (value & 1) == 0;
    }
}
