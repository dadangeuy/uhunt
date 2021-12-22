package dev.rizaldi.uhunt.c2.p713;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Main {
    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        String[] lines;

        line = in.readLine();
        int totalTest = Integer.parseInt(line);
        while (totalTest-- > 0) {
            line = in.readLine();
            lines = line.split(" ");

            Solution solution = new Solution(lines[0], lines[1]);
            String sum = solution.getSum();

            System.out.println(sum);
        }
    }
}

// reverse first & second number
// convert those number into java's BigInteger data structure
// sum those BigInteger
// reverse the sum
// omit leading zero
class Solution {
    private final String firstNumber;
    private final String secondNumber;

    public Solution(String firstNumber, String secondNumber) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
    }

    public String getSum() {
        BigInteger first = new BigInteger(reverse(firstNumber));
        BigInteger second = new BigInteger(reverse(secondNumber));

        BigInteger sum = first.add(second);

        return omitLeadingZero(reverse(sum.toString()));
    }

    private String reverse(String text) {
        char[] buffer = new char[text.length()];
        for (int i = 0, j = text.length() - 1; i < text.length(); i++, j--) {
            buffer[i] = text.charAt(j);
        }
        return new String(buffer);
    }

    private String omitLeadingZero(String text) {
        int start = 0;
        while (start < text.length() - 1 && text.charAt(start) == '0') start++;
        return text.substring(start);
    }
}
