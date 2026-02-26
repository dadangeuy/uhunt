package uva.c3.g7.p307;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * 307 - Sticks
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=243
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 8);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 8);
        final Process process = new Process();

        while (true) {
            final Input input = new Input();
            input.countPart = Integer.parseInt(in.readLine());
            if (input.countPart == 0) break;

            input.parts = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            final Output output = process.process(input);
            out.write(Integer.toString(output.averageStick));
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int countPart;
    public int[] parts;
}

class Output {
    public int averageStick;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        Arrays.sort(input.parts);
        final boolean[] usedParts = new boolean[input.countPart];

        final int sumPart = Arrays.stream(input.parts).sum();
        final int maxPart = Arrays.stream(input.parts).max().orElse(sumPart);
        final int minPart = Arrays.stream(input.parts).min().orElse(sumPart);

        // backtrack average stick
        for (int averageStick = maxPart; averageStick <= sumPart; averageStick++) {
            // prune: skip non divisible average
            final boolean isDivisible = sumPart % averageStick == 0;
            if (!isDivisible) {
                continue;
            }

            // prune: it's always possible to combine all parts, no need to backtrack
            if (averageStick == sumPart) {
                output.averageStick = sumPart;
                return output;
            }

            // prune: if all parts have equal length, no need to backtrack
            final boolean equalPart = minPart == maxPart;
            if (equalPart) {
                output.averageStick = minPart;
                return output;
            }

            // backtrack stick
            final boolean isValid = backtrackWithArray(
                    input.parts,
                    usedParts,
                    0,
                    averageStick,
                    0,
                    input.parts.length
            );
            if (isValid) {
                output.averageStick = averageStick;
                break;
            }
        }

        return output;
    }

    private boolean backtrackWithArray(
            final int[] parts,
            final boolean[] usedParts,
            final int countUsedParts,
            final int averageStick,
            final int currentStick,
            final int previousPartId
    ) {
        // base case: all parts are being used, valid stick
        if (countUsedParts == parts.length) return true;

        // backtrack combination of parts
        final int desiredPart = averageStick - currentStick;
        for (int partId = previousPartId - 1; partId >= 0; partId--) {
            // prune: skip used parts
            if (usedParts[partId]) continue;

            // prune: skip greater than desired parts
            if (parts[partId] > desiredPart) continue;

            // backtrack combination of smaller parts
            usedParts[partId] = true;
            final boolean isValid = backtrackWithArray(
                    parts,
                    usedParts,
                    countUsedParts + 1,
                    averageStick,
                    parts[partId] == desiredPart ? 0 : currentStick + parts[partId],
                    parts[partId] == desiredPart ? parts.length : partId
            );
            usedParts[partId] = false;

            // if valid, stop backtrack
            if (isValid) return true;

            /**
             * [Prune - Desired Part]
             * Desired part could be build using a part that perfectly matches with the desired part or from combining
             * multiple smaller parts. We should prioritize using desired part and leave the smaller parts for other
             * sticks. If the desired part exists, and we're not able to build the sticks, then it's impossible to
             * construct the sticks.
             */
            if (parts[partId] == desiredPart) return false;

            /**
             * [Prune - Zero Stick]
             * If it's not possible to build the stick from zero with currently available parts, then it's impossible
             * to construct the sticks.
             */
            if (currentStick == 0) return false;

            /**
             * [Prune - Similar Sticks]
             * If it's not possible to build the stick using the current part, we should try it with a different parts.
             */
            while (partId - 1 >= 0 && parts[partId] == parts[partId - 1]) partId--;
        }

        return false;
    }
}
