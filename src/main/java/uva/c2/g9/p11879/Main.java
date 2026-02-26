package uva.c2.g9.p11879;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        BigInteger seventeen = BigInteger.valueOf(17);
        while (true) {
            BigInteger value = in.nextBigInteger();
            if (value.equals(BigInteger.ZERO)) break;

            if (value.mod(seventeen).equals(BigInteger.ZERO)) out.println(1);
            else out.println(0);
        }

        in.close();
        out.flush();
        out.close();
    }
}
