package uhunt.c4.p670;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 * 670 - The dog task
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=611
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        final int totalDatasets = in.nextInt();
        for (int dataset = 0; dataset < totalDatasets; dataset++) {
            final Input input = new Input();
            input.totalBobWalks = in.nextInt();
            input.totalLandscapes = in.nextInt();

            input.bobWalks = new Coordinate[input.totalBobWalks];
            for (int i = 0; i < input.totalBobWalks; i++) {
                final Coordinate coordinate = new Coordinate(
                    String.format("WALK-%d", i),
                    in.nextInt(),
                    in.nextInt()
                );
                input.bobWalks[i] = coordinate;
            }

            input.landscapes = new Coordinate[input.totalLandscapes];
            for (int i = 0; i < input.totalLandscapes; i++) {
                final Coordinate coordinate = new Coordinate(
                    String.format("LANDSCAPE-%d", i),
                    in.nextInt(),
                    in.nextInt()
                );
                input.landscapes[i] = coordinate;
            }

            final Output output = process.process(input);
            if (dataset > 0) out.write('\n');
            out.println(output.ralphWalks.length);
            for (int i = 0; i < output.ralphWalks.length; i++) {
                final Coordinate landscape = output.ralphWalks[i];
                if (i > 0) out.write(' ');
                out.format("%d %d", landscape.x, landscape.y);
            }
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalBobWalks;
    public int totalLandscapes;
    public Coordinate[] bobWalks;
    public Coordinate[] landscapes;
}

class Output {
    public Coordinate[] ralphWalks;
}

class Process {
    private static final Coordinate SOURCE = new Coordinate("SOURCE", 0, 0);
    private static final Coordinate SINK = new Coordinate("SINK", 0, 0);

    public Output process(final Input input) {
        final Output output = new Output();

        final FlowNetwork<Coordinate> flowNetwork = createFlowNetwork(input);
        flowNetwork.doMaximumFlow();
        final Coordinate[] ralphWalks = getRalphWalks(input, flowNetwork);

        output.ralphWalks = ralphWalks;
        return output;
    }

    /**
     * create flow network with structure: ORIGIN -> WALK -> LANDSCAPE -> DESTINATION.
     * each edge on the network has weight = 1.
     */
    private FlowNetwork<Coordinate> createFlowNetwork(final Input input) {
        final FlowNetwork<Coordinate> flowNetwork = new FlowNetwork<>(SOURCE, SINK);

        for (final Coordinate walk : input.bobWalks) {
            flowNetwork.increment(SOURCE, walk, 1);
        }

        for (final Coordinate landscape : input.landscapes) {
            flowNetwork.increment(landscape, SINK, 1);
        }

        for (int i = 0, j = 1; j < input.totalBobWalks; i++, j++) {
            final Coordinate fromWalk = input.bobWalks[i];
            final Coordinate intoWalk = input.bobWalks[j];

            for (int k = 0; k < input.totalLandscapes; k++) {
                final Coordinate landscape = input.landscapes[k];
                if (isVisitable(fromWalk, landscape, intoWalk)) {
                    flowNetwork.increment(fromWalk, landscape, 1);
                }
            }
        }

        return flowNetwork;
    }

    private boolean isVisitable(final Coordinate fromWalk, final Coordinate landscape, final Coordinate intoWalk) {
        final double distanceFLI = getDistance(fromWalk, landscape) + getDistance(landscape, intoWalk);
        final double distanceFI = getDistance(fromWalk, intoWalk);
        return distanceFLI <= 2 * distanceFI;
    }

    private double getDistance(final Coordinate first, final Coordinate second) {
        final int dx = Math.abs(first.x - second.x);
        final int dy = Math.abs(first.y - second.y);
        return Math.sqrt(dx * dx + dy * dy);
    }

