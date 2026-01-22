package uhunt.c3.g1.p10821;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.stream.IntStream;

/**
 * 10821 - Constructing BST
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1762
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 8);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 8);
        final Process process = new Process();

        for (int caseId = 1; ; caseId++) {
            final String[] l1 = readSplitLine(in);
            final int totalNumbers = Integer.parseInt(l1[0]);
            final int height = Integer.parseInt(l1[1]);

            final boolean isEOF = totalNumbers == 0 && height == 0;
            if (isEOF) break;

            final Input input = new Input(caseId, totalNumbers, height);
            final Output output = process.process(input);

            if (output.isPossible) {
                out.write(String.format("Case %d:", output.caseId));
                for (int number : output.numbers) {
                    out.write(String.format(" %d", number));
                }
                out.write('\n');
            } else {
                out.write(String.format("Case %d: Impossible.\n", output.caseId));
            }
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
    public final int caseId;
    public final int totalNumbers;
    public final int height;

    public Input(final int caseId, final int totalNumbers, final int height) {
        this.caseId = caseId;
        this.totalNumbers = totalNumbers;
        this.height = height;
    }
}

class Output {
    public final int caseId;
    public final boolean isPossible;
    public final int[] numbers;

    public Output(final int caseId, final boolean isPossible, final int[] numbers) {
        this.caseId = caseId;
        this.isPossible = isPossible;
        this.numbers = numbers;
    }
}

class Process {
    private static final int[] MAX_TOTAL_LENGTHS_PER_HEIGHT = precalculateMaximumTotalLengthsPerHeight();

    private static int[] precalculateMaximumTotalLengthsPerHeight() {
        final int[] totalLengthsPerHeight = new int[31];
        for (
                int height = 0, powerOf2 = 1;
                height <= 30;
                height++, powerOf2 <<= 1
        ) {
            totalLengthsPerHeight[height] = powerOf2 - 1;
        }
        return totalLengthsPerHeight;
    }

    public Output process(final Input input) {
        final int maxTotalNumbers = MAX_TOTAL_LENGTHS_PER_HEIGHT[input.height];
        final boolean isImpossible = maxTotalNumbers < input.totalNumbers;
        if (isImpossible) return new Output(input.caseId, false, new int[0]);

        final int[] numbers = IntStream.rangeClosed(1, input.totalNumbers).toArray();
        final LinkedList<Integer> path = new LinkedList<>();
        preorderTraversal(numbers, 0, numbers.length - 1, input.height, path);

        final int[] orderedNumbers = path.stream()
                .mapToInt(i -> i)
                .toArray();

        return new Output(input.caseId, true, orderedNumbers);
    }

    private void preorderTraversal(
            final int[] numbers,
            final int from,
            final int until,
            final int height,
            final LinkedList<Integer> path
    ) {
        if (from > until) return;

        final int rightMaxTotalLengths = MAX_TOTAL_LENGTHS_PER_HEIGHT[height - 1];
        final int middle = Math.max(from, until - rightMaxTotalLengths);
        path.addLast(numbers[middle]);

        preorderTraversal(numbers, from, middle - 1, height - 1, path);
        preorderTraversal(numbers, middle + 1, until, height - 1, path);
    }
}
