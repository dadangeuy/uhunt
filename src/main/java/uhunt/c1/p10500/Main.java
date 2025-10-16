package uhunt.c1.p10500;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 10500 - Robot maps
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1441
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextInt()) {
            int totalRow = in.nextInt(), totalCol = in.nextInt();
            if (totalRow == 0 && totalCol == 0) break;
            int startRow = in.nextInt() - 1, startCol = in.nextInt() - 1;
            char[][] map = new char[totalRow][totalCol];
            for (int row = 0; row < totalRow; row++)
                for (int col = 0; col < totalCol; col++)
                    map[row][col] = in.next().charAt(0);

            Solution solution = new Solution(totalRow, totalCol, startRow, startCol, map);
            Result scanResult = solution.scanMap();
            char[][] partialMap = scanResult.getPartialMap();
            int totalMove = scanResult.getTotalMove();

            out.println();
            for (int row = 0; row < totalRow; row++) {
                for (int col = 0; col < totalCol; col++) out.print("|---");
                out.println('|');
                for (int col = 0; col < totalCol; col++) out.format("| %c ", partialMap[row][col]);
                out.println('|');
            }
            for (int col = 0; col < totalCol; col++) out.print("|---");
            out.println('|');
            out.format("\nNUMBER OF MOVEMENTS: %d\n", totalMove);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final char OCCUPIED = 'X';
    private static final char EMPTY = '0';
    private static final char UNKNOWN = '?';
    private static final int[][] MOVES = new int[][]{
        new int[]{-1, 0},
        new int[]{0, 1},
        new int[]{1, 0},
        new int[]{0, -1},
    };

    private final int totalRow;
    private final int totalCol;
    private final int startRow;
    private final int startCol;
    private final char[][] map;

    public Solution(int totalRow, int totalCol, int startRow, int startCol, char[][] map) {
        this.totalRow = totalRow;
        this.totalCol = totalCol;
        this.startRow = startRow;
        this.startCol = startCol;
        this.map = map;
    }

    public Result scanMap() {
        char[][] partialMap = new char[totalRow][totalCol];
        boolean[][] visited = new boolean[totalRow][totalCol];
        int totalMove = 0;

        fill(partialMap, UNKNOWN);
        int row = startRow, col = startCol;
        partialMap[row][col] = EMPTY;
        visited[row][col] = true;

        while (true) {
            // update partialMap
            for (int[] move : MOVES) {
                int nrow = row + move[0], ncol = col + move[1];
                boolean valid = between(0, nrow, totalRow - 1) && between(0, ncol, totalCol - 1);
                if (!valid) continue;

                partialMap[nrow][ncol] = map[nrow][ncol];
            }

            // find next position
            int[] npos = null;
            for (int[] move : MOVES) {
                int nrow = row + move[0], ncol = col + move[1];
                boolean valid = between(0, nrow, totalRow - 1) && between(0, ncol, totalCol - 1);
                if (!valid || map[nrow][ncol] == OCCUPIED || visited[nrow][ncol]) continue;

                npos = new int[]{nrow, ncol};
                break;
            }
            if (npos == null) break;

            row = npos[0];
            col = npos[1];
            visited[row][col] = true;
            totalMove++;
        }

        return new Result(partialMap, totalMove);
    }

    private void fill(char[][] array, char value) {
        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array[i].length; j++)
                array[i][j] = value;
    }

    private boolean between(int left, int mid, int right) {
        return 0 <= mid && mid <= right;
    }
}

class Result {
    private final char[][] partialMap;
    private final int totalMove;

    public Result(char[][] partialMap, int totalMove) {
        this.partialMap = partialMap;
        this.totalMove = totalMove;
    }

    public char[][] getPartialMap() {
        return partialMap;
    }

    public int getTotalMove() {
        return totalMove;
    }
}
