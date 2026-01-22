package uhunt.c1.g7.p11787;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * 11787 - Numeral Hieroglyphs
 * Time limit: ? seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2887
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            String hieroglyph = in.next();

            Solution solution = new Solution(hieroglyph);
            Optional<Integer> decimal = solution.toDecimal();

            if (decimal.isPresent()) out.println(decimal.get());
            else out.println("error");
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final char[] symbols = "BUSPFTM".toCharArray();
    private static final Map<Character, Integer> decimalPerSymbol = new HashMap<Character, Integer>() {{
        put('B', 1);
        put('U', 10);
        put('S', 100);
        put('P', 1_000);
        put('F', 10_000);
        put('T', 100_000);
        put('M', 1_000_000);
    }};

    private final String hieroglyph;

    public Solution(String hieroglyph) {
        this.hieroglyph = hieroglyph;
    }

    public Optional<Integer> toDecimal() {
        if (notOrdered() || moreThanNineEqualCharacter()) return Optional.empty();

        int decimal = 0;
        for (char symbol : hieroglyph.toCharArray()) {
            decimal += decimalPerSymbol.get(symbol);
        }

        return Optional.of(decimal);
    }

    private boolean notOrdered() {
        return !increasing() && !decreasing();
    }

    private boolean increasing() {
        for (int i = 0; i < hieroglyph.length() - 1; i++) {
            char prev = hieroglyph.charAt(i);
            char next = hieroglyph.charAt(i + 1);
            boolean increasing = decimalPerSymbol.get(prev) <= decimalPerSymbol.get(next);
            if (!increasing) return false;
        }
        return true;
    }

    private boolean decreasing() {
        for (int i = 0; i < hieroglyph.length() - 1; i++) {
            char prev = hieroglyph.charAt(i);
            char next = hieroglyph.charAt(i + 1);
            boolean increasing = decimalPerSymbol.get(prev) >= decimalPerSymbol.get(next);
            if (!increasing) return false;
        }
        return true;
    }

    private boolean moreThanNineEqualCharacter() {
        int maxCount = 0;
        for (char symbol : symbols) {
            int count = count(hieroglyph.toCharArray(), symbol);
            maxCount = Math.max(maxCount, count);
        }
        return maxCount > 9;
    }

    private int count(char[] array, char target) {
        int count = 0;
        for (char letter : array) if (letter == target) count++;
        return count;
    }
}
