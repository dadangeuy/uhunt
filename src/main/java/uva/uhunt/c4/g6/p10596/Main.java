package uva.uhunt.c4.g6.p10596;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
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
        output.isPossible = hasEulerianCycle(maxVertex, edges);

        return output;
    }

    private boolean hasEulerianCycle(final int maxVertex, final int[][] edges) {
        return isConnectedGraph(maxVertex, edges) && hasEvenDegreesOnly(maxVertex, edges);
    }

    private boolean isConnectedGraph(final int maxVertex, final int[][] edges) {
        final DisjointSet connectedComponents = getConnectedComponents(maxVertex, edges);
        final int[] degrees = getDegrees(maxVertex, edges);
        final int[] groups = IntStream.rangeClosed(0, maxVertex)
            .filter(vertex -> degrees[vertex] > 0)
            .map(connectedComponents::find)
            .distinct()
            .toArray();
        return groups.length == 1;
    }

    private DisjointSet getConnectedComponents(final int maxVertex, final int[][] edges) {
        final DisjointSet set = new DisjointSet(maxVertex);
        for (final int[] edge : edges) set.union(edge[0], edge[1]);
        return set;
    }

    private boolean hasEvenDegreesOnly(final int maxVertex, final int[][] edges) {
        final int[] degrees = getDegrees(maxVertex, edges);
        return Arrays.stream(degrees).allMatch(degree -> degree % 2 == 0);
    }

    private int[] getDegrees(final int maxVertex, final int[][] edges) {
        final int[] degrees = new int[maxVertex + 1];
        for (final int[] edge : edges) {
            for (final int vertex : edge) {
                degrees[vertex]++;
            }
        }
        return degrees;
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
