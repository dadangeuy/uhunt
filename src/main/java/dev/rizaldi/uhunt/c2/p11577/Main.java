package dev.rizaldi.uhunt.c2.p11577;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static BufferedReader in;
    private static StringBuilder out;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        int totalText = Integer.parseInt(in.readLine());
        for (int i = 0; i < totalText; i++) {
            String text = in.readLine();
            Solution solution = new Solution(text);
            List<Character> letters = solution.getMostFrequentLetters();
            letters.forEach(letter -> out.append(letter));
            out.append('\n');
        }

        System.out.print(out);
    }
}

class Solution {
    private static final DirectAdressTable countPerLetter = new DirectAdressTable();
    private final String text;

    public Solution(String text) {
        this.text = text;
    }

    public List<Character> getMostFrequentLetters() {
        countPerLetter.clear();

        for (char letter : text.toLowerCase().toCharArray()) {
            boolean alphabet = 'a' <= letter && letter <= 'z';
            if (!alphabet) continue;

            int count = countPerLetter.get(letter);
            countPerLetter.set(letter, count + 1);
        }

        LinkedList<Character> letters = new LinkedList<>();
        int max = countPerLetter.max();
        for (char letter = 'a'; letter <= 'z'; letter++) {
            int count = countPerLetter.get(letter);
            if (count == max) letters.addLast(letter);
        }

        return letters;
    }
}

class DirectAdressTable {
    private final int[] tables;

    public DirectAdressTable() {
        this.tables = new int[26];
    }

    public void set(char key, int value) {
        tables[address(key)] = value;
    }

    public int get(char key) {
        return tables[address(key)];
    }

    public int address(char key) {
        return key - 'a';
    }

    public int max() {
        int max = 0;
        for (int value : tables) max = Math.max(max, value);
        return max;
    }

    public void clear() {
        Arrays.fill(tables, 0);
    }
}
