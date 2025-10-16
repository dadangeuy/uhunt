package uhunt.c1.p10284;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNext()) {
            char[] notation = in.next().toCharArray();

            Solution solution = new Solution(notation);
            int total = solution.getTotalUnoccupiedAndUnattackedSquare();

            out.println(total);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final char WHITE_KING = 'K';
    private static final char WHITE_QUEEN = 'Q';
    private static final char WHITE_BISHOP = 'B';
    private static final char WHITE_KNIGHT = 'N';
    private static final char WHITE_ROOK = 'R';
    private static final char WHITE_PAWN = 'P';

    private static final char BLACK_KING = 'k';
    private static final char BLACK_QUEEN = 'q';
    private static final char BLACK_BISHOP = 'b';
    private static final char BLACK_KNIGHT = 'n';
    private static final char BLACK_ROOK = 'r';
    private static final char BLACK_PAWN = 'p';

    private static final char ATTACK_FLAG = '#';
    private static final char EMPTY_FLAG = '$';

    // Forsyth-Edwards Notation
    private final char[] notation;

    public Solution(char[] notation) {
        this.notation = notation;
    }

    public int getTotalUnoccupiedAndUnattackedSquare() {
        char[][] board = transformNotationToChessBoard();
        flagAttackSquare(board);
        return count(board, EMPTY_FLAG);
    }

    private char[][] transformNotationToChessBoard() {
        char[][] board = new char[8][8];

        int row = 0, col = 0;
        for (int i = 0; i < notation.length; i++) {
            char symbol = notation[i];
            if (symbol == '/') {
                row++;
                col = 0;
            } else if (Character.isDigit(symbol)) {
                col += symbol - '0';
            } else {
                board[row][col] = symbol;
                col++;
            }
        }

        replace(board, (char) 0, EMPTY_FLAG);

        return board;
    }

    private void flagAttackSquare(char[][] board) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                char square = board[row][col];
                switch (Character.toUpperCase(square)) {
                    case WHITE_KING:
                        flagKingAttackSquare(board, row, col);
                        break;
                    case WHITE_QUEEN:
                        flagQueenAttackSquare(board, row, col);
                        break;
                    case WHITE_BISHOP:
                        flagBishopAttackSquare(board, row, col);
                        break;
                    case WHITE_KNIGHT:
                        flagKnightAttackSquare(board, row, col);
                        break;
                    case WHITE_ROOK:
                        flagRookAttackSquare(board, row, col);
                        break;
                    case WHITE_PAWN:
                        flagPawnAttackSquare(board, row, col);
                        break;
                }
            }
        }
    }

    private void flagKingAttackSquare(char[][] board, int row, int col) {
        flag(board, row - 1, col - 1);
        flag(board, row - 1, col);
        flag(board, row - 1, col + 1);
        flag(board, row, col - 1);
        flag(board, row, col);
        flag(board, row, col + 1);
        flag(board, row + 1, col - 1);
        flag(board, row + 1, col);
        flag(board, row + 1, col + 1);
    }

    private void flagQueenAttackSquare(char[][] board, int row, int col) {
        flagHorizontal(board, row, col);
        flagVertical(board, row, col);
        flagDiagonal(board, row, col);
    }

    private void flagBishopAttackSquare(char[][] board, int row, int col) {
        flagDiagonal(board, row, col);
    }

    private void flagKnightAttackSquare(char[][] board, int row, int col) {
        flag(board, row - 1, col - 2);
        flag(board, row - 1, col + 2);
        flag(board, row + 1, col - 2);
        flag(board, row + 1, col + 2);
        flag(board, row - 2, col - 1);
        flag(board, row - 2, col + 1);
        flag(board, row + 2, col - 1);
        flag(board, row + 2, col + 1);
    }

    private void flagRookAttackSquare(char[][] board, int row, int col) {
        flagHorizontal(board, row, col);
        flagVertical(board, row, col);
    }

    private void flagPawnAttackSquare(char[][] board, int row, int col) {
        boolean white = Character.isUpperCase(board[row][col]);
        if (white) {
            flag(board, row - 1, col - 1);
            flag(board, row - 1, col + 1);
        } else {
            flag(board, row + 1, col - 1);
            flag(board, row + 1, col + 1);
        }

    }

    private void flagHorizontal(char[][] board, int row, int col) {
        for (int i = col - 1; i >= 0; i--) {
            boolean flagged = flag(board, row, i);
            if (!flagged) break;
        }

        for (int i = col + 1; i < 8; i++) {
            boolean flagged = flag(board, row, i);
            if (!flagged) break;
        }
    }

    private void flagVertical(char[][] board, int row, int col) {
        for (int i = row - 1; i >= 0; i--) {
            boolean flagged = flag(board, i, col);
            if (!flagged) break;
        }

        for (int i = row + 1; i < 8; i++) {
            boolean flagged = flag(board, i, col);
            if (!flagged) break;
        }
    }

    private void flagDiagonal(char[][] board, int row, int col) {
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            boolean flagged = flag(board, i, j);
            if (!flagged) break;
        }

        for (int i = row - 1, j = col + 1; i >= 0 && j < 8; i--, j++) {
            boolean flagged = flag(board, i, j);
            if (!flagged) break;
        }

        for (int i = row + 1, j = col - 1; i < 8 && j >= 0; i++, j--) {
            boolean flagged = flag(board, i, j);
            if (!flagged) break;
        }

        for (int i = row + 1, j = col + 1; i < 8 && j < 8; i++, j++) {
            boolean flagged = flag(board, i, j);
            if (!flagged) break;
        }
    }

    private boolean flag(char[][] board, int row, int col) {
        boolean valid = 0 <= row && row < 8 && 0 <= col && col < 8;
        if (!valid) return false;

        boolean occupied = Character.isAlphabetic(board[row][col]);
        if (occupied) return false;

        board[row][col] = ATTACK_FLAG;
        return true;
    }

    private void replace(char[][] array, char from, char to) {
        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array[i].length; j++)
                if (array[i][j] == from)
                    array[i][j] = to;
    }

    private int count(char[][] array2d, char target) {
        int count = 0;
        for (char[] array1d : array2d)
            for (char value : array1d)
                if (value == target)
                    count++;
        return count;
    }
}
