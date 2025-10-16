package uhunt.c1.p12643;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextInt()) {
            int seed = in.nextInt();
            int player1 = in.nextInt();
            int player2 = in.nextInt();

            Solution solution = new Solution(seed, player1, player2);
            int totalRound = solution.getTotalRoundUntilMet();

            out.println(totalRound);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int seed;
    private final int player1;
    private final int player2;

    public Solution(int seed, int player1, int player2) {
        this.seed = seed;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int getTotalRoundUntilMet() {
        int totalRound = 1;

        int round1 = nextRound(player1);
        int round2 = nextRound(player2);
        while (round1 != round2) {
            round1 = nextRound(round1);
            round2 = nextRound(round2);
            totalRound++;
        }

        return totalRound;
    }

    private int nextRound(int player) {
        return ceilEven(player) >> 1;
    }

    private int ceilEven(int value) {
        return even(value) ? value : value + 1;
    }

    private boolean even(int value) {
        return (value & 1) == 0;
    }
}
