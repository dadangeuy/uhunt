package dev.rizaldi.uhunt.c4.p872;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 2 << 16));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 2 << 16));

        int totalCase = in.nextInt();
        in.nextLine();

        for (int i = 0; i < totalCase; i++) {
            in.nextLine();

            String[] rawVariables = in.nextLine().split(" ");
            char[] variables = new char[rawVariables.length];
            for (int j = 0; j < variables.length; j++) {
                char variable = rawVariables[j].charAt(0);
                variables[j] = variable;
            }

            String[] rawConstraints = in.nextLine().split(" ");
            char[][] constraints = new char[rawConstraints.length][];
            for (int j = 0; j < constraints.length; j++) {
                char[] constraint = new char[]{
                    rawConstraints[j].charAt(0),
                    rawConstraints[j].charAt(2)
                };
                constraints[j] = constraint;
            }

            Solution solution = new Solution(variables, constraints);
            char[][] orderings = solution.getAllOrderings();

            if (orderings.length == 0) out.println("NO");
            for (char[] ordering : orderings) {
                for (int j = 0; j < ordering.length - 1; j++) {
                    out.print(ordering[j]);
                    out.print(' ');
                }
                out.println(ordering[ordering.length - 1]);
            }
            if (i < totalCase - 1) out.println();
        }

        in.close();
        out.flush();
        out.close();
    }
}

/**
 * 1) use topological sort + DFS to iterate over variable ordering that satisfy constraint
 * 2) we want to retrieve all possible orderings, so we need to keep track of taken variables in each iteration
 * 3) simulate taking free variable before further DFS, then restore the variable
 * <p>
 * Time Complexity: O(300), maximum possible orderings stated on problem description
 */
class Solution {
    private final char[] variables;
    private final char[][] constraints;

    // topological sort state
    private Map<Character, List<Character>> graph;
    private Map<Character, Integer> inNodes;
    private LinkedHashSet<Character> ordering;
    private LinkedList<char[]> orderings;

    public Solution(char[] variables, char[][] constraints) {
        this.variables = variables;
        this.constraints = constraints;
    }

    public char[][] getAllOrderings() {
        Arrays.sort(variables);

        // initialize state
        graph = buildGraph();
        inNodes = buildInNodes();
        ordering = new LinkedHashSet<>();
        orderings = new LinkedList<>();

        topologicalSort();

        return orderings.toArray(new char[0][]);
    }

    private Map<Character, List<Character>> buildGraph() {
        Map<Character, List<Character>> graph = new HashMap<>(2 * variables.length);
        for (char node : variables) graph.put(node, new LinkedList<>());
        for (char[] edge : constraints) graph.get(edge[0]).add(edge[1]);
        return graph;
    }

    private Map<Character, Integer> buildInNodes() {
        Map<Character, Integer> inNodes = new HashMap<>(2 * variables.length);
        for (char node : variables) inNodes.put(node, 0);
        for (char[] edge : constraints) inNodes.computeIfPresent(edge[1], (k, v) -> v + 1);
        return inNodes;
    }

    private void topologicalSort() {
        boolean complete = ordering.size() == variables.length;
        if (complete) {
            orderings.add(toArray(ordering));
            return;
        }

        for (char node : variables) {
            boolean free = inNodes.get(node) == 0;
            if (!free) continue;

            boolean taken = ordering.contains(node);
            if (taken) continue;

            // take node
            ordering.add(node);
            for (char childNode : graph.get(node)) {
                inNodes.computeIfPresent(childNode, (k, v) -> v - 1);
            }
            topologicalSort();

            // restore node
            ordering.remove(node);
            for (char childNode : graph.get(node)) {
                inNodes.computeIfPresent(childNode, (k, v) -> v + 1);
            }
        }
    }

    private char[] toArray(Collection<Character> list) {
        char[] array = new char[list.size()];
        int i = 0;
        for (char value : list) array[i++] = value;
        return array;
    }
}
