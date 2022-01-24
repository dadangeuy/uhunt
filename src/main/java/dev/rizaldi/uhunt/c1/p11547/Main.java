package dev.rizaldi.uhunt.c1.p11547;

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
            int n = in.nextInt();
            int ans = (((((n * 567) / 9) + 7492) * 235) / 47) - 498;
            ans = Math.abs(ans % 100 - ans % 10) / 10;
            out.println(ans);
        }

        in.close();
        out.flush();
        out.close();
    }
}
