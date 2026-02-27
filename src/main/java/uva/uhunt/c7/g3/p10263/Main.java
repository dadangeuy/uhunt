package uva.uhunt.c7.g3.p10263;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * 10263 - Railway
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1204
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        while (in.hasNextDouble()) {
            final Input input = new Input();
            input.destination = new Vector(
                in.nextDouble(),
                in.nextDouble()
            );
            input.totalSegments = in.nextInt();
            input.corners = new Vector[input.totalSegments + 1];
            for (int i = 0; i < input.totalSegments + 1; i++) {
                input.corners[i] = new Vector(
                    in.nextDouble(),
                    in.nextDouble()
                );
            }

            final Output output = process.process(input);
            out.format("%.4f\n%.4f\n", rounding(output.station.values[0]), rounding(output.station.values[1]));
        }

        in.close();
        out.flush();
        out.close();
    }

    private static double rounding(final double value) {
        return BigDecimal.valueOf(value).setScale(4, RoundingMode.HALF_UP).doubleValue();
    }
}

class Input {
    public Vector destination;
    public int totalSegments;
    public Vector[] corners;
}

class Output {
    public Vector station;
}

class Process {

    /**
     * the position of the station is either on:
     * 1. station = corner1
     * 2. station = corner2
     * 3. corner1 <= station <= corner2, if station ⟂ destination
     */
    public Output process(final Input input) {
        final Output output = new Output();

        final LinkedList<Vector> stations = new LinkedList<>();

        for (int i = 0; i < input.totalSegments; i++) {
            final Vector corner1 = input.corners[i];
            final Vector corner2 = input.corners[i + 1];
            final Vector destination = input.destination;

            // movement from corner 1 to corner 2
            final Vector c1c2 = corner2.subtract(corner1);
            // movement from corner 1 to destination
            final Vector c1d = destination.subtract(corner1);
            // movement from corner 1 to station
            final Vector c1s = c1d.projection(c1c2);

            final double ratio = c1d.projectionRatio(c1c2);
            final Vector station = corner1.add(c1s);

            if (ratio <= 0) {
                stations.add(corner1);
            } else if (ratio >= 1) {
                stations.add(corner2);
            } else {
                stations.add(station);
            }
        }

        final Comparator<Vector> orderStationByDistance = Comparator.nullsLast(Comparator.comparingDouble(v -> v.distance(input.destination)));
        output.station = stations.stream().min(orderStationByDistance).orElseThrow(NullPointerException::new);

        return output;
    }
}

class Vector {
    public final double[] values;

    public Vector(final double... values) {
        this.values = values;
    }

    public Vector add(final Vector other) {
        final double[] result = IntStream.range(0, values.length)
            .mapToDouble(i -> values[i] + other.values[i])
            .toArray();
        return new Vector(result);
    }

    public Vector subtract(final Vector other) {
        final double[] result = IntStream.range(0, values.length)
            .mapToDouble(i -> values[i] - other.values[i])
            .toArray();
        return new Vector(result);
    }

    public Vector multiply(final double scalar) {
        final double[] result = Arrays.stream(values)
            .map(v -> v * scalar)
            .toArray();
        return new Vector(result);
    }

    public double dot(final Vector other) {
        return IntStream.range(0, values.length)
            .mapToDouble(i -> values[i] * other.values[i])
            .sum();
    }

    public double distance(final Vector other) {
        final double squared = IntStream.range(0, values.length)
            .mapToDouble(i -> values[i] - other.values[i])
            .map(v -> v * v)
            .sum();
        return Math.sqrt(squared);
    }

    public Vector projection(final Vector other) {
        return other.multiply(projectionRatio(other));
    }

    public double projectionRatio(final Vector other) {
        return this.dot(other) / other.dot(other);
    }
}
