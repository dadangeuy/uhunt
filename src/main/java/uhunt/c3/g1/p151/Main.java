package uhunt.c3.g1.p151;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 151 - Power Crisis
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=87
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int totalRegions = in.nextInt();
            if (totalRegions == 0) break;

            Solution solution = new Solution(totalRegions);
            int skip = solution.getSkip();
            out.println(skip);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int totalRegions;

    public Solution(int totalRegions) {
        this.totalRegions = totalRegions;
    }

    // 1st region will be killed first
    // 13th region will be killed last
    public int getSkip() {
        for (int skip = 1; ; skip++) {
            int lastRegion = josephus(totalRegions - 1, skip) + 1;
            if (lastRegion == 13) return skip;
        }
    }

    // based on O(n) recursive josephus
    // https://www.geeksforgeeks.org/josephus-problem-set-1-a-on-solution/
    private int josephus(int total, int skip) {
        int position = 1;
        for (int current = 2; current <= total; current++) position = (position + skip - 1) % current + 1;
        return position;
    }
}
