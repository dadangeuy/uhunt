package uhunt.c8.g6.p10626;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 10626 - Buying Coke
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1567
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        final int totalTestCases = in.nextInt();
        for (int testCase = 1; testCase <= totalTestCases; testCase++) {
            final Input input = new Input();
            input.totalCokes = in.nextInt();
            input.totalCoins1 = in.nextInt();
            input.totalCoins5 = in.nextInt();
            input.totalCoins10 = in.nextInt();

            final Output output = process.process(input);
            out.println(output.minimumTotalCoins);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalCokes;
    public int totalCoins1;
    public int totalCoins5;
    public int totalCoins10;
}

class Output {
    public int minimumTotalCoins;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        final int value = input.totalCoins1 + 5 * input.totalCoins5 + 10 * input.totalCoins10;
        final Integer[][][] dp = new Integer[value + 1][(value / 5) + 1][(value / 10) + 1];
        output.minimumTotalCoins = getMinimumTotalCoins(
            input.totalCokes,
            input.totalCoins1,
            input.totalCoins5,
            input.totalCoins10,
            dp
        );
        return output;
    }

    private int getMinimumTotalCoins(
        final int totalCokes,
        final int totalCoins1,
        final int totalCoins5,
        final int totalCoins10,
        final Integer[][][] dp
    ) {
        if (dp[totalCoins1][totalCoins5][totalCoins10] != null) return dp[totalCoins1][totalCoins5][totalCoins10];
        if (totalCokes == 0) return dp[totalCoins1][totalCoins5][totalCoins10] = 0;

        int minimumTotalCoins = Integer.MAX_VALUE;
        if (totalCoins10 >= 1) {
            final int currentTotalCoins = 1;
            final int nextTotalCoins = getMinimumTotalCoins(totalCokes - 1, totalCoins1 + 2, totalCoins5, totalCoins10 - 1, dp);
            final int totalCoins = currentTotalCoins + nextTotalCoins;
            minimumTotalCoins = Math.min(minimumTotalCoins, totalCoins);
        }
        if (totalCoins10 >= 1 && totalCoins1 >= 3) {
            final int currentTotalCoins = 4;
            final int nextTotalCoins = getMinimumTotalCoins(totalCokes - 1, totalCoins1 - 3, totalCoins5 + 1, totalCoins10 - 1, dp);
            final int totalCoins = currentTotalCoins + nextTotalCoins;
            minimumTotalCoins = Math.min(minimumTotalCoins, totalCoins);
        }
        if (totalCoins5 >= 2) {
            final int currentTotalCoins = 2;
            final int nextTotalCoins = getMinimumTotalCoins(totalCokes - 1, totalCoins1 + 2, totalCoins5 - 2, totalCoins10, dp);
            final int totalCoins = currentTotalCoins + nextTotalCoins;
            minimumTotalCoins = Math.min(minimumTotalCoins, totalCoins);
        }
        if (totalCoins5 >= 1 && totalCoins1 >= 3) {
            final int currentTotalCoins = 4;
            final int nextTotalCoins = getMinimumTotalCoins(totalCokes - 1, totalCoins1 - 3, totalCoins5 - 1, totalCoins10, dp);
            final int totalCoins = currentTotalCoins + nextTotalCoins;
            minimumTotalCoins = Math.min(minimumTotalCoins, totalCoins);
        }
        if (totalCoins1 >= 8) {
            final int currentTotalCoins = 8;
            final int nextTotalCoins = getMinimumTotalCoins(totalCokes - 1, totalCoins1 - 8, totalCoins5, totalCoins10, dp);
            final int totalCoins = currentTotalCoins + nextTotalCoins;
            minimumTotalCoins = Math.min(minimumTotalCoins, totalCoins);
        }

        return dp[totalCoins1][totalCoins5][totalCoins10] = minimumTotalCoins;
    }
}
