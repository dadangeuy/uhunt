package uhunt.c4.g7.p907;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 907 - Winterim Backpacking Trip
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=848
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        while (in.hasNextInt()) {
            final Input input = new Input();
            input.totalCampsites = in.nextInt();
            input.totalNights = in.nextInt();
            input.distances = new int[input.totalCampsites + 1];
            for (int i = 0; i < input.totalCampsites + 1; i++) {
                input.distances[i] = in.nextInt();
            }

            final Output output = process.process(input);
            out.println(output.minimumOfMaximumDistance);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalCampsites;
    public int totalNights;
    public int[] distances;
}

class Output {
    public int minimumOfMaximumDistance;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.minimumOfMaximumDistance = getMinimumOfMaximumDistance(
            input,
            0,
            0,
            new Integer[input.distances.length + 1][input.totalNights + 1]
        );
        return output;
    }

    private int getMinimumOfMaximumDistance(
        final Input input,
        final int originCamp,
        final int totalNights,
        final Integer[][] minimumOfMaximumDistancePerState
    ) {
        if (minimumOfMaximumDistancePerState[originCamp][totalNights] != null) {
            return minimumOfMaximumDistancePerState[originCamp][totalNights];
        }

        int minimumOfMaximumDistance = Integer.MAX_VALUE;
        int totalWalkDistances = 0;

        for (int destinationCamp = originCamp; destinationCamp < input.distances.length; destinationCamp++) {
            final int walkDistance = input.distances[destinationCamp];
            totalWalkDistances += walkDistance;

            if (totalNights < input.totalNights) {
                final int totalCampDistances = getMinimumOfMaximumDistance(
                    input,
                    destinationCamp + 1,
                    totalNights + 1,
                    minimumOfMaximumDistancePerState
                );
                final int maximumDistance = Math.max(totalWalkDistances, totalCampDistances);
                minimumOfMaximumDistance = Math.min(
                    minimumOfMaximumDistance,
                    maximumDistance
                );
            }
        }

        minimumOfMaximumDistance = Math.min(
            minimumOfMaximumDistance,
            totalWalkDistances
        );
        return minimumOfMaximumDistancePerState[originCamp][totalNights] = minimumOfMaximumDistance;
    }
}
