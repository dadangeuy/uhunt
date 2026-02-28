package uva.uhunt.c4.g3.p12363;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * 12363 - Hedge Mazes
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=onlinejudge&page=show_problem&problem=3785
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final FastReader in = new FastReader(System.in, 1 << 8);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        final Process process = new Process();

        while (true) {
            final Input input = new Input();
            input.totalRooms = in.nextInt();
            input.totalCorridors = in.nextInt();
            input.totalQueries = in.nextInt();
            if (input.isEOF()) break;

            input.corridors = new int[input.totalCorridors][];
            for (int i = 0; i < input.totalCorridors; i++) {
                final int[] corridor = new int[2];
                corridor[0] = in.nextInt();
                corridor[1] = in.nextInt();
                input.corridors[i] = corridor;
            }

            input.queries = new int[input.totalQueries][];
            for (int i = 0; i < input.totalQueries; i++) {
                final int[] query = new int[2];
                query[0] = in.nextInt();
                query[1] = in.nextInt();
                input.queries[i] = query;
            }

            final Output output = process.process(input);
            for (int i = 0; i < output.totalAnswers; i++) {
                out.write(output.answers[i]);
                out.write('\n');
            }
            out.write('-');
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }
}

final class FastReader {
    private final InputStream stream;
    private final byte[] buffer;
    private int length = 0;
    private int offset = 0;
    private final byte[] readBuffer;

    public FastReader(InputStream stream, int bufferSize) {
        this.stream = stream;
        this.buffer = new byte[bufferSize];
        this.readBuffer = new byte[bufferSize];
    }

    public boolean hasNextInt() throws IOException {
        skipUntil(b -> !Character.isWhitespace(b));
        return !end() && (buffer[offset] == '-' || Character.isDigit(buffer[offset]));
    }

    public int nextInt() throws IOException {
        if (!hasNextInt()) return 0;

        boolean negative = buffer[offset] == '-';
        if (negative) offset++;

        int length = readWhile(Character::isDigit, readBuffer);
        int value = 0;
        for (int i = 0; i < length; i++) value = value * 10 + readBuffer[i] - '0';
        return negative ? -value : value;
    }

    public boolean hasNext() throws IOException {
        skipUntil(b -> !Character.isWhitespace(b));
        return !end();
    }

    public String next() throws IOException {
        if (!hasNext()) return "";

        int length = readWhile(b -> !Character.isWhitespace(b), readBuffer);
        return new String(readBuffer, 0, length);
    }

    private void skipUntil(Function<Byte, Boolean> criteria) throws IOException {
        while (true) {
            if (exhausted()) read();
            if (end()) return;
            if (criteria.apply(buffer[offset])) return;
            ++offset;
        }
    }

    private int readWhile(Function<Byte, Boolean> criteria, byte[] output) throws IOException {
        for (int i = 0; ; i++) {
            if (exhausted()) read();
            if (end()) return i;
            if (!criteria.apply(buffer[offset])) return i;
            output[i] = buffer[offset];
            offset++;
        }
    }

    private boolean end() {
        return length == -1;
    }

    private boolean exhausted() {
        return offset >= length;
    }

    private void read() throws IOException {
        length = stream.read(buffer);
        offset = 0;
    }

    public void close() throws IOException {
        stream.close();
    }
}

class Input {
    public int totalRooms;
    public int totalCorridors;
    public int totalQueries;
    public int[][] corridors;
    public int[][] queries;

    public boolean isEOF() {
        return totalRooms == 0 && totalCorridors == 0 && totalQueries == 0;
    }
}

class Output {
    public int totalAnswers;
    public char[] answers;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.totalAnswers = input.totalQueries;
        output.answers = new char[input.totalQueries];

        final DisjointSet connectedComponents = new DisjointSet(input.totalRooms);
        for (int[] corridor : input.corridors) {
            connectedComponents.union(corridor[0], corridor[1]);
        }

        final UndirectedGraph graph = createGraph(input);
        final TarjanBridges tarjanBridges = new TarjanBridges(graph);
        final DisjointSet bridgeConnectedComponents = new DisjointSet(input.totalRooms);

        final int[][] bridges = tarjanBridges.getBridges();
        for (final int[] bridge : bridges) {
            bridgeConnectedComponents.union(bridge[0], bridge[1]);
        }

