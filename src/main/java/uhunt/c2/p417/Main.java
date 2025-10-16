package uhunt.c2.p417;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * 417 - Word Index
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=358
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNext()) {
            String word = in.next();

            Solution solution = new Solution(word);
            int number = solution.decodeNumber();

            out.println(number);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final int MAX_NUMBER = 83681;
    private static final Map<String, Integer> NUMBER_PER_WORD = findNumberPerWord();
    private final String word;

    public Solution(String word) {
        this.word = word;
    }

    public int decodeNumber() {
        return NUMBER_PER_WORD.getOrDefault(word, 0);
    }

    private static Map<String, Integer> findNumberPerWord() {
        LinkedList<String> words = new LinkedList<>();
        for (int length = 1; length <= 5; length++) {
            appendWords(new char[length], 0, words);
        }

        Map<String, Integer> numberPerWord = new HashMap<>(MAX_NUMBER * 2);
        for (String word : words) {
            numberPerWord.put(word, numberPerWord.size() + 1);
        }

        return numberPerWord;
    }

    private static void appendWords(char[] word, int charAt, List<String> words) {
        if (charAt == word.length) {
            words.add(new String(word));
            return;
        }

        char firstLetter = charAt == 0 ? 'a' : increment(word[charAt - 1]);
        for (char letter = firstLetter; letter <= 'z'; letter++) {
            word[charAt] = letter;
            appendWords(word, charAt + 1, words);
        }
    }

    private static char increment(char c) {
        return (char) (c + 1);
    }
}
