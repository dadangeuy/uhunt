package uhunt.c2.g3.p10703;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 10703 - Free spots
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1644
 */
public class Main {
    public static void main(String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextInt()) {
            final int width = in.nextInt();
            final int height = in.nextInt();
            final int totalSubBoards = in.nextInt();
            if (!in.hasNextInt()) break;

            final boolean[][] board = new boolean[width][height];

            for (int i = 0; i < totalSubBoards; i++) {
                final int x1 = in.nextInt();
                final int y1 = in.nextInt();
                final int x2 = in.nextInt();
                final int y2 = in.nextInt();

                for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                    for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                        board[x - 1][y - 1] = true;
                    }
                }
            }

            int totalEmptySpots = 0;
            for (boolean[] row : board) {
                for (boolean cell : row) {
                    totalEmptySpots += cell ? 0 : 1;
                }
            }

            if (totalEmptySpots == 0) {
                out.println("There is no empty spots.");
            } else if (totalEmptySpots == 1) {
                out.println("There is one empty spot.");
            } else {
                out.format("There are %d empty spots.\n", totalEmptySpots);
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}
