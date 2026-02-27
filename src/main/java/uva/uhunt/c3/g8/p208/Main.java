package uva.uhunt.c3.g8.p208;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

import static java.lang.String.format;

/**
 * 208 - Firetruck
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=144
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);

        final Input input = new Input();
        final Process process = new Process();
        final Output output = new Output();

        for (int i = 1; ; i++) {
            String line = in.readLine();
            if (line == null || line.isEmpty()) break;

            input.fireCorner = Integer.parseInt(line);
            input.adjacentCorners = new LinkedList<>();

            for (line = in.readLine(); !line.equals("0 0"); line = in.readLine()) {
                final String[] lines = line.split(" ");
                final int corner1 = Integer.parseInt(lines[0]);
                final int corner2 = Integer.parseInt(lines[1]);
                input.adjacentCorners.add(new int[]{corner1, corner2});
            }

            process.process(input, output);

            out.write(format("CASE %d:\n", i));
            for (final int[] route : output.routes) {
                for (int j = 0; j < route.length; j++) {
                    if (j == 0) out.write(Integer.toString(route[j]));
                    else out.write(format(" %d", route[j]));
                }
                out.write('\n');
            }
            out.write(format(
                    "There are %d routes from the firestation to streetcorner %d.\n",
                    output.routes.size(),
                    input.fireCorner
            ));
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int fireCorner;
    public List<int[]> adjacentCorners;
}

class Output {
    public List<int[]> routes;
}

class Process {
    private static final int NO_ROUTE = Integer.MAX_VALUE;
    private static final int MAX_VERTEX = 21;
    private static final int FIRE_STATION_CORNER = 1;

    public void process(final Input input, final Output output) {
        final boolean[][] edges = buildEdges(input.adjacentCorners);
        final LinkedList<int[]> routes = new LinkedList<>();

        dfsFromFireStation(edges, input.fireCorner, routes);

        output.routes = routes;
    }

    private boolean[][] buildEdges(final List<int[]> adjacentCorners) {
        final boolean[][] edges = new boolean[MAX_VERTEX + 1][MAX_VERTEX + 1];
        for (final int[] adjacentCorner : adjacentCorners) {
            edges[adjacentCorner[0]][adjacentCorner[1]] = true;
            edges[adjacentCorner[1]][adjacentCorner[0]] = true;
        }

        return edges;
    }

    private void dfsFromFireStation(
            // immutable input
            final boolean[][] edges,
            final int targetVertex,

            // output
            final LinkedList<int[]> routes
    ) {
        final int[][] distances = getDistancesWithFloydWarshall(edges);
        final LinkedList<Integer> currentRoute = new LinkedList<>();
        final boolean[] visitedVertices = new boolean[MAX_VERTEX + 1];

        // pre-dfs: mark visited
        visitedVertices[FIRE_STATION_CORNER] = true;
        currentRoute.addLast(FIRE_STATION_CORNER);

        // dfs: explore fire station
        dfs(
                edges,
                targetVertex,
                distances,
                visitedVertices,
                FIRE_STATION_CORNER,
                currentRoute,
                routes
        );

        // post-dfs: unmark visited
        visitedVertices[FIRE_STATION_CORNER] = false;
        currentRoute.removeLast();
    }

    private void dfs(
            // immutable input
            final boolean[][] edges,
            final int targetVertex,
            final int[][] distances,

            // mutable input
            final boolean[] visitedVertices,
            final int currentVertex,
            final LinkedList<Integer> currentRoute,

            // output
            final LinkedList<int[]> routes
    ) {
        // base case: stop at target vertex
        final boolean finished = currentVertex == targetVertex;
        if (finished) {
            final int[] route = currentRoute.stream().mapToInt(Integer::intValue).toArray();
            routes.addLast(route);
        }

        // base case: no route from current to target vertex
        final boolean noRoute = distances[currentVertex][targetVertex] == NO_ROUTE;
        if (noRoute) {
            return;
        }

        // dfs: explore directly connected vertices
        for (int nextVertex = 1; nextVertex <= MAX_VERTEX; nextVertex++) {
            // prune: skip non connected vertex
            final boolean connected = edges[currentVertex][nextVertex];
            if (!connected) continue;

            // prune: skip visited vertices
            final boolean visited = visitedVertices[nextVertex];
            if (visited) continue;

            // pre-dfs: mark visited
            visitedVertices[nextVertex] = true;
            currentRoute.addLast(nextVertex);

            // dfs: explore next vertex
            dfs(
                    edges,
                    targetVertex,
                    distances,
                    visitedVertices,
                    nextVertex,
                    currentRoute,
                    routes
            );

            // post-dfs: unmark visited
            visitedVertices[nextVertex] = false;
            currentRoute.removeLast();
        }
    }

    private int[][] getDistancesWithFloydWarshall(boolean[][] edges) {
        final int[][] distances = new int[edges.length][edges.length];

        // marked vertices as non-connected
        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < edges.length; j++) {
                distances[i][j] = NO_ROUTE;
            }
        }

        // fill travel to current vertex
        for (int vertex = 0; vertex < edges.length; vertex++) {
            distances[vertex][vertex] = 0;
        }

        // fill travel to directly connected vertex
        for (int start = 0; start < edges.length; start++) {
            for (int end = 0; end < edges.length; end++) {
                if (!edges[start][end]) continue;
                distances[start][end] = 1;
            }
        }

        // find minimum distance between current distance vs alternate distance
        for (int middle = 0; middle < edges.length; middle++) {
            for (int start = 0; start < edges.length; start++) {
                for (int end = 0; end < edges.length; end++) {
                    if (distances[start][middle] == NO_ROUTE || distances[middle][end] == NO_ROUTE) continue;

                    final int currentDistance = distances[start][end];
                    final int alternateDistance = distances[start][middle] + distances[middle][end];
                    distances[start][end] = Math.min(currentDistance, alternateDistance);
                }
            }
        }

        return distances;
    }
}
