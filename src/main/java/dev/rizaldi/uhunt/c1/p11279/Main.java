package dev.rizaldi.uhunt.c1.p11279;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextLine()) {
            String text = in.nextLine();

            Solution solution = new Solution(text);
            double qwerty2h = solution.getDistanceForQwertyTwoHand();
            double qwerty1h = solution.getDistanceForQwertyOneHand();
            double dvorak = solution.getDistanceForDvorak();

            out.format("%.2f %.2f %.2f\n", qwerty2h, qwerty1h, dvorak);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    // layout
    private static final char[][] LAYOUT_QWERTY = new char[][]{
        "`1234567890-=".toCharArray(),
        " qwertyuiop[]\\".toCharArray(),
        " asdfghjkl;'".toCharArray(),
        " zxcvbnm,./".toCharArray()
    };
    private static final char[][] LAYOUT_SHIFT_QWERTY = new char[][]{
        "~!@#$%^&*()_+".toCharArray(),
        " QWERTYUIOP{}|".toCharArray(),
        " ASDFGHJKL:\"".toCharArray(),
        " ZXCVBNM<>?".toCharArray()
    };
    private static final char[][] LAYOUT_DVORAK = new char[][]{
        "`123qjlmfp/[]".toCharArray(),
        " 456.orsuyb;=\\".toCharArray(),
        " 789aehtdck-".toCharArray(),
        " 0zx,inwvg'".toCharArray()
    };
    private static final char[][] LAYOUT_SHIFT_DVORAK = new char[][]{
        "~!@#QJLMFP?{}".toCharArray(),
        " $%^>ORSUYB:+|".toCharArray(),
        " &*(AEHTDCK_".toCharArray(),
        " )ZX<INWVG\"".toCharArray()
    };

    // key position
    private static final Map<Character, int[]> QWERTY_POSITIONS = new HashMap<Character, int[]>() {{
        putAll(mapKey(LAYOUT_QWERTY));
        putAll(mapKey(LAYOUT_SHIFT_QWERTY));
    }};
    private static final Map<Character, int[]> DVORAK_POSITIONS = new HashMap<Character, int[]>() {{
        putAll(mapKey(LAYOUT_DVORAK));
        putAll(mapKey(LAYOUT_SHIFT_DVORAK));
    }};

    // home
    private static final char[] HOME_QWERTY_TWO_HAND = "ASDFJKL;".toCharArray();
    private static final char[] HOME_QWERTY_ONE_HAND = "FGHJ".toCharArray();
    private static final char[] HOME_DVORAK = "EHTD".toCharArray();

    private final String text;

    public Solution(String text) {
        this.text = text;
    }

    private static Map<Character, int[]> mapKey(char[][] layout) {
        Map<Character, int[]> positions = new HashMap<>();

        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                char key = layout[i][j];
                if (key == ' ') continue;

                int[] position = new int[]{i, j};
                positions.put(key, position);
            }
        }

        return positions;
    }

    public double getDistanceForQwertyTwoHand() {
        return getTotalDistance(text, HOME_QWERTY_TWO_HAND, QWERTY_POSITIONS);
    }

    public double getDistanceForQwertyOneHand() {
        return getTotalDistance(text, HOME_QWERTY_ONE_HAND, QWERTY_POSITIONS);
    }

    public double getDistanceForDvorak() {
        return getTotalDistance(text, HOME_DVORAK, DVORAK_POSITIONS);
    }

    private String removeSpaceAndShift(String text) {
        return text.toLowerCase().replaceAll(" ", "");
    }

    private double getTotalDistance(String text, char[] homeKeys, Map<Character, int[]> keyPositions) {
        String cleanText = removeSpaceAndShift(text);

        double totalDistance = 0;
        for (char targetKey : cleanText.toCharArray()) {
            int[] targetPosition = keyPositions.get(targetKey);
            double minDistance = Double.MAX_VALUE;

            for (char homeKey : homeKeys) {
                int[] homePosition = keyPositions.get(homeKey);
                double distance = keyDistance(targetPosition, homePosition);
                minDistance = Math.min(minDistance, distance);
            }
            totalDistance += minDistance;
        }

        return totalDistance;
    }

    private double keyDistance(int[] p1, int[] p2) {
        return euclideanDistance(p1, p2) * 2;
    }

    private double euclideanDistance(int[] p1, int[] p2) {
        int d1 = p1[0] - p2[0];
        int d2 = p1[1] - p2[1];
        return Math.sqrt(d1 * d1 + d2 * d2);
    }
}
