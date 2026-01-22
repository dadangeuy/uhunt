package uhunt.c1.g2.p11332;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int n = in.nextInt();
            if (n == 0) break;

            int ans = sumDigit(n);
            out.println(ans);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static int sumDigit(int target) {
        if (target < 10) return target;

        int sum = 0;
        while (target > 0) {
            int digit = target % 10;
            target /= 10;
            sum += digit;
        }
        return sumDigit(sum);
    }
}
