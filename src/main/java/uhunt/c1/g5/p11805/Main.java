package uhunt.c1.g5.p11805;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 11805 - Bafana Bafana
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2905
 */
public class Main {

    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalTestCases = in.nextInt();
        for (int testCase = 1; testCase <= totalTestCases; testCase++) {
            int totalPlayers = in.nextInt();
            int firstPlayer = in.nextInt();
            int totalPasses = in.nextInt();

            Solution solution = new Solution(totalPlayers, firstPlayer, totalPasses);
            int lastPlayer = solution.getLastPlayer();

            out.format("Case %d: %d\n", testCase, lastPlayer);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    int totalPlayers;
    int firstPlayer;
    int totalPasses;

    public Solution(int totalPlayers, int firstPlayer, int totalPasses) {
        this.totalPlayers = totalPlayers;
        this.firstPlayer = firstPlayer;
        this.totalPasses = totalPasses;
    }

    public int getLastPlayer() {
        return (((firstPlayer - 1) + totalPasses) % totalPlayers) + 1;
    }
}
