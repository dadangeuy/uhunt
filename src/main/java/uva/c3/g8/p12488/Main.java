package uva.c3.g8.p12488;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 12488 - Start Grid
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3932
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        final Process process = new Process();

        while (true) {
            final String l1 = readLine(in);
            final boolean isEOF = l1 == null;
            if (isEOF) break;

            final String[] l2 = readSplitLine(in);
            final String[] l3 = readSplitLine(in);

            final int totalCompetitors = Integer.parseInt(l1);
            final int[] startPositions = Arrays.stream(l2).mapToInt(Integer::parseInt).toArray();
            final int[] finishPositions = Arrays.stream(l3).mapToInt(Integer::parseInt).toArray();

            final Input input = new Input();
            input.totalCompetitors = totalCompetitors;
            input.startPositions = startPositions;
            input.finishPositions = finishPositions;

            final Output output = process.process(input);
            out.write(Integer.toString(output.minimumTotalOvertakes));
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String[] readSplitLine(final BufferedReader in) throws IOException {
        final String line = readLine(in);
        return line == null ? null : line.split(" ");
    }

    private static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Input {
    public int totalCompetitors;
    public int[] startPositions;
    public int[] finishPositions;
}

class Output {
    public int minimumTotalOvertakes;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final List<Competitor> competitors = IntStream.rangeClosed(1, input.totalCompetitors)
            .mapToObj(id -> {
                final Competitor competitor = new Competitor();
                competitor.id = id;
                competitor.startedAt = indexOf(input.startPositions, id) + 1;
                competitor.finishedAt = indexOf(input.finishPositions, id) + 1;
                return competitor;
            })
            .sorted(Comparator.comparingInt(competitor -> competitor.startedAt))
            .collect(Collectors.toList());

        int totalOvertakes = 0;
        for (int i = 0; i < input.totalCompetitors; i++) {
            for (int j = 0, k = 1; k < input.totalCompetitors; j++, k++) {
                final Competitor competitor1 = competitors.get(j);
                final Competitor competitor2 = competitors.get(k);

                final boolean mustSwap = competitor2.finishedAt < competitor1.finishedAt;
                if (mustSwap) {
                    swap(competitors, j, k);
                    totalOvertakes++;
                }
            }
        }

        output.minimumTotalOvertakes = totalOvertakes;
        return output;
    }

    private void swap(final List<Competitor> list, final int index1, final int index2) {
        final Competitor competitor1 = list.get(index1);
        final Competitor competitor2 = list.get(index2);

        list.set(index1, competitor2);
        list.set(index2, competitor1);
    }

    private int indexOf(final int[] array, final int value) {
        for (int idx = 0; idx < array.length; idx++) {
            if (array[idx] == value) {
                return idx;
            }
        }
        return -1;
    }
}

class Competitor {
    public int id;
    public int startedAt;
    public int finishedAt;
}
