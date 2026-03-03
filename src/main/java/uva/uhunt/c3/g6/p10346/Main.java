package uva.uhunt.c3.g6.p10346;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 10346 - Peter's Smokes
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1287
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(System.in);
        final PrintWriter out = new PrintWriter(System.out);
        final Process process = new Process();

        while (in.hasNextInt()) {
            final Input input = new Input();
            input.totalCigarettes = in.nextInt();
            input.totalButtsForNewCigarettes = in.nextInt();
            final Output output = process.process(input);
            out.println(output.totalCigarettes);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalCigarettes;
    public int totalButtsForNewCigarettes;
}

class Output {
    public int totalCigarettes;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.totalCigarettes = getTotalRecycledCigarettes(
            input.totalCigarettes,
            0,
            input.totalButtsForNewCigarettes
        );
        return output;
    }

    private int getTotalRecycledCigarettes(
        final int totalCigarettes,
        final int totalButts,
        final int totalButtsForNewCigarettes
    ) {
        if (totalCigarettes == 0) return 0;

        final int totalRecycledCigarettes = getTotalRecycledCigarettes(
            (totalCigarettes + totalButts) / totalButtsForNewCigarettes,
            (totalCigarettes + totalButts) % totalButtsForNewCigarettes,
            totalButtsForNewCigarettes
        );
        return totalCigarettes + totalRecycledCigarettes;
    }
}
