package uhunt.c1.g2.p12372;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 1; i <= totalCase; i++) {
            int length = in.nextInt();
            int width = in.nextInt();
            int height = in.nextInt();

            boolean fits = fits(length, width, height);
            System.out.format("Case %d: %s\n", i, fits ? "good" : "bad");
        }

        in.close();
        out.flush();
        out.close();
    }

    private static boolean fits(int length, int width, int height) {
        return length <= 20 && width <= 20 && height <= 20;
    }
}
