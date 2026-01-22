package uhunt.c3.g8.p10128;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 10128 - Queue
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1069#google_vignette
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        final Process process = new Process();
        final int totalTestCases = in.nextInt();
        for (int i = 0; i < totalTestCases; i++) {
            final Input input = new Input();
            input.totalPeoples = in.nextInt();
            input.totalLeftViews = in.nextInt();
            input.totalRightViews = in.nextInt();
            final Output output = process.process(input);
            out.println(output.totalPermutations);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Process {
    private static final long[] FACTORIALS = buildFactorials(13);
    private static final long[][] UP_PERMUTATIONS = buildUpPermutations(13);

    public Output process(final Input input) {
        final Output output = new Output();

        long totalPermutations = 0;
        // pick 1 person as peak, divide left and right. get permutation for each left/right configuration
        for (int left = 0, right = input.totalPeoples - 1; right >= 0; left++, right--) {
            final long leftPermutation = UP_PERMUTATIONS[left][input.totalLeftViews - 1] * combination(input.totalPeoples - 1, left);
            final long rightPermutation = UP_PERMUTATIONS[right][input.totalRightViews - 1] * combination(input.totalPeoples - 1 - left, right);
            final long currentPermutation = leftPermutation * rightPermutation;
            totalPermutations += currentPermutation;
        }
        output.totalPermutations = totalPermutations;

        return output;
    }

    private static long permutation(int n, int r) {
        return FACTORIALS[n] / FACTORIALS[n - r];
    }

    private static long combination(int n, int r) {
        return FACTORIALS[n] / FACTORIALS[r] / FACTORIALS[n - r];
    }

    private static long[] buildFactorials(int max) {
        final long[] factorials = new long[max + 1];
        factorials[0] = 1;
        for (int i = 1; i < factorials.length; i++) factorials[i] = i * factorials[i - 1];
        return factorials;
    }

    private static long[][] buildUpPermutations(int totalPeoples) {
        // dp, total peoples and total views
        long[][] permutations = new long[totalPeoples + 1][totalPeoples + 1];
        permutations[0][0] = 1;

        // for each total peoples
        for (int i = 1; i <= totalPeoples; i++) {
            // for each total views
            for (int j = 1; j <= i; j++) {
                long totalPermutations = 0;
                // pick 1 person as peak, divide left and right. get permutation for each left/right configuration
                for (int left = 0, right = i - 1; right >= 0; left++, right--) {
                    // left is visible, use previously calculated values
                    final long leftPermutation = permutations[left][j - 1] * combination(i - 1, left);
                    // right is not visible (hidden by peak), use totalPermutations
                    final long rightPermutation = permutation(right, right);
                    final long currentPermutation = leftPermutation * rightPermutation;
                    totalPermutations += currentPermutation;
                }
                permutations[i][j] = totalPermutations;
            }
        }

        return permutations;
    }
}

class Input {
    public int totalPeoples;
    public int totalLeftViews;
    public int totalRightViews;
}

class Output {
    public long totalPermutations;
}