    private Coordinate[] getRalphWalks(
        final Input input,
        final FlowNetwork<Coordinate> flowNetwork
    ) {
        final LinkedList<Coordinate> ralphWalks = new LinkedList<>();
        for (final Coordinate bobWalk : input.bobWalks) {
            ralphWalks.add(bobWalk);
            for (final Coordinate landscape : flowNetwork.get(bobWalk)) {
                final int capacity = flowNetwork.get(landscape, bobWalk).orElse(0);
                final boolean isVisited = capacity > 0;
                if (isVisited) {
                    ralphWalks.add(landscape);
                }
            }
        }

        return ralphWalks.toArray(new Coordinate[0]);
    }
}

class Coordinate {
    public final String id;
    public final int x;
    public final int y;

    public Coordinate(final String id, final int x, final int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}

class FlowNetwork<V> {
    private final V source;
    private final V sink;
    private final Map<V, Map<V, Integer>> pipes;

    public FlowNetwork(
        final V source,
        final V sink
    ) {
        this.source = source;
        this.sink = sink;
        this.pipes = new HashMap<>();
    }

    public void increment(final V fromVertex, final V intoVertex, final int capacity) {
        pipes
            .computeIfAbsent(fromVertex, k -> new HashMap<>())
            .compute(intoVertex, (k, v) -> v == null ? capacity : capacity + v);
    }

    public Set<V> get(final V fromVertex) {
        return pipes.getOrDefault(fromVertex, Collections.emptyMap()).keySet();
    }

    public Optional<Integer> get(final V fromVertex, final V intoVertex) {
        return Optional.ofNullable(pipes.getOrDefault(fromVertex, Collections.emptyMap()).get(intoVertex));
    }

    public void doMaximumFlow() {
        doMaximumFlowWithEdmondsKarp();
    }

    private void doMaximumFlowWithEdmondsKarp() {
        Collection<V> path;
        while ((path = getShortestPath()) != null) {
            final int bottleneck = getBottleneck(path);
            doAugmentFlow(path, bottleneck);
        }
    }

    private Collection<V> getShortestPath() {
        return getShortestPathWithBreadthFirstSearch();
    }

    private Collection<V> getShortestPathWithBreadthFirstSearch() {
        final Set<V> visitedVertices = new HashSet<>();
        final Queue<V> pendingVertices = new LinkedList<>();
        final Map<V, V> previousVertices = new HashMap<>();

        visitedVertices.add(source);
        pendingVertices.add(source);
        previousVertices.put(source, null);

        while (!pendingVertices.isEmpty()) {
            final V vertex = pendingVertices.remove();
            if (vertex == sink) {
                return getPath(previousVertices);
            }

            for (final V nextVertex : get(vertex)) {
                final boolean isVisited = visitedVertices.contains(nextVertex);
                if (isVisited) continue;

                final boolean hasPositiveFlow = get(vertex, nextVertex).orElse(0) > 0;
                if (!hasPositiveFlow) continue;

                visitedVertices.add(nextVertex);
                pendingVertices.add(nextVertex);
                previousVertices.put(nextVertex, vertex);
            }
        }

        return null;
    }

    private Collection<V> getPath(final Map<V, V> previousVertices) {
        final LinkedList<V> path = new LinkedList<>();
        for (V currentVertex = sink; currentVertex != null; currentVertex = previousVertices.get(currentVertex)) {
            path.addFirst(currentVertex);
        }
        return path;
    }

    private int getBottleneck(final Collection<V> path) {
        int bottleneck = Integer.MAX_VALUE;

        final Iterator<V> it = path.iterator();
        V from, into = it.next();
        while (it.hasNext()) {
            from = into;
            into = it.next();
            final int capacity = get(from, into).orElse(0);
            bottleneck = Math.min(bottleneck, capacity);
        }

        return bottleneck;
    }

    private void doAugmentFlow(final Collection<V> path, final int flow) {
        final Iterator<V> it = path.iterator();
        V from, into = it.next();
        while (it.hasNext()) {
            from = into;
            into = it.next();
            increment(from, into, -flow);
            increment(into, from, +flow);
        }
    }
}
