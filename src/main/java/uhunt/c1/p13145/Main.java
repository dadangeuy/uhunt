package uhunt.c1.p13145;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 13145 - Wuymul Wixcha
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=5056
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int shift = in.nextInt();
            if (shift == 0) break;

            in.nextLine();
            String message = in.nextLine();
            Solution solution = new Solution(message, shift);
            String encodedMessage = solution.encode();

            out.println(encodedMessage);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final String message;
    private final int shift;

    public Solution(String message, int shift) {
        this.message = message;
        this.shift = shift;
    }

    public String encode() {
        StringBuilder encodedMessage = new StringBuilder();
        for (char letter : message.toCharArray()) {
            if ('a' <= letter && letter <= 'z') {
                char encodedLetter = encodeLetter(letter, 'a');
                encodedMessage.append(encodedLetter);
            } else if ('A' <= letter && letter <= 'Z') {
                char encodedLetter = encodeLetter(letter, 'A');
                encodedMessage.append(encodedLetter);
            } else {
                encodedMessage.append(letter);
            }
        }
        return encodedMessage.toString();
    }

    private char encodeLetter(char letter, char reducer) {
        int base = letter - reducer;
        int shiftBase = base + (shift >= 0 ? shift : (26 - (-shift % 26)));
        int modShiftBase = shiftBase % 26;
        char encodedLetter = (char) (modShiftBase + reducer);
        return encodedLetter;
    }
}
