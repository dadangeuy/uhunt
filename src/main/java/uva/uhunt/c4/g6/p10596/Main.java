package uva.uhunt.c4.g6.p10596;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * 10596 - Morning Walk
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1537
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        while (in.hasNextInt()) {
            final Input input = new Input();
            input.totalIntersections = in.nextInt();
            input.totalRoads = in.nextInt();
            input.roads = new int[input.totalRoads][];
            for (int i = 0; i < input.totalRoads; i++) {
                final int[] road = new int[2];
                road[0] = in.nextInt();
                road[1] = in.nextInt();
                input.roads[i] = road;
            }

            final Output output = process.process(input);
            if (output.isPossible) out.write("Possible\n");
            else out.write("Not Possible\n");
        }


        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalIntersections;
    public int totalRoads;
    public int[][] roads;
}

class Output {
    public boolean isPossible;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final int maxVertex = input.totalIntersections - 1;
        final int[][] edges = input.roads;
        output.isPossible = isEulerianCycle(maxVertex, edges);

        return output;
    }

    private boolean isEulerianCycle(final int maxVertex, final int[][] edges) {
        return isConnectedGraph(maxVertex, edges) && hasNoVerticesWithOddDegrees(maxVertex, edges);
    }

    private boolean isConnectedGraph(final int maxVertex, final int[][] edges) {
        final DisjointSet connectedComponents = getConnectedComponents(maxVertex, edges);
        final Set<Integer> groups = new HashSet<>();
        for (final int[] edge : edges) {
            for (final int vertex : edge) {
                final int group = connectedComponents.find(vertex);
                groups.add(group);
            }
        }
        return groups.size() == 1;
    }

    private DisjointSet getConnectedComponents(final int maxVertex, final int[][] edges) {
        final DisjointSet set = new DisjointSet(maxVertex);
        for (final int[] edge : edges) set.union(edge[0], edge[1]);
        return set;
    }

    private boolean hasNoVerticesWithOddDegrees(final int maxVertex, final int[][] edges) {
        return getTotalVerticesWithOddDegrees(maxVertex, edges) == 0;
    }

    private int getTotalVerticesWithOddDegrees(final int maxVertex, final int[][] edges) {
        final int[] degreesPerVertex = getDegreesPerVertex(maxVertex, edges);

        return (int) IntStream.rangeClosed(0, maxVertex)
            .map(vertex -> degreesPerVertex[vertex])
            .filter(degrees -> degrees % 2 != 0)
            .count();
    }

    private int[] getDegreesPerVertex(final int maxVertex, final int[][] edges) {
        final int[] degreesPerVertex = new int[maxVertex + 1];
        for (final int[] edge : edges) {
            for (final int vertex : edge) {
                degreesPerVertex[vertex]++;
            }
        }
        return degreesPerVertex;
    }
}

final class DisjointSet {
    private final int[] parents;

    public DisjointSet(final int maxVertex) {
        parents = new int[maxVertex + 1];
        for (int vertex = 0; vertex <= maxVertex; vertex++) parents[vertex] = vertex;
    }

    public int find(final int child) {
        final int parent = parents[child];
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
