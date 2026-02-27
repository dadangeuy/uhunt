package uva.uhunt.c3.g5.p1225;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 1225 - Digit Counting
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=3666
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));

        final int totalTestCases = in.nextInt();
        for (int testCase = 0; testCase < totalTestCases; testCase++) {
            final int number = in.nextInt();
            final int[] count = count(number);
            out.print(count[0]);
            for (int i = 1; i <= 9; i++) {
                out.print(' ');
                out.print(count[i]);
            }
            out.print('\n');
        }

        in.close();
        out.flush();
        out.close();
    }

    private static int[] count(final int number) {
        if (number == 0) {
            return new int[10];
        }

        int[] count = count(number - 1);
        for (int remainingNumber = number; remainingNumber > 0; remainingNumber /= 10) {
            final int digit = remainingNumber % 10;
            count[digit]++;
        }
        return count;
    }
}
