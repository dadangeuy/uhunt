package uhunt.c1.g0.p1200;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 1200 - A DP Problem
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3641
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            String equation = in.next();

            Solution solution = new Solution(equation);
            try {
                int x = solution.findValueOfX();
                out.println(x);
            } catch (Exception e) {
                out.println(e.getMessage());
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final Pattern PATTERN_VALUE = Pattern.compile("([+-]?[0-9]*x)|([+-]?[0-9]+)");
    private static final Exception IMPOSSIBLE = new Exception("IMPOSSIBLE");
    private static final Exception IDENTITY = new Exception("IDENTITY");

    private final String equation;

    public Solution(String equation) {
        this.equation = equation;
    }

    public int findValueOfX() throws Exception {
        String left = equation.split("=")[0], right = equation.split("=")[1];

        int leftDividend = asStream(PATTERN_VALUE.matcher(left))
            .filter(v -> !v.endsWith("x"))
            .mapToInt(Integer::parseInt)
            .sum();
        int rightDividend = asStream(PATTERN_VALUE.matcher(right))
            .filter(v -> !v.endsWith("x"))
            .mapToInt(Integer::parseInt)
            .sum();

        int leftDivisor = asStream(PATTERN_VALUE.matcher(left))
            .filter(v -> v.endsWith("x"))
            .mapToInt(v -> v.equals("x") || v.equals("+x") ? 1
                : v.equals("-x") ? -1
                : Integer.parseInt(v.substring(0, v.length() - 1)))
            .sum();
        int rightDivisor = asStream(PATTERN_VALUE.matcher(right))
            .filter(v -> v.endsWith("x"))
            .mapToInt(v -> v.equals("x") || v.equals("+x") ? 1
                : v.equals("-x") ? -1
                : Integer.parseInt(v.substring(0, v.length() - 1)))
            .sum();

        int dividend = -leftDividend + rightDividend;
        int divisor = leftDivisor - rightDivisor;

        if (dividend == 0 && divisor == 0) throw IDENTITY;
        if (dividend != 0 && divisor == 0) throw IMPOSSIBLE;
        return floorDiv(dividend, divisor);
    }

    private Stream<String> asStream(Matcher matcher) {
        List<String> matches = new LinkedList<>();
        while (matcher.find()) matches.add(matcher.group());
        return matches.stream();
    }

    private int floorDiv(int dividend, int divisor) {
        if (dividend % divisor == 0) return dividend / divisor;
        int quotient = dividend / divisor;
        return quotient < 0 ? quotient - 1 : quotient;
    }
}
