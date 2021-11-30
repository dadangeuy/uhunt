package dev.rizaldi.uhunt.c2.p10887;

import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int totalTest = in.nextInt();
        for (int test = 0; test < totalTest; test++) {
            int totalWord1 = in.nextInt();
            int totalWord2 = in.nextInt();
            in.nextLine();

            String[] words1 = new String[totalWord1];
            String[] words2 = new String[totalWord2];

            for (int i = 0; i < totalWord1; i++) words1[i] = in.nextLine();
            for (int i = 0; i < totalWord2; i++) words2[i] = in.nextLine();

            Solution solution = new Solution(words1, words2);
            int total = solution.countConcat();

            System.out.format("Case %d: %d\n", test + 1, total);
        }
    }
}

class Solution {
    private final String[] words1;
    private final String[] words2;

    public Solution(String[] words1, String[] words2) {
        this.words1 = words1;
        this.words2 = words2;
    }

    public int countConcat() {
        HashSet<String> concatWords = new HashSet<>();
        for (String word1 : words1) {
            for (String word2 : words2) {
                String word3 = word1 + word2;
                concatWords.add(word3);
            }
        }

        return concatWords.size();
    }
}
