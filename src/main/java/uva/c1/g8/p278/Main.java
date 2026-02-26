package uva.c1.g8.p278;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String... args) throws IOException {
        Scanner in = new Scanner(System.in);
        StringBuilder out = new StringBuilder();

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            char piece = in.next().charAt(0);
            int totalRow = in.nextInt();
            int totalCol = in.nextInt();

            Solution solution = new Solution(piece, totalRow, totalCol);
            int total = solution.getMaximumTotalPiece();

            out.append(total).append('\n');
        }

        System.out.print(out);
    }
}

/**
 * how to calculate max piece?
 * 1) rook
 * if we put a rook, it'll block  the whole horizontal/vertical cell. we can attain maximum configuration if
 * we put the rook diagonally. in other words, max = min(totalRow, totalCol)
 * <p>
 * 2) knight
 * knights can only attack other color in a chess board, i.e. if the knight was on the white cell, it can only attack
 * black cell
 * https://www.geeksforgeeks.org/maximum-non-attacking-knights-that-can-be-placed-on-an-nm-chessboard/
 * <p>
 * 3) queen
 * the optimal way to put a queen was by using L pattern, e.g.:
 * Q----
 * |\Q--
 * |/|\Q
 * try to put the queen on odd-column, and after that try to put the queen on even-column
 * <p>
 * 4) king
 * the optimal way to put a king was by giving 1 cell-space (horizontal & vertical), e.g.:
 * K-K-K
 * |.|.|
 * K-K.K
 * in other words, max = ceil(totalRow / 2) * ceil(totalCol / 2)
 */
class Solution {
    private static final char ROOK = 'r';
    private static final char KNIGHT = 'k';
    private static final char QUEEN = 'Q';
    private static final char KING = 'K';

    private final char piece; // r, k, Q, K
    private final int totalRow; // 4..10
    private final int totalCol; // 4..10

    public Solution(char piece, int totalRow, int totalCol) {
        this.piece = piece;
        this.totalRow = totalRow;
        this.totalCol = totalCol;
    }

    public int getMaximumTotalPiece() {
        switch (piece) {
            case ROOK:
                return getMaximumTotalRook();
            case KNIGHT:
                return getMaximumTotalKnight();
            case QUEEN:
                return getMaximumTotalQueen();
            case KING:
                return getMaximumTotalKing();
        }
        return 0;
    }

    private int getMaximumTotalRook() {
        return Math.min(totalRow, totalCol);
    }

    private int getMaximumTotalKnight() {
        if (totalRow == 1 || totalCol == 1) return Math.max(totalRow, totalCol);
        else if (totalRow == 2 || totalCol == 2) {
            int c = (Math.max(totalRow, totalCol) / 4) * 4;
            if (Math.max(totalRow, totalCol) % 4 == 1) c += 2;
            else if (Math.max(totalRow, totalCol) % 4 > 1) c += 4;
            return c;
        }
        return (totalRow * totalCol + 1) / 2;
    }

    private int getMaximumTotalQueen() {
        int odd = Math.min((totalCol + 1) / 2, totalRow);
        int even = Math.min((totalCol) / 2, totalRow - odd);
        return odd + even;
    }

    private int getMaximumTotalKing() {
        int vertical = (totalRow + 1) / 2;
        int horizontal = (totalCol + 1) / 2;
        return vertical * horizontal;
    }
}
