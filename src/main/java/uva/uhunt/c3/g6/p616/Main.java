package uva.uhunt.c3.g6.p616;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 616 - Coconuts, Revisited
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=557
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        final Process process = new Process();

        while (in.hasNextInt()) {
            final int totalCoconuts = in.nextInt();
            if (totalCoconuts < 0) break;

            final Input input = new Input(totalCoconuts);
            final Output output = process.process(input);
            if (output.hasSolution) {
                out.format(
                        "%d coconuts, %d people and %d monkey\n",
                        output.totalCoconuts,
                        output.totalPeoples,
                        output.totalMonkeys
                );
            } else {
                out.format(
                        "%d coconuts, no solution\n",
                        output.totalCoconuts
                );
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Process {
    public Output process(final Input input) {
        for (int totalPeoples = 10; totalPeoples >= 2; totalPeoples--) {
            boolean valid = true;
            int totalCoconuts = input.totalCoconuts;
            for (int round = 0; round < totalPeoples && valid; round++) {
                valid = totalCoconuts % totalPeoples == 1;
                totalCoconuts = totalCoconuts - 1 - (totalCoconuts / totalPeoples);
            }

            valid = valid && totalCoconuts % totalPeoples == 0;
            if (!valid) continue;

            return new Output(
                    input.totalCoconuts,
                    true,
                    totalPeoples,
                    1
            );
        }

        return new Output(
                input.totalCoconuts,
                false,
                0,
                0
        );
    }
}

class Input {
    public final int totalCoconuts;

    public Input(final int totalCoconuts) {
        this.totalCoconuts = totalCoconuts;
    }
}

class Output {
    public final int totalCoconuts;
    public final boolean hasSolution;
    public final int totalPeoples;
    public final int totalMonkeys;

    public Output(
            final int totalCoconuts,
            final boolean hasSolution,
            final int totalPeoples,
            final int totalMonkeys
    ) {
        this.totalCoconuts = totalCoconuts;
        this.hasSolution = hasSolution;
        this.totalPeoples = totalPeoples;
        this.totalMonkeys = totalMonkeys;
    }
}
