package dev.rizaldi.uhunt.c3.p307;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.TreeMap;

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
            input.countStick = Integer.parseInt(in.readLine());
            if (input.countStick == 0) break;

            input.sticks = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            final Output output = process.process(input);
            out.write(Integer.toString(output.averageOriginalStick));
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int countStick;
    public int[] sticks;
}

class Output {
    public int averageOriginalStick;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final int sumStick = Arrays.stream(input.sticks).sum();
        final int maxStick = Arrays.stream(input.sticks).max().orElse(sumStick);
        final TreeMap<Integer, Integer> countPerStick = buildCountPerValue(input.sticks);

        for (int countOriginalStick = sumStick / maxStick; countOriginalStick >= 1; countOriginalStick--) {
            final boolean isDivisible = sumStick % countOriginalStick == 0;
            if (!isDivisible) {
                continue;
            }

            final int averageOriginalStick = sumStick / countOriginalStick;
            if (averageOriginalStick < maxStick) {
                continue;
            }
            if (countOriginalStick == 1) {
                output.averageOriginalStick = sumStick;
                return output;
            }

            final int equalCount = countPerStick.getOrDefault(averageOriginalStick, 0);
            if (equalCount > 0) countPerStick.remove(averageOriginalStick);

            final boolean isValid = backtrack(
                    countPerStick,
                    averageOriginalStick,
                    0,
                    averageOriginalStick
            );

            if (equalCount > 0) countPerStick.put(averageOriginalStick, equalCount);

            if (isValid) {
                output.averageOriginalStick = averageOriginalStick;
                break;
            }
        }

        return output;
    }

    private TreeMap<Integer, Integer> buildCountPerValue(int[] array) {
        final TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int value : array) increment(map, value);
        return map;
    }

    private void increment(final TreeMap<Integer, Integer> map, int key) {
        map.compute(key, (k, v) -> v == null ? 1 : v + 1);
    }

    private void decrement(final TreeMap<Integer, Integer> map, int key) {
        map.computeIfPresent(key, (k, v) -> v - 1 == 0 ? null : v - 1);
    }

    private boolean backtrack(
            final TreeMap<Integer, Integer> countPerStick,
            final int averageOriginalStick,
            final int originalStick,
            final int maximumStick
    ) {
        if (countPerStick.isEmpty()) return true;

        for (
                Integer i = countPerStick.floorKey(Math.min(maximumStick, averageOriginalStick - originalStick));
                i != null;
                i = countPerStick.lowerKey(i)
        ) {
            decrement(countPerStick, i);
            final boolean isValid = backtrack(
                    countPerStick,
                    averageOriginalStick,
                    (originalStick + i) % averageOriginalStick,
                    (originalStick + i) % averageOriginalStick == 0 ? averageOriginalStick : i
            );
            increment(countPerStick, i);

            if (isValid) return true;

            if (originalStick == 0) return false;
        }

        return false;
    }
}
