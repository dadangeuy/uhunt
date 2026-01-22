package uhunt.c2.g7.p12187;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 12187 - Brothers
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=3339
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int totalHeir = in.nextInt();
            int totalRow = in.nextInt();
            int totalCol = in.nextInt();
            int totalBattle = in.nextInt();

            if (totalHeir == 0 && totalRow == 0 && totalCol == 0 && totalBattle == 0) break;

            int[][] lands = new int[totalRow][totalCol];
            for (int i = 0; i < totalRow; i++) {
                for (int j = 0; j < totalCol; j++) {
                    lands[i][j] = in.nextInt();
                }
            }

            Solution solution = new Solution(totalHeir, totalRow, totalCol, totalBattle, lands);
            int[][] newLands = solution.getLandsAfterBattles();

            for (int i = 0; i < totalRow; i++) {
                out.print(newLands[i][0]);
                for (int j = 1; j < totalCol; j++) {
                    out.print(' ');
                    out.print(newLands[i][j]);
                }
                out.println();
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final int[] UP = new int[]{-1, 0};
    private static final int[] DOWN = new int[]{1, 0};
    private static final int[] LEFT = new int[]{0, -1};
    private static final int[] RIGHT = new int[]{0, 1};
    private static final int[][] MOVES = new int[][]{UP, DOWN, LEFT, RIGHT};
    private final int totalHeir;
    private final int totalRow;
    private final int totalCol;
    private final int totalBattle;
    private final int[][] lands;

    public Solution(int totalHeir, int totalRow, int totalCol, int totalBattle, int[][] lands) {
        this.totalHeir = totalHeir;
        this.totalRow = totalRow;
        this.totalCol = totalCol;
        this.totalBattle = totalBattle;
        this.lands = lands;
    }

    public int[][] getLandsAfterBattles() {
        int[][] prevLand = lands;
        int[][] nextLand = new int[totalRow][totalCol];
        copy(prevLand, nextLand);

        for (int battle = 0; battle < totalBattle; battle++) {
            for (int row = 0; row < totalRow; row++) {
                for (int col = 0; col < totalCol; col++) {
                    int owner = prevLand[row][col];

                    for (int[] move : MOVES) {
                        int nextRow = row + move[0];
                        int nextCol = col + move[1];
                        if (!valid(nextRow, nextCol)) continue;

                        int nextOwner = prevLand[nextRow][nextCol];
                        if (!isEnemy(owner, nextOwner)) continue;

                        nextLand[nextRow][nextCol] = owner;
                    }
                }
            }
            copy(nextLand, prevLand);
        }

        return nextLand;
    }

    private void copy(int[][] source, int[][] destination) {
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, destination[i], 0, source[i].length);
        }
    }

    private boolean valid(int row, int col) {
        return 0 <= row && row < totalRow && 0 <= col && col < totalCol;
    }

    private boolean isEnemy(int owner, int otherOwner) {
        return ((owner + 1) % totalHeir) == otherOwner;
    }
}
