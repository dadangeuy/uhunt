package uva.uhunt.c3.g2.p12192;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static BufferedReader in;
    private static StringBuilder out;
    private static String[] lines;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        while (true) {
            lines = in.readLine().split(" ", 2);

            int totalRow = Integer.parseInt(lines[0]);
            int totalCol = Integer.parseInt(lines[1]);
            if (totalRow == 0 && totalCol == 0) break;

            int[][] grid = new int[totalRow][totalCol];
            for (int i = 0; i < totalRow; i++) {
                lines = in.readLine().split(" ", totalCol);
                for (int j = 0; j < totalCol; j++) {
                    int height = Integer.parseInt(lines[j]);
                    grid[i][j] = height;
                }
            }

            Solution solution = new Solution(totalRow, totalCol, grid);

            int totalQuery = Integer.parseInt(in.readLine());
            for (int i = 0; i < totalQuery; i++) {
                lines = in.readLine().split(" ", 2);
                int min = Integer.parseInt(lines[0]);
                int max = Integer.parseInt(lines[1]);

                int size = solution.getMaxSize(min, max);

                out.append(size).append('\n');
            }

            out.append('-').append('\n');
        }

        System.out.print(out);
    }
}

// convert rxc grid into grid of diagonal
// for each diagonal, binary search range (gte min, lte max)
// time: O((r+c)*log(r)) -> O((1000)*log(500))=9000
// since there are 10^4 query, time: 9*10^7, +-1s
class Solution {
    private final int totalRow;
    private final int totalCol;
    private final int[][] grid;
    private final int[][] diagonalGrid;

    public Solution(int totalRow, int totalCol, int[][] grid) {
        this.totalRow = totalRow;
        this.totalCol = totalCol;
        this.grid = grid;

        this.diagonalGrid = createDiagonalGrid();
    }

    private int[][] createDiagonalGrid() {
        int[][] diagonalGrid = new int[totalRow + totalCol - 1][];

        int gridSize = 0;
        for (int i = 1; i < totalRow; i++) {
            int[] diagonals = new int[Math.min(totalRow - i, totalCol)];
            for (int row = i, col = 0, j = 0; row < totalRow && col < totalCol; row++, col++, j++) {
                diagonals[j] = grid[row][col];
            }
            diagonalGrid[gridSize++] = diagonals;
        }

        for (int i = 0; i < totalCol; i++) {
            int[] diagonals = new int[Math.min(totalRow, totalCol - i)];
            for (int row = 0, col = i, j = 0; row < totalRow && col < totalCol; row++, col++, j++) {
                diagonals[j] = grid[row][col];
            }
            diagonalGrid[gridSize++] = diagonals;
        }

        return diagonalGrid;
    }

    public int getMaxSize(int min, int max) {
        int maxSize = 0;
        for (int[] diagonals : diagonalGrid) {
            if (diagonals.length <= maxSize) continue;

            int minId = upperBound(diagonals, min);
            int maxId = lowerBound(diagonals, max);
            if (minId == -1 || maxId == -1) continue;
            if (minId > maxId) continue;

            int size = maxId - minId + 1;
            maxSize = Math.max(maxSize, size);
        }

        return maxSize;
    }

    private int upperBound(int[] values, int target) {
        int left = 0, right = values.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (values[mid] >= target) right = mid;
            else left = mid + 1;
        }
        return values[right] >= target ? right : -1;
    }

    private int lowerBound(int[] values, int target) {
        int left = 0, right = values.length - 1;
        while (left < right) {
            int mid = (left + right + 1) / 2;
            if (values[mid] <= target) left = mid;
            else right = mid - 1;
        }
        return values[left] <= target ? left : -1;
    }
}
