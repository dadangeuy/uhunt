package uhunt.c1.p10055;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 10055 - Hashmat the Brave Warrior
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=996
 */
public class Main {
    public static void main(String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextLong()) {
            final long totalHashmatArmies = in.nextLong();
            final long totalOpponentArmies = in.nextLong();
            final long totalDifferences = Math.abs(totalOpponentArmies - totalHashmatArmies);
            out.println(totalDifferences);
        }

        in.close();
        out.flush();
        out.close();
    }
}
