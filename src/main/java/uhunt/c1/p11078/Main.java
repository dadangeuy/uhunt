package uhunt.c1.p11078;

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
            int totalStudent = in.nextInt();
            int[] scores = new int[totalStudent];
            for (int j = 0; j < totalStudent; j++) scores[j] = in.nextInt();

            Solution solution = new Solution(scores);
            int difference = solution.getMaximumDifferenceBetweenSeniorAndJunior();

            out.println(difference);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int[] scores;

    public Solution(int[] scores) {
        this.scores = scores;
    }

    public int getMaximumDifferenceBetweenSeniorAndJunior() {
        int maxDifference = scores[0] - scores[1];
        int maxScore = scores[0];
        for (int i = 1; i < scores.length; i++) {
            int difference = maxScore - scores[i];
            maxDifference = Math.max(maxDifference, difference);

            maxScore = Math.max(maxScore, scores[i]);
        }


        return maxDifference;
    }
}
