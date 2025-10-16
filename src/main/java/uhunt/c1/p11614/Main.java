package uhunt.c1.p11614;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0 ; i < totalCase; i++) {
            long totalWarrior = in.nextLong();
            long totalRow = (long) Math.floor((-1 + Math.sqrt(1 + 8 * totalWarrior)) / 2);
            out.println(totalRow);
        }

        in.close();
        out.flush();
        out.close();
    }
}
