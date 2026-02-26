package uva.c4.g6.p1056;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 1056 - Degrees of Separation
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3497
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        for (int networkId = 1; ; networkId++) {
            final int totalPeoples = in.nextInt();
            final int totalRelationships = in.nextInt();

            final boolean isEOF = totalPeoples == 0 && totalRelationships == 0;
            if (isEOF) break;

            final Relationship[] relationships = new Relationship[totalRelationships];
            for (int i = 0; i < totalRelationships; i++) {
                final String name1 = in.next();
                final String name2 = in.next();
                final Relationship relationship = new Relationship(new String[]{name1, name2});
                relationships[i] = relationship;
            }

            final Input input = new Input(networkId, totalPeoples, totalRelationships, relationships);
            final Output output = process.process(input);

            if (output.isConnected) {
                out.format("Network %d: %d\n\n", output.networkId, output.maximumSeparationDegree);
            } else {
                out.format("Network %d: DISCONNECTED\n\n", output.networkId);
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Relationship {
    public final String[] names;

    public Relationship(final String[] names) {
        this.names = names;
    }
}

class Input {
    public final int networkId;
    public final int totalPeoples;
    public final int totalRelationships;
    public final Relationship[] relationships;

    public Input(final int networkId, final int totalPeoples, final int totalRelationships, final Relationship[] relationships) {
        this.networkId = networkId;
        this.totalPeoples = totalPeoples;
        this.totalRelationships = totalRelationships;
        this.relationships = relationships;
    }
}

class Output {
    public final int networkId;
    public final boolean isConnected;
    public final int maximumSeparationDegree;

    public Output(final int networkId, final boolean isConnected, final int maximumSeparationDegree) {
        this.networkId = networkId;
        this.isConnected = isConnected;
        this.maximumSeparationDegree = maximumSeparationDegree;
    }
}

class Process {
    public Output process(final Input input) {
        final Set<String> names = Arrays.stream(input.relationships)
                .flatMap(r -> Stream.of(r.names))
                .collect(Collectors.toSet());
        final List<Relationship> relationships = Arrays.asList(input.relationships);
        if (names.size() < input.totalPeoples) {
            return new Output(input.networkId, false, -1);
        }

        final Map<String, Map<String, Integer>> distances = getDistancesWithFloydWarshall(names, relationships);
        final boolean isConnected = isConnected(names, distances);
        final int maximumDistance = getMaximumDistance(distances);

        return new Output(input.networkId, isConnected, maximumDistance);
    }

    private Map<String, Map<String, Integer>> getDistancesWithFloydWarshall(
            final Set<String> names,
            final List<Relationship> relationships
    ) {
        final Map<String, Map<String, Integer>> distances = new HashMap<>();

        for (final String name : names) {
            distances
                    .computeIfAbsent(name, k -> new HashMap<>())
                    .put(name, 0);
        }

        for (final Relationship relationship : relationships) {
            distances
                    .computeIfAbsent(relationship.names[0], k -> new HashMap<>())
                    .put(relationship.names[1], 1);
            distances
                    .computeIfAbsent(relationship.names[1], k -> new HashMap<>())
                    .put(relationship.names[0], 1);
        }

        for (final String name3 : names) {
            for (final String name1 : names) {
                for (final String name2 : names) {
                    final int distance12 = distances.getOrDefault(name1, Collections.emptyMap()).getOrDefault(name2, Integer.MAX_VALUE);
                    final int distance13 = distances.getOrDefault(name1, Collections.emptyMap()).getOrDefault(name3, Integer.MAX_VALUE);
                    final int distance32 = distances.getOrDefault(name3, Collections.emptyMap()).getOrDefault(name2, Integer.MAX_VALUE);

                    final boolean hasAlternative = distance13 != Integer.MAX_VALUE && distance32 != Integer.MAX_VALUE;
                    final int alternativeDistance = distance13 + distance32;
                    final boolean hasBetterAlternative = hasAlternative && alternativeDistance < distance12;
                    if (hasBetterAlternative) {
                        distances
                                .computeIfAbsent(name1, k -> new HashMap<>())
                                .put(name2, alternativeDistance);
                    }
                }
            }
        }

        return distances;
    }

    private boolean isConnected(
            final Set<String> names,
            final Map<String, Map<String, Integer>> distances
    ) {
        for (final String name1 : names) {
            for (final String name2 : names) {
                final int distance = distances.getOrDefault(name1, Collections.emptyMap()).getOrDefault(name2, Integer.MAX_VALUE);
                final boolean isConnected = distance != Integer.MAX_VALUE;
                if (!isConnected) return false;
            }
        }
        return true;
    }

    private int getMaximumDistance(
            final Map<String, Map<String, Integer>> distances
    ) {
        return distances.entrySet().stream()
                .flatMap(e -> e.getValue().entrySet().stream())
                .mapToInt(Entry::getValue)
                .max()
                .orElse(0);
    }
}
