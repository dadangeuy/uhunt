package dev.rizaldi.uhunt.c4.p11686;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 11686 - Pick up sticks
 * Time limit: 5.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=2733
 * Note: unit test fail due to output variance
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        while (true) {
            final String[] l1 = Util.readSplitLine(in);
            final int totalSticks = Integer.parseInt(l1[0]);
            final int totalRelationships = Integer.parseInt(l1[1]);

            if (totalSticks == 0 && totalRelationships == 0) break;

            final Relationship[] relationships = new Relationship[totalRelationships];
            for (int i = 0; i < totalRelationships; i++) {
                final String[] l2 = Util.readSplitLine(in);
                final int top = Integer.parseInt(l2[0]);
                final int bottom = Integer.parseInt(l2[1]);
                final Relationship relationship = new Relationship(top, bottom);
                relationships[i] = relationship;
            }

            final Input input = new Input(totalSticks, totalRelationships, relationships);
            final Output output = process.process(input);

            if (output.permutation.isPresent()) {
                for (final int stick : output.permutation.get()) {
                    out.write(String.format("%d\n", stick));
                }
            } else {
                out.write("IMPOSSIBLE\n");
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Graph<A, B> {
    private final Map<A, Set<B>> forwards = new HashMap<>();
    private final Map<B, Set<A>> backwards = new HashMap<>();

    public void add(final A a, final B b) {
        forwards.computeIfAbsent(a, k -> new HashSet<>()).add(b);
        backwards.computeIfAbsent(b, k -> new HashSet<>()).add(a);
    }

    public void remove(final A a, final B b) {
        forwards.getOrDefault(a, Collections.emptySet()).remove(b);
        backwards.getOrDefault(b, Collections.emptySet()).remove(a);
    }

    public Set<B> getForwards(final A a) {
        return forwards.getOrDefault(a, Collections.emptySet());
    }

    public Set<A> getBackwards(final B b) {
        return backwards.getOrDefault(b, Collections.emptySet());
    }
}

class Relationship {
    public final int top;
    public final int bottom;

    public Relationship(final int top, final int bottom) {
        this.top = top;
        this.bottom = bottom;
    }
}

class Process {
    public Output process(final Input input) {
        // Graph of Bottom -> Top
        final Graph<Integer, Integer> dependencyGraph = new Graph<>();
        for (final Relationship relationship : input.relationships) {
            dependencyGraph.add(relationship.bottom, relationship.top);
        }

        final LinkedList<Integer> availableSticks = new LinkedList<>();
        for (int stick = 1; stick <= input.totalSticks; stick++) {
            final boolean isAvailable = dependencyGraph.getForwards(stick).isEmpty();
            if (isAvailable) {
                availableSticks.addLast(stick);
            }
        }

        final LinkedList<Integer> sequence = new LinkedList<>();
        while (!availableSticks.isEmpty()) {
            final int stick = availableSticks.removeFirst();
            sequence.add(stick);

            final List<Integer> dependents = new LinkedList<>(dependencyGraph.getBackwards(stick));
            for (final int dependent : dependents) {
                dependencyGraph.remove(dependent, stick);
                final boolean isAvailable = dependencyGraph.getForwards(dependent).isEmpty();
                if (isAvailable) {
                    availableSticks.addLast(dependent);
                }
            }
        }

        final boolean isSolvable = sequence.size() == input.totalSticks;
        if (isSolvable) {
            final int[] array = sequence.stream().mapToInt(i -> i).toArray();
            return new Output(Optional.of(array));
        }

        return new Output(Optional.empty());
    }
}

class Input {
    public final int totalSticks;
    public final int totalRelationships;
    public final Relationship[] relationships;

    public Input(final int totalSticks, final int totalRelationships, final Relationship[] relationships) {
        this.totalSticks = totalSticks;
        this.totalRelationships = totalRelationships;
        this.relationships = relationships;
    }
}

class Output {
    public final Optional<int[]> permutation;

    public Output(final Optional<int[]> permutation) {
        this.permutation = permutation;
    }
}

class Util {
    private static final String SEPARATOR = " ";

    public static String[] readSplitLine(final BufferedReader in) throws IOException {
        final String line = readLine(in);
        return line == null ? new String[0] : line.split(SEPARATOR);
    }

    public static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}
