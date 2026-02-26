package uva.c3.g3.p10003;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 10003 - Cutting Sticks
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=944
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        for (int caseId = 1; ; caseId++) {
            final int length = in.nextInt();
            final boolean isEOF = length == 0;
            if (isEOF) break;

            final int totalCuts = in.nextInt();
            final int[] positions = new int[totalCuts];
            for (int i = 0; i < totalCuts; i++) positions[i] = in.nextInt();

            final Input input = new Input(caseId, length, totalCuts, positions);
            final Output output = process.process(input);

            out.format("The minimum cutting is %d.\n", output.minimumCost);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public final int caseId;
    public final int length;
    public final int totalCuts;
    public final int[] positions;

    public Input(final int caseId, final int length, final int totalCuts, final int[] positions) {
        this.caseId = caseId;
        this.length = length;
        this.totalCuts = totalCuts;
        this.positions = positions;
    }
}

class Output {
    public final int caseId;
    public final int minimumCost;

    public Output(final int caseId, final int minimumCost) {
        this.caseId = caseId;
        this.minimumCost = minimumCost;
    }
}

class Process {
    public Output process(final Input input) {
        final int cost = backtracking(
            0, input.length,
            input.positions,
            new int[input.length + 1][input.length + 1]
        );
        return new Output(input.caseId, cost);
    }

    private int backtracking(
        final int fromStick,
        final int untilStick,
        final int[] positions,
        final int[][] dp
    ) {
        if (dp[fromStick][untilStick] == 0) {
            int minCost = Integer.MAX_VALUE;

            for (int i = 0; i < positions.length; i++) {
                final int position = positions[i];

                final boolean isValid = fromStick < position && position < untilStick;
                if (!isValid) continue;

                final int leftCost = backtracking(fromStick, position, positions, dp);
                final int rightCost = backtracking(position, untilStick, positions, dp);
                final int cost = (untilStick - fromStick) + leftCost + rightCost;

                minCost = Math.min(minCost, cost);
            }

            if (minCost == Integer.MAX_VALUE) {
                return 0;
            }

            dp[fromStick][untilStick] = minCost;
        }

        return dp[fromStick][untilStick];
    }
}
