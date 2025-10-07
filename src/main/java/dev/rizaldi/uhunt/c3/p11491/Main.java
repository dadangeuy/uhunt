package dev.rizaldi.uhunt.c3.p11491;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 11491 - Erasing and Winning
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2486
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        while (true) {
            final String[] l1 = readSplitLine(in);
            final int totalDigits = Integer.parseInt(l1[0]);
            final int totalErases = Integer.parseInt(l1[1]);
            if (totalDigits == 0 && totalErases == 0) break;

            final String numberText = readLine(in);

            final Input input = new Input(totalDigits, totalErases, numberText);
            final Output output = process.process(input);

            out.write(output.maxNumberText);
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
    public final int totalDigits;
    public final int totalErases;
    public final String numberText;

    public Input(final int totalDigits, final int totalErases, final String numberText) {
        this.totalDigits = totalDigits;
        this.totalErases = totalErases;
        this.numberText = numberText;
    }
}

class Output {
    public final String maxNumberText;

    public Output(final String maxNumberText) {
        this.maxNumberText = maxNumberText;
    }
}

class Process {
    public Output process(final Input input) {
        final List<Integer> digits = toDigits(input.numberText);

        // list every digit & indexes
        final List<DigitIndex> all = IntStream.range(0, digits.size())
                .mapToObj(index -> new DigitIndex(digits.get(index), index))
                .collect(Collectors.toList());

        // take first D elements as pending (to-be erased) digits
        final PriorityQueue<DigitIndex> pendingQueue = new PriorityQueue<>(DigitIndex.PRIORITIZE_MAX_AND_LEFT);
        for (int i = 0; i < input.totalErases; i++) {
            pendingQueue.add(all.get(i));
        }

        // take the remaining elements as taken digits
        final List<DigitIndex> takenList = new ArrayList<>();
        for (int i = input.totalErases; i < input.totalDigits; i++) {
            takenList.add(all.get(i));
        }

        // swap taken digits with the best pending digits
        // the best pending digit is the digit with the greatest value and having leftmost position
        for (int i = 0; i < takenList.size(); i++) {
            final DigitIndex previous = i > 0? takenList.get(i - 1) : null;
            final DigitIndex current = takenList.get(i);

            while (!pendingQueue.isEmpty()) {
                final DigitIndex candidate = pendingQueue.remove();

                final boolean isCandidateBeforePrevious = previous != null && candidate.index <= previous.index;
                if (isCandidateBeforePrevious) continue;

                final boolean isCandidateBetterThanCurrent = candidate.digit >= current.digit && candidate.index <= current.index;
                if (isCandidateBetterThanCurrent) {
                    takenList.set(i, candidate);
                    pendingQueue.add(current);
                    break;
                }
            }
        }

        final List<Integer> takenDigits = takenList.stream()
                .map(d -> d.digit)
                .collect(Collectors.toList());

        return new Output(toNumberText(takenDigits));
    }

    // convert "123" into [1, 2, 3]
    private List<Integer> toDigits(final String numberText) {
        final LinkedList<Integer> digits = new LinkedList<>();
        for (int i = 0; i < numberText.length(); i++) {
            final int digit = numberText.charAt(i) - '0';
            digits.addLast(digit);
        }
        return new ArrayList<>(digits);
    }

    // convert [1, 2, 3] into "123"
    private String toNumberText(final List<Integer> digits) {
        StringBuilder numberText = new StringBuilder();
        for (final int digit : digits) {
            numberText.append(digit);
        }
        return numberText.toString();
    }
}

class DigitIndex {
    public static final Comparator<DigitIndex> PRIORITIZE_MAX_AND_LEFT = Comparator
            .comparingInt((DigitIndex d) -> -d.digit)
            .thenComparingInt((DigitIndex d) -> d.index);

    public final int digit;
    public final int index;

    public DigitIndex(final int digit, final int index) {
        this.digit = digit;
        this.index = index;
    }
}
