package dev.rizaldi.uhunt.c1.p10188;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

enum Verdict {
    ACCEPTED, PRESENTATION_ERROR, WRONG_ANSWER;

    @Override
    public String toString() {
        switch (this) {
            case ACCEPTED:
                return "Accepted";
            case PRESENTATION_ERROR:
                return "Presentation Error";
            case WRONG_ANSWER:
                return "Wrong Answer";
        }
        throw new RuntimeException("Unknown Verdict: " + this);
    }
}

/**
 * 10188 - Automated Judge Script
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1129
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        for (int runId = 1; ; runId++) {
            int totalInputLine = in.nextInt();
            if (totalInputLine == 0) break;

            in.nextLine();
            StringBuilder inputBuilder = new StringBuilder();
            for (int i = 0; i < totalInputLine; i++) inputBuilder.append(in.nextLine()).append('\n');
            String input = inputBuilder.toString();

            int totalOutputLine = in.nextInt();

            in.nextLine();
            StringBuilder outputBuilder = new StringBuilder();
            for (int i = 0; i < totalOutputLine; i++) outputBuilder.append(in.nextLine()).append('\n');
            String output = outputBuilder.toString();

            Solution solution = new Solution(input, output);
            Verdict verdict = solution.judge();

            out.format("Run #%d: %s\n", runId, verdict.toString());
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final String input;
    private final String output;

    public Solution(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public Verdict judge() {
        if (input.equals(output)) return Verdict.ACCEPTED;

        String numericIn = input.replaceAll("[^0-9]", "");
        String numericOut = output.replaceAll("[^0-9]", "");
        if (numericIn.equals(numericOut)) return Verdict.PRESENTATION_ERROR;

        return Verdict.WRONG_ANSWER;
    }
}
