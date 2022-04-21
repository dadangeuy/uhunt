package dev.rizaldi.uhunt.c1.p759;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * 759 - The Return of the Roman Empire
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=700
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextLine()) {
            String roman = in.nextLine();

            Solution solution = new Solution(roman);
            Optional<Integer> decimal = solution.convertToDecimal();

            if (decimal.isPresent()) out.println(decimal.get());
            else out.println("This is not a valid number");
        }

        in.close();
        out.flush();
        out.close();
    }
}

/**
 * A smaller number in front of a larger number means subtraction, all else means addition. For example, IV means 4, VI means 6.
 * You would not put more than one smaller number in front of a larger number to subtract. For example, IIV would not mean 3.
 * You must separate ones, tens, hundreds, and thousands as separate items. That means that 99 is XCIX, 90 + 9, but never should be written as IC. Similarly, 999 cannot be IM and 1999 cannot be MIM.
 * <p>
 * I	The numeral one. II is two, III is three. You seldom see IIII as 4, since IV can also mean 4, plus its shorter to write.
 * V	The numeral 5. IV is 4, VI is 6, VII is 7, VIII is 8.
 * X	The numeral 10. IX is 9, XI is 11, etc.
 * L	The numeral 50. XL would be 40.
 * C	The numeral 100. Think of Century having a hundred years. C is short for the Latin word Centum, but that's not very easy to remember.
 * D	The numeral 500.
 * M	The numeral 1000.
 * <p>
 * Sometimes you will see a numeral with a line over it. That means to multiply it by 1000. A numeral V with a line over it means 5000.
 */
class Solution {
    private static final Map<String, Integer> DECIMAL_PER_ROMAN = new HashMap<String, Integer>() {{
        put("I", 1);
        put("V", 5);
        put("X", 10);
        put("L", 50);
        put("C", 100);
        put("D", 500);
        put("M", 1000);
        put("IV", 4);
        put("IX", 9);
        put("XL", 40);
        put("XC", 90);
        put("CD", 400);
        put("CM", 900);
    }};
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
    private static final String[] ROMANS = DECIMAL_PER_ROMAN.keySet().toArray(new String[0]);

    private final String roman;

    public Solution(String roman) {
        this.roman = roman;
    }

    public Optional<Integer> convertToDecimal() {
        try {
            validate(roman);
            int decimal = romanToDecimal(roman);
            String expectedRoman = decimalToRoman(decimal);
            return roman.equals(expectedRoman) ? Optional.of(decimal) : Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private int romanToDecimal(String roman) throws Exception {
        int decimal = 0;
        for (int i = 0; i < roman.length(); ) {
            if (i + 2 <= roman.length() && DECIMAL_PER_ROMAN.containsKey(roman.substring(i, i + 2))) {
                decimal += DECIMAL_PER_ROMAN.get(roman.substring(i, i + 2));
                i += 2;
            } else if (DECIMAL_PER_ROMAN.containsKey(roman.substring(i, i + 1))) {
                decimal += DECIMAL_PER_ROMAN.get(roman.substring(i, i + 1));
                i += 1;
            } else {
                throw new Exception("unknown roman letter");
            }
        }
        return decimal;
    }

    private String decimalToRoman(int decimal) {
        StringBuilder sb = new StringBuilder();
        while (decimal > 0) {
            Map.Entry<Integer, String> entry = ROMAN_PER_DECIMAL.floorEntry(decimal);
            decimal -= entry.getKey();
            sb.append(entry.getValue());
        }
        return sb.toString();
    }

    private void validate(String roman) throws Exception {
        // case repeat more than 3 times
        for (String romanLetter : ROMANS) {
            String repeated = romanLetter + romanLetter + romanLetter + romanLetter;
            if (roman.contains(repeated)) throw new Exception("repeat more than 3 times");
        }
    }
}
