package uva.c7.g5.p1595;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * 1595 - Symmetry
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4470
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        final int totalTestCases = in.nextInt();
        for (int i = 0; i < totalTestCases; i++) {
            final Input input = new Input();
            input.totalDots = in.nextInt();
            input.dots = new int[input.totalDots][];
            for (int j = 0; j < input.totalDots; j++) {
                final int[] dots = new int[]{in.nextInt(), in.nextInt()};
                input.dots[j] = dots;
            }

            final Output output = process.process(input);
            if (output.isSymmetric) out.println("YES");
            else out.println("NO");
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalDots;
    public int[][] dots;
}

class Output {
    public boolean isSymmetric;
}

class Process {
    private static final Comparator<int[]> ORDER_BY_X_AND_Y = Comparator
        .comparingInt((int[] d) -> d[0])
        .thenComparingInt((int[] d) -> d[1]);
    private static final int PRECISION = 2;

    public Output process(final Input input) {
        final Output output = new Output();

        final int[][] dots = copy(input.dots);

        for (int i = 0; i < dots.length; i++) {
            for (int j = 0; j < dots[i].length; j++) {
                dots[i][j] *= PRECISION;
            }
        }

        final int verticalLine = findVerticalLine(dots);

        final int[][][] cutDots = cut(dots, verticalLine);
        final int[][] dots1 = cutDots[0];
        final int[][] dots2 = cutDots[1];

        output.isSymmetric = isEqual(dots1, dots2);

        return output;
    }

    private int[][] copy(final int[][] original) {
        final int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) copy[i] = Arrays.copyOf(original[i], original[i].length);
        return copy;
    }

    private int findVerticalLine(final int[][] dots) {
        final int minimumX = Arrays.stream(dots)
            .mapToInt(d -> d[0])
            .min()
            .orElseThrow(NullPointerException::new);
        final int maximumX = Arrays.stream(dots)
            .mapToInt(d -> d[0])
            .max()
            .orElseThrow(NullPointerException::new);

        return (minimumX + maximumX) / 2;
    }

    private int[][][] cut(final int[][] dots, final int verticalLine) {
        final int[][] dots1 = Arrays.stream(dots)
            .filter(d -> d[0] < verticalLine)
            .map(d -> new int[]{verticalLine - d[0], d[1]})
            .toArray(int[][]::new);
        final int[][] dots2 = Arrays.stream(dots)
            .filter(d -> verticalLine < d[0])
            .map(d -> new int[]{d[0] - verticalLine, d[1]})
            .toArray(int[][]::new);

        return new int[][][]{dots1, dots2};
    }

    private boolean isEqual(final int[][] dots1, final int[][] dots2) {
        Arrays.sort(dots1, ORDER_BY_X_AND_Y);
        Arrays.sort(dots2, ORDER_BY_X_AND_Y);

        final int length = Math.max(dots1.length, dots2.length);
        for (int i = 0; i < length; i++) {
            final int[] dot1 = get(dots1, i);
            final int[] dot2 = get(dots2, i);
            if (!isEqual(dot1, dot2)) return false;
        }

        return true;
    }

    private int[] get(final int[][] array, final int index) {
        return index < array.length ? array[index] : null;
    }

    private boolean isEqual(final int[] dot1, final int[] dot2) {
        if (dot1 == null || dot2 == null) return false;
        return dot1[0] == dot2[0] && dot1[1] == dot2[1];
    }
}
