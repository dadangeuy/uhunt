package uhunt.c3.p12673;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 12673 - Football
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4411
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 8);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 8);
        final Process process = new Process();

        while (true) {
            final String[] l1 = readSplitLine(in);

            final boolean isEOF = l1 == null;
            if (isEOF) break;

            final int totalMatches = Integer.parseInt(l1[0]);
            final int totalAvailableGoals = Integer.parseInt(l1[1]);

            final int[][] goalsPerMatches = new int[totalMatches][];
            for (int i = 0; i < totalMatches; i++) {
                final String[] l2 = readSplitLine(in);
                final int scored = Integer.parseInt(l2[0]);
                final int received = Integer.parseInt(l2[1]);

                final int[] goals = new int[]{scored, received};
                goalsPerMatches[i] = goals;
            }

            final Input input = new Input(totalMatches, totalAvailableGoals, goalsPerMatches);
            final Output output = process.process(input);

            out.write(Integer.toString(output.maximumTotalPoints));
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String[] readSplitLine(final BufferedReader in) throws IOException {
        final String line = readLine(in);
        return line == null ? null : line.split(" ");
    }

    private static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Input {
    public final int totalMatches;
    public final int totalAvailableGoals;
    public final int[][] goalsPerMatch;

    public Input(final int totalMatches, final int totalAvailableGoals, final int[][] goalsPerMatch) {
        this.totalMatches = totalMatches;
        this.totalAvailableGoals = totalAvailableGoals;
        this.goalsPerMatch = goalsPerMatch;
    }
}

class Output {
    public final int maximumTotalPoints;

    public Output(final int maximumTotalPoints) {
        this.maximumTotalPoints = maximumTotalPoints;
    }
}

class Process {
    private static final Comparator<int[]> ORDER_GOALS_BY_DEFICIT = Comparator
        .comparingInt(goals -> {
            final int scored = goals[0];
            final int received = goals[1];
            return received - scored;
        });

    public Output process(final Input input) {
        final int[][] goalsPerMatch = input.goalsPerMatch;
        Arrays.sort(goalsPerMatch, ORDER_GOALS_BY_DEFICIT);

        int totalAvailableGoals = input.totalAvailableGoals;
        int totalPoints = 0;

        for (int[] goals : goalsPerMatch) {
            final int scored = goals[0];
            final int received = goals[1];

            final boolean isWin = scored > received;
            if (isWin) {
                totalPoints += 3;
            } else {
                final int deficitForWin = received - scored + 1;
                final int deficitForDraw = received - scored;

                if (totalAvailableGoals >= deficitForWin) {
                    totalAvailableGoals -= deficitForWin;
                    totalPoints += 3;
                } else if (totalAvailableGoals >= deficitForDraw) {
                    totalAvailableGoals -= deficitForDraw;
                    totalPoints += 1;
                }
            }
        }

        return new Output(totalPoints);
    }
}
