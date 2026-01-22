package uhunt.c3.g2.p13142;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 13142 - Destroy the Moon to Save the Earth
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=5053
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        final int totalTestCases = in.nextInt();
        for (int i = 0; i < totalTestCases; i++) {
            final Input input = new Input();
            input.time = in.nextInt();
            input.speed = in.nextInt();
            input.distance = in.nextInt();

            final Output output = process.process(input);
            if (output.mass >= 0d) out.format("Add %.0f tons\n", Math.abs(output.mass));
            else out.format("Remove %.0f tons\n", Math.abs(output.mass));
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    // days
    public double time;
    // milimeters/seconds
    public double speed;
    // kilometers
    public double distance;
}

class Output {
    // tons
    public double mass;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final double timeS = input.time * 24d * 60d * 60d;
        final double speedMMPS = input.speed;
        final double distanceMM = timeS * speedMMPS;

        final double deltaDistanceMM = input.distance * 1_000_000d;
        final double desiredDistanceMM = distanceMM + deltaDistanceMM;
        final double desiredSpeedMMPS = desiredDistanceMM / timeS;
        final double deltaSpeedMMPS = desiredSpeedMMPS - speedMMPS;

        final double roundedMass = round(-deltaSpeedMMPS);
        output.mass = roundedMass;
        return output;
    }

    private double round(final double value) {
        return value >= 0d ? Math.floor(value) : Math.ceil(value);
    }
}
