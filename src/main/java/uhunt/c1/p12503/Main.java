package uhunt.c1.p12503;

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
            int totalAction = in.nextInt();
            int[] moves = new int[totalAction];

            for (int j = 0; j < totalAction; j++) {
                String action = in.next();

                if (action.equals("SAME")) {
                    in.next();
                    int actionID = in.nextInt();
                    moves[j] = moves[actionID - 1];

                } else {
                    int move = action.equals("LEFT") ? -1 : 1;
                    moves[j] = move;
                }
            }

            int position = sum(moves);
            out.println(position);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static int sum(int[] array) {
        int sum = 0;
        for (int value : array) sum += value;
        return sum;
    }
}
