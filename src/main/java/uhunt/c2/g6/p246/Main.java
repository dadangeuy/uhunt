package uhunt.c2.g6.p246;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 246 - 10-20-30
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=182
 */
public class Main {
    public static void main(String... args) throws IOException {
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        while (true) {
            int[] cards = new int[52];
            cards[0] = in.nextInt();
            if (cards[0] == 0) break;
            for (int i = 1; i < 52; i++) cards[i] = in.nextInt();

            Game game = new Game(cards);
            game.initiate();
            while (!game.isEnd()) {
                game.play();
            }

            if (game.isWin()) {
                out.append(String.format("Win : %d\n", game.getStep()));
            } else if (game.isLoss()) {
                out.append(String.format("Loss: %d\n", game.getStep()));
            } else if (game.isDraw()) {
                out.append(String.format("Draw: %d\n", game.getStep()));
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Game {
    private final Set<String> states = new HashSet<>();
    private final Pile deck = new Pile();
    private final Pile[] piles = new Pile[7];
    private int step;
    private int pileId;

    public Game(int[] cards) {
        for (int card : cards) deck.addBottom(card);
    }

    public void initiate() {
        for (int k = 0; k < 7; k++) {
            piles[k] = new Pile();
            piles[k].addBottom(deck.removeTop());
        }
        step = 7;
        pileId = 6;
    }

    public void play() {
        step++;
        do {
            pileId = (pileId + 1) % 7;
        } while (piles[pileId].isEmpty());
        Pile pile = piles[pileId];

        pile.addBottom(deck.removeTop());

        while (true) {
            int[] removedCards = pile.removeCombination();
            for (int removedCard : removedCards) deck.addBottom(removedCard);
            if (removedCards.length == 0) break;
        }
    }

    public boolean isEnd() {
        return (isWin() || isLoss() || isDraw());
    }

    public boolean isWin() {
        boolean win = true;
        for (Pile pile : piles) win &= pile.isEmpty();
        return win;
    }

    public boolean isLoss() {
        return deck.isEmpty();
    }

    public boolean isDraw() {
        String state = toString();
        if (states.contains(state)) return true;
        states.add(state);
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(deck.toString());
        for (Pile pile : piles) sb.append('&').append(pile.toString());
        return sb.toString();
    }

    public int getStep() {
        return step;
    }
}

class Pile {
    // top-to-bottom or first-to-last
    private final LinkedList<Integer> cards = new LinkedList<>();

    public int removeTop() {
        return cards.removeFirst();
    }

    public void addBottom(int card) {
        cards.addLast(card);
    }

    public int[] removeCombination() {
        if (cards.size() < 3) return new int[0];

        int[][] allPositions = new int[][]{
                new int[]{0, 1, cards.size() - 1},
                new int[]{0, cards.size() - 2, cards.size() - 1},
                new int[]{cards.size() - 3, cards.size() - 2, cards.size() - 1}
        };

        for (int[] positions : allPositions) {
            int value = getValue(positions);
            if (matchOne(value, 10, 20, 30)) {
                return removeCards(positions);
            }
        }

        return new int[0];
    }

    private int getValue(int... positions) {
        int value = 0;
        for (int position : positions) value += cards.get(position);

        return value;
    }

    private boolean matchOne(int value, int... targetValues) {
        boolean match = false;
        for (int targetValue : targetValues) match |= (value == targetValue);

        return match;
    }

    private int[] removeCards(int... positions) {
        List<Integer> removedCards = new ArrayList<>(positions.length);
        for (int i = 0; i < positions.length; i++) {
            removedCards.add(cards.get(positions[i]));
        }
        for (int i = positions.length - 1; i >= 0; i--) {
            cards.remove(positions[i]);
        }

        return removedCards.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public String toString() {
        return cards.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(":"));
    }
}
