package dev.rizaldi.uhunt.c1.p12247;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int[] princessCards = new int[3];
            for (int i = 0; i < 3; i++) princessCards[i] = in.nextInt();

            int[] princeCard = new int[2];
            for (int i = 0; i < 2; i++) princeCard[i] = in.nextInt();

            if (all(princessCards, 0) && all(princeCard, 0)) break;

            Solution solution = new Solution(princessCards, princeCard);
            int card = solution.getMinimumWinningCardForPrince();

            out.println(card);
        }

        in.close();
        out.flush();
        out.close();
    }

    private static boolean all(int[] array, int target) {
        for (int value : array) if (value != target) return false;
        return true;
    }
}

class Solution {
    private final int[] princessCards;
    private final int[] princeCards;

    public Solution(int[] princessCards, int[] princeCards) {
        this.princessCards = princessCards;
        this.princeCards = princeCards;
    }

    public int getMinimumWinningCardForPrince() {
        for (int card = 1; card <= 52; card++) {
            if (any(princessCards, card) || any(princeCards, card)) continue;
            boolean win = sureWin(new int[]{princeCards[0], princeCards[1], card}, princessCards);
            if (win) return card;
        }
        return -1;
    }

    private boolean sureWin(int[] cards, int[] enemyCards) {
        Arrays.sort(cards);
        Arrays.sort(enemyCards);

        boolean loseInWorstCase = cards[1] < enemyCards[2] && cards[0] < enemyCards[1];
        return !loseInWorstCase;
    }

    private boolean any(int[] array, int target) {
        for (int value : array) if (value == target) return true;
        return false;
    }
}
