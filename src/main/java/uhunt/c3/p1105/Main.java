package uhunt.c3.p1105;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1105 - Coffee Central
 * Time limit: 5.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3546
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        for (int caseId = 1; ; caseId++) {
            final Input input = new Input();
            input.caseId = caseId;

            input.horizontalLength = in.nextInt();
            input.verticalLength = in.nextInt();

            input.totalShops = in.nextInt();
            input.totalQueries = in.nextInt();

            final boolean isEOF = input.horizontalLength == 0 &&
                                  input.verticalLength == 0 &&
                                  input.totalShops == 0 &&
                                  input.totalQueries == 0;
            if (isEOF) {
                break;
            }

            input.shops = new Location[input.totalShops];
            for (int i = 0; i < input.totalShops; i++) {
                final Location shop = new Location(
                    in.nextInt(),
                    in.nextInt()
                );

                input.shops[i] = shop;
            }

            input.queries = new Query[input.totalQueries];
            for (int i = 0; i < input.totalQueries; i++) {
                final Query query = new Query();
                query.distance = in.nextInt();
                input.queries[i] = query;
            }

            final Output output = process.process(input);
            out.format("Case %d:\n", output.caseId);
            for (final Answer answer : output.answers) {
                out.format("%d (%d,%d)\n", answer.totalShops, answer.location.x, answer.location.y);
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

/**
 * 2D Array prefix sum on with diamond-shaped area (manhattan distance).
 * The coordinate is rotated by 45 degrees to convert the diamond-shaped area into rectangle.
 * https://cp-algorithms.com/geometry/manhattan-distance.html#rotating-the-points-and-chebyshev-distance
 */
class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.caseId = input.caseId;

        final Location[] shops = rotateShift(input);
        final Grid prefixSum = createPrefixSum(input, shops);

        output.answers = Arrays.stream(input.queries)
            .map(query -> answer(input, query, prefixSum))
            .toArray(Answer[]::new);

        return output;
    }

    private Location[] rotateShift(final Input input) {
        return Arrays.stream(input.shops)
            .map(shop -> shop.rotate().shift(input.horizontalLength))
            .toArray(Location[]::new);
    }

    private Grid createPrefixSum(final Input input, final Location[] locations) {
        final int length = input.horizontalLength + input.verticalLength;
        final Grid prefixSum = new Grid(
            length + 1,
            length + 1
        );

        for (final Location location : locations) {
            prefixSum.set(location.x, location.y, 1);
        }

        for (int x = 1; x <= length; x++) {
            for (int y = 1; y <= length; y++) {
                final int ps1 = prefixSum.get(x - 1, y - 1);
                final int ps2 = prefixSum.get(x - 1, y);
                final int ps3 = prefixSum.get(x, y - 1);
                final int ps4 = prefixSum.get(x, y);

                final int ps = ps4 + ps3 + ps2 - ps1;
                prefixSum.set(x, y, ps);
            }
        }

        return prefixSum;
    }

    private Answer answer(
        final Input input,
        final Query query,
        final Grid prefixSum
    ) {
        final Answer answer = new Answer();
        answer.query = query;

        int maximumTotalShops = -1;
        Location optimalLocation = null;

        for (int x = 1; x <= input.horizontalLength; x++) {
            for (int y = 1; y <= input.verticalLength; y++) {
                final Location location = new Location(x, y);
                final Location rotatedLocation = location.rotate().shift(input.horizontalLength);

                final int x1 = rotatedLocation.x - query.distance;
                final int x2 = rotatedLocation.x + query.distance;

                final int y1 = rotatedLocation.y - query.distance;
                final int y2 = rotatedLocation.y + query.distance;

                final int ps1 = prefixSum.get(x1 - 1, y1 - 1);
                final int ps2 = prefixSum.get(x1 - 1, y2);
                final int ps3 = prefixSum.get(x2, y1 - 1);
                final int ps4 = prefixSum.get(x2, y2);

                final int ps = ps4 - ps2 - ps3 + ps1;

                if (ps > maximumTotalShops) {
                    maximumTotalShops = ps;
                    optimalLocation = location;
                } else if (ps == maximumTotalShops) {
                    if (location.y < optimalLocation.y) {
                        optimalLocation = location;
                    } else if (location.y == optimalLocation.y) {
                        if (location.x < optimalLocation.x) {
                            optimalLocation = location;
                        }
                    }
                }
            }
        }

        answer.totalShops = maximumTotalShops;
        answer.location = optimalLocation;
        return answer;
    }
}

class Input {
    public int caseId;
    public int horizontalLength;
    public int verticalLength;
    public int totalShops;
    public int totalQueries;
    public Location[] shops;
    public Query[] queries;
}

class Output {
    public int caseId;
    public Answer[] answers;
}

class Query {
    public int distance;
}

class Answer {
    public Query query;
    public int totalShops;
    public Location location;
}

class Location {
    public int x;
    public int y;

    public Location(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Location rotate() {
        return new Location(x + y, y - x);
    }

    public Location shift(final int shift) {
        return new Location(x, y + shift);
    }
}

class Grid {
    private final int[][] grid;
    private final int horizontalLength;
    private final int verticalLength;

    public Grid(
        final int horizontalLength,
        final int verticalLength
    ) {
        this.horizontalLength = horizontalLength;
        this.verticalLength = verticalLength;
        this.grid = new int[horizontalLength][verticalLength];
    }

    public void set(final int x, final int y, final int value) {
        grid[x][y] = value;
    }

    public int get(final int x, final int y) {
        final int lx = limit(x, 0, horizontalLength - 1);
        final int ly = limit(y, 0, verticalLength - 1);
        return grid[lx][ly];
    }

    private int limit(final int value, final int min, final int max) {
        return Math.min(max, Math.max(min, value));
    }
}
