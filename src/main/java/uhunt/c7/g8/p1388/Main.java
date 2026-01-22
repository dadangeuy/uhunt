package uhunt.c7.g8.p1388;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * 1388 - Graveyard
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4134
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        while (in.hasNextInt()) {
            final Input input = new Input();
            input.totalStatues = in.nextInt();
            input.totalNewStatues = in.nextInt();

            final Output output = process.process(input);
            final String totalDistances = output.totalDistances
                .setScale(4, RoundingMode.UP)
                .toPlainString();
            out.write(totalDistances);
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalStatues;
    public int totalNewStatues;
}

class Output {
    public BigDecimal totalDistances;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final BigDecimal perimeter = BigDecimal.valueOf(10_000);

        final BigDecimal oldTotalStatues = BigDecimal.valueOf(input.totalStatues);
        final BigDecimal newTotalStatues = BigDecimal.valueOf(input.totalStatues + input.totalNewStatues);

        final BigDecimal oldGap = perimeter.divide(oldTotalStatues, 16, RoundingMode.UP);
        final BigDecimal newGap = perimeter.divide(newTotalStatues, 16, RoundingMode.UP);

        final TreeSet<BigDecimal> oldPositions = buildPositions(perimeter, oldGap);
        final TreeSet<BigDecimal> newPositions = buildPositions(perimeter, newGap);

        BigDecimal totalDistances = BigDecimal.ZERO;

        for (final BigDecimal position : oldPositions) {
            final BigDecimal lte = newPositions.floor(position);
            final BigDecimal gte = newPositions.ceiling(position);

            final BigDecimal deltaLTE = position.subtract(lte);
            final BigDecimal deltaGTE = gte.subtract(position);

            final BigDecimal minDelta = deltaLTE.min(deltaGTE);
            totalDistances = totalDistances.add(minDelta);
        }

        output.totalDistances = totalDistances;
        return output;
    }

    private TreeSet<BigDecimal> buildPositions(final BigDecimal perimeter, final BigDecimal gap) {
        final TreeSet<BigDecimal> positions = new TreeSet<>();
        for (BigDecimal position = BigDecimal.ZERO; position.compareTo(perimeter) < 0; position = position.add(gap)) {
            positions.add(position);
        }
        return positions;
    }
}
