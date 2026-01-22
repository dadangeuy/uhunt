package uhunt.c4.g5.p10505;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 * 10505 - Montesco vs Capuleto
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1446
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));
        final Process process = new Process();

        final int totalCases = in.nextInt();
        for (int i = 0; i < totalCases; i++) {
            final int totalPeoples = in.nextInt();
            final int[][] enemiesPerPeople = new int[totalPeoples][];
            for (int j = 0; j < totalPeoples; j++) {
                final int totalEnemies = in.nextInt();
                final int[] enemies = new int[totalEnemies];
                for (int k = 0; k < totalEnemies; k++) {
                    enemies[k] = in.nextInt() - 1;
                }
                enemiesPerPeople[j] = enemies;
            }

            final Input input = new Input(totalPeoples, enemiesPerPeople);
            final Output output = process.process(input);

            out.println(output.maxTotalInvitedPeoples);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public final int totalPeoples;
    public final int[][] enemiesPerPeople;

    public Input(final int totalPeoples, final int[][] enemiesPerPeople) {
        this.totalPeoples = totalPeoples;
        this.enemiesPerPeople = enemiesPerPeople;
    }
}

class Output {
    public final int maxTotalInvitedPeoples;

    public Output(final int maxTotalInvitedPeoples) {
        this.maxTotalInvitedPeoples = maxTotalInvitedPeoples;
    }
}

class Process {
    public Output process(final Input input) {
        final Graph<Integer, Integer> enemyGraph = new Graph<>();
        for (int people = 0; people < input.totalPeoples; people++) {
            for (int enemy : input.enemiesPerPeople[people]) {
                final boolean validEnemy = 0 <= enemy && enemy < input.totalPeoples;
                if (!validEnemy) continue;

                enemyGraph.add(people, enemy);
                enemyGraph.add(enemy, people);
            }
        }

        final List<Set<Integer>> groups = new ArrayList<>();
        final Set<Integer> groupedPeoples = new HashSet<>();
        for (int people = 0; people < input.totalPeoples; people++) {
            if (!groupedPeoples.contains(people)) {
                final Set<Integer> group = bfs(enemyGraph, people);
                groups.add(group);
                groupedPeoples.addAll(group);
            }
        }

        final LinkedList<Set<Integer>> factions = new LinkedList<>();
        for (final Set<Integer> group : groups) {
            final Set<Integer> faction = findLargestFaction(enemyGraph, group);
            factions.add(faction);
        }

        final int totalInvitedPeoples = factions.stream().mapToInt(Set::size).sum();
        return new Output(totalInvitedPeoples);
    }

    private Set<Integer> bfs(final Graph<Integer, Integer> graph, final int startVertex) {
        final Set<Integer> visitedVertices = new HashSet<>();

        final Queue<Integer> nextVertices = new LinkedList<>();

        nextVertices.add(startVertex);
        visitedVertices.add(startVertex);

        while (!nextVertices.isEmpty()) {
            final int vertex = nextVertices.remove();
            for (final int nextVertex : graph.get(vertex)) {
                if (!visitedVertices.contains(nextVertex)) {
                    nextVertices.add(nextVertex);
                    visitedVertices.add(nextVertex);
                }
            }
        }

        return visitedVertices;
    }

    private Set<Integer> findLargestFaction(final Graph<Integer, Integer> graph, final Set<Integer> group) {
        if (group.isEmpty()) {
            return Collections.emptySet();
        }

        final Set<Integer> faction1 = new HashSet<>();
        final Set<Integer> faction2 = new HashSet<>();

        final Queue<Integer> faction1q = new LinkedList<>();
        final Queue<Integer> faction2q = new LinkedList<>();

        final int first = group.iterator().next();
        faction1.add(first);
        faction1q.add(first);

        boolean validFaction = true;

        while (validFaction && (!faction1q.isEmpty() || !faction2q.isEmpty())) {
            while (validFaction && !faction1q.isEmpty()) {
                final int person = faction1q.remove();
                final Set<Integer> enemies = graph.get(person);
                for (final int enemy : enemies) {
                    if (faction1.contains(enemy)) {
                        validFaction = false;
                        break;
                    } else if (faction2.contains(enemy)) {
                        continue;
                    } else {
                        faction2.add(enemy);
                        faction2q.add(enemy);
                    }
                }
            }
            while (validFaction && !faction2q.isEmpty()) {
                final int person = faction2q.remove();
                final Set<Integer> enemies = graph.get(person);
                for (final int enemy : enemies) {
                    if (faction1.contains(enemy)) {
                        continue;
                    } else if (faction2.contains(enemy)) {
                        validFaction = false;
                        break;
                    } else {
                        faction1.add(enemy);
                        faction1q.add(enemy);
                    }
                }
            }
        }

        return validFaction ? faction1.size() >= faction2.size() ? faction1 : faction2 : Collections.emptySet();
    }
}

class Graph<FROM, TO> {
    private final Map<FROM, Set<TO>> forwards = new HashMap<>();

    public void add(FROM from, TO to) {
        forwards.computeIfAbsent(from, k -> new HashSet<>()).add(to);
    }

    public Set<TO> get(final FROM from) {
        return forwards.getOrDefault(from, Collections.emptySet());
    }
}
