package uhunt.c2.p13148;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * 13148 - A Giveaway
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=5070
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int number = in.nextInt();
            if (number == 0) break;

            Solution solution = new Solution(number);
            boolean isSpecial = solution.isSpecial();

            out.println(isSpecial ? "Special" : "Ordinary");
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final HashSet<Integer> SPECIAL_NUMBERS = new HashSet<>(Arrays.asList(
            1, 64, 729, 4096, 15625, 46656, 117649, 262144, 531441, 1000000, 1771561, 2985984, 4826809, 7529536,
            11390625, 16777216, 24137569, 34012224, 47045881, 64000000, 85766121
    ));
    private final int number;

    public Solution(int number) {
        this.number = number;
    }

    public boolean isSpecial() {
        return SPECIAL_NUMBERS.contains(number);
    }
}
