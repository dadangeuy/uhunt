package uhunt.c3.p12324;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.TreeMap;

/**
 * 12324 - Philip J. Fry Problem
 * Time limit: 2.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3746
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 8);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 8);
        final Process process = new Process();

        while (true) {
            final Input input = new Input();

            final String l1 = readLine(in);
            input.totalTrips = Integer.parseInt(l1);

            input.trips = new int[input.totalTrips][];
            for (int i = 0; i < input.totalTrips; i++) {
                final String[] l2 = readSplitLine(in);
                input.trips[i] = new int[]{
                    Integer.parseInt(l2[0]),
                    Integer.parseInt(l2[1])
                };
            }

            if (input.isEOF()) break;

            final Output output = process.process(input);
            out.write(Integer.toString(output.minimumDuration));
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String[] readSplitLine(final BufferedReader in) throws IOException {
        final String line = readLine(in);
        return line.split(" ");
    }

    private static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Input {
    public int totalTrips;
    public int[][] trips;

    public boolean isEOF() {
        return totalTrips == 0;
    }
}

class Output {
    public int minimumDuration;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final Map<Integer, Integer> previousTotalSpheresPerDuration = new TreeMap<>();
        final Map<Integer, Integer> nextTotalSpheresPerDuration = new TreeMap<>();

        previousTotalSpheresPerDuration.put(0, 0);

        for (final int[] trip : input.trips) {
            final int duration = trip[0];
            final int totalSpheres = trip[1];

            // generate every possible duration and total spheres
            for (Map.Entry<Integer, Integer> entry : previousTotalSpheresPerDuration.entrySet()) {
                final int previousDuration = entry.getKey();
                final int previousTotalSpheres = entry.getValue();

                // if using spheres
                if (previousTotalSpheres >= 1) {
                    final int nextDuration = previousDuration + (duration / 2);
                    final int nextTotalSpheres = previousTotalSpheres - 1 + totalSpheres;

                    nextTotalSpheresPerDuration.putIfAbsent(nextDuration, nextTotalSpheres);
                    nextTotalSpheresPerDuration.computeIfPresent(nextDuration, (k, v) -> Math.max(v, nextTotalSpheres));
                }

                // if not using spheres
                {
                    final int nextDuration = previousDuration + duration;
                    final int nextTotalSpheres = previousTotalSpheres + totalSpheres;

                    nextTotalSpheresPerDuration.putIfAbsent(nextDuration, nextTotalSpheres);
                    nextTotalSpheresPerDuration.computeIfPresent(nextDuration, (k, v) -> Math.max(v, nextTotalSpheres));
                }
            }

            previousTotalSpheresPerDuration.clear();

            // copy optimal duration & total spheres from next to previous
            // optimal = maximum total spheres with minimum duration
            int maximumTotalSpheres = -1;
            for (Map.Entry<Integer, Integer> entry : nextTotalSpheresPerDuration.entrySet()) {
                final int nextDuration = entry.getKey();
                final int nextTotalSpheres = entry.getValue();

                if (nextTotalSpheres > maximumTotalSpheres) {
                    previousTotalSpheresPerDuration.put(nextDuration, nextTotalSpheres);
                    maximumTotalSpheres = nextTotalSpheres;
                }
            }

            nextTotalSpheresPerDuration.clear();
        }

        final int minimumDuration = previousTotalSpheresPerDuration.keySet().stream()
            .mapToInt(i -> i)
            .min()
            .orElseThrow(NullPointerException::new);

        output.minimumDuration = minimumDuration;

        return output;
    }
}
