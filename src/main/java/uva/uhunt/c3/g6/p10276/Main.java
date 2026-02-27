package uva.uhunt.c3.g6.p10276;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 10276 - Hanoi Tower Troubles Again!
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1217
 */
public class Main {
    private static final int MAX_TOTAL_PEGS = 50;
    private static boolean[] IS_SQUARE_NUMBERS;
    private static int[] TOTAL_BALLS_PER_TOTAL_PEGS;
    private static Scanner in;
    private static PrintWriter out;

    public static void main(final String... args) {
        in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));
        IS_SQUARE_NUMBERS = buildIsSquareNumbers();
        TOTAL_BALLS_PER_TOTAL_PEGS = buildTotalBallsPerTotalPegs();

        final int totalCases = in.nextInt();
        for (int i = 0; i < totalCases; i++) {
            final int totalPegs = in.nextInt();
            out.println(TOTAL_BALLS_PER_TOTAL_PEGS[totalPegs]);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static int[] buildTotalBallsPerTotalPegs() {
        final int[] totalBallsPerTotalPegs = new int[MAX_TOTAL_PEGS + 1];
        for (int totalPegs = 1; totalPegs <= MAX_TOTAL_PEGS; totalPegs++) {
            final int[] tower = new int[totalPegs];
            for (int ball = 1; ; ball++) {
                boolean isPlaced = false;
                for (int i = 0; i < totalPegs; i++) {
                    int prevBall = tower[i];
                    if (prevBall == 0) {
                        tower[i] = ball;
                        isPlaced = true;
                        break;
                    } else if (IS_SQUARE_NUMBERS[prevBall + ball]) {
                        tower[i] = ball;
                        isPlaced = true;
                        break;
                    }
                }
                if (!isPlaced) {
                    totalBallsPerTotalPegs[totalPegs] = ball - 1;
                    break;
                }
            }
        }
        return totalBallsPerTotalPegs;
    }

    private static boolean[] buildIsSquareNumbers() {
        final boolean[] isSquareNumbers = new boolean[10000];
        for (int i = 0; i * i < isSquareNumbers.length; i++) {
            isSquareNumbers[i * i] = true;
        }
        return isSquareNumbers;
    }
}
