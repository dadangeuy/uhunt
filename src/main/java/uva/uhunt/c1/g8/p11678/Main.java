package uva.uhunt.c1.g8.p11678;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int totalAliceCard = in.nextInt();
            int totalBettyCard = in.nextInt();
            if (totalAliceCard == 0 && totalBettyCard == 0) break;

            int[] aliceCards = new int[totalAliceCard];
            for (int i = 0; i < totalAliceCard; i++) aliceCards[i] = in.nextInt();

            int[] bettyCards = new int[totalBettyCard];
            for (int i = 0; i < totalBettyCard; i++) bettyCards[i] = in.nextInt();

            Solution solution = new Solution(totalAliceCard, totalBettyCard, aliceCards, bettyCards);
            int maximumTrade = solution.getMaximumUniqueTrade();

            out.println(maximumTrade);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int totalAliceCard;
    private final int totalBettyCard;
    private final int[] aliceCards;
    private final int[] bettyCards;

    public Solution(int totalAliceCard, int totalBettyCard, int[] aliceCards, int[] bettyCards) {
        this.totalAliceCard = totalAliceCard;
        this.totalBettyCard = totalBettyCard;
        this.aliceCards = aliceCards;
        this.bettyCards = bettyCards;
    }

    public int getMaximumUniqueTrade() {
        Set<Integer> uac = Arrays.stream(aliceCards).boxed().collect(Collectors.toSet());
        Set<Integer> ubc = Arrays.stream(bettyCards).boxed().collect(Collectors.toSet());

        for (int bettyCard : bettyCards) uac.remove(bettyCard);
        for (int aliceCard : aliceCards) ubc.remove(aliceCard);

        return Math.min(uac.size(), ubc.size());
    }
}
