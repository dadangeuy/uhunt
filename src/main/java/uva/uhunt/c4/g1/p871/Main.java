package uva.uhunt.c4.g1.p871;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 2 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 2 << 16));

        int totalCase = in.nextInt();
        in.nextLine();
        in.nextLine();

        for (int i = 0; i < totalCase; i++) {
            LinkedList<String> rows = new LinkedList<>();
            while (in.hasNextLine()) {
                String row = in.nextLine();
                if (row.isEmpty()) break;
                rows.addLast(row);
            }

            char[][] grid = new char[rows.size()][];
            int gridLength = 0;
            for (String row : rows) grid[gridLength++] = row.toCharArray();

            Solution solution = new Solution(grid);
            int largestBlob = solution.findLargestBlob();

            out.println(largestBlob);
            if (i < totalCase - 1) out.println();
        }

        in.close();
        out.flush();
        out.close();
    }
}

/**
 * 1) flood fill grid, starting from filled cell
 * 2) for simplicity, use DFS recursive for flood fill
 * 3) explore adjacent horizontal, vertical, and diagonal
 * 4) return total filled grid after flood fill
 * 5) time complexity: O(grid size)
 */
class Solution {
    private static final char filled = '1';
    private static final char empty = '0';
    private static final char explored = '#';
    private static final int[][] moves = new int[][]{
            new int[]{-1, -1},
            new int[]{-1, 0},
            new int[]{-1, 1},
            new int[]{0, -1},
            new int[]{0, 0},
            new int[]{0, 1},
            new int[]{1, -1},
            new int[]{1, 0},
            new int[]{1, 1},
    };
    private final char[][] grid;

    public Solution(char[][] grid) {
        this.grid = grid;
    }

    public int findLargestBlob() {
        int largestBlob = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != filled) continue;

                grid[i][j] = explored;
                largestBlob = Math.max(largestBlob, floodFill(i, j));
            }
        }

        return largestBlob;
    }

    private int floodFill(int row, int col) {
        int total = 1;
        for (int[] move : moves) {
            int nrow = row + move[0];
            int ncol = col + move[1];

            if (!valid(nrow, ncol)) continue;
            if (grid[nrow][ncol] != filled) continue;

            grid[nrow][ncol] = explored;
            total += floodFill(nrow, ncol);
        }

        return total;
    }

    private boolean valid(int row, int col) {
        return 0 <= row && row < grid.length && 0 <= col && col < grid[row].length;
    }
}
