package uhunt.c2.g0.p11300;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 11300 - Spreading the Wealth
 * Time limit: 6.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2275
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextInt()) {
            final int totalVillagers = in.nextInt();
            final long[] coins = new long[totalVillagers];
            for (int i = 0; i < totalVillagers; i++) {
                coins[i] = in.nextLong();
            }

            final long minimumTransfer = findMinimumTransferForEqualDistribution(coins);
            out.println(minimumTransfer);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static long findMinimumTransferForEqualDistribution(final long[] coins) {
        if (coins.length == 0) {
            return 0;
        }

        final long sum = Arrays.stream(coins).sum();
        final long mean = sum / coins.length;
        final long[] surplus = Arrays.stream(coins)
                .map(coin -> mean - coin)
                .toArray();
        final long[] cumulative = new long[coins.length];
        for (int i = 0; i < coins.length; i++) {
            cumulative[i] = i == 0? 0 : cumulative[i - 1] + surplus[i];
        }
        final long median = Arrays.stream(cumulative).sorted().toArray()[(coins.length - 1) / 2];
        final long absoluteDeviation = Arrays.stream(cumulative).map(c -> Math.abs(c - median)).sum();

        return absoluteDeviation;
    }
}
