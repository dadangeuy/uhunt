package uhunt.c2.g0.p12720;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 12720 - Algorithm of Phil
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4572
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            String bits = in.next();

            Solution solution = new Solution(bits);
            int value = solution.getPhilValue();

            out.format("Case #%d: %d\n", i + 1, value);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final int MOD = 1_000_000_000 + 7;
    private final String bits;

    public Solution(String bits) {
        this.bits = bits;
    }

    public int getPhilValue() {
        String philBits = toPhilBits(bits);
        return toInteger(philBits, MOD);
    }

    private String toPhilBits(String bits) {
        StringBuilder pBits = new StringBuilder();
        int left = (bits.length() - 1) / 2, right = (bits.length()) / 2, total = bits.length();
        while (total > 0) {
            if (left == right) {
                // take left, skip right
                pBits.append(bits.charAt(left--));
                right++;
                total--;
            } else if (bits.charAt(left) == '1') {
                // take left & right
                pBits.append(bits.charAt(left--)).append(bits.charAt(right++));
                total -= 2;
            } else {
                // take right & left
                pBits.append(bits.charAt(right++)).append(bits.charAt(left--));
                total -= 2;
            }
        }
        return pBits.toString();
    }

    private int toInteger(String bits, int mod) {
        int value = 0;
        for (int i = 0; i < bits.length(); i++) {
            char bit = bits.charAt(i);
            value <<= 1;
            value |= bit == '1' ? 1 : 0;
            value %= mod;
        }
        return value;
    }
}
