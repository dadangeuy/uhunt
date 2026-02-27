package uva.uhunt.c3.g2.p242;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * 242 - Stamps and Envelope Size
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=178
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        while (true) {
            final String[] l1 = Util.readSplitLine(in);
            final int maxStamps = Integer.parseInt(l1[0]);
            if (maxStamps == 0) break;

            final String[] l2 = Util.readSplitLine(in);
            final int totalStampSet = Integer.parseInt(l2[0]);

            final int[] totalDenominationsPerStampSet = new int[totalStampSet];
            final int[][] denominationsPerStampSet = new int[totalStampSet][];
            for (int i = 0; i < totalStampSet; i++) {
                final String[] l3 = Util.readSplitLine(in);
                final int[] totalAndDenominations = Arrays.stream(l3).mapToInt(Integer::parseInt).toArray();
                final int totalDenominations = totalAndDenominations[0];
                final int[] denominations = Arrays.copyOfRange(totalAndDenominations, 1, totalAndDenominations.length);

                totalDenominationsPerStampSet[i] = totalDenominations;
                denominationsPerStampSet[i] = denominations;
            }

            final Input input = new Input(
                    maxStamps,
                    totalStampSet,
                    totalDenominationsPerStampSet,
                    denominationsPerStampSet
            );
            final Output output = process.process(input);

            out.write(String.format("max coverage =%4d :", output.maxCoverage));
            for (final int denomination : output.minDenominations) {
                out.write(String.format("%3d", denomination));
            }
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public final int maxStamps;
    public final int totalStampSet;
    public final int[] totalDenominationsPerStampSet;
    public final int[][] denominationsPerStampSet;

    public Input(
            final int maxStamps,
            final int totalStampSet,
            final int[] totalDenominationsPerStampSet,
            final int[][] denominationsPerStampSet
    ) {
        this.maxStamps = maxStamps;
        this.totalStampSet = totalStampSet;
        this.totalDenominationsPerStampSet = totalDenominationsPerStampSet;
        this.denominationsPerStampSet = denominationsPerStampSet;
    }
}

class Output {
    public final int maxCoverage;
    public final int[] minDenominations;

    public Output(final int maxCoverage, final int[] minDenominations) {
        this.maxCoverage = maxCoverage;
        this.minDenominations = minDenominations;
    }
}

class Process {
    public static final Comparator<int[]> ORDER_BY_LENGTH_AND_LAST = buildOrderByLengthAndLast(10);
    private static final int EMPTY = -1;

    public Output process(final Input input) {
        final int[] coveragePerStampSet = Arrays
                .stream(input.denominationsPerStampSet)
                .mapToInt(d -> getCoverage(input.maxStamps, d))
                .toArray();
        final int maxCoverage = Arrays.stream(coveragePerStampSet)
                .max()
                .orElseThrow(NullPointerException::new);
        final int[][] maxCoverageDenominations = IntStream
                .range(0, input.totalStampSet)
                .filter(i -> coveragePerStampSet[i] == maxCoverage)
                .mapToObj(i -> input.denominationsPerStampSet[i])
                .toArray(int[][]::new);
        final int[] minDenomination = Arrays.stream(maxCoverageDenominations)
                .min(ORDER_BY_LENGTH_AND_LAST)
                .orElseThrow(NullPointerException::new);

        return new Output(maxCoverage, minDenomination);
    }

    private int getCoverage(final int maxStamps, final int[] denominations) {
        final int maxValue = denominations[denominations.length - 1] * maxStamps;
        final int[] totalStampsPerValue = new int[maxValue + 1];

        Arrays.fill(totalStampsPerValue, EMPTY);
        totalStampsPerValue[0] = 0;

        for (int i = 0; i < maxStamps; i++) {
            for (final int denomination : denominations) {
                for (int value = 0; value <= maxValue; value++) {
                    final int totalStamps = totalStampsPerValue[value];
                    if (0 <= totalStamps && totalStamps < maxStamps) {
                        final int combinedValue = value + denomination;
                        final int combinedTotalStamps = totalStamps + 1;

                        if (totalStampsPerValue[combinedValue] == EMPTY) {
                            totalStampsPerValue[combinedValue] = combinedTotalStamps;
                        } else {
                            totalStampsPerValue[combinedValue] = Math.min(
                                    totalStampsPerValue[combinedValue],
                                    combinedTotalStamps
                            );
                        }
                    }
                }
            }
        }

        int coverage = 0;
        for (int value = 1; value <= maxValue; value++) {
            if (totalStampsPerValue[value] <= 0) break;
            coverage++;
        }

        return coverage;
    }

    private static Comparator<int[]> buildOrderByLengthAndLast(final int length) {
        Comparator<int[]> comparator = Comparator.comparingInt(d -> d.length);
        for (int i = 1; i <= length; i++) {
            final int j = i;
            comparator = comparator.thenComparingInt(d -> (d.length - j) < 0 ? 0 : d[d.length - j]);
        }
        return comparator;
    }
}

class Util {
    private static final String SEPARATOR = " ";

    public static String[] readSplitLine(final BufferedReader in) throws IOException {
        final String line = readLine(in);
        return line.split(SEPARATOR);
    }

    public static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}
