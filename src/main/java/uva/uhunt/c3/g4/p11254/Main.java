package uva.uhunt.c3.g4.p11254;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 11254 - Consecutive Integers
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=2221
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        while (in.hasNextInt()) {
            final int n = in.nextInt();
            final boolean isEOF = n == -1;
            if (isEOF) break;

            final Input input = new Input(n);
            final Output output = process.process(input);
            out.format("%d = %d + ... + %d\n", output.n, output.a, output.b);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public final int n;

    public Input(final int n) {
        this.n = n;
    }
}

class Output {
    public final int n;
    public final int a;
    public final int b;

    public Output(final int n, final int a, final int b) {
        this.n = n;
        this.a = a;
        this.b = b;
    }
}

class Process {
    public Output process(final Input input) {
        int maxL = -1;
        int maxA = -1;
        int maxB = -1;

        final int _2n = 2 * input.n;
        for (int factor1 = 1; factor1 * factor1 <= _2n; factor1++) {
            final int factor2 = _2n / factor1;
            if (factor1 * factor2 != _2n) continue;

            final int l = factor1 - 1;
            final int a = (factor2 - l) / 2;
            final int b = a + l;

            final boolean isDivisible = (factor2 - l) % 2 == 0;
            if (!isDivisible) continue;

            final boolean isPositive = a > 0;
            if (!isPositive) continue;

            final boolean isLonger = l > maxL;
            if (!isLonger) continue;

            maxL = l;
            maxA = a;
            maxB = b;
        }

        return new Output(input.n, maxA, maxB);
    }
}
