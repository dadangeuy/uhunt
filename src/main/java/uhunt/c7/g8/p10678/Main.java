package uhunt.c7.g8.p10678;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 * 10678 - The Grazing Cow
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1619
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        final int totalInputs = in.nextInt();
        for (int i = 0; i < totalInputs; i++) {
            final Input input = new Input();
            input.distance = in.nextInt();
            input.length = in.nextInt();

            final Output output = process.process(input);
            out.format("%.3f\n", rounding(output.area));
        }

        in.close();
        out.flush();
        out.close();
    }

    private static BigDecimal rounding(final double value) {
        return BigDecimal.valueOf(value).setScale(3, RoundingMode.HALF_UP);
    }
}

class Input {
    public int distance;
    public int length;
}

class Output {
    public double area;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final double majorRadius = input.length / 2.0;

        final double halfLength = input.length / 2.0;
        final double halfDistance = input.distance / 2.0;
        final double minorRadius = Math.sqrt(halfLength * halfLength - halfDistance * halfDistance);

        output.area = majorRadius * minorRadius * 2 * Math.acos(0);
        return output;
    }
}
