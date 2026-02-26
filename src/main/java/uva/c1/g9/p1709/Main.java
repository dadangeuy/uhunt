package uva.c1.g9.p1709;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextInt()) {
            int p = in.nextInt();
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            int d = in.nextInt();
            int n = in.nextInt();

            double maxDecline = 0;
            double maxPrice = Double.MIN_VALUE;
            for (int k = 1; k <= n; k++) {
                double price = getPrice(p, a, b, c, d, k);
                maxPrice = Math.max(maxPrice, price);

                double decline = maxPrice - price;
                maxDecline = Math.max(maxDecline, decline);
            }

            out.format("%.9f\n", maxDecline);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static double getPrice(int p, int a, int b, int c, int d, int k) {
        return p * (Math.sin(a * k + b) + Math.cos(c * k + d) + 2);
    }
}
