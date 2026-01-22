package uhunt.c1.g6.p10646;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 2 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 2 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            String[] cards = new String[52];
            for (int j = 0; j < 52; j++) cards[j] = in.next();

            Solution solution = new Solution(cards);
            String card = solution.getYthCard();

            out.format("Case %d: %s\n", i + 1, card);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final String[] cards;

    public Solution(String[] cards) {
        this.cards = cards;
    }

    public String getYthCard() {
        // top-bottom card pile
        LinkedList<String> pile = new LinkedList<>();
        for (String card : cards) pile.addFirst(card);

        // pick top 25 card from pile, top-bottom card
        LinkedList<String> hand = new LinkedList<>();
        for (int i = 0; i < 25; i++) {
            String card = pile.removeFirst();
            hand.addLast(card);
        }

        // get top card, then discard the top card + N top card
        int totalValue = 0;
        for (int i = 0; i < 3; i++) {
            String card = pile.removeFirst();
            int value = Character.isDigit(card.charAt(0)) ? card.charAt(0) - '0' : 10;
            totalValue += value;
            for (int j = 0; j < 10 - value; j++) pile.removeFirst();
        }

        // put card on the hand back to the pile
        while (!hand.isEmpty()) {
            String card = hand.removeLast();
            pile.addFirst(card);
        }

        // find yth card, from bottom
        int fromTop = pile.size() - totalValue;
        return pile.get(fromTop);
    }
}
