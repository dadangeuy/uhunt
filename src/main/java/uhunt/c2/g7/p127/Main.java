package uhunt.c2.g7.p127;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

/**
 * 127 - "Accordian" Patience
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=onlinejudge&Itemid=8&page=show_problem&problem=63
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final StringBuilder out = new StringBuilder();

        final String[] cards = new String[52];

        while (true) {
            final String[] firstCardTexts = in.readLine().split(" ");
            if (firstCardTexts[0].equals("#")) break;
            final String[] secondCardTexts = in.readLine().split(" ");

            for (int i = 0; i < 26; i++) {
                cards[i] = firstCardTexts[i];
                cards[i + 26] = secondCardTexts[i];
            }

            final int[] remainingPiles = getRemainingPiles(cards);

            if (remainingPiles.length > 1) {
                out.append(remainingPiles.length);
                out.append(" piles remaining:");
            } else {
                out.append(remainingPiles.length);
                out.append(" pile remaining:");
            }
            for (int i = 0; i < remainingPiles.length; i++) out.append(' ').append(remainingPiles[i]);
            out.append('\n');
        }

        System.out.print(out);
    }

    private static int[] getRemainingPiles(final String[] cards) {
        final ArrayList<Stack<String>> piles = new ArrayList<>(52);
        for (int i = 0; i < 52; i++) {
            piles.add(new Stack<>());
            piles.get(i).add(cards[i]);
        }

        for (int i = 1; i < piles.size(); ) {
            if (piles.get(i).isEmpty()) {
                piles.remove(i);
                continue;
            }

            final String card = piles.get(i).pop();

            int target = i;
            while (true) {
                final int target3 = target - 3;
                final int target1 = target - 1;

                if (target3 >= 0 && isMoveable(card, piles.get(target3).peek())) {
                    target = target3;
                } else if (target1 >= 0 && isMoveable(card, piles.get(target1).peek())) {
                    target = target1;
                } else {
                    break;
                }
            }

            piles.get(target).add(card);
            if (i == target) {
                i++;
            } else {
                i = target;
            }
        }

        return piles.stream().mapToInt(Vector::size).toArray();
    }

    private static boolean isMoveable(final String card1, final String card2) {
        return card1.charAt(0) == card2.charAt(0) || card1.charAt(1) == card2.charAt(1);
    }
}
