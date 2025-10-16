package uhunt.c1.p584;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            String line = in.nextLine();
            if (line.equals("Game Over")) break;
            char[] rolls = line.replaceAll(" ", "").toCharArray();

            Solution solution = new Solution(rolls);
            int totalScore = solution.getTotalScore();

            out.println(totalScore);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final char STRIKE = 'X';
    private static final char SPARE = '/';
    private final char[] rolls;

    public Solution(char[] rolls) {
        this.rolls = rolls;
    }

    public int getTotalScore() {
        int[] pins = new int[rolls.length];

        for (int roll = 0; roll < rolls.length; roll++) {
            if (rolls[roll] == STRIKE) pins[roll] = 10;
            else if (rolls[roll] == SPARE) pins[roll] = 10 - pins[roll - 1];
            else pins[roll] = digit(rolls[roll]);
        }

        int totalScore = 0;
        for (int frame = 0, roll = 0; frame < 10; frame++) {
            if (rolls[roll] == STRIKE) {
                int score = pins[roll];
                if (roll + 1 < rolls.length) score += pins[roll + 1];
                if (roll + 2 < rolls.length) score += pins[roll + 2];

                totalScore += score;
                roll += 1;
            } else if (rolls[roll + 1] == SPARE) {
                int score = pins[roll] + pins[roll + 1];
                if (roll + 2 < rolls.length) score += pins[roll + 2];

                totalScore += score;
                roll += 2;
            } else {
                totalScore += pins[roll] + pins[roll + 1];
                roll += 2;
            }
        }

        return totalScore;
    }

    private int digit(char c) {
        return c - '0';
    }
}
