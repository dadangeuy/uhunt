package uva.c1.g4.p12364;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 12364 - In Braille
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3786
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int totalDigit = in.nextInt();
            if (totalDigit == 0) break;

            String type = in.next();
            if (type.equals("S")) {
                char[] digits = in.next().toCharArray();

                Solution solution = new Solution(totalDigit, digits);
                char[][][] brailles = solution.parseBrailles();

                for (int row = 0; row < 3; row++) {
                    for (int digit = 0; digit < totalDigit; digit++) {
                        out.print(brailles[digit][row]);
                        if (digit < totalDigit - 1) out.print(' ');
                    }
                    out.println();
                }
            } else if (type.equals("B")) {
                char[][][] brailles = new char[totalDigit][3][];
                for (int row = 0; row < 3; row++) {
                    for (int digit = 0; digit < totalDigit; digit++) {
                        brailles[digit][row] = in.next().toCharArray();
                    }
                }

                Solution solution = new Solution(totalDigit, brailles);
                char[] digits = solution.parseDigits();

                out.println(digits);
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final String[] BRAILLES_PER_DIGIT = new String[]{
            ".***..",
            "*.....",
            "*.*...",
            "**....",
            "**.*..",
            "*..*..",
            "***...",
            "****..",
            "*.**..",
            ".**..."
    };

    private final int totalDigit;
    private final char[][][] brailles;
    private final char[] digits;

    public Solution(int totalDigit, char[][][] brailles) {
        this.totalDigit = totalDigit;
        this.brailles = brailles;
        this.digits = new char[totalDigit];
    }

    public Solution(int totalDigit, char[] digits) {
        this.totalDigit = totalDigit;
        this.digits = digits;
        this.brailles = new char[totalDigit][][];
    }

    public char[][][] parseBrailles() {
        for (int i = 0; i < totalDigit; i++) {
            char digit = digits[i];
            String flatBraille = BRAILLES_PER_DIGIT[digit - '0'];
            char[][] braille = unflatten(flatBraille);
            brailles[i] = braille;
        }
        return brailles;
    }

    public char[] parseDigits() {
        for (int i = 0; i < totalDigit; i++) {
            char[][] braille = brailles[i];
            char digit = findDigit(braille);
            digits[i] = digit;
        }
        return digits;
    }

    private char findDigit(char[][] braille) {
        String flatten = flatten(braille);
        for (int digit = 0; digit <= 9; digit++)
            if (BRAILLES_PER_DIGIT[digit].equals(flatten))
                return (char) ('0' + digit);
        throw new RuntimeException("invalid braille");
    }

    private String flatten(char[][] braille) {
        StringBuilder sb = new StringBuilder(6);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 2; col++) {
                sb.append(braille[row][col]);
            }
        }
        return sb.toString();
    }

    private char[][] unflatten(String flatBraille) {
        char[][] braille = new char[3][2];
        for (int row = 0, i = 0; row < 3; row++) {
            for (int col = 0; col < 2; col++, i++) {
                braille[row][col] = flatBraille.charAt(i);
            }
        }
        return braille;
    }
}
