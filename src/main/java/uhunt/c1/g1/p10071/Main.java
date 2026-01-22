package uhunt.c1.g1.p10071;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextInt()) {
            int velocity = in.nextInt();
            int time = in.nextInt();

            out.println(velocity * time * 2);
        }

        in.close();
        out.flush();
        out.close();
    }
}
