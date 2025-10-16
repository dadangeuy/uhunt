package uhunt.c3.p1213;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    private static BufferedReader in;
    private static StringBuilder out;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        while (true) {
            String[] lines = in.readLine().split(" ", 2);
            int n = Integer.parseInt(lines[0]);
            int k = Integer.parseInt(lines[1]);
            if (n == 0 && k == 0) break;

            Solution solution = new Solution(n, k);
            int total = solution.getTotalWayToGetNFromKPrimes();

            out.append(total).append('\n');
        }

        System.out.print(out);
    }
}

// step 1: precalculate prime from 1..n (1120) using sieve of eratosthenes, time O(n)
// step 2: use dp, with state dp[sum][total prime] = <total way>
// iterate each prime (1..p), iterate each sum (1..n), iterate each total prime(1..k)
// next sum = curr sum + curr prime, next total prime = curr total prime + 1
// increment dp[next sum][next total prime] += dp[curr sum][curr total prime]
// time O(total prime * n * k)
// additional improvement: precalculate step 2
class Solution {
    private static final int MAX_N = 1120;
    private static final int MAX_K = 14;
    private static final int[] PRIMES = findPrimes(MAX_N);
    private static final int[][] WAYS = findWays(MAX_N, MAX_K);
    private final int n;
    private final int k;

    public Solution(int n, int k) {
        this.n = n;
        this.k = k;
    }

    private static int[] findPrimes(int limit) {
        boolean[] isPrimes = new boolean[limit + 1];
        Arrays.fill(isPrimes, true);

        int total = 0;
        isPrimes[0] = isPrimes[1] = false;
        for (int i = 2; i <= limit; i++) {
            if (!isPrimes[i]) continue;
            total++;
            for (int j = i + i; j <= limit; j += i) isPrimes[j] = false;
        }

        int[] primes = new int[total];
        for (int i = 2, j = 0; i <= limit && j < total; i++) {
            if (!isPrimes[i]) continue;
            primes[j++] = i;
        }

        return primes;
    }

    private static int[][] findWays(int n, int k) {
        int[][] dp = new int[n + 1][k + 1];
        dp[0][0] = 1;

        for (int prime : PRIMES) {
            for (int sum = n - 1; sum >= 0; sum--) {
                int nextSum = sum + prime;
                if (nextSum > n) continue;

                for (int totalPrime = 0; totalPrime < k; totalPrime++) {
                    int totalWay = dp[sum][totalPrime];
                    int nextTotalPrime = totalPrime + 1;

                    dp[nextSum][nextTotalPrime] += totalWay;
                }
            }
        }

        return dp;
    }

    public int getTotalWayToGetNFromKPrimes() {
        return WAYS[n][k];
    }
}
