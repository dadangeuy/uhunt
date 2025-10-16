package uhunt.c4.p291;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 291 - The House Of Santa Claus
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=227
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        final Process process = new Process();
        final Output output = process.process();
        for (final List<Integer> path : output.sortedPaths) {
            for (int vertex : path) {
                out.write(Integer.toString(vertex));
            }
            out.write('\n');
        }
        out.flush();
        out.close();
    }
}

class Output {
    public final List<List<Integer>> sortedPaths;

    public Output(final List<List<Integer>> sortedPaths) {
        this.sortedPaths = sortedPaths;
    }
}

class Process {
    public Output process() {
        final Graph<Integer> graph = new Graph<>();

        // create house of santa claus graph
        graph.addBidirectional(1, 2);
        graph.addBidirectional(1, 5);
        graph.addBidirectional(1, 3);
        graph.addBidirectional(2, 5);
        graph.addBidirectional(2, 3);
        graph.addBidirectional(5, 3);
        graph.addBidirectional(5, 4);
        graph.addBidirectional(3, 4);

        final int startVertex = 1;
        final boolean[][] visited = new boolean[6][6];
        final LinkedList<Integer> path = new LinkedList<>();
        path.addLast(startVertex);
        final List<List<Integer>> paths = new ArrayList<>();

        dfs(graph, startVertex, visited, path, paths);

        paths.sort(orderByElementAsc(9));

        return new Output(paths);
    }

    private void dfs(
            final Graph<Integer> graph,
            final int vertex,
            final boolean[][] visited,
            final LinkedList<Integer> path,
            final List<List<Integer>> paths
    ) {
        if (path.size() == 9) {
            paths.add(new ArrayList<>(path));
            return;
        }

        for (final int nextVertex : graph.get(vertex)) {
            final boolean isVisitedEdge = visited[vertex][nextVertex];
            if (isVisitedEdge) continue;

            visited[vertex][nextVertex] = true;
            visited[nextVertex][vertex] = true;
            path.addLast(nextVertex);
            dfs(graph, nextVertex, visited, path, paths);
            path.removeLast();
            visited[vertex][nextVertex] = false;
            visited[nextVertex][vertex] = false;
        }
    }

    private Comparator<List<Integer>> orderByElementAsc(final int length) {
        Comparator<List<Integer>> comparator = Comparator.comparingInt(l -> l.get(0));
        for (int i = 1; i < length; i++) {
            final int idx = i;
            comparator = comparator.thenComparingInt(l -> l.get(idx));
        }
        return comparator;
    }
}

class Graph<V> {
    public final Map<V, Set<V>> edges = new HashMap<>();

    public void addBidirectional(final V from, final V into) {
        addUnidirectional(from, into);
        addUnidirectional(into, from);
    }

    public void addUnidirectional(final V from, final V into) {
        edges.computeIfAbsent(from, k -> new HashSet<>()).add(into);
    }

    public Set<V> get(final V from) {
        return edges.getOrDefault(from, Collections.emptySet());
    }
}
