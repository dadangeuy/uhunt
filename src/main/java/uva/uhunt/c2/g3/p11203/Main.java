package uva.uhunt.c2.g3.p11203;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 11203 - Can you decide it for ME?
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2144
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalWord = in.nextInt();
        in.nextLine();
        for (int i = 0; i < totalWord; i++) {
            String word = in.nextLine();

            Solution solution = new Solution(word);
            boolean isTheorem = solution.isTheorem();

            out.println(isTheorem ? "theorem" : "no-theorem");
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final Pattern ME_PATTERN = Pattern.compile("[?]+M[?]+E[?]+");
    private final String word;

    public Solution(String word) {
        this.word = word;
    }

    public boolean isTheorem() {
        boolean hasQuestionMarkInBetween = ME_PATTERN.matcher(word).matches();
        if (!hasQuestionMarkInBetween) return false;

        String[] questions = word.split("[M|E]");
        int x = questions[0].length(), y = questions[1].length(), z = questions[2].length();
        return x + y == z;
    }
}
