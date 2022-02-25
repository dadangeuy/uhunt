package dev.rizaldi.uhunt.c1.p11044;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) throws IOException {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            int totalRow = in.nextInt();
            int totalCol = in.nextInt();

            Solution solution = new Solution(totalRow, totalCol);
            int totalSonar = solution.getTotalSonar();

            out.println(totalSonar);
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

    public int getTotalSonar() {
        int r = totalRow - 2;
        int c = totalCol - 2;
        return ceilDiv(r, 3) * ceilDiv(c, 3);
    }

    private int ceilDiv(int dividend, int divisor) {
        if (dividend % divisor != 0) {
            dividend = dividend - dividend % divisor + divisor;
        }
        return dividend / divisor;
    }
}
