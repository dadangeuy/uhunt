package uva.c4.g9.p11749;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * 11749 - Poor Trade Advisor
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2849#google_vignette
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        String line;
        String[] lines;
        for (
                line = in.readLine();
                !line.equals("0 0");
                line = in.readLine()
        ) {
            lines = line.split(" ");

            final Input input = new Input();
            input.totalCities = Integer.parseInt(lines[0]);
            input.totalRoads = Integer.parseInt(lines[1]);
            input.roads = new Road[input.totalRoads];

            for (int roadIdx = 0; roadIdx < input.totalRoads; roadIdx++) {
                line = in.readLine();
                lines = line.split(" ");

                final Road road = new Road();
                road.city1 = Integer.parseInt(lines[0]);
                road.city2 = Integer.parseInt(lines[1]);
                road.profit = Integer.parseInt(lines[2]);
                input.roads[roadIdx] = road;
            }

            final Output output = process.process(input);
            out.write(Integer.toString(output.totalCitiesWithHighestAverageProfit));
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalCities;
    public int totalRoads;
    public Road[] roads;
}

class Output {
    public int totalCitiesWithHighestAverageProfit;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final int maxProfit = getMaxProfit(input.roads);
        final Road[] maxProfitRoads = getRoadsWithProfit(input.roads, maxProfit);
        final Integer[][] profitGraph = buildProfitGraph(maxProfitRoads, input.totalCities);
        output.totalCitiesWithHighestAverageProfit = floodFillAllVertices(profitGraph);

        return output;
    }

    private int getMaxProfit(final Road[] roads) {
        return Arrays.stream(roads)
                .mapToInt(r -> r.profit)
                .max()
                .orElseThrow(NullPointerException::new);
    }

    private Road[] getRoadsWithProfit(final Road[] roads, final int profit) {
        return Arrays.stream(roads)
                .filter(r -> r.profit == profit)
                .toArray(Road[]::new);
    }

    private Integer[][] buildProfitGraph(final Road[] roads, final int totalCities) {
        final Integer[][] profitPerRoad = new Integer[totalCities + 1][totalCities + 1];
        for (final Road road : roads) {
            profitPerRoad[road.city1][road.city2] = road.profit;
            profitPerRoad[road.city2][road.city1] = road.profit;
        }
        return profitPerRoad;
    }

    private int floodFillAllVertices(final Integer[][] graph) {
        final boolean[] visitedVertices = new boolean[graph.length];
        int maxCount = 0;

        for (int vertex = 1; vertex < graph.length; vertex++) {
            final int count = floodFill(graph, vertex, visitedVertices);
            maxCount = Math.max(maxCount, count);
        }

        return maxCount;
    }

    private int floodFill(
            // immutable input
            final Integer[][] graph,
            final int initialVertex,

            // mutable input
            final boolean[] visitedVertices
    ) {
        if (visitedVertices[initialVertex]) {
            return 0;
        }

        final LinkedList<Integer> nextVertices = new LinkedList<>();
        nextVertices.addLast(initialVertex);
        visitedVertices[initialVertex] = true;
        int count = 1;

        while (!nextVertices.isEmpty()) {
            final int vertex = nextVertices.removeFirst();
            for (int nextVertex = 1; nextVertex < graph.length; nextVertex++) {
                if (visitedVertices[nextVertex]) continue;
                if (graph[vertex][nextVertex] == null) continue;

                nextVertices.addLast(nextVertex);
                visitedVertices[nextVertex] = true;
                count++;
            }
        }

        return count;
    }
}

class Road {
    public int city1;
    public int city2;
    public int profit;
}
