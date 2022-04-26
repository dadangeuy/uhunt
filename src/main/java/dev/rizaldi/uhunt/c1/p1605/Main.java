package dev.rizaldi.uhunt.c1.p1605;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 1605 - Building for UN
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4480
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextInt()) {
            int totalCountry = in.nextInt();

            Solution solution = new Solution(totalCountry);
            char[][][] building = solution.designBuilding();
            int height = building.length, width = building[0].length, length = building[0][0].length;

            out.format("%d %d %d\n", height, width, length);
            for (int i = 0; i < building.length; i++) {
                for (int j = 0; j < building[i].length; j++) {
                    for (int k = 0; k < building[i][j].length; k++) {
                        out.print(building[i][j][k]);
                    }
                    out.println();
                }
                if (i < building.length - 1) out.println();
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final char[] countries = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private final int totalCountry;

    public Solution(int totalCountry) {
        this.totalCountry = totalCountry;
    }

    public char[][][] designBuilding() {
        char[][][] building = new char[2][totalCountry][totalCountry];
        for (int i = 0; i < totalCountry; i++) {
            for (int j = 0; j < totalCountry; j++) {
                building[0][i][j] = building[1][j][i] = countries[i];
            }
        }

        return building;
    }
}
