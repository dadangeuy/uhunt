package uva.uhunt.c2.g9.p499;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while ((line = in.readLine()) != null && !line.isEmpty()) {
            Solution solution = new Solution(line);

            Pair<String, Integer> frequentLetter = solution.getMostFrequentLetter();

            System.out.format("%s %d\n", frequentLetter.first, frequentLetter.second);
        }
    }
}

class Pair<K, V> {
    public final K first;
    public final V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }
}

class Solution {
    private static final int[] counts = new int[52];
    private final String text;

    public Solution(String text) {
        this.text = text;

        Arrays.fill(counts, 0);
    }

    public Pair<String, Integer> getMostFrequentLetter() {
        countLetter();
        int maxCount = maxCount();

        StringBuilder letters = new StringBuilder();
        for (int hash = 0; hash < 52; hash++) {
            int count = counts[hash];
            if (count < maxCount) continue;

            char letter = reverseHash(hash);
            letters.append(letter);
        }

        return new Pair<>(letters.toString(), maxCount);
    }

    private void countLetter() {
        for (char letter : text.toCharArray()) {
            if (!Character.isAlphabetic(letter)) continue;
            counts[hash(letter)]++;
        }
    }

    private int hash(char letter) {
        if ('A' <= letter && letter <= 'Z') return letter - 'A';
        if ('a' <= letter && letter <= 'z') return letter - 'a' + 26;
        return 0;
    }

    private char reverseHash(int hash) {
        if (0 <= hash && hash < 26) return (char) (hash + 'A');
        if (26 <= hash && hash < 52) return (char) (hash - 26 + 'a');
        return 0;
    }

    private int maxCount() {
        int max = 0;
        for (int count : counts) max = Math.max(max, count);
        return max;
    }
}
