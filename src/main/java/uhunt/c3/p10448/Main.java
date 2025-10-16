package uhunt.c3.p10448;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 10448 - Unique World
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&category=0&problem=1389&mosmsg=Submission+received+with+ID+30711936
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        final int totalCases = Integer.parseInt(readLine(in));
        for (int i = 0; i < totalCases; i++) {
            final String[] l1 = readSplitLine(in);
            final int totalUnikas = Integer.parseInt(l1[0]);

            final int totalRoads = Integer.parseInt(l1[1]);
            final Road[] roads = new Road[totalRoads];
            for (int j = 0; j < totalRoads; j++) {
                final String[] l2 = readSplitLine(in);
                final int unika1 = Integer.parseInt(l2[0]);
                final int unika2 = Integer.parseInt(l2[1]);
                final int cost = Integer.parseInt(l2[2]);
                final Road road = new Road(unika1, unika2, cost);
                roads[j] = road;
            }

            final int totalQueries = Integer.parseInt(readLine(in));
            final Query[] queries = new Query[totalQueries];
            for (int j = 0; j < totalQueries; j++) {
                final String[] l3 = readSplitLine(in);
                final int fromUnika = Integer.parseInt(l3[0]);
                final int intoUnika = Integer.parseInt(l3[1]);
                final int requiredCost = Integer.parseInt(l3[2]);
                final Query query = new Query(fromUnika, intoUnika, requiredCost);
                queries[j] = query;
            }

            final Input input = new Input(totalUnikas, totalRoads, roads, totalQueries, queries);
            final Output output = process.process(input);

            if (i > 0) out.write('\n');
            for (final Result result : output.results) {
                if (result.isPossible) {
                    out.write(String.format("Yes %d\n", result.minimumTotalRoads));
                } else {
                    out.write("No\n");
                }
            }
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
    public final int totalUnikas;
    public final int totalRoads;
    public final Road[] roads;
    public final int totalQueries;
    public final Query[] queries;

    public Input(
            final int totalUnikas,
            final int totalRoads,
            final Road[] roads,
            final int totalQueries,
            final Query[] queries
    ) {
        this.totalUnikas = totalUnikas;
        this.totalRoads = totalRoads;
        this.roads = roads;
        this.totalQueries = totalQueries;
        this.queries = queries;
    }
}

class Output {
    public final Result[] results;

    public Output(final Result[] results) {
        this.results = results;
    }
}

class Road {
    public final int unika1;
    public final int unika2;
    public final int cost;

    public Road(final int unika1, final int unika2, final int cost) {
        this.unika1 = unika1;
        this.unika2 = unika2;
        this.cost = cost;
    }
}

class Query {
    public final int fromUnika;
    public final int intoUnika;
    public final int requiredCost;

    public Query(
            final int fromUnika,
            final int intoUnika,
            final int requiredCost
    ) {
        this.fromUnika = fromUnika;
        this.intoUnika = intoUnika;
        this.requiredCost = requiredCost;
    }
}

class Result {
    public final boolean isPossible;
    public final int minimumTotalRoads;

    public Result(
            final boolean isPossible,
            final int minimumTotalRoads
    ) {
        this.isPossible = isPossible;
        this.minimumTotalRoads = minimumTotalRoads;
    }
}

class Process {
    private static final Result IMPOSSIBLE = new Result(false, -1);

    public Output process(final Input input) {
        final Graph<Integer, Road> graph = buildGraph(input.roads);
        final List<Result> results = Arrays.stream(input.queries)
                .map(query -> answer(query, graph))
                .collect(Collectors.toList());
        return new Output(results.toArray(new Result[0]));
    }

    private Graph<Integer, Road> buildGraph(final Road[] roads) {
        final Graph<Integer, Road> graph = new Graph<>();
        for (final Road road : roads) {
            graph.add(road.unika1, road.unika2, road);
            graph.add(road.unika2, road.unika1, road);
        }
        return graph;
    }

