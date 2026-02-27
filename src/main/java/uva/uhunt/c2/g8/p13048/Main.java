package uva.uhunt.c2.g8.p13048;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 13048 - Burger Stand
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4946
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            char[] roads = in.next().toCharArray();
            Solution solution = new Solution(roads);
            int total = solution.getTotalPosition();
            out.format("Case %d: %d\n", i + 1, total);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final char DRIVEWAY = 'D';
    private static final char BUS_STOP = 'B';
    private static final char SIDE_STREET = 'S';
    private static final char OTHER = '-';
    private final char[] roads;

    public Solution(char[] roads) {
        this.roads = roads;
    }

    public int getTotalPosition() {
        boolean[] flags = new boolean[roads.length];
        for (int i = 0; i < roads.length; i++) {
            char road = roads[i];
            switch (road) {
                case DRIVEWAY:
                    rangeFlag(flags, i, i, true);
                    break;
                case BUS_STOP:
                    rangeFlag(flags, i - 2, i, true);
                    break;
                case SIDE_STREET:
                    rangeFlag(flags, i - 1, i + 1, true);
                    break;
            }
        }
        return countMatch(flags, false);
    }

    private void rangeFlag(boolean[] flags, int from, int to, boolean flag) {
        for (int i = Math.max(0, from); i <= Math.min(to, flags.length - 1); i++) {
            flags[i] = flag;
        }
    }

    private int countMatch(boolean[] flags, boolean flag) {
        int count = 0;
        for (boolean v : flags) {
            count += v == flag ? 1 : 0;
        }
        return count;
    }
}
