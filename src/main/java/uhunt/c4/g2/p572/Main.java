package uhunt.c4.g2.p572;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Main {
    private static BufferedReader in;
    private static String[] lines;
    private static StringBuilder out;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        while (true) {
            lines = in.readLine().split(" ", 2);
            int totalRow = Integer.parseInt(lines[0]);
            int totalCol = Integer.parseInt(lines[1]);
            if (totalRow == 0 && totalCol == 0) break;

            char[][] grid = new char[totalRow][];
            for (int i = 0; i < totalRow; i++) grid[i] = in.readLine().toCharArray();

            Solution solution = new Solution(totalRow, totalCol, grid);
            int count = solution.countPocket();

            out.append(count).append('\n');
        }

        System.out.print(out);
    }
}

// flood-fill, starting from any cell that has oil
// time complexity: O(col * row)
class Solution {
    private static final char OIL = '@';
    private static final char NON_OIL = '#';
    private static final int[][] MOVES = new int[][]{
        new int[]{-1, -1},
        new int[]{-1, 0},
        new int[]{-1, 1},
        new int[]{0, -1},
        new int[]{0, 1},
        new int[]{1, -1},
        new int[]{1, 0},
        new int[]{1, 1},
    };
    private final int totalRow;
    private final int totalCol;
    private final char[][] grid;

    public Solution(int totalRow, int totalCol, char[][] grid) {
        this.totalRow = totalRow;
        this.totalCol = totalCol;
        this.grid = grid;
    }

    public int countPocket() {
        int count = 0;
        for (int row = 0; row < totalRow; row++) {
            for (int col = 0; col < totalCol; col++) {
                if (grid[row][col] != OIL) continue;
                floodFill(row, col);
                count++;
            }
        }

        return count;
    }

    private void floodFill(int startRow, int startCol) {
        LinkedList<int[]> cellq = new LinkedList<>();

        grid[startRow][startCol] = NON_OIL;
        cellq.addLast(new int[]{startRow, startCol});

        while (!cellq.isEmpty()) {
            int[] cell = cellq.removeFirst();

            for (int[] move : MOVES) {
                int[] nextCell = new int[]{cell[0] + move[0], cell[1] + move[1]};
                if (!valid(nextCell)) continue;
                if (grid[nextCell[0]][nextCell[1]] != OIL) continue;

                grid[nextCell[0]][nextCell[1]] = NON_OIL;
                cellq.addLast(nextCell);
            }
        }
    }

    private boolean valid(int[] cell) {
        return valid(cell[0], cell[1]);
    }

    private boolean valid(int row, int col) {
        return 0 <= row && row < totalRow && 0 <= col && col < totalCol;
    }
}
