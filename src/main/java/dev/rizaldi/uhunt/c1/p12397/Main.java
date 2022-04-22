package dev.rizaldi.uhunt.c1.p12397;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * 12397 - Roman Numerals
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3828
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        while (in.hasNextInt()) {
            int decimal = in.nextInt();

            Solution solution = new Solution(decimal);
            int totalMatch = solution.getTotalMatchInLusiversFont();

            out.println(totalMatch);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final TreeMap<Integer, String> ROMAN_PER_DECIMAL = new TreeMap<Integer, String>() {{
        put(1, "I");
        put(5, "V");
        put(10, "X");
        put(50, "L");
        put(100, "C");
        put(500, "D");
        put(1000, "M");
        put(4, "IV");
        put(9, "IX");
        put(40, "XL");
        put(90, "XC");
        put(400, "CD");
        put(900, "CM");
    }};
    private static final Map<Character, Integer> MATCH_PER_ROMAN = new HashMap<Character, Integer>(14) {{
        put('I', 1);
        put('V', 2);
        put('X', 2);
        put('L', 2);
        put('C', 2);
        put('D', 3);
        put('M', 4);
    }};

    private final int decimal;

    public Solution(int decimal) {
        this.decimal = decimal;
    }

    public int getTotalMatchInLusiversFont() {
        String roman = decimalToRoman(decimal);
        return countMatch(roman);
    }

    private String decimalToRoman(int decimal) {
        StringBuilder roman = new StringBuilder();
        while (decimal > 0) {
            Map.Entry<Integer, String> entry = ROMAN_PER_DECIMAL.floorEntry(decimal);
            decimal -= entry.getKey();
            roman.append(entry.getValue());
        }
        return roman.toString();
    }

    private int countMatch(String roman) {
        int count = 0;
        for (char letter : roman.toCharArray()) count += MATCH_PER_ROMAN.get(letter);
        return count;
    }
}
