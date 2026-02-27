package uva.uhunt.c1.g9.p12279;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        for (int i = 1; ; i++) {
            int totalEvent = in.nextInt();
            if (totalEvent == 0) break;

            int[] events = new int[totalEvent];
            for (int j = 0; j < totalEvent; j++) {
                int number = in.nextInt();
                events[j] = number;
            }

            int emoogleBalance = getEmoogleBalance(events);
            out.format("Case %d: %d\n", i, emoogleBalance);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static int getEmoogleBalance(int[] events) {
        int countTreat = 0;
        int countNonTreat = 0;
        for (int event : events) {
            boolean giveTreat = event == 0;
            if (giveTreat) countTreat++;
            else countNonTreat++;
        }
        return countNonTreat - countTreat;
    }
}
