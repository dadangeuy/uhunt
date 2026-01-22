package uhunt.c1.g4.p11764;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            int totalWall = in.nextInt();
            int[] walls = new int[totalWall];
            for (int j = 0; j < totalWall; j++) {
                int wall = in.nextInt();
                walls[j] = wall;
            }

            int inc = countInc(walls);
            int dec = countDec(walls);
            out.format("Case %d: %d %d\n", i + 1, inc, dec);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static int countInc(int[] values) {
        int count = 0;
        for (int i = 0, j = 1; j < values.length; i++, j++) if (values[i] < values[j]) count++;
        return count;
    }

    private static int countDec(int[] values) {
        int count = 0;
        for (int i = 0, j = 1; j < values.length; i++, j++) if (values[i] > values[j]) count++;
        return count;
    }
}