        for (int i = 0; i < input.totalQueries; i++) {
            final int[] query = input.queries[i];
            final int cc1 = connectedComponents.find(query[0]);
            final int cc2 = connectedComponents.find(query[1]);
            if (cc1 != cc2) {
                output.answers[i] = 'N';
                continue;
            }

            final int bcc1 = bridgeConnectedComponents.find(query[0]);
            final int bcc2 = bridgeConnectedComponents.find(query[1]);
            if (bcc1 != bcc2) {
                output.answers[i] = 'N';
                continue;
            }

            output.answers[i] = 'Y';
        }

        return output;
    }

    private UndirectedGraph createGraph(final Input input) {
        final UndirectedGraph.Builder builder = new UndirectedGraph.Builder(input.totalRooms);
        for (final int[] corridor : input.corridors) {
            builder.add(corridor[0], corridor[1]);
        }
        return builder.build();
    }
}

final class DisjointSet {
    private final int[] parents;

    public DisjointSet(final int maxVertex) {
        parents = new int[maxVertex + 1];
        Arrays.fill(parents, -1);
    }

    public int find(final int child) {
        final int parent = parents[child] == -1 ? child : parents[child];
        if (parent == child) {
            return parent;
        } else {
            final int grandparent = find(parent);
            parents[child] = grandparent;
            return grandparent;
        }
    }

    public void union(final int child1, final int child2) {
        final int parent1 = find(child1);
        final int parent2 = find(child2);
        parents[parent2] = parent1;
    }
}

final class UndirectedGraph {
    private final int[][] edges;

    private UndirectedGraph(final Builder builder) {
        edges = new int[builder.edges.length][];
        for (int vertex = 0; vertex < builder.edges.length; vertex++) {
            final List<Integer> destinations = builder.edges[vertex] == null ? Collections.emptyList() : builder.edges[vertex];
            edges[vertex] = destinations.stream().mapToInt(Integer::intValue).distinct().toArray();
        }
    }

    public int[][] get() {
        return edges;
    }

    public int[] get(final int from) {
        return edges[from];
    }

    static class Builder {
        private final LinkedList<Integer>[] edges;

        public Builder(final int maxVertex) {
            this.edges = new LinkedList[maxVertex + 1];
        }

        public void add(final int first, final int second) {
            addUni(first, second);
            addUni(second, first);
        }

        private void addUni(final int from, final int into) {
            if (edges[from] == null) edges[from] = new LinkedList<>();
            edges[from].add(into);
        }

        public UndirectedGraph build() {
            return new UndirectedGraph(this);
        }
    }
}

final class TarjanBridges {
    private static final int NULL = -1;

    private final UndirectedGraph graph;
    private int[] discovery;
    private int[] low;
    private int[] parent;
    private LinkedList<int[]> bridgeList;
    private int[][] bridges;
    private int time;

    public TarjanBridges(final UndirectedGraph graph) {
        this.graph = graph;
        initArrays();
        initBridges();
    }

    private void initArrays() {
        this.discovery = new int[graph.get().length];
        this.low = new int[graph.get().length];
        this.parent = new int[graph.get().length];

        Arrays.fill(discovery, NULL);
        Arrays.fill(low, NULL);
        Arrays.fill(parent, NULL);
    }

    private void initBridges() {
        this.bridgeList = new LinkedList<>();
        for (int vertex = 0; vertex < graph.get().length; vertex++) {
            if (discovery[vertex] == NULL) {
                depthFirstSearch(vertex);
            }
        }
        bridges = bridgeList.toArray(new int[0][0]);
    }

    private void depthFirstSearch(final int vertex) {
        discovery[vertex] = low[vertex] = ++time;
        for (int nextVertex : graph.get(vertex)) {
            if (discovery[nextVertex] == NULL) {
                parent[nextVertex] = vertex;
                depthFirstSearch(nextVertex);
                low[vertex] = Math.min(low[vertex], low[nextVertex]);

                if (low[nextVertex] > discovery[vertex]) {
                    foundBridge(vertex, nextVertex);
                    foundBridge(nextVertex, vertex);
                }
            } else if (nextVertex != parent[vertex]) {
                low[vertex] = Math.min(low[vertex], discovery[nextVertex]);
            }
        }
    }

    private void foundBridge(final int fromVertex, final int intoVertex) {
        bridgeList.addLast(new int[]{fromVertex, intoVertex});
    }

    public int[][] getBridges() {
        return bridges;
    }
}
