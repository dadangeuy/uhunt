package uva.uhunt.c2.g1.p12571;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 12571 - Brother & Sisters!
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4016
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            int totalNumber = in.nextInt();
            int totalQuery = in.nextInt();

            int[] numbers = new int[totalNumber];
            for (int j = 0; j < totalNumber; j++) numbers[j] = in.nextInt();

            Solution solution = new Solution(numbers);
            for (int j = 0; j < totalQuery; j++) {
                int target = in.nextInt();
                int max = solution.getMaxAnd(target);
                out.println(max);
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final int MAX_TARGET = 230;
    private static final int EMPTY = -1;
    private final int[] numbers;
    private final int[] cached_max;

    public Solution(int[] numbers) {
        this.numbers = numbers;
        this.cached_max = new int[MAX_TARGET];
        Arrays.fill(cached_max, EMPTY);
    }

    public int getMaxAnd(int target) {
        if (cached_max[target] != EMPTY) return cached_max[target];

        int max = 0;
        for (int number : numbers) max = Math.max(max, target & number);
        return cached_max[target] = max;
    }
}
