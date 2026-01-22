package uhunt.c3.g9.p639;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 2 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 2 << 16));

        while (true) {
            int size = in.nextInt();
            if (size == 0) break;

            char[][] board = new char[size][];
            for (int i = 0; i < board.length; i++) board[i] = in.next().toCharArray();

            Solution solution = new Solution(size, board);
            int totalRook = solution.getMaxNumberOfRook();

            out.println(totalRook);
        }

        in.close();
        out.flush();
        out.close();
    }
}

/**
 * size: 1..4
 * <p>
 * 1) try all possible combination of rook placement (2^16 = 65536)
 * 2) for each cell, we can either (1) put rook or (2) left it empty
 * 3) use dfs to iterate over all possible configuration
 * 4) check validity by checking horizontal/vertical of each filled cell (2^2*2^2 = 2^4 = 16)
 * 5) overall time complexity: O(2^20) = 1.048.576 => 10^6/10^8s = 10^-2s = 0.01s
 */
class Solution {
    private static final char wall = 'X';
    private static final char empty = '.';
    private static final char rook = 'R';
    private final int size;
    private final char[][] board;

    public Solution(int size, char[][] board) {
        this.size = size;
        this.board = board;
    }

    public int getMaxNumberOfRook() {
        return doGetMaxNumberOfRook(0, 0, 0, 0);
    }

    private int doGetMaxNumberOfRook(int row, int col, int step, int totalRook) {
        if (step == size * size) {
            return valid() ? totalRook : 0;
        }

        int maxTotalRook = 0;
        int nextRow = row + 1 == size ? 0 : row + 1;
        int nextCol = row + 1 == size ? col + 1 : col;

        // branch 1: fill rook
        if (board[row][col] == empty) {
            board[row][col] = rook;

            int totalRookFill = doGetMaxNumberOfRook(nextRow, nextCol, step + 1, totalRook + 1);
            maxTotalRook = Math.max(maxTotalRook, totalRookFill);

            board[row][col] = empty;
        }

        // branch 2: leave it empty
        int totalRookSkip = doGetMaxNumberOfRook(nextRow, nextCol, step + 1, totalRook);
        maxTotalRook = Math.max(maxTotalRook, totalRookSkip);

        return maxTotalRook;
    }

    private boolean valid() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] != rook) continue;

                // check horizontal-left
                for (int hcol = col - 1; hcol >= 0; hcol--) {
                    if (board[row][hcol] == wall) break;
                    if (board[row][hcol] == rook) return false;
                }

                // check horizontal-right
                for (int hcol = col + 1; hcol < size; hcol++) {
                    if (board[row][hcol] == wall) break;
                    if (board[row][hcol] == rook) return false;
                }

                // check vertical-up
                for (int vrow = row - 1; vrow >= 0; vrow--) {
                    if (board[vrow][col] == wall) break;
                    if (board[vrow][col] == rook) return false;
                }

                // check vertical-down
                for (int vrow = row + 1; vrow < size; vrow++) {
                    if (board[vrow][col] == wall) break;
                    if (board[vrow][col] == rook) return false;
                }
            }
        }

        return true;
    }
}
