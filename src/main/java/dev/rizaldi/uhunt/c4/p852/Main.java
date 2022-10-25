package dev.rizaldi.uhunt.c4.p852;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 852 - Deciding victory in Go
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=793
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalBoards = Integer.parseInt(in.nextLine());
        for (int i = 0; i < totalBoards; i++) {
            char[][] board = new char[9][];
            for (int j = 0; j < 9; j++) board[j] = in.next().toCharArray();

            Solution solution = new Solution(board);
            int[] blackAndWhitePoints = solution.getBlackAndWhitePoints();
            int blackPoint = blackAndWhitePoints[0], whitePoint = blackAndWhitePoints[1];
            out.format("Black %d White %d\n", blackPoint, whitePoint);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final char BLACK = 'X';
    private static final char WHITE = 'O';
    private static final char EMPTY = '.';
    private static final char BLACK_WHITE = '#';
    private final char[][] board;
    private final boolean[][] visited1;
    private final boolean[][] visited2;

    public Solution(char[][] board) {
        this.board = board;
        this.visited1 = new boolean[board.length][board[0].length];
        this.visited2 = new boolean[board.length][board[0].length];
    }

    public int[] getBlackAndWhitePoints() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != EMPTY) continue;
                if (visited1[row][col]) continue;

                char owner = findOwner(row, col);
                floodFill(row, col, owner);
            }
        }

        return new int[]{count(BLACK), count(WHITE)};
    }

    private char findOwner(int row, int col) {
        if (!valid(row, col)) return EMPTY;
        if (visited1[row][col]) return EMPTY;
        if (board[row][col] != EMPTY) return board[row][col];

        visited1[row][col] = true;

        char up = findOwner(row - 1, col);
        char down = findOwner(row + 1, col);
        char left = findOwner(row, col - 1);
        char right = findOwner(row, col + 1);

        char owner = EMPTY;
        for (char candidate : new char[]{up, down, left, right}) {
            if (candidate == EMPTY || owner == candidate) continue;
            else if (owner == EMPTY) owner = candidate;
            else owner = BLACK_WHITE;
        }

        return owner;
    }

    private void floodFill(int row, int col, char owner) {
        if (!valid(row, col)) return;
        if (visited2[row][col]) return;
        if (board[row][col] != EMPTY) return;

        board[row][col] = owner;
        visited2[row][col] = true;

        floodFill(row - 1, col, owner);
        floodFill(row + 1, col, owner);
        floodFill(row, col - 1, owner);
        floodFill(row, col + 1, owner);
    }

    private int count(char stone) {
        int count = 0;
        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board[row].length; col++)
                count += board[row][col] == stone ? 1 : 0;
        return count;
    }

    private boolean valid(int row, int col) {
        return 0 <= row && row < board.length && 0 <= col && col < board[row].length;
    }
}
