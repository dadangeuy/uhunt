package uva.c4.g4.p124;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 124 - Following Orders
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=onlinejudge&page=show_problem&problem=60
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        for (int caseId = 0; ; caseId++) {
            final String vl = in.readLine();
            if (vl == null || vl.isEmpty()) break;
            final String[] variables = vl.split(" ");

            final String cl = in.readLine();
            final String[] constraintLines = cl == null || cl.isEmpty() ? new String[0] : cl.trim().split(" ");
            final String[][] constraints = new String[constraintLines.length / 2][2];
            for (int i = 0, j = 0; i < constraintLines.length; i += 2, j++) {
                constraints[j][0] = constraintLines[i];
                constraints[j][1] = constraintLines[i + 1];
            }

            final Input input = new Input(variables, constraints);
            final Output output = process.process(input);

            if (caseId > 0) out.write('\n');
            for (final String permutation : output.permutations) {
                out.write(permutation);
                out.write('\n');
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Process {
    public Output process(final Input input) {
        final Graph<String, String> dependencyGraph = createDependencyGraph(input.constraints);
        final List<String> permutations = topologicalDfs(input.variables, dependencyGraph);
        permutations.sort(Comparator.naturalOrder());

        final String[] arrayPermutations = permutations.toArray(new String[0]);
        return new Output(arrayPermutations);
    }

    private Graph<String, String> createDependencyGraph(final String[][] constraints) {
        final Graph<String, String> dependencyGraph = new Graph<>();
        for (final String[] constraint : constraints) {
            dependencyGraph.add(constraint[1], constraint[0]);
        }

        return dependencyGraph;
    }

    private List<String> topologicalDfs(
            final String[] variables,
            final Graph<String, String> dependencyGraph
    ) {
        final LinkedList<String> availableVariables = new LinkedList<>(findAvailableVariables(variables, dependencyGraph));
        final LinkedList<String> permutations = new LinkedList<>();
        topologicalDfs(variables.length, dependencyGraph, availableVariables, new LinkedList<>(), permutations);
        return permutations;
    }

    private List<String> findAvailableVariables(
            final String[] variables,
            final Graph<String, String> dependencyGraph
    ) {
        return Arrays.stream(variables)
                .filter(variable -> dependencyGraph.getForwards(variable).isEmpty())
                .collect(Collectors.toList());
    }

    private void topologicalDfs(
            final int remainingVariables,
            final Graph<String, String> dependencyGraph,
            final LinkedList<String> availableVariables,
            final LinkedList<String> permutationBuilder,
            final LinkedList<String> permutations
    ) {
        if (remainingVariables == 0) {
            final String permutation = String.join("", permutationBuilder);
            permutations.add(permutation);
        }

        for (int i = 0; i < availableVariables.size(); i++) {
            // take a variable
            final String variable = availableVariables.removeFirst();
            permutationBuilder.addLast(variable);

            // find the next free variable from its dependents and update the graph
            final List<String> dependents = new LinkedList<>(dependencyGraph.getBackwards(variable));
            dependents.forEach(dependent -> dependencyGraph.remove(dependent, variable));
            final List<String> freeDependents = dependents.stream()
                    .filter(dependent -> dependencyGraph.getForwards(dependent).isEmpty())
                    .collect(Collectors.toList());
            freeDependents.forEach(availableVariables::addLast);

            // dfs to explore this permutation
            topologicalDfs(
                    remainingVariables - 1,
                    dependencyGraph,
                    availableVariables,
                    permutationBuilder,
                    permutations
            );

            // rollback
            freeDependents.forEach(dependent -> availableVariables.removeLast());
            dependents.forEach(dependent -> dependencyGraph.add(dependent, variable));
            availableVariables.addLast(variable);
            permutationBuilder.removeLast();
        }
    }
}

class Input {
    public final String[] variables;
    public final String[][] constraints;

    public Input(final String[] variables, final String[][] constraints) {
        this.variables = variables;
        this.constraints = constraints;
    }
}

class Output {
    public final String[] permutations;

    public Output(final String[] permutations) {
        this.permutations = permutations;
    }
}

class Graph<A, B> {
    public final Map<A, Set<B>> forwards = new HashMap<>();
    public final Map<B, Set<A>> backwards = new HashMap<>();

    public void add(A a, B b) {
        forwards.computeIfAbsent(a, k -> new HashSet<>()).add(b);
        backwards.computeIfAbsent(b, k -> new HashSet<>()).add(a);
    }

    public void remove(A a, B b) {
        forwards.getOrDefault(a, Collections.emptySet()).remove(b);
        backwards.getOrDefault(b, Collections.emptySet()).remove(a);
    }

    public Set<B> getForwards(A a) {
        return forwards.getOrDefault(a, Collections.emptySet());
    }

    public Set<A> getBackwards(B b) {
        return backwards.getOrDefault(b, Collections.emptySet());
    }
}
