package uhunt.c1.p10851;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 10851 - 2D Hieroglyphs decoder
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1792
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalMessage = in.nextInt();
        for (int i = 0; i < totalMessage; i++) {
            char[][] hieroglyph = new char[10][];
            for (int j = 0; j < 10; j++) hieroglyph[j] = in.next().toCharArray();

            Solution solution = new Solution(hieroglyph);
            String message = solution.decodeMessage();

            out.println(message);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final char[][] hieroglyph;

    public Solution(char[][] hieroglyph) {
        this.hieroglyph = hieroglyph;
    }

    public String decodeMessage() {
        int columnLength = hieroglyph[0].length;
        int messageLength = columnLength - 2;
        char[] message = new char[messageLength];

        for (int col = 1; col < columnLength - 1; col++) {
            char letter = 0;
            for (int row = 1; row < 9; row++) {
                char encoded = hieroglyph[row][col];
                if (encoded == '\\') letter |= (1 << (row - 1));
            }
            message[col - 1] = letter;
        }

        return new String(message);
    }
}
