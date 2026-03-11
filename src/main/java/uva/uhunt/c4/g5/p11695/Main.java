package uva.uhunt.c4.g5.p11695;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 11695 - Flight Planning
 * Time limit: 15.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2742
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedIO io = new BufferedIO(System.in, System.out);
        final Process process = new Process();

        final int totalTestCases = readInt(io);
        for (int i = 0; i < totalTestCases; i++) {
            final Input input = new Input();
            input.totalCities = readInt(io);
            input.flights = new int[input.totalFlights()][];
            for (int j = 0; j < input.totalFlights(); j++) {
                input.flights[j] = readInts(io);
            }

            final Output output = process.process(input);
            io.write(
                "%d\n%d %d\n%d %d\n",
                output.worstTotalFlights,
                output.cancelledFlight[0], output.cancelledFlight[1],
                output.approvedFlight[0], output.approvedFlight[1]
            );
        }

        io.close();
    }

    private static int[] readInts(final BufferedIO io) throws IOException {
        return Arrays.stream(io.readLines(" ")).mapToInt(Integer::parseInt).toArray();
    }

    private static int readInt(final BufferedIO io) throws IOException {
        return Integer.parseInt(io.readLine());
    }
}

final class BufferedIO {
    private final BufferedReader in;
    private final BufferedWriter out;

    public BufferedIO(final InputStream in, final OutputStream out) {
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new BufferedWriter(new OutputStreamWriter(out));
    }

    public String[] readLines(final String separator) throws IOException {
        final String line = readLine();
        return line.split(separator);
    }

    public String readLine() throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }

    public void write(final String format, Object... args) throws IOException {
        final String string = String.format(format, args);
        write(string);
    }

    public void write(final String string) throws IOException {
        out.write(string);
    }

    public void close() throws IOException {
        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalCities;
    public int[][] flights;

    public int totalFlights() {
        return totalCities - 1;
    }
}

class Output {
    public int worstTotalFlights;
    public int[] cancelledFlight;
    public int[] approvedFlight;

    public Output best(final Output o) {
        return isBetterThan(o) ? this : o;
    }

    public boolean isBetterThan(final Output o) {
        return o == null || worstTotalFlights < o.worstTotalFlights;
    }
}

class Process {
    public Output process(final Input input) {
        Output output = null;

        final UndirectedGraph graph = new UndirectedGraph(input.totalCities);
        fillGraph(graph, input.flights);

        for (final int[] cancelledFlight : input.flights) {
            // re-route flights
            graph.remove(cancelledFlight[0], cancelledFlight[1]);
            final Path path1 = getDiameterPath(input.totalCities, graph, cancelledFlight[0]);
            final Path path2 = getDiameterPath(input.totalCities, graph, cancelledFlight[1]);
            final int[] vertices1 = path1.collect();
            final int[] vertices2 = path2.collect();
            final int[] approvedFlight = new int[]{middle(vertices1), middle(vertices2)};
            graph.add(approvedFlight[0], approvedFlight[1]);

            // compare current flights with previous best flights
            final Output candidate = new Output();
            candidate.cancelledFlight = cancelledFlight;
            candidate.approvedFlight = approvedFlight;
            candidate.worstTotalFlights = getDiameterPath(input.totalCities, graph, approvedFlight[0]).distance;
            output = candidate.best(output);

            // restore flights
            graph.remove(approvedFlight[0], approvedFlight[1]);
            graph.add(cancelledFlight[0], cancelledFlight[1]);
        }

        return output;
    }

    private Path getDiameterPath(
        final int totalVertices,
        final UndirectedGraph graph,
        final int initialVertex
    ) {
        final Path path1 = getFarthestPath(totalVertices, graph, initialVertex);
        final Path path2 = getFarthestPath(totalVertices, graph, path1.vertex);

        return path2;
    }

    private Path getFarthestPath(
        final int totalVertices,
        final UndirectedGraph graph,
        final int initialVertex
    ) {
        final Path[] shortestPaths = new Path[totalVertices + 1];
        final Queue<Path> pathq = new LinkedList<>();

        final Path initial = new Path(initialVertex);
        shortestPaths[initial.vertex] = initial;
        pathq.add(initial);

        Path farthestPath = initial;
        while (!pathq.isEmpty()) {
            final Path current = pathq.remove();
            farthestPath = current;

            for (final int nextVertex : graph.get(current.vertex)) {
                final Path next = current.next(nextVertex);

                if (shortestPaths[next.vertex] == null) {
                    shortestPaths[next.vertex] = next;
                    pathq.add(next);
                }
            }
        }

        return farthestPath;
    }

    private void fillGraph(UndirectedGraph graph, final int[][] edges) {
        for (final int[] edge : edges) graph.add(edge[0], edge[1]);
    }

    private int middle(final int[] array) {
        return array[array.length / 2];
    }
}

final class UndirectedGraph {
    private final List<Integer>[] edges;

    public UndirectedGraph(final int totalVertices) {
        edges = new LinkedList[totalVertices + 1];
    }

    public void add(final int first, final int second) {
        addDirected(first, second);
        addDirected(second, first);
    }

    private void addDirected(final int from, final int into) {
        if (edges[from] == null) edges[from] = new LinkedList<>();
        edges[from].add(into);
    }

    public void remove(final int first, final int second) {
        removeDirected(first, second);
        removeDirected(second, first);
    }

    private void removeDirected(final int from, final int into) {
        if (edges[from] == null) return;
        edges[from].removeIf(value -> value == into);
    }

    public Collection<Integer> get(final int from) {
        final List<Integer> vertices = edges[from];
        return vertices == null ? Collections.emptyList() : vertices;
    }
}

class Path {
    public Path previous;
    public int vertex;
    public int distance;

    public Path(final int vertex) {
        this(null, vertex, 0);
    }

    private Path(final Path previous, final int vertex, final int distance) {
        this.previous = previous;
        this.vertex = vertex;
        this.distance = distance;
    }

    public Path next(final int nextVertex) {
        return new Path(this, nextVertex, distance + 1);
    }

    public int[] collect() {
        final LinkedList<Integer> list = new LinkedList<>();
        for (Path current = this; current != null; current = current.previous) {
            list.addFirst(current.vertex);
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
}
