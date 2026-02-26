package uva.c2.g8.p10928;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 10928 - My Dear Neighbours
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1869
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        final int totalTestCases = Integer.parseInt(in.readLine());

        for (int i = 0; i < totalTestCases; i++) {
            final Input input = new Input();
            final Output output = new Output();
            final Process process = new Process(input, output);

            input.totalPlaces = Integer.parseInt(in.readLine());
            for (int j = 0; j < input.totalPlaces; j++) {
                final int[] neighbours = Arrays.stream(in.readLine().split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                input.neighboursForEachPlace.addLast(neighbours);
            }
            in.readLine();

            process.process();

            final Iterator<Integer> placeIterator = output.minimumNeighbourPlaces.iterator();
            out.append(String.valueOf(placeIterator.next()));
            while (placeIterator.hasNext()) out.append(' ').append(String.valueOf(placeIterator.next()));
            out.append('\n');
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Process {
    private final Input input;
    private final Output output;

    public Process(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void process() {
        final int minimumNeighbours = input.neighboursForEachPlace.stream()
                .mapToInt(neighbours -> neighbours.length)
                .min()
                .orElse(0);

        final Iterator<int[]> neighboursIterator = input.neighboursForEachPlace.iterator();
        for (int i = 0; i < input.totalPlaces; i++) {
            final int[] neighbours = neighboursIterator.next();
            if (neighbours.length == minimumNeighbours) {
                int place = i + 1;
                output.minimumNeighbourPlaces.addLast(place);
            }
        }
    }
}

class Input {
    public int totalPlaces = 0;
    public LinkedList<int[]> neighboursForEachPlace = new LinkedList<>();
}

class Output {
    public LinkedList<Integer> minimumNeighbourPlaces = new LinkedList<>();
}
