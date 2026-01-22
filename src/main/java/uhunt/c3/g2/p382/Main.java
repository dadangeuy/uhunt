package uhunt.c3.g2.p382;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 382 - Perfection
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=318
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        out.println("PERFECTION OUTPUT");

        while (true) {
            final int number = in.nextInt();

            final boolean isEOF = number == 0;
            if (isEOF) break;

            final Input input = new Input(number);
            final Output output = process.process(input);

            out.format("%5d  %s\n", output.number, output.category);
        }

        out.println("END OF OUTPUT");

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public final int number;

    Input(final int number) {
        this.number = number;
    }
}

class Output {
    public final int number;
    public final String category;

    Output(final int number, final String category) {
        this.number = number;
        this.category = category;
    }
}

class Process {
    public Output process(final Input input) {
        final String category = getCategory(input.number);
        return new Output(input.number, category);
    }

    private String getCategory(final int number) {
        final int sum = getSumOfProperDivisors(number);
        if (sum < number) {
            return "DEFICIENT";
        } else if (sum > number) {
            return "ABUNDANT";
        } else {
            return "PERFECT";
        }
    }

    private int getSumOfProperDivisors(final int number) {
        final int[] properDivisors = getProperDivisors(number);
        return Arrays.stream(properDivisors).sum();
    }

    private int[] getProperDivisors(final int number) {
        final int[] factors = getFactors(number);
        return Arrays.copyOf(factors, factors.length - 1);
    }

    private int[] getFactors(final int number) {
        final LinkedList<Integer> factors = new LinkedList<>();

        for (int factor1 = 1; factor1 * factor1 <= number; factor1++) {
            final int factor2 = number / factor1;
            if (factor1 * factor2 != number) continue;

            factors.add(factor1);
            factors.add(factor2);
        }

        return factors.stream()
            .mapToInt(f -> f)
            .distinct()
            .sorted()
            .toArray();
    }
}
