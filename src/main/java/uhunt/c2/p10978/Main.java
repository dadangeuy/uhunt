package uhunt.c2.p10978;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 10978 - Let's Play Magic!
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1919
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int totalCard = in.nextInt();
            if (totalCard == 0) break;

            Card[] cards = new Card[totalCard];
            for (int i = 0; i < totalCard; i++) {
                String name = in.next();
                String word = in.next();

                Card card = new Card(name, word);
                cards[i] = card;
            }

            Solution solution = new Solution(totalCard, cards);
            Card[] magicCards = solution.getMagicCards();

            out.append(magicCards[0].name);
            for (int i = 1; i < totalCard; i++) out.append(' ').append(magicCards[i].name);
            out.append('\n');
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Card {
    public final String name;
    public final String word;

    public Card(String name, String word) {
        this.name = name;
        this.word = word;
    }
}

class Solution {
    private final int totalCard;
    private final Card[] cards;

    public Solution(int totalCard, Card[] cards) {
        this.totalCard = totalCard;
        this.cards = cards;
    }

    public Card[] getMagicCards() {
        Card[] magicCards = new Card[totalCard];
        int position = totalCard - 1;
        for (Card card : cards) {
            int increment = card.word.length();
            while (increment > 0) {
                position = (position + 1) % totalCard;
                if (magicCards[position] == null) increment--;
            }
            magicCards[position] = card;
        }

        return magicCards;
    }
}
