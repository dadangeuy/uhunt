package uva.uhunt.c4.g0.p10350;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 10350 - Liftless EME
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1291
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(System.in);
        final PrintWriter out = new PrintWriter(System.out);
        final Process process = new Process();

        while (in.hasNext()) {
            final Input input = new Input();
            input.caseName = in.next();
            input.totalRoofs = in.nextInt();
            input.totalHoles = in.nextInt();
            input.walkTimes = new int[input.totalHoles * (input.totalRoofs - 1)][];
            in.nextLine();
            for (int i = 0; i < input.walkTimes.length; i++) {
                final String line = in.nextLine();
                final int[] row = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
                input.walkTimes[i] = row;
            }

            final Output output = process.process(input);
            out.println(output.caseName);
            out.println(output.minimumTotalTimes);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public String caseName;
    public int totalRoofs;
    public int totalHoles;
    public int[][] walkTimes;
}

class Output {
    public String caseName;
    public int minimumTotalTimes;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.caseName = input.caseName;
        output.minimumTotalTimes = dynamicProgramming(input);
        return output;
    }

    private int dynamicProgramming(final Input input) {
        // total times per roof and hole
        final int[][] dp = new int[input.totalRoofs][input.totalHoles];
        fill(dp, Integer.MAX_VALUE);

        for (int hole = 0; hole < input.totalHoles; hole++) {
            dp[0][hole] = 0;
        }

        for (int roof = 0; roof < input.totalRoofs - 1; roof++) {
            final int upperRoof = roof + 1;
            for (int hole = 0; hole < input.totalHoles; hole++) {
                for (int ladder = 0; ladder < input.totalHoles; ladder++) {
                    final int line = getLine(input, roof, hole);
                    final int walkTime = input.walkTimes[line][ladder];
                    final int climbTime = 2;

                    final int oldTotalTime = dp[upperRoof][ladder];
                    final int newTotalTime = dp[roof][hole] + walkTime + climbTime;
                    if (newTotalTime < oldTotalTime) {
                        dp[upperRoof][ladder] = newTotalTime;
                    }
                }
            }
        }

        return Arrays.stream(dp[input.totalRoofs - 1])
            .min()
            .orElse(Integer.MAX_VALUE);
    }

    private void fill(final int[][] array2d, final int value) {
        for (int[] array1d : array2d) Arrays.fill(array1d, value);
    }

    private int getLine(final Input input, final int roof, final int hole) {
        return roof * input.totalHoles + hole;
    }
}
