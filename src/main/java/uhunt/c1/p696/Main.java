package uhunt.c1.p696;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int totalRow = in.nextInt();
            int totalCol = in.nextInt();
            if (totalRow == 0 && totalCol == 0) break;

            Solution solution = new Solution(totalRow, totalCol);
            int total = solution.getMaxTotalKnightOnBoard();

            out.format("%d knights may be placed on a %d row %d column board.\n", total, totalRow, totalCol);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int totalRow;
    private final int totalCol;

    public Solution(int totalRow, int totalCol) {
        this.totalRow = totalRow;
        this.totalCol = totalCol;
    }

    public int getMaxTotalKnightOnBoard() {
        int minSide = Math.min(totalRow, totalCol);
        int maxSide = Math.max(totalRow, totalCol);

        if (minSide == 0) return 0;
        else if (minSide == 1) return maxSide;
        else if (minSide == 2) return cdiv(maxSide, 4) * 2 + cdiv(maxSide - 1, 4) * 2;
        else return cdiv(maxSide, 2) * cdiv(minSide, 2) + fdiv(maxSide, 2) * fdiv(minSide, 2);
    }

    private int cdiv(int dividend, int divisor) {
        if (dividend % divisor == 0) return dividend / divisor;
        return dividend / divisor + 1;
    }

    private int fdiv(int dividend, int divisor) {
        return dividend / divisor;
    }
}
