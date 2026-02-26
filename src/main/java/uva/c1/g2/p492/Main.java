package uva.c1.g2.p492;

import java.io.IOException;
import java.util.Arrays;

/**
 * 492 - Pig-Latin
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=433
 */
public class Main {
    public static void main(String... args) throws IOException {
        byte[] buffer = new byte[1 << 24];
        int length = System.in.read(buffer);
        String text = new String(buffer, 0, length);

        Solution solution = new Solution(text);
        String pigLatinText = solution.toPigLatin();

        System.out.print(pigLatinText);

        System.in.close();
        System.out.flush();
        System.out.close();
    }
}

class Solution {
    private static final char[] vowels = new char[]{'A', 'E', 'I', 'O', 'U', 'a', 'e', 'i', 'o', 'u'};
    private final String text;

    public Solution(String text) {
        this.text = text;
    }

    public String toPigLatin() {
        StringBuilder pigLatin = new StringBuilder();
        for (int i = 0; i < text.length(); ) {
            if (alphabet(text.charAt(i))) {
                int j = i + 1;
                while (j < text.length() && alphabet(text.charAt(j))) j++;

                String word = text.substring(i, j);
                if (vowel(word.charAt(0))) pigLatin.append(word).append("ay");
                else pigLatin.append(word.substring(1)).append(word.charAt(0)).append("ay");

                i = j;
            } else {
                pigLatin.append(text.charAt(i));
                i++;
            }
        }

        return pigLatin.toString();
    }

    private boolean alphabet(char letter) {
        return ('a' <= letter && letter <= 'z') || ('A' <= letter && letter <= 'Z');
    }

    private boolean vowel(char letter) {
        int index = Arrays.binarySearch(vowels, letter);
        return 0 <= index && index < vowels.length;
    }
}
