package dev.rizaldi.uhunt.c1.p10205;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 10205 - Stack 'em Up
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1146
 */
public class Main {
    public static void main(String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        final int totalTestCases = in.nextInt();
        for (int i = 0; i < totalTestCases; i++) {
            final int totalPossibleShuffles = in.nextInt();
            final int[][] possibleShuffles = new int[totalPossibleShuffles][52];
            for (int j = 0; j < totalPossibleShuffles; j++) {
                for (int k = 0; k < 52; k++) {
                    possibleShuffles[j][k] = in.nextInt();
                }
            }
            in.nextLine();

            final Deck deck = new Deck();
            String nextLine = in.nextLine();
            while (!nextLine.isEmpty()) {
                final int shuffleId = Integer.parseInt(nextLine);
                final int[] shuffles = possibleShuffles[shuffleId - 1];
                deck.shuffleCards(shuffles);

                nextLine = in.hasNextLine() ? in.nextLine() : "";
            }

            deck.getCards().forEach(card -> out.println(card.getName()));
            if (i + 1 < totalTestCases) out.println();
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Deck {
    private final List<Card> cards = new ArrayList<>(52);

    public Deck() {
        Card.CARD_SUITS.forEach(cardSuit ->
                Card.CARD_VALUES.forEach(cardValue ->
                        cards.add(new Card(cardValue, cardSuit))));
    }

    public List<Card> getCards() {
        return cards;
    }

    public void shuffleCards(int[] shuffles) {
        final List<Card> shuffledCards = new ArrayList<>(52);
        for (int shuffle : shuffles) {
            shuffledCards.add(cards.get(shuffle - 1));
        }

        this.cards.clear();
        this.cards.addAll(shuffledCards);
    }
}

class Card {
    public static final List<String> CARD_VALUES = Arrays.asList(
            "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "Jack", "Queen", "King", "Ace"
    );
    public static final List<String> CARD_SUITS = Arrays.asList(
            "Clubs", "Diamonds", "Hearts", "Spades"
    );

    private final String value;
    private final String suit;

    public Card(String value, String suit) {
        this.value = value;
        this.suit = suit;
    }

    public String getName() {
        return String.format("%s of %s", value, suit);
    }
}
