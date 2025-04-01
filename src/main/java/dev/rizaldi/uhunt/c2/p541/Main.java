package dev.rizaldi.uhunt.c2.p541;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 541 - Error Correction
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=482
 */
public class Main {
    public static void main(String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        do {
            final int size = in.nextInt();
            if (size == 0) break;

            final boolean[][] matrix = new boolean[size][size];
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    matrix[row][col] = in.nextInt() == 1;
                }
            }

            final boolean isParity = isParity(matrix, size);
            final int[] changingCell = isParity? null : findChangingCell(matrix, size);

            if (isParity) {
                out.println("OK");
            } else if (changingCell != null) {
                out.format("Change bit (%d,%d)\n", changingCell[0], changingCell[1]);
            } else {
                out.println("Corrupt");
            }
        } while (in.hasNextInt());

        in.close();
        out.flush();
        out.close();
    }

    private static int[] findChangingCell(boolean[][] matrix, int size) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                matrix[row][col] = !matrix[row][col];
                if (isParity(matrix, size)) return new int[]{row + 1, col + 1};
                matrix[row][col] = !matrix[row][col];
            }
        }
        return null;
    }

    private static boolean isParity(boolean[][] matrix, int size) {
        for (int row = 0; row < size; row++) {
            int count = 0;
            for (int col = 0; col < size; col++) {
                count += matrix[row][col]? 1 : 0;
            }
            if (isOdd(count)) return false;
        }

        for (int col = 0; col < size; col++) {
            int count = 0;
            for (int row = 0; row < size; row++) {
                count += matrix[row][col]? 1 : 0;
            }
            if (isOdd(count)) return false;
        }

        return true;
    }

    private static boolean isOdd(int number) {
        return (number & 1) != 0;
    }
}
