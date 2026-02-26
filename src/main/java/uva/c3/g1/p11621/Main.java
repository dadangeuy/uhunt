package uva.c3.g1.p11621;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static BufferedReader in;
    private static StringBuilder out;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        while (true) {
            long m = Long.parseLong(in.readLine());
            if (m == 0) break;

            Solution solution = new Solution(m);
            long n = solution.getMinimumN();

            out.append(n).append('\n');
        }

        System.out.print(out);
    }
}

// calculate 2^0..2^x, store in a array (precalculate) P2
// calculate 3^0..3^x, store in a array (precalculate) P3
// for all combination of P2(i)*P3(j), find minimum
// time complexity: O(32)
class Solution {
    private static final long[] twoPowers = precalculatePowers(2, 32);
    private static final long[] threePowers = precalculatePowers(3, 32);
    private final long m;

    public Solution(long m) {
        this.m = m;
    }

    private static long[] precalculatePowers(int power, int size) {
        long[] powers = new long[size];
        powers[0] = 1;
        for (int i = 1; i < size; i++) powers[i] = powers[i - 1] * power;
        return powers;
    }

    public long getMinimumN() {
        long minN = twoPowers[31];
        for (int i = 0; i < twoPowers.length; i++) {
            for (int j = 0; j < threePowers.length; j++) {
                long n = twoPowers[i] * threePowers[j];
                if (n >= m) minN = Math.min(minN, n);
            }
        }

        return minN;
    }
}
