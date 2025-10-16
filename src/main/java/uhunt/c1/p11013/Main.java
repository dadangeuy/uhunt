package uhunt.c1.p11013;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            String[] cards = in.nextLine().split(" ");
            if (cards[0].equals("#")) break;

            Solution solution = new Solution(cards);
            Optional<String> opCard = solution.getOptimalExchangeCard();

            if (opCard.isPresent()) out.format("Exchange %s\n", opCard.get());
            else out.println("Stay");
        }

        in.close();
        out.flush();
        out.close();
    }
}

/**
 * for all possible swap 1-card combination,
 * - count straight
 * - count invite the neighbours
 * - count bed and breakfast
 * - count manage a trois
 * - count double dutch
 * - count dummy
 * <p>
 * expected earning: (100*countS + 10*countITN + 5*countBAB + 3*countMAT + 1*countDD) / 47
 */
class Solution {
    private static final int STRAIGHT = 100;
    private static final int INVITE_THE_NEIGHBOURS = 10;
    private static final int BED_AND_BREAKFAST = 5;
    private static final int MENAGE_A_TROIS = 3;
    private static final int DOUBLE_DUTCH = 1;
    private static final String[] cardValues = new String[]{"A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K"};
    private static final String[] cardSuites = new String[]{"S", "H", "D", "C"};
    private final String[] cards;

    public Solution(String[] cards) {
        this.cards = cards;
    }

    public Optional<String> getOptimalExchangeCard() {
        String maxCard = null;
        double maxSwapEarning = -2;

        // swap i-th card
        for (int i = 0; i < 5; i++) {
            int countS = 0;
            int countITN = 0;
            int countBAB = 0;
            int countMAT = 0;
            int countDD = 0;

            // swap with deck card
            for (String cv : cardValues) {
                for (String cs : cardSuites) {
                    String newCard = cv + cs;
                    if (contains(newCard, cards)) continue;

                    // replace
                    String oldCard = cards[i];
                    cards[i] = newCard;

                    int payment = calculatePayment(cards);
                    switch (payment) {
                        case STRAIGHT:
                            countS++;
                            break;
                        case INVITE_THE_NEIGHBOURS:
                            countITN++;
                            break;
                        case BED_AND_BREAKFAST:
                            countBAB++;
                            break;
                        case MENAGE_A_TROIS:
                            countMAT++;
                            break;
                        case DOUBLE_DUTCH:
                            countDD++;
                            break;
                    }

                    // put-back
                    cards[i] = oldCard;
                }
            }

            double swapEarning = (STRAIGHT * countS +
                INVITE_THE_NEIGHBOURS * countITN +
                BED_AND_BREAKFAST * countBAB +
                MENAGE_A_TROIS * countMAT +
                DOUBLE_DUTCH * countDD
            ) / 47.0 - 2;
            if (swapEarning > maxSwapEarning) {
                maxSwapEarning = swapEarning;
                maxCard = cards[i];
            }
        }

        int initialEarning = calculatePayment(cards) - 1;
        return maxSwapEarning > initialEarning ? Optional.ofNullable(maxCard) : Optional.empty();
    }

    private boolean contains(String target, String... values) {
        for (String v : values) if (v.equals(target)) return true;
        return false;
    }

    private int calculatePayment(String... cards) {
        int maxPayment = 0;

        for (int c1 = 0; c1 < 5; c1++) {
            for (int c2 = 0; c2 < 5; c2++) {
                if (c2 == c1) continue;

                for (int c3 = 0; c3 < 5; c3++) {
                    if (c3 == c2 || c3 == c1) continue;

                    boolean menageATrois = inc(cards[c1], cards[c2], cards[c3]);
                    if (menageATrois) maxPayment = Math.max(maxPayment, MENAGE_A_TROIS);

                    for (int c4 = 0; c4 < 5; c4++) {
                        if (c4 == c3 || c4 == c2 || c4 == c1) continue;

                        boolean inviteTheNeighbours = inc(cards[c1], cards[c2], cards[c3], cards[c4]);
                        if (inviteTheNeighbours) maxPayment = Math.max(maxPayment, INVITE_THE_NEIGHBOURS);

                        boolean doubleDutch = inc(cards[c1], cards[c2]) && inc(cards[c3], cards[c4]);
                        if (doubleDutch) maxPayment = Math.max(maxPayment, DOUBLE_DUTCH);

                        for (int c5 = 0; c5 < 5; c5++) {
                            if (c5 == c4 || c5 == c3 || c5 == c2 || c5 == c1) continue;

                            boolean straight = inc(cards[c1], cards[c2], cards[c3], cards[c4], cards[c5]);
                            if (straight) maxPayment = Math.max(maxPayment, STRAIGHT);

                            boolean bedAndBreakfast = inc(cards[c1], cards[c2], cards[c3]) && inc(cards[c4], cards[c5]);
                            if (bedAndBreakfast) maxPayment = Math.max(maxPayment, BED_AND_BREAKFAST);
                        }
                    }
                }
            }
        }

        return maxPayment;
    }

    private boolean inc(String... cards) {
        int prevValue = value(cards[0]);
        for (int i = 1; i < cards.length; i++) {
            int nextValue = value(cards[i]);
            boolean increasing = (prevValue + 1 == nextValue) || (prevValue - 12 == nextValue);
            if (!increasing) return false;

            prevValue = nextValue;
        }
        return true;
    }

    private int value(String card) {
        char value = card.charAt(0);
        switch (value) {
            case 'A':
                return 1;
            case 'T':
                return 10;
            case 'J':
                return 11;
            case 'Q':
                return 12;
            case 'K':
                return 0;
            default:
                return value - '0';
        }
    }
}
