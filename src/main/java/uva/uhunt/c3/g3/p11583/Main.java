package uva.uhunt.c3.g3.p11583;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 11583 - Alien DNA
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2630
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        final int totalTestCases = in.nextInt();
        for (int i = 0; i < totalTestCases; i++) {
            final Input input = new Input();
            input.strandLength = in.nextInt();
            input.strand = new String[input.strandLength];
            for (int j = 0; j < input.strandLength; j++) {
                final String baseSet = in.next();
                input.strand[j] = baseSet;
            }

            final Output output = process.process(input);
            out.println(output.totalCuts);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int strandLength;
    public String[] strand;
}

class Output {
    public int totalCuts;
}

class Process {
    private static final int ONE_BITMASK = -1;

    public Output process(final Input input) {
        final Output output = new Output();
        output.totalCuts = 0;

        final int[] strand = new int[input.strandLength];
        for (int i = 0; i < input.strandLength; i++) {
            strand[i] = toBitmask(input.strand[i]);
        }

        for (int i = 0; i < strand.length; ) {
            int segment = ONE_BITMASK;

            for (; i < strand.length; i++) {
                final int baseSet = strand[i];
                final boolean hasCommonBase = (segment & baseSet) > 0;

                if (hasCommonBase) {
                    segment &= baseSet;
                } else {
                    output.totalCuts++;
                    break;
                }
            }
        }

        return output;
    }

    private int toBitmask(final String text) {
        int bitmask = 0;
        for (char letter : text.toCharArray()) {
            final int bit = letter - 'a';
            bitmask |= (1 << bit);
        }
        return bitmask;
    }
}
