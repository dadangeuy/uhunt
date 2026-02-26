package uva.c3.g4.p10774;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 10774 - Repeated Josephus
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1715
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        final int totalTestCases = in.nextInt();
        for (int testCase = 1; testCase <= totalTestCases; testCase++) {
            final Input input = new Input();
            input.testCase = testCase;
            input.totalPeoples = in.nextInt();

            final Output output = process.process(input);
            out.format("Case %d: %d %d\n", output.testCase, output.totalRepetitions, output.survivor);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int testCase;
    public int totalPeoples;
}

class Output {
    public int testCase;
    public int totalRepetitions;
    public int survivor;
}

class Process {
    private static final int MAX_TOTAL_PEOPLES = 30000;
    private final Integer[] cachedFindSurvivor = new Integer[MAX_TOTAL_PEOPLES + 1];

    public Output process(final Input input) {
        final Output output = new Output();
        output.testCase = input.testCase;

        int totalPeoples = input.totalPeoples;
        int survivor = findSurvivor(totalPeoples);
        int totalRepetitions = 0;

        while (totalPeoples != survivor) {
            totalPeoples = survivor;
            survivor = findSurvivor(totalPeoples);
            totalRepetitions++;
        }

        output.totalRepetitions = totalRepetitions;
        output.survivor = survivor;

        return output;
    }

    private int findSurvivor(final int totalPeoples) {
        if (cachedFindSurvivor[totalPeoples] != null) {
            return cachedFindSurvivor[totalPeoples];
        }

        Chain chain = createChain(1, totalPeoples);
        while (chain.next != chain) {
            chain.next = chain.next.next;
            chain = chain.next;
        }

        return cachedFindSurvivor[totalPeoples] = chain.value;
    }

    private Chain createChain(final int from, final int until) {
        final Chain first = new Chain(1);
        Chain last = first;
        for (int i = from + 1; i <= until; i++) {
            last.next = new Chain(i);
            last = last.next;
        }
        last.next = first;
        return first;
    }
}

class Chain {
    public final int value;
    public Chain next;

    public Chain(final int value) {
        this.value = value;
    }
}
