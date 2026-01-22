package uhunt.c1.g6.p11616;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String text = in.nextLine();
            Solution solution = new Solution(text);
            String tranformed = solution.transformToArabicOrRoman();
            System.out.println(tranformed);
        }
    }
}

class Solution {
    private static final TreeMap<Integer, String> romans = new TreeMap<Integer, String>() {{
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
    private static final HashMap<String, Integer> arabics = reverse(romans);
    private final String text;

    public Solution(String text) {
        this.text = text;
    }

    public String transformToArabicOrRoman() {
        return isArabic(text) ? arabicToRoman(text) : romanToArabic(text);
    }

    private boolean isArabic(String text) {
        return isDigit(text.charAt(0));
    }

    private boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    private String arabicToRoman(String arabic) {
        int value = Integer.parseInt(arabic);
        StringBuilder roman = new StringBuilder();
        while (value > 0) {
            Map.Entry<Integer, String> entry = romans.floorEntry(value);
            value -= entry.getKey();
            roman.append(entry.getValue());
        }
        return roman.toString();
    }

    private String romanToArabic(String roman) {
        int value = 0;
        for (int i = 0; i < roman.length(); ) {
            String r2 = roman.substring(i, Math.min(i + 2, roman.length()));
            String r1 = roman.substring(i, i + 1);
            String r = arabics.containsKey(r2) ? r2 : r1;

            value += arabics.get(r);
            i += r.length();
        }
        return String.valueOf(value);
    }

    private static <K, V> HashMap<V, K> reverse(TreeMap<K, V> map) {
        HashMap<V, K> rmap = new HashMap<>(2 * map.size());
        map.forEach((k, v) -> rmap.put(v, k));
        return rmap;
    }
}
