package uhunt.c2.p1610;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1610 - Party Games
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=4485
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int totalGuest = in.nextInt();
            if (totalGuest == 0) break;

            String[] guests = new String[totalGuest];
            for (int i = 0; i < totalGuest; i++) guests[i] = in.next();

            Solution solution = new Solution(guests);
            String separator = solution.getShortestSeparator();

            out.println(separator);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final String[] guests;

    public Solution(String[] guests) {
        this.guests = guests;
    }

    public String getShortestSeparator() {
        Arrays.sort(guests);
        String prev = guests[guests.length / 2 - 1];
        String next = guests[guests.length / 2];

        StringBuilder separator = new StringBuilder();
        for (int i = 0; i < prev.length(); i++) {
            separator.append('?');

            for (char c = prev.charAt(i); c <= 'Z' && c <= increment(c); c++) {
                separator.setCharAt(i, c);
                if (between(prev, separator.toString(), next)) return separator.toString();
            }

            separator.setCharAt(i, prev.charAt(i));
        }

        return separator.toString();
    }

    private char increment(char c) {
        return (char) (c + 1);
    }

    private boolean between(String prev, String mid, String next) {
        return prev.compareTo(mid) <= 0 && mid.compareTo(next) < 0;
    }
}
