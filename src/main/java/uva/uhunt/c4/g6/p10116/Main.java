package uva.uhunt.c4.g6.p10116;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static BufferedReader in;
    private static String line;
    private static String[] lines;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            line = in.readLine();
            lines = line.split(" ", 3);

            int totalRow = Integer.parseInt(lines[0]);
            int totalCol = Integer.parseInt(lines[1]);
            int startCol = Integer.parseInt(lines[2]) - 1;

            if (totalRow == 0 && totalCol == 0 && startCol == -1) break;

            char[][] grid = new char[totalRow][];
            for (int i = 0; i < totalRow; i++) {
                line = in.readLine();
                grid[i] = line.toCharArray();
            }

            Solution solution = new Solution(totalRow, totalCol, startCol, grid);
            int[] stepAndLoop = solution.getStepAndLoop();
            int step = stepAndLoop[0];
            int loop = stepAndLoop[1];

            if (loop == 0) System.out.format("%d step(s) to exit\n", step);
            else System.out.format("%d step(s) before a loop of %d step(s)\n", step, loop);
        }
    }
}

// traverse the grid by following the direction (similar to DFS, but only 1 possible path on the grid)
// keep track of visited cell and number of step taken to reach those cell
// if visit a visited cell, then return the step (step of visited cell) and loop step (current step - step of visited cell)
class Solution {
    private final int totalRow;
    private final int totalCol;
    private final int startCol;
    private final char[][] grid;

    public Solution(int totalRow, int totalCol, int startCol, char[][] grid) {
        this.totalRow = totalRow;
        this.totalCol = totalCol;
        this.startCol = startCol;
        this.grid = grid;
    }

    public int[] getStepAndLoop() {
        int[][] steps = new int[totalRow][totalCol];
        fill(steps, -1);

        int[] location = new int[]{0, startCol};
        steps[location[0]][location[1]] = 0;

        while (true) {
            int nextStep = steps[location[0]][location[1]] + 1;
            int[] nextLocation = next(grid[location[0]][location[1]], location);

            boolean exit = !valid(nextLocation);
            if (exit) return new int[]{nextStep, 0};

            boolean loop = steps[nextLocation[0]][nextLocation[1]] != -1;
            int loopStep = steps[nextLocation[0]][nextLocation[1]];
            if (loop) return new int[]{loopStep, nextStep - loopStep};

            steps[nextLocation[0]][nextLocation[1]] = nextStep;
            location = nextLocation;
        }
    }

    private void fill(int[][] arr, int val) {
        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr[i].length; j++)
                arr[i][j] = val;
    }

    private boolean valid(int[] location) {
        return 0 <= location[0] && location[0] < totalRow && 0 <= location[1] && location[1] < totalCol;
    }

    private int[] next(char direction, int[] location) {
        if (direction == 'N') return new int[]{location[0] - 1, location[1]};
        if (direction == 'S') return new int[]{location[0] + 1, location[1]};
        if (direction == 'W') return new int[]{location[0], location[1] - 1};
        if (direction == 'E') return new int[]{location[0], location[1] + 1};
        return location;
    }
}
