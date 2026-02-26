package uva.c1.g5.p1585;

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
            char[] answers = in.next().toCharArray();
            int score = getScore(answers);
            out.println(score);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static int getScore(char[] answers) {
        int[] scores = new int[answers.length];
        for (int i = 0; i < answers.length; i++) {
            boolean correct = answers[i] == 'O';
            if (!correct) continue;
            scores[i] = i > 0 ? scores[i - 1] + 1 : 1;
        }
        return sum(scores);
    }

    private static int sum(int[] values) {
        int sum = 0;
        for (int value : values) sum += value;
        return sum;
    }
}
