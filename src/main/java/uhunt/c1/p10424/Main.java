package uhunt.c1.p10424;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextLine()) {
            String name1 = in.nextLine();
            String name2 = in.nextLine();

            Solution solution = new Solution(name1, name2);
            double love = solution.getPercentageOfLove();

            out.format("%.2f %%\n", love);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final String name1;
    private final String name2;

    public Solution(String name1, String name2) {
        this.name1 = name1;
        this.name2 = name2;
    }

    public double getPercentageOfLove() {
        int sum1 = sumUntilOneDigit(sumAlphabet(name1));
        int sum2 = sumUntilOneDigit(sumAlphabet(name2));

        int min = Math.min(sum1, sum2);
        int max = Math.max(sum1, sum2);

        return 100.0 * min / max;
    }

    private int sumAlphabet(String text) {
        int sum = 0;
        for (char letter : text.toCharArray()) {
            if ('a' <= letter && letter <= 'z') sum += letter - 'a' + 1;
            else if ('A' <= letter && letter <= 'Z') sum += letter - 'A' + 1;
        }
        return sum;
    }

    private int sumUntilOneDigit(int value) {
        if (value < 10) return value;

        int newValue = sumDigit(value);
        return sumUntilOneDigit(newValue);
    }

    private int sumDigit(int value) {
        int sum = 0;
        for (; value > 0; value /= 10) sum += value % 10;
        return sum;
    }
}
