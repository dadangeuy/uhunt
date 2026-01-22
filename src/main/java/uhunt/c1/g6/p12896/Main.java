package uhunt.c1.g6.p12896;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 12896 - Mobile SMS
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4761
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            int messageLength = in.nextInt();

            int[] numbers = new int[messageLength];
            for (int j = 0; j < messageLength; j++) numbers[j] = in.nextInt();

            int[] presses = new int[messageLength];
            for (int j = 0; j < messageLength; j++) presses[j] = in.nextInt();

            Solution solution = new Solution(numbers, presses);
            String message = solution.getMessage();

            out.println(message);
        }

        in.close();
        out.close();
    }
}

class Solution {
    private static final char[][] LAYOUT = new char[][]{
        " ".toCharArray(),
        ".,?\"".toCharArray(),
        "abc".toCharArray(),
        "def".toCharArray(),
        "ghi".toCharArray(),
        "jkl".toCharArray(),
        "mno".toCharArray(),
        "pqrs".toCharArray(),
        "tuv".toCharArray(),
        "wxyz".toCharArray()
    };

    private final int[] numbers;
    private final int[] presses;

    public Solution(int[] numbers, int[] presses) {
        this.numbers = numbers;
        this.presses = presses;
    }

    public String getMessage() {
        StringBuilder message = new StringBuilder(numbers.length);
        for (int i = 0; i < numbers.length; i++) {
            int number = numbers[i], press = presses[i];
            char letter = LAYOUT[number][press - 1];
            message.append(letter);
        }
        return message.toString();
    }
}
