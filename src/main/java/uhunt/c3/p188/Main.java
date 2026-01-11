package uhunt.c3.p188;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 188 - Perfect Hash
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=124
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        final Process process = new Process();

        while (true) {
            final String text = readLine(in);
            final boolean isEOF = text == null;
            if (isEOF) break;

            final Input input = new Input();
            input.text = text;

            final Output output = process.process(input);
            out.write(output.text);
            out.write('\n');
            out.write(Integer.toString(output.constant));
            out.write('\n');
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Input {
    public String text;
}

class Output {
    public String text;
    public int constant;
}

class Process {
    private static final Comparator<Integer> ORDER_BY_VALUE = Comparator.comparingInt(i -> i);

    public Output process(final Input input) {
        final Output output = new Output();
        output.text = input.text;

        final String[] words = input.text.split("\\s+");
        final int[] binaries = Arrays.stream(words).mapToInt(this::convertToBinary).toArray();

        final Set<Integer> constants = new HashSet<>();
        final PriorityQueue<Integer> constantq = new PriorityQueue<>(ORDER_BY_VALUE);

        for (int constant : binaries) {
            if (!constants.contains(constant)) {
                constants.add(constant);
                constantq.add(constant);
            }
        }

        while (!constantq.isEmpty()) {
            final int constant = constantq.remove();
            final int length = binaries.length;
            final Map<Integer, List<Integer>> binariesPerHash = Arrays.stream(binaries)
                .boxed()
                .collect(Collectors.groupingBy(binary -> hash(constant, binary, length)));

            final boolean isUnique = binariesPerHash.values()
                .stream()
                .allMatch(b -> b.size() == 1);
            if (isUnique) {
                output.constant = constant;
                break;
            }

            for (final List<Integer> hashBinaries : binariesPerHash.values()) {
                if (hashBinaries.size() == 1) continue;

                final int nextConstant = hashBinaries.stream()
                    .mapToInt(binary -> nextConstant(constant, binary))
                    .min()
                    .orElseThrow(NullPointerException::new);

                if (!constants.contains(nextConstant)) {
                    constantq.add(nextConstant);
                    constants.add(nextConstant);
                }
            }
        }

        return output;
    }

    private int convertToBinary(final String word) {
        int number = 0;
        for (char letter : word.toCharArray()) {
            number <<= 5;
            number += letter - 'a' + 1;
        }
        return number;
    }

    private int hash(final int constant, final int binary, final int length) {
        return (constant / binary) % length;
    }

    private int nextConstant(final int constant, final int binary) {
        return ((constant / binary) + 1) * binary;
    }
}
