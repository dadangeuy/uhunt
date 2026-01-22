package uhunt.c4.g5.p10805;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * 10805 - Cockroach Escape Networks
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1746
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        final int totalCases = in.nextInt();
        for (int caseId = 1; caseId <= totalCases; caseId++) {
            final Input input = new Input();
            input.caseId = caseId;
            input.totalNests = in.nextInt();
            input.totalTrails = in.nextInt();
            input.trails = new int[input.totalTrails][];
            for (int trailIdx = 0; trailIdx < input.totalTrails; trailIdx++) {
                input.trails[trailIdx] = new int[]{in.nextInt(), in.nextInt()};
            }

            final Output output = process.process(input);
            out.format("Case #%d:\n%d\n\n", output.caseId, output.smallestEmergencyResponseTime);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int caseId;
    public int totalNests;
    public int totalTrails;
    public int[][] trails;
}

class Output {
    public int caseId;
    public int smallestEmergencyResponseTime;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.caseId = input.caseId;

        int[][] distances = floydWarshall(input.trails, input.totalNests);
        int minimumDiameter = Integer.MAX_VALUE;

        // case for even
        for (int nest = 0; nest < input.totalNests; nest++) {
            final int eccentricity = eccentricity(distances, nest);
            final int diameter = eccentricity * 2;
            minimumDiameter = Math.min(minimumDiameter, diameter);
        }

        // case for odd
        for (int[] trail : input.trails) {
            final int eccentricity = eccentricity(distances, trail[0], trail[1]);
            final int diameter = eccentricity * 2 + 1;
            minimumDiameter = Math.min(minimumDiameter, diameter);
        }

        output.smallestEmergencyResponseTime = minimumDiameter;
        return output;
    }

    private int[][] floydWarshall(final int[][] trails, final int totalNests) {
        final int[][] distances = new int[totalNests][totalNests];

        for (int nest1 = 0; nest1 < totalNests; nest1++) {
            for (int nest2 = nest1; nest2 < totalNests; nest2++) {
                distances[nest1][nest2] = distances[nest2][nest1] = Integer.MAX_VALUE;
                distances[nest1][nest1] = distances[nest2][nest2] = 0;
            }
        }

        for (final int[] trail : trails) {
            distances[trail[0]][trail[1]] = distances[trail[1]][trail[0]] = 1;
        }

        for (int alternative = 0; alternative < totalNests; alternative++) {
            for (int origin = 0; origin < totalNests; origin++) {
                for (int destination = 0; destination < totalNests; destination++) {
                    final int d0 = distances[origin][destination];
                    final int d1 = distances[origin][alternative];
                    final int d2 = distances[alternative][destination];
                    final int d12 = d1 + d2;

                    if (d1 != Integer.MAX_VALUE && d2 != Integer.MAX_VALUE) {
                        distances[origin][destination] = Math.min(d0, d12);
                    }
                }
            }
        }

        return distances;
    }

    private int eccentricity(final int[][] distances, final int... centerNests) {
        return IntStream.range(0, distances.length)
            .map(i -> Arrays.stream(centerNests).map(centerNest -> distances[centerNest][i]).min().orElse(0))
            .max()
            .orElse(0);
    }
}
