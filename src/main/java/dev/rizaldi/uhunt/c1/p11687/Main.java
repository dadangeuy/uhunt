package dev.rizaldi.uhunt.c1.p11687;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        String number;
        while (!(number = in.next()).equals("END")) {
            int sequence = getSmallestEqualSequence(number);
            out.println(sequence);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static int getSmallestEqualSequence(String number) {
        int sequence = 1;
        while (!number.equals(String.valueOf(number.length()))) {
            ++sequence;
            number = String.valueOf(number.length());
        }
        return sequence;
    }
}