    private Result answer(final Query query, final Graph<Integer, Road> graph) {
        if (query.fromUnika == query.intoUnika) return IMPOSSIBLE;

        final GraphTraversal traversal = depthFirstSearch(graph, query.fromUnika, query.intoUnika);
        if (traversal == null) return IMPOSSIBLE;

        final int remainingCost = query.requiredCost - traversal.cost;
        if (remainingCost < 0) return IMPOSSIBLE;
        if (remainingCost == 0) return new Result(true, traversal.step);

        final Optional<Integer> roundTripStep = getRoundTripStep(traversal, remainingCost);
        if (!roundTripStep.isPresent()) return IMPOSSIBLE;

        final int step = traversal.step + roundTripStep.get();
        return new Result(true, step);
    }

    private GraphTraversal depthFirstSearch(
            final Graph<Integer, Road> graph,
            final int origin,
            final int destination
    ) {
        final Set<Integer> visited = new HashSet<>();
        visited.add(origin);
        final LinkedList<Road> roads = new LinkedList<>();

        return doDepthFirstSearch(
                graph,
                origin,
                destination,
                0,
                0,
                visited,
                roads
        );
    }

    private GraphTraversal doDepthFirstSearch(
            final Graph<Integer, Road> graph,
            final int origin,
            final int destination,
            final int step,
            final int cost,
            final Set<Integer> visited,
            final LinkedList<Road> roads
    ) {
        if (origin == destination) {
            return new GraphTraversal(
                    new ArrayList<>(roads),
                    step,
                    cost
            );
        }

        for (final Road road : graph.get(origin)) {
            for (final int next : Arrays.asList(road.unika1, road.unika2)) {
                if (visited.contains(next)) continue;

                visited.add(next);
                roads.addLast(road);
                final GraphTraversal traversal = doDepthFirstSearch(
                        graph,
                        next,
                        destination,
                        step + 1,
                        cost + road.cost,
                        visited,
                        roads
                );
                if (traversal != null) return traversal;
                roads.removeLast();
            }
        }

        return null;
    }

    private Optional<Integer> getRoundTripStep(
            final GraphTraversal traversal,
            final int targetCost
    ) {
        final int[] roundTripCosts = getRoundTripCosts(traversal);
        final int[] stepPerCost = new int[targetCost + 1];
        Arrays.fill(stepPerCost, Integer.MAX_VALUE);
        stepPerCost[0] = 0;

        boolean isModified = true;
        while (isModified) {
            isModified = false;
            for (int currentCost = 0; currentCost < targetCost; currentCost++) {
                final int currentStep = stepPerCost[currentCost];
                if (currentStep == Integer.MAX_VALUE) continue;

                for (int roundTripCost : roundTripCosts) {
                    final int nextCost = currentCost + roundTripCost;
                    final int nextStep = currentStep + 2;

                    if (nextCost > targetCost) continue;

                    final int oldStep = stepPerCost[nextCost];
                    if (nextStep >= oldStep) continue;

                    stepPerCost[nextCost] = nextStep;
                    isModified = true;
                }
            }
        }

        final int step = stepPerCost[targetCost];
        return Optional.ofNullable(step == Integer.MAX_VALUE ? null : step);
    }

    private int[] getRoundTripCosts(
            final GraphTraversal traversal
    ) {
        final List<Road> multipleUseRoads = traversal.roads.subList(0, traversal.roads.size() - 1);
        final int[] roundTripCosts = multipleUseRoads.stream()
                .mapToInt(r -> r.cost * 2)
                .distinct()
                .toArray();

        return roundTripCosts;
    }
}

class Graph<V, E> {
    private final Map<V, Map<V, E>> edges = new HashMap<>();

    public void add(final V from, final V into, final E edge) {
        edges.computeIfAbsent(from, k -> new HashMap<>()).put(into, edge);
    }

    public Collection<E> get(final V from) {
        return edges.getOrDefault(from, Collections.emptyMap()).values();
    }

    public void remove(final V from, final V into) {
        edges.getOrDefault(from, Collections.emptyMap()).remove(into);
    }
}

class GraphTraversal {
    public final List<Road> roads;
    public final int step;
    public final int cost;

    public GraphTraversal(
            final List<Road> roads,
            final int step,
            final int cost
    ) {
        this.roads = roads;
        this.step = step;
        this.cost = cost;
    }
}
