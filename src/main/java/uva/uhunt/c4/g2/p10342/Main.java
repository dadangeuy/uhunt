package uva.uhunt.c4.g2.p10342;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

/**
 * 10342 - Always Late
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1283
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        for (int dataset = 1; in.hasNextInt(); dataset++) {
            final Input input = new Input();

            input.dataset = dataset;
            input.totalJunctions = in.nextInt();
            input.totalRoads = in.nextInt();
            input.roads = new Road[input.totalRoads];

            for (int i = 0; i < input.totalRoads; i++) {
                final Road road = new Road();
                input.roads[i] = road;
                road.junction1 = in.nextInt();
                road.junction2 = in.nextInt();
                road.length = in.nextInt();
            }

            input.totalQueries = in.nextInt();
            input.queries = new Query[input.totalQueries];

            for (int i = 0; i < input.totalQueries; i++) {
                final Query query = new Query();
                input.queries[i] = query;
                query.source = in.nextInt();
                query.destination = in.nextInt();
            }

            final Output output = process.process(input);
            out.format("Set #%d\n", output.dataset);
            for (final Answer answer : output.answers) {
                if (answer.isSolvable) {
                    out.println(answer.length);
                } else {
                    out.println('?');
                }
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int dataset;
    public int totalJunctions;
    public int totalRoads;
    public Road[] roads;
    public int totalQueries;
    public Query[] queries;
}

class Output {
    public int dataset;
    public Answer[] answers;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.dataset = input.dataset;

        final Integer[][] paths = getAllPairsShortestPaths(input.totalJunctions, input.roads);

        output.answers = Arrays.stream(input.queries)
            .map(query -> {
                final Answer answer = new Answer();
                answer.query = query;

                final Optional<Integer> path = Optional.ofNullable(paths[query.source][query.destination]);
                answer.isSolvable = path.isPresent();
                answer.length = path.orElse(-1);

                return answer;
            })
            .toArray(Answer[]::new);

        return output;
    }

    private Integer[][] getAllPairsShortestPaths(final int totalJunctions, final Road[] roads) {
        final Integer[][] distances1 = new Integer[totalJunctions][totalJunctions];
        final Integer[][] distances2 = new Integer[totalJunctions][totalJunctions];

        for (int junction = 0; junction < totalJunctions; junction++) {
            distances1[junction][junction] = 0;
        }

        for (final Road road : roads) {
            distances1[road.junction1][road.junction2] = road.length;
            distances1[road.junction2][road.junction1] = road.length;
        }

        for (int alternative = 0; alternative < totalJunctions; alternative++) {
            for (int origin = 0; origin < totalJunctions; origin++) {
                for (int destination = 0; destination < totalJunctions; destination++) {
                    final Integer candidate = add(distances1[origin][alternative], distances1[alternative][destination]);
                    if (candidate == null) continue;
                    distances1[origin][destination] = min(distances1[origin][destination], candidate);
                }
            }
        }

        for (int origin = 0; origin < totalJunctions; origin++) {
            for (int destination = 0; destination < totalJunctions; destination++) {
                for (final Road road : roads) {
                    final Integer candidate1 = add(add(distances1[origin][road.junction1], road.length), distances1[road.junction2][destination]);
                    final Integer candidate2 = add(add(distances1[origin][road.junction2], road.length), distances1[road.junction1][destination]);
                    for (final Integer candidate : Arrays.asList(candidate1, candidate2)) {
                        if (candidate == null) continue;
                        if (candidate <= distances1[origin][destination]) continue;
                        distances2[origin][destination] = min(distances2[origin][destination], candidate);
                    }
                }
            }
        }

        return distances2;
    }

    private Integer add(final Integer first, final Integer second) {
        if (first == null) return null;
        if (second == null) return null;
        return first + second;
    }

    private Integer min(final Integer first, final Integer second) {
        if (first == null && second == null) return null;
        if (first == null) return second;
        if (second == null) return first;
        return Math.min(first, second);
    }
}

class Road {
    public int junction1;
    public int junction2;
    public int length;
}

class Query {
    public int source;
    public int destination;
}

class Answer {
    public Query query;
    public boolean isSolvable;
    public int length;
}
