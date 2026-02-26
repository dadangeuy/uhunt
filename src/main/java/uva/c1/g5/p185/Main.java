package uva.c1.g5.p185;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * 185 - Roman Numerals
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=121
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNext()) {
            String equation = in.next();
            if (equation.equals("#")) break;

            Solution solution = new Solution(equation);
            State[] romanAndArabicState = solution.getRomanAndArabicState();
            State romanState = romanAndArabicState[0], arabicState = romanAndArabicState[1];

            out.format("%s %s\n", romanState.toString(), arabicState.toString());
        }

        in.close();
        out.flush();
        out.close();
    }
}

enum State {
    CORRECT, INCORRECT, // Roman
    AMBIGUOUS, IMPOSSIBLE, VALID; // Arabic


    @Override
    public String toString() {
        switch (this) {
            case CORRECT:
                return "Correct";
            case INCORRECT:
                return "Incorrect";
        }
        return super.toString().toLowerCase();
    }
}

class Solution {
    private static final char[] romans = new char[]{'I', 'V', 'X', 'L', 'C', 'D', 'M'};
    private static final Map<Character, Integer> romanValues = new HashMap<Character, Integer>() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1_000);
    }};

    private final String equation;

    public Solution(String equation) {
        this.equation = equation;
    }

    public State[] getRomanAndArabicState() {
        String[] values = equation.split("[+=]");

        String roman1 = values[0];
        String roman2 = values[1];
        String romanSum = values[2];

        return new State[]{getRomanState(roman1, roman2, romanSum), getArabicState(roman1, roman2, romanSum)};
    }

    private State getRomanState(String roman1, String roman2, String romanSum) {
        int decimal1 = toInteger(roman1);
        int decimal2 = toInteger(roman2);
        int decimalSum = toInteger(romanSum);

        boolean correct = decimal1 + decimal2 == decimalSum;
        return correct ? State.CORRECT : State.INCORRECT;
    }

    private int toInteger(String roman) {
        int sum = 0;
        for (int i = 0; i < roman.length(); i++) {
            int curr = romanValues.get(roman.charAt(i));
            int next = i < roman.length() - 1 ? romanValues.get(roman.charAt(i + 1)) : 0;

            boolean increasing = curr < next;
            if (increasing) sum -= curr;
            else sum += curr;
        }
        return sum;
    }

    private State getArabicState(String roman1, String roman2, String romanSum) {
        Set<String> equations = new TreeSet<>();

        for (char ri = '0'; ri <= '9'; ri++) {
            for (char rv = '0'; rv <= '9'; rv++) {
                if (rv == ri) continue;

                for (char rx = '0'; rx <= '9'; rx++) {
                    if (rx == rv || rx == ri) continue;

                    for (char rl = '0'; rl <= '9'; rl++) {
                        if (rl == rx || rl == rv || rl == ri) continue;

                        for (char rc = '0'; rc <= '9'; rc++) {
                            if (rc == rl || rc == rx || rc == rv || rc == ri) continue;

                            for (char rd = '0'; rd <= '9'; rd++) {
                                if (rd == rc || rd == rl || rd == rx || rd == rv || rd == ri) continue;

                                for (char rm = '0'; rm <= '9'; rm++) {
                                    if (rm == rd || rm == rc || rm == rl || rm == rx || rm == rv || rm == ri) continue;

                                    String arabic1 = roman1
                                        .replace('I', ri).replace('V', rv).replace('X', rx)
                                        .replace('L', rl).replace('C', rc).replace('D', rd)
                                        .replace('M', rm);
                                    String arabic2 = roman2
                                        .replace('I', ri).replace('V', rv).replace('X', rx)
                                        .replace('L', rl).replace('C', rc).replace('D', rd)
                                        .replace('M', rm);
                                    String arabicSum = romanSum
                                        .replace('I', ri).replace('V', rv).replace('X', rx)
                                        .replace('L', rl).replace('C', rc).replace('D', rd)
                                        .replace('M', rm);

                                    boolean correctSum = Integer.parseInt(arabic1) + Integer.parseInt(arabic2) == Integer.parseInt(arabicSum);
                                    boolean leadingZero = arabic1.charAt(0) == '0' || arabic2.charAt(0) == '0' || arabicSum.charAt(0) == '0';
                                    if (correctSum && !leadingZero) equations.add(arabic1 + arabic2 + arabicSum);
                                    if (equations.size() > 1) return State.AMBIGUOUS;
                                }
                            }
                        }
                    }
                }
            }
        }

        return equations.size() == 1 ? State.VALID : State.IMPOSSIBLE;
    }
}
