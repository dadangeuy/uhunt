package uva.c1.g4.p344;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    /*
    general view of the algorithm:
    1. convert integer to roman (string)
    2. convert each integer, from 1..n to roman
    3. count each roman character appearance using a map

    specific implementation:
    1. how to implement integer to roman?
      1. create a map, containing roman of each integer (integer[key] -> roman[value])
      2. while our target integer was bigger than 0
        1. take highest value (<=target) we can get from integer->roman map
        2. append roman
        2. reduce target by the integer value
    2. how to count roman character?
      1. create a character counter map (character[key] -> count[value])
      2. loop each character
      3. increment counter for that character (map[character]++)
    */
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            int totalPages = in.nextInt();
            if (totalPages == 0) break;

            Solution solution = new Solution(totalPages);
            int[] counts = solution.countCharacter();

            System.out.format(
                    "%d: %d i, %d v, %d x, %d l, %d c\n",
                    totalPages, counts[0], counts[1], counts[2], counts[3], counts[4]
            );
        }
    }
}

class Solution {
    private static final TreeMap<Integer, String> ROMAN_PER_INTEGER = new TreeMap<Integer, String>() {{
        put(1, "i");
        put(5, "v");
        put(10, "x");
        put(50, "l");
        put(100, "c");
        put(4, "iv");
        put(9, "ix");
        put(40, "xl");
        put(90, "xc");
    }};
    private final int totalPages;

    public Solution(int totalPages) {
        this.totalPages = totalPages;
    }

    public int[] countCharacter() {
        int[] counts = new int[5];

        for (int page = 1; page <= totalPages; page++) {
            String roman = integerToRoman(page);
            for (char c : roman.toCharArray()) {
                int id = getRomanID(c);
                counts[id]++;
            }
        }

        return counts;
    }

    private String integerToRoman(int integer) {
        StringBuilder roman = new StringBuilder();
        while (integer > 0) {
            Map.Entry<Integer, String> highestEntry = ROMAN_PER_INTEGER.floorEntry(integer);
            int highestInteger = highestEntry.getKey();
            String highestRoman = highestEntry.getValue();

            integer -= highestInteger;
            roman.append(highestRoman);
        }
        return roman.toString();
    }

    private int getRomanID(char c) {
        switch (c) {
            case 'i':
                return 0;
            case 'v':
                return 1;
            case 'x':
                return 2;
            case 'l':
                return 3;
            case 'c':
                return 4;
        }
        return -1;
    }
}
