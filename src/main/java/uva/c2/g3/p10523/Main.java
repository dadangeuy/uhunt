package uva.c2.g3.p10523;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    private static Scanner in;
    private static StringBuilder res;

    public static void main(String... args) throws IOException {
        in = new Scanner(System.in);
        res = new StringBuilder();

        while (in.hasNextInt()) {
            int n = in.nextInt();
            int a = in.nextInt();

            Solution solution = new Solution(n, a);
            BigInteger sum = solution.getSum();

            res.append(sum.toString()).append('\n');
        }

        System.out.print(res);
    }
}

// calculate a^i while iterate
// store the total on a biginteger
class Solution {
    private final int n;
    private final int a;

    public Solution(int n, int a) {
        this.n = n;
        this.a = a;
    }

    // 1 * (a ^ 1) + ... + n * (a ^ n)
    public BigInteger getSum() {
        BigInteger bigA = BigInteger.valueOf(a);
        BigInteger bigAi = BigInteger.ONE;
        BigInteger bigSum = BigInteger.ZERO;

        for (int i = 1; i <= n; i++) {
            bigAi = bigAi.multiply(bigA);
            bigSum = bigSum.add(bigAi.multiply(BigInteger.valueOf(i)));
        }

        return bigSum;
    }
}
