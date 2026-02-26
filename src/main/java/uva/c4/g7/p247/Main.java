package uva.c4.g7.p247;

import java.util.*;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int testID = 0;

        while (true) {
            testID++;

            int totalPeople = in.nextInt();
            int totalCall = in.nextInt();

            if (totalPeople == 0 && totalCall == 0) break;
            if (testID > 1) System.out.println();

            String[][] calls = new String[totalCall][];
            for (int i = 0; i < totalCall; i++) {
                String people1 = in.next();
                String people2 = in.next();
                String[] call = new String[]{people1, people2};
                calls[i] = call;
            }

            Solution solution = new Solution(totalPeople, totalCall, calls);
            List<List<String>> circles = solution.getCircles();

            System.out.format("Calling circles for data set %d:\n", testID);
            circles.forEach(circle -> {
                Iterator<String> circleIt = circle.iterator();
                while (circleIt.hasNext()) {
                    String people = circleIt.next();
                    if (circleIt.hasNext()) System.out.format("%s, ", people);
                    else System.out.println(people);
                }
            });
        }

    }
}

class Solution {
    private final int totalPeople;
    private final int totalCall;
    private final String[][] calls;

    private final Set<String> vertices;
    private final Map<String, Set<String>> graph;
    private final Map<String, Set<String>> transposeGraph;

    public Solution(int totalPeople, int totalCall, String[][] calls) {
        this.totalPeople = totalPeople;
        this.totalCall = totalCall;
        this.calls = calls;

        this.vertices = getVertices(calls);
        this.graph = createGraph(calls);
        this.transposeGraph = createTransposeGraph(calls);
    }

    public List<List<String>> getCircles() {
        Set<String> visited = new TreeSet<>();
        LinkedList<String> sequence = new LinkedList<>();
        vertices.forEach(vertex -> dfsWithState(graph, vertex, visited, sequence));

        List<List<String>> circles = new LinkedList<>();

        visited.clear();
        sequence.forEach(vertex -> {
            if (visited.contains(vertex)) return;
            LinkedList<String> circle = new LinkedList<>();
            circles.add(circle);
            dfsWithState(transposeGraph, vertex, visited, circle);
        });

        return circles;
    }

    private Set<String> getVertices(String[][] edges) {
        Set<String> vertices = new TreeSet<>();
        for (String[] edge : edges) {
            String vertex1 = edge[0];
            String vertex2 = edge[1];
            vertices.add(vertex1);
            vertices.add(vertex2);
        }

        return vertices;
    }

    private Map<String, Set<String>> createGraph(String[][] edges) {
        Map<String, Set<String>> graph = new TreeMap<>();
        for (String[] edge : edges) {
            String vertex1 = edge[0];
            String vertex2 = edge[1];
            graph.computeIfAbsent(vertex1, k -> new TreeSet<>()).add(vertex2);
        }
        return graph;
    }

    private Map<String, Set<String>> createTransposeGraph(String[][] edges) {
        Map<String, Set<String>> graph = new TreeMap<>();
        for (String[] edge : edges) {
            String vertex1 = edge[0];
            String vertex2 = edge[1];
            graph.computeIfAbsent(vertex2, k -> new TreeSet<>()).add(vertex1);
        }

        return graph;
    }

    private void dfsWithState(
            Map<String, Set<String>> graph,
            String vertex,
            Set<String> visited,
            LinkedList<String> kosarajuSequence
    ) {
        if (visited.contains(vertex)) return;

        visited.add(vertex);
        Set<String> neighbors = graph.getOrDefault(vertex, Collections.emptySet());
        neighbors.forEach(nextVertex -> dfsWithState(graph, nextVertex, visited, kosarajuSequence));

        kosarajuSequence.addFirst(vertex);
    }
}
