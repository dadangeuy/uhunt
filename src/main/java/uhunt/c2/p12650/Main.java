package uhunt.c2.p12650;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 12650 - Dangerous Dive
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4379
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));

        while (in.hasNextInt()) {
            int totalVolunteer = in.nextInt();
            int totalReturnVolunteer = in.nextInt();

            int[] returnVolunteers = new int[totalReturnVolunteer];
            for (int i = 0; i < totalReturnVolunteer; i++) returnVolunteers[i] = in.nextInt();

            Solution solution = new Solution(totalVolunteer, totalReturnVolunteer, returnVolunteers);
            int[] unreturnVolunteers = solution.findUnreturnVolunteers();

            if (unreturnVolunteers.length == 0) {
                out.println("*");
            } else {
                for (int volunteer : unreturnVolunteers) {
                    out.print(volunteer);
                    out.print(' ');
                }
                out.println();
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int totalVolunteer;
    private final int totalReturnVolunteer;
    private final int[] returnVolunteers;

    public Solution(int totalVolunteer, int totalReturnVolunteer, int[] returnVolunteers) {
        this.totalVolunteer = totalVolunteer;
        this.totalReturnVolunteer = totalReturnVolunteer;
        this.returnVolunteers = returnVolunteers;
    }

    public int[] findUnreturnVolunteers() {
        boolean[] isReturns = new boolean[totalVolunteer + 1];
        for (int volunteer : returnVolunteers) isReturns[volunteer] = true;

        int[] unreturnVolunteers = new int[totalVolunteer - totalReturnVolunteer];
        for (int volunteer = 1, i = 0; volunteer <= totalVolunteer; volunteer++) {
            if (isReturns[volunteer]) continue;
            unreturnVolunteers[i++] = volunteer;
        }

        return unreturnVolunteers;
    }
}
