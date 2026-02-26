package uva.c1.g3.p10363;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            char[][] board = new char[3][];
            board[0] = in.next().toCharArray();
            board[1] = in.next().toCharArray();
            board[2] = in.next().toCharArray();

            Solution solution = new Solution(board);
            boolean valid = solution.isValid();

            out.println(valid ? "yes" : "no");
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    // tic-tac-toe board
    private final char[][] board;

    public Solution(char[][] board) {
        this.board = board;
    }

    public boolean isValid() {
        boolean xWin = isWin('X');
        boolean oWin = isWin('O');
        boolean hasTwoWinner = xWin && oWin;
        if (hasTwoWinner) return false;

        int totalX = count(board, 'X');
        int totalO = count(board, 'O');
        boolean isLastMoveFromX = totalX - 1 == totalO;
        boolean isLastMoveFromY = totalX == totalO;

        if (xWin) return isLastMoveFromX;
        if (oWin) return isLastMoveFromY;
        return isLastMoveFromX || isLastMoveFromY;
    }

    private int count(char[][] arr2d, char target) {
        int count = 0;
        for (char[] arr1d : arr2d)
            for (char value : arr1d)
                if (value == target) count++;
        return count;
    }

    private boolean isWin(char target) {
        boolean win = false;
        for (int i = 0; i < 3; i++) {
            win |= equals(target, board[i][0], board[i][1], board[i][2]);
            win |= equals(target, board[0][i], board[1][i], board[2][i]);
        }
        win |= equals(target, board[0][0], board[1][1], board[2][2]);
        win |= equals(target, board[2][0], board[1][1], board[0][2]);
        return win;
    }

    private boolean equals(char target, char... values) {
        for (char value : values) if (value != target) return false;
        return true;
    }
}
