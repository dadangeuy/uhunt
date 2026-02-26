package uva.c2.g2.p482;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * 482 - Permutation Arrays
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=423
 */
public class Main {
    public static void main(String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalTestCases = Integer.parseInt(in.nextLine());
        for (int testCase = 1; testCase <= totalTestCases; testCase++) {
            in.nextLine();
            final int[] orders = Arrays
                    .stream(in.nextLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            final String[] texts = in.nextLine().split(" ");

            final Solution solution = new Solution(orders, texts);
            final String[] orderedTexts = solution.getOrderedTexts();

            if (testCase > 1) out.println();
            for (String text : orderedTexts) out.println(text);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int[] orders;
    private final String[] texts;

    public Solution(int[] orders, String[] texts) {
        this.orders = orders;
        this.texts = texts;
    }

    public String[] getOrderedTexts() {
        final TreeMap<Integer, String> textPerOrder = new TreeMap<>();
        for (int i = 0; i < texts.length; i++) textPerOrder.put(orders[i], texts[i]);
        return textPerOrder.values().toArray(new String[0]);
    }
}
