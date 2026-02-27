package uva.uhunt.c3.g5.p10385;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 10385 - Duathlon
 * Time limit: 4.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1326
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        for (int testCase = 1; in.hasNextInt(); testCase++) {
            final Input input = new Input();
            input.testCase = testCase;
            input.totalDistances = in.nextLong();
            input.totalContestants = in.nextInt();
            input.contestants = new Contestant[input.totalContestants];
            for (int i = 0; i < input.totalContestants; i++) {
                final Contestant contestant = new Contestant();
                input.contestants[i] = contestant;

                contestant.runningSpeed = in.nextDouble();
                contestant.cyclingSpeed = in.nextDouble();
            }

            final Output output = process.process(input);
            if (output.canWin) {
                out.format(
                    "The cheater can win by %.0f seconds with r = %.2fkm and k = %.2fkm.\n",
                    output.winMargin,
                    output.runningDistance,
                    output.cyclingDistance
                );
            } else {
                out.println("The cheater cannot win.");
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int testCase;
    // kilometer
    public long totalDistances;
    public int totalContestants;
    public Contestant[] contestants;
}

class Output {
    public int testCase;
    public boolean canWin;
    // second
    public double winMargin;
    // kilometer
    public double runningDistance;
    // kilometer
    public double cyclingDistance;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.testCase = input.testCase;

        final Contestant cheaterContestant = input.contestants[input.totalContestants - 1];
        final Contestant[] normalContestants = Arrays.copyOf(input.contestants, input.totalContestants - 1);

        double minimumRunningDistance = 0;
        double maximumRunningDistance = input.totalDistances;

        final double precision = 0.0001;

        while (maximumRunningDistance - minimumRunningDistance >= precision) {
            final double difference = maximumRunningDistance - minimumRunningDistance;
            final double aThirdOfTheDifference = difference / 3;

            final double nextMinimumRunningDistance = minimumRunningDistance + aThirdOfTheDifference;
            final double nextMinimumCyclingDistance = input.totalDistances - nextMinimumRunningDistance;
            final double winMarginForNextMinimum = getWinMargin(cheaterContestant, normalContestants, nextMinimumRunningDistance, nextMinimumCyclingDistance);

            final double nextMaximumRunningDistance = maximumRunningDistance - aThirdOfTheDifference;
            final double nextMaximumCyclingDistance = input.totalDistances - nextMaximumRunningDistance;
            final double winMarginForNextMaximum = getWinMargin(cheaterContestant, normalContestants, nextMaximumRunningDistance, nextMaximumCyclingDistance);

            if (winMarginForNextMinimum <= winMarginForNextMaximum) {
                minimumRunningDistance = nextMinimumRunningDistance;
            } else {
                maximumRunningDistance = nextMaximumRunningDistance;
            }
        }

        final double runningDistance1 = minimumRunningDistance;
        final double cyclingDistance1 = input.totalDistances - runningDistance1;
        final double winMargin1 = getWinMargin(cheaterContestant, normalContestants, runningDistance1, cyclingDistance1);

        final double runningDistance2 = maximumRunningDistance;
        final double cyclingDistance2 = input.totalDistances - runningDistance2;
        final double winMargin2 = getWinMargin(cheaterContestant, normalContestants, runningDistance2, cyclingDistance2);

        if (winMargin1 >= winMargin2) {
            output.canWin = winMargin1 >= 0;
            output.winMargin = winMargin1;
            output.runningDistance = runningDistance1;
            output.cyclingDistance = cyclingDistance1;
        } else {
            output.canWin = winMargin2 >= 0;
            output.winMargin = winMargin2;
            output.runningDistance = runningDistance2;
            output.cyclingDistance = cyclingDistance2;
        }

        return output;
    }

    // second
    private double getWinMargin(final Contestant cheaterContestant, final Contestant[] normalContestants, final double runningDistance, final double cyclingDistance) {
        final double cheaterDuration = getDuration(cheaterContestant, runningDistance, cyclingDistance);
        final double normalDuration = getMinimumDuration(normalContestants, runningDistance, cyclingDistance);
        return normalDuration - cheaterDuration;
    }

    // second
    private double getMinimumDuration(final Contestant[] contestants, final double runningDistance, final double cyclingDistance) {
        return Arrays.stream(contestants)
            .mapToDouble(c -> getDuration(c, runningDistance, cyclingDistance))
            .min()
            .orElseThrow(NullPointerException::new);
    }

    // second
    private double getDuration(final Contestant contestant, final double runningDistance, final double cyclingDistance) {
        final double runningDuration = getDuration(contestant.runningSpeed, runningDistance);
        final double cyclingDuration = getDuration(contestant.cyclingSpeed, cyclingDistance);
        return runningDuration + cyclingDuration;
    }

    // second
    private double getDuration(final double speed, final double distance) {
        return 3600d * distance / speed;
    }
}

class Contestant {
    // kilometer per hour
    public double runningSpeed;
    // kilometer per hour
    public double cyclingSpeed;
}
