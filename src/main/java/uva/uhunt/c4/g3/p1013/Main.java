package uva.uhunt.c4.g3.p1013;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 * 1013 - Island Hopping
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3454
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        for (int group = 1; ; group++) {
            final Input input = new Input();

            input.group = group;
            input.totalIslands = in.nextInt();
            input.islands = new Island[input.totalIslands];

            for (int islandIdx = 0; islandIdx < input.totalIslands; islandIdx++) {
                final Coordinate coordinate = new Coordinate();
                coordinate.x = in.nextInt();
                coordinate.y = in.nextInt();

                final Island island = new Island();
                island.router = coordinate;
                island.totalPopulations = in.nextInt();

                input.islands[islandIdx] = island;
            }

            if (input.isEOF()) {
                break;
            }

            final Output output = process.process(input);
            out.write(String.format("Island Group: %d Average %.2f\n\n", output.group, output.average));
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int group;
    public int totalIslands;
    public Island[] islands;

    public boolean isEOF() {
        return totalIslands == 0;
    }
}

class Output {
    public int group;
    public double average;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final List<Distance> distances = new ArrayList<>(input.totalIslands * input.totalIslands);
        for (int i = 0; i < input.totalIslands; i++) {
            for (int j = i + 1; j < input.totalIslands; j++) {
                final Distance distance = new Distance();
                distance.firstIsland = input.islands[i];
                distance.secondIsland = input.islands[j];
                distances.add(distance);
            }
        }

        distances.sort(Distance.ORDER_BY_DISTANCE);

        final DisjointSet<Island> set = new DisjointSet<>();
        final Graph<Island, Distance> graph = new Graph<>();

        for (final Distance distance : distances) {
            final boolean isConnected = set.find(distance.firstIsland) == set.find(distance.secondIsland);
            if (isConnected) {
                continue;
            }

            set.union(distance.firstIsland, distance.secondIsland);
            graph.addBi(distance.firstIsland, distance.secondIsland, distance);
        }

        final double averageConnectionTime = getAverageConnectionTime(graph, input.islands);

        output.group = input.group;
        output.average = averageConnectionTime;
        return output;
    }

    private double getAverageConnectionTime(final Graph<Island, Distance> graph, final Island[] islands) {
        final double connectionTime = Arrays.stream(islands)
            .mapToDouble(island -> island.totalPopulations * getMaximumDistance(graph, island, islands[0]))
            .sum();
        final int totalPopulations = Arrays.stream(islands)
            .mapToInt(i -> i.totalPopulations)
            .sum();
        return connectionTime / totalPopulations;
    }

    private double getMaximumDistance(final Graph<Island, Distance> graph, final Island origin, final Island destination) {
        final Set<Island> visitedSet = new HashSet<>();
        final Queue<Island> islandQueue = new LinkedList<>();
        final Queue<Double> maximumDistanceQueue = new LinkedList<>();

        visitedSet.add(origin);
        islandQueue.add(origin);
        maximumDistanceQueue.add(0d);

        while (!islandQueue.isEmpty()) {
            final Island island = islandQueue.remove();
            final double maximumDistance = maximumDistanceQueue.remove();

            if (island == destination) {
                return maximumDistance;
            }

            for (final Island nextIsland : graph.getVertices(island)) {
                if (visitedSet.contains(nextIsland)) {
                    continue;
                }

                final Distance distance = graph.getEdge(island, nextIsland);
                final double nextMaximumDistance = Math.max(maximumDistance, distance.getDistance());

                visitedSet.add(nextIsland);
                islandQueue.add(nextIsland);
                maximumDistanceQueue.add(nextMaximumDistance);
            }
        }

        throw new NullPointerException();
    }
}

class Distance {
    public static final Comparator<Distance> ORDER_BY_DISTANCE = Comparator.comparingDouble(Distance::getDistance);
    public Island firstIsland;
    public Island secondIsland;

    public double getDistance() {
        final int dx = Math.abs(firstIsland.router.x - secondIsland.router.x);
        final int dy = Math.abs(firstIsland.router.y - secondIsland.router.y);
        return Math.sqrt(dx * dx + dy * dy);
    }
}

class Island {
    public Coordinate router;
    public int totalPopulations;
}

class Coordinate {
    public int x;
    public int y;
}

class DisjointSet<V> {
    public Map<V, V> parents = new HashMap<>();

    public V find(final V child) {
        final V parent = parents.getOrDefault(child, child);
        if (parent == child) {
            return parent;
        } else {
            final V grandparent = find(parent);
            parents.put(child, grandparent);
            return grandparent;
        }
    }

    public void union(final V first, final V second) {
        final V firstParent = find(first);
        final V secondParent = find(second);
        parents.put(secondParent, firstParent);
    }
}

class Graph<V, E> {
    public Map<V, Map<V, E>> edges = new HashMap<>();

    public void addBi(final V first, final V second, final E edge) {
        addUni(first, second, edge);
        addUni(second, first, edge);
    }

    public void addUni(final V from, final V into, final E edge) {
        edges.computeIfAbsent(from, k -> new HashMap<>()).put(into, edge);
    }

    public Collection<V> getVertices(final V from) {
        return edges.getOrDefault(from, Collections.emptyMap()).keySet();
    }

    public E getEdge(final V from, final V into) {
        return edges.getOrDefault(from, Collections.emptyMap()).getOrDefault(into, null);
    }
}
