package uva.uhunt.c3.g0.p12840;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

/**
 * 12840 - The Archery Puzzle
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4705
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        final Process process = new Process();

        final int totalTestCases = Integer.parseInt(readLine(in));
        for (int i = 0; i < totalTestCases; i++) {
            final Input input = new Input();
            input.caseId = i + 1;

            final String[] l1 = readSplitLine(in);
            input.totalTargets = Integer.parseInt(l1[0]);
            input.expectedTotalPoints = Integer.parseInt(l1[1]);
            input.targets = new int[input.totalTargets];

            final String[] l2 = readSplitLine(in);
            for (int j = 0; j < input.totalTargets; j++) {
                input.targets[j] = Integer.parseInt(l2[j]);
            }

            final Output output = process.process(input);
            out.write(String.format("Case %d: ", output.caseId));
            if (output.isPossible) {
                out.write(String.format("[%d]", output.totalArrows));
                for (final int point : output.points) {
                    out.write(String.format(" %d", point));
                }
                out.write('\n');
            } else {
                out.write("impossible\n");
            }
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String[] readSplitLine(final BufferedReader in) throws IOException {
        final String line = readLine(in);
        return line == null ? null : line.split(" ");
    }

    private static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Input {
    public int caseId;
    public int totalTargets;
    public int[] targets;
    public int expectedTotalPoints;
}

class Output {
    public int caseId;
    public boolean isPossible;
    public int totalArrows;
    public int[] points;
}

class Process {
    public Output process(final Input input) {
        final Progress result = doCoinChangeDynamicProgramming(input);

        final Output output = new Output();
        output.caseId = input.caseId;
        output.isPossible = result != null;
        output.totalArrows = output.isPossible? result.totalArrows : 0;
        output.points = output.isPossible? result.points() : null;

        return output;
    }

    private Progress doCoinChangeDynamicProgramming(final Input input) {
        final Progress[] progressPerTotalPoints = new Progress[input.expectedTotalPoints + 1];
        progressPerTotalPoints[0] = new Progress();

        for (int targetId = 0; targetId < input.totalTargets; targetId++) {
            final int target = input.targets[targetId];

            for (int totalPoints = 0; totalPoints <= input.expectedTotalPoints; totalPoints++) {
                final Progress progress = progressPerTotalPoints[totalPoints];
                if (progress == null) continue;

                final Progress newProgress = progress.next(target);
                if (newProgress.totalPoints > input.expectedTotalPoints) continue;

                final Progress oldProgress = progressPerTotalPoints[newProgress.totalPoints];
                if (oldProgress == null || newProgress.totalArrows <= oldProgress.totalArrows) {
                    progressPerTotalPoints[newProgress.totalPoints] = newProgress;
                }
            }
        }

        return progressPerTotalPoints[input.expectedTotalPoints];
    }
}

class Progress {
    public final Progress previous;
    public final int totalPoints;
    public final int totalArrows;
    public final int target;

    public Progress() {
        this.previous = null;
        this.totalPoints = 0;
        this.totalArrows = 0;
        this.target = 0;
    }

    private Progress(
        final Progress previous,
        final int totalPoints,
        final int totalArrows,
        final int target
    ) {
        this.previous = previous;
        this.totalPoints = totalPoints;
        this.totalArrows = totalArrows;
        this.target = target;
    }

    public Progress next(final int target) {
        return new Progress(
            this,
            totalPoints + target,
            totalArrows + 1,
            target
        );
    }

    public int[] points() {
        final LinkedList<Integer> targets = new LinkedList<>();
        for (Progress progress = this; progress != null; progress = progress.previous) {
            if (progress.target != 0) {
                targets.add(progress.target);
            }
        }
        final int[] points = targets.stream().mapToInt(Integer::intValue).sorted().toArray();
        for (int i = 0, j = points.length - 1; i < j; i++, j--) {
            final int point1 = points[i];
            final int point2 = points[j];
            points[i] = point2;
            points[j] = point1;
        }
        return points;
    }
}
