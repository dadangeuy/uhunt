package uva.c1.g5.p12085;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 12085 - Mobile Casanova
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3237
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        for (int caseId = 1; in.hasNextInt(); caseId++) {
            int totalNumber = in.nextInt();
            if (totalNumber == 0) break;

            String[] numbers = new String[totalNumber];
            for (int i = 0; i < totalNumber; i++) numbers[i] = in.next();

            Solution solution = new Solution(numbers);
            String[] compressedNumbers = solution.compressNumbers();

            out.format("Case %d:\n", caseId);
            for (String compressedNumber : compressedNumbers) out.println(compressedNumber);
            out.println();
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final String[] numbers;

    public Solution(String[] numbers) {
        this.numbers = numbers;
    }

    public String[] compressNumbers() {
        LinkedList<String> compressedNumbers = new LinkedList<>();
        for (int i = 0; i < numbers.length; ) {
            int j = i + 1;
            while (j < numbers.length && next(numbers[j - 1]).equals(numbers[j])) j++;

            int total = j - i;
            if (total == 1) {
                compressedNumbers.addLast(numbers[i]);
            } else {
                String prefix = prefix(numbers[i], numbers[j - 1]);
                String suffix = numbers[j - 1].substring(prefix.length());
                String compressedNumber = String.format("%s-%s", numbers[i], suffix);
                compressedNumbers.addLast(compressedNumber);
            }

            i = j;
        }

        return compressedNumbers.toArray(new String[0]);
    }

    private String next(String number) {
        char[] next = number.toCharArray();

        next[next.length - 1] = (char) (next[next.length - 1] + 1);
        for (int i = next.length - 1; i >= 1; i--) {
            if (next[i] <= '9') continue;
            next[i - 1] = (char) (next[i - 1] + 1);
            next[i] = '0';
        }

        return new String(next);
    }

    private String prefix(String number1, String number2) {
        StringBuilder prefix = new StringBuilder();
        for (int i = 0, j = 0; i < number1.length() && j < number2.length(); i++, j++) {
            if (number1.charAt(i) != number2.charAt(j)) break;
            prefix.append(number1.charAt(i));
        }
        return prefix.toString();
    }
}
