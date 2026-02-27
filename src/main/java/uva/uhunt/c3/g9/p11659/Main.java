package uva.uhunt.c3.g9.p11659;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 11659 - Informants
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2706
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        while (true) {
            final Input input = new Input();
            input.totalInformants = in.nextInt();
            input.totalAnswers = in.nextInt();
            if (input.isEOF()) break;
            input.answers = new int[input.totalAnswers][];
            for (int i = 0; i < input.totalAnswers; i++) {
                final int[] answer = new int[]{in.nextInt(), in.nextInt()};
                input.answers[i] = answer;
            }

            final Output output = process.process(input);
            out.write(String.format("%d\n", output.maxTotalReliableInformants));
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalInformants;
    public int totalAnswers;
    public int[][] answers;

    public boolean isEOF() {
        return totalInformants == 0 && totalAnswers == 0;
    }
}

class Output {
    public int maxTotalReliableInformants;
}

class Process {
    public Output process(final Input input) {
        final Bitmask[] bitmasks = new Bitmask[input.totalInformants + 1];

        // trust yourself
        for (int i = 1; i <= input.totalInformants; i++) {
            bitmasks[i] = new Bitmask().trust(i);
        }

        // trust direct trusted informant
        for (final int[] answer : input.answers) {
            final int informant = answer[0];
            final int suspect = Math.abs(answer[1]);
            final boolean reliable = answer[1] >= 0;

            bitmasks[informant] = reliable ? bitmasks[informant].trust(suspect) : bitmasks[informant].untrust(suspect);
        }

        // trust indirect trusted informant (inspired by floyd-warshall algorithm)
        // O(totalInformants ^ 3) = O(20^3) = O(8000) = O(10^3)
        for (int alt = 1; alt <= input.totalInformants; alt++) {
            for (int informant = 1; informant <= input.totalInformants; informant++) {
                for (int suspect = 1; suspect <= input.totalInformants; suspect++) {
                    if (!bitmasks[informant].hasTrust(suspect)) continue;

                    bitmasks[informant] = bitmasks[informant].combine(bitmasks[suspect]);
                }
            }
        }

        // combine non-conflict trusted informant
        // O(totalInformants * totalBitmasks) = O(20 * 2^20) = O(20 * 1048576) = O(20971520) = O(10^7)
        final List<Bitmask> validBitmasks = new LinkedList<>();
        validBitmasks.add(new Bitmask());
        for (int i = 1; i <= input.totalInformants; i++) {
            final Bitmask bitmask = bitmasks[i];
            if (!bitmask.isValid()) continue;

            final List<Bitmask> newBitmasks = validBitmasks.stream()
                .map(validBitmask -> validBitmask.combine(bitmask))
                .filter(Bitmask::isValid)
                .collect(Collectors.toList());
            validBitmasks.addAll(newBitmasks);
        }

        // find maximum reliable informants
        final Output output = new Output();
        output.maxTotalReliableInformants = validBitmasks.stream()
            .mapToInt(Bitmask::getTotalTrusts)
            .max()
            .orElse(0);
        return output;
    }
}

class Bitmask {
    public final int trusted;
    public final int untrusted;

    public Bitmask() {
        this(0, 0);
    }

    public Bitmask(final int trusted, final int untrusted) {
        this.trusted = trusted;
        this.untrusted = untrusted;
    }

    public boolean isValid() {
        return (trusted & untrusted) == 0;
    }

    public boolean hasTrust(final int suspect) {
        return (trusted & (1 << suspect)) != 0;
    }

    public Bitmask trust(final int suspect) {
        final int trusted = this.trusted | (1 << suspect);
        return new Bitmask(trusted, untrusted);
    }

    public Bitmask untrust(final int suspect) {
        final int untrusted = this.untrusted | (1 << suspect);
        return new Bitmask(trusted, untrusted);
    }

    public Bitmask combine(final Bitmask other) {
        final int trusted = this.trusted | other.trusted;
        final int untrusted = this.untrusted | other.untrusted;
        return new Bitmask(trusted, untrusted);
    }

    public int getTotalTrusts() {
        int count = 0;
        for (int trusted = this.trusted; trusted > 0; trusted >>= 1) {
            count += trusted & 1;
        }
        return count;
    }
}