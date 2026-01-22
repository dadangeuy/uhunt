package uhunt.c1.g9.p11679;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int totalBank = in.nextInt();
            int totalDebenture = in.nextInt();
            if (totalBank == 0 && totalDebenture == 0) break;

            int[] reserves = new int[totalBank];
            for (int i = 0; i < totalBank; i++) reserves[i] = in.nextInt();

            int[][] debentures = new int[totalDebenture][3];
            for (int i = 0; i < totalDebenture; i++) {
                int debtor = in.nextInt() - 1;
                int creditor = in.nextInt() - 1;
                int value = in.nextInt();

                debentures[i][0] = debtor;
                debentures[i][1] = creditor;
                debentures[i][2] = value;
            }

            if (canLiquidateWithoutBailout(reserves, debentures)) out.println('S');
            else out.println('N');
        }

        in.close();
        out.flush();
        out.close();
    }

    private static boolean canLiquidateWithoutBailout(int[] reserves, int[][] debentures) {
        for (int[] debenture : debentures) {
            int debtor = debenture[0];
            int creditor = debenture[1];
            int value = debenture[2];

            reserves[debtor] -= value;
            reserves[creditor] += value;
        }

        return !hasNegative(reserves);
    }

    private static boolean hasNegative(int[] array) {
        for (int value : array) if (value < 0) return true;
        return false;
    }
}
