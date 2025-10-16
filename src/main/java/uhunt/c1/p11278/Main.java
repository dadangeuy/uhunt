package uhunt.c1.p11278;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 11278 - One-Handed Typist
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2253
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextLine()) {
            String qwertyText = in.nextLine();

            Solution solution = new Solution(qwertyText);
            String dvorakText = solution.convertToDvorak();

            out.println(dvorakText);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final char[][] LAYOUT_QWERTY = new char[][]{
        "`1234567890-=\b".toCharArray(),
        "\tqwertyuiop[]\\".toCharArray(),
        "asdfghjkl;'\n".toCharArray(),
        "zxcvbnm,./".toCharArray(),
        " ".toCharArray(),
    };
    private static final char[][] LAYOUT_SHIFT_QWERTY = new char[][]{
        "~!@#$%^&*()_+\b".toCharArray(),
        "\tQWERTYUIOP{}|".toCharArray(),
        "ASDFGHJKL:\"\n".toCharArray(),
        "ZXCVBNM<>?".toCharArray(),
        " ".toCharArray(),
    };
    private static final char[][] LAYOUT_DVORAK = new char[][]{
        "`123qjlmfp/[]\b".toCharArray(),
        "\t456.orsuyb;=\\".toCharArray(),
        "789aehtdck-\n".toCharArray(),
        "0zx,inwvg'".toCharArray(),
        " ".toCharArray(),
    };
    private static final char[][] LAYOUT_SHIFT_DVORAK = new char[][]{
        "~!@#QJLMFP?{}\b".toCharArray(),
        "\t$%^>ORSUYB:+|".toCharArray(),
        "&*(AEHTDCK_\n".toCharArray(),
        ")ZX<INWVG\"".toCharArray(),
        " ".toCharArray(),
    };
    private static final Map<Character, Character> qwertyToDvorak = mapKeys(LAYOUT_QWERTY, LAYOUT_DVORAK);
    private static final Map<Character, Character> shiftQwertyToShiftDvorak = mapKeys(LAYOUT_SHIFT_QWERTY, LAYOUT_SHIFT_DVORAK);

    private final String qwertyText;

    public Solution(String qwertyText) {
        this.qwertyText = qwertyText;
    }

    private static Map<Character, Character> mapKeys(char[][] layout1, char[][] layout2) {
        Map<Character, Character> keys = new HashMap<>(150);
        for (int i = 0; i < layout1.length; i++) {
            for (int j = 0; j < layout1[i].length; j++) {
                keys.put(layout1[i][j], layout2[i][j]);
            }
        }
        return keys;
    }

    public String convertToDvorak() {
        StringBuilder dvorakText = new StringBuilder(qwertyText.length());
        for (char qwertyLetter : qwertyText.toCharArray()) {
            char dvorakLetter = qwertyToDvorak.getOrDefault(qwertyLetter, shiftQwertyToShiftDvorak.get(qwertyLetter));
            dvorakText.append(dvorakLetter);
        }
        return dvorakText.toString();
    }
}
