package uva.uhunt.c4.g1.p11561;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (in.hasNextInt()) {
            int width = in.nextInt();
            int height = in.nextInt();

            char[][] map = new char[height][];
            for (int i = 0; i < height; i++) map[i] = in.next().toCharArray();

            Solution solution = new Solution(width, height, map);
            int gold = solution.getMaxNumberOfGold();

            out.println(gold);
        }

        in.close();
        out.flush();
        out.close();
    }
}

/**
 * 1) starting from P, flood-fill entire map
 * 2) if encounter G, increment total gold, and proceed to flood fill
 * 3) if encounter ., proceed to flood fill
 * 4) if encounter # or T, avoid it
 * 5) time complexity: O(width * height)
 * 6) for simplicity, use DFS recursive
 */
class Solution {
    private static final char player = 'P';
    private static final char gold = 'G';
    private static final char trap = 'T';
    private static final char wall = '#';
    private static final char floor = '.';
    private static final char visited = 'V';
    private static final int[][] moves = new int[][]{
        new int[]{0, -1},
        new int[]{0, 1},
        new int[]{-1, 0},
        new int[]{1, 0},
    };
    private static final List<Character> obstacles = Arrays.asList(trap, wall, visited);

    private final int width;
    private final int height;
    private final char[][] map;

    public Solution(int width, int height, char[][] map) {
        this.width = width;
        this.height = height;
        this.map = map;
    }

    public int getMaxNumberOfGold() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (map[row][col] == player) {
                    return floodFill(row, col);
                }
            }
        }
        return 0;
    }

    private int floodFill(int row, int col) {
        boolean valid = 0 <= row && row < height && 0 <= col && col < width;
        if (!valid) return 0;

        boolean obstacle = obstacles.contains(map[row][col]);
        if (obstacle) return 0;

        int totalGold = map[row][col] == gold ? 1 : 0;
        map[row][col] = visited;

        boolean senseDraft = false;
        for (int[] move : moves) {
            int nextRow = row + move[0];
            int nextCol = col + move[1];

            boolean nextValid = 0 <= nextRow && nextRow < height && 0 <= nextCol && nextCol < width;
            if (!nextValid) continue;

            boolean nextToTrap = map[nextRow][nextCol] == trap;
            senseDraft |= nextToTrap;
        }

        if (senseDraft) return totalGold;

        for (int[] move : moves) {
            int nextRow = row + move[0];
            int nextCol = col + move[1];
            totalGold += floodFill(nextRow, nextCol);
        }
        return totalGold;
    }
}
