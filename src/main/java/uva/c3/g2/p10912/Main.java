package uva.c3.g2.p10912;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 10912 - Simple Minded Hashing
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1853
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        for (int caseId = 1; in.hasNextInt(); caseId++) {
            final int length = in.nextInt();
            final int hash = in.nextInt();

            final boolean isEOF = length == 0 && hash == 0;
            if (isEOF) break;

            final Input input = new Input(caseId, length, hash);
            final Output output = process.process(input);

            out.format("Case %d: %d\n", output.caseId, output.totalMatches);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public final int caseId;
    public final int length;
    public final int hash;

    public Input(final int caseId, final int length, final int hash) {
        this.caseId = caseId;
        this.length = length;
        this.hash = hash;
    }
}

class Output {
    public final int caseId;
    public final int totalMatches;

    public Output(final int caseId, final int totalMatches) {
        this.caseId = caseId;
        this.totalMatches = totalMatches;
    }
}

class Process {
    public Output process(final Input input) {
        final int count = backtracking(input.length, input.hash, 1);
        return new Output(input.caseId, count);
    }

    private int backtracking(final int remainingLength, final int remainingHash, final int fromValue) {
        if (remainingLength == 0 && remainingHash == 0) {
            return 1;
        }

        final int minHash = getMinHash(remainingLength, fromValue);
        final int maxHash = getMaxHash(remainingLength, 26);
        final boolean isPossible = minHash <= remainingHash && remainingHash <= maxHash;
        if (!isPossible) {
            return 0;
        }

        int count = 0;
        for (int value = fromValue; value <= 26; value++) {
            count += backtracking(
                remainingLength - 1,
                remainingHash - value,
                value + 1
            );
        }
        return count;
    }

    private int getMinHash(final int length, final int firstValue) {
        final int lastValue = firstValue + length - 1;
        final int minHash = sumOfArithmeticSeries(length, firstValue, lastValue);
        return minHash;
    }

    private int getMaxHash(final int length, final int lastValue) {
        final int firstValue = lastValue - length + 1;
        final int maxHash = sumOfArithmeticSeries(length, firstValue, lastValue);
        return maxHash;
    }

    private int sumOfArithmeticSeries(final int length, final int firstValue, final int lastValue) {
        return length * (firstValue + lastValue) / 2;
    }
}
