package uhunt.c3.p1588;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1588 - Kickdown
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4463
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        while (in.hasNext()) {
            final Input input = new Input();
            input.masterGear = in.next();
            input.drivenGear = in.next();

            final Output output = process.process(input);
            out.println(output.minimumLength);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public String masterGear;
    public String drivenGear;
}

class Output {
    public int minimumLength;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final int maxLength = input.masterGear.length() + input.drivenGear.length();
        final int[] alloy = new int[maxLength];

        int minLength = maxLength;
        for (int masterStartIdx = 0; masterStartIdx + input.masterGear.length() <= maxLength; masterStartIdx++) {
            for (int drivenStartIdx = 0; drivenStartIdx + input.drivenGear.length() <= maxLength; drivenStartIdx++) {
                Arrays.fill(alloy, 0);
                fill(alloy, masterStartIdx, input.masterGear);
                fill(alloy, drivenStartIdx, input.drivenGear);

                final boolean isValid = isValid(alloy);
                final int length = getLength(alloy);
                if (isValid && length < minLength) {
                    minLength = length;
                }
            }
        }

        output.minimumLength = minLength;
        return output;
    }

    private void fill(final int[] alloy, final int startIdx, final String gear) {
        for (int i = 0; i < gear.length(); i++) {
            final int height = gear.charAt(i) - '0';
            alloy[startIdx + i] += height;
        }
    }

    private int getLength(final int[] alloy) {
        int first = 0, last = alloy.length - 1;
        while (alloy[first] == 0) first++;
        while (alloy[last] == 0) last--;
        return last - first + 1;
    }

    private boolean isValid(final int[] alloy) {
        for (final int height : alloy) {
            if (height > 3) {
                return false;
            }
        }
        return true;
    }
}
