package dev.rizaldi.uhunt.c3.p750;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);

        Precompute precompute = new Precompute();
        Collection<int[]> possibleSolutions = precompute.getPossibleSolutions();

        int totalTest = in.nextInt();
        for (int test = 0; test < totalTest; test++) {
            int row = in.nextInt() - 1; // 0-based
            int col = in.nextInt() - 1; // 0-based

            System.out.format("SOLN       COLUMN\n #      1 2 3 4 5 6 7 8\n\n");

            int totalSolution = 0;
            for (int[] solution : possibleSolutions) {
                if (solution[col] == row) {
                    System.out.format("%2d      ", ++totalSolution);
                    System.out.format("%d %d %d %d %d %d %d %d\n",
                            solution[0] + 1, // 1-based
                            solution[1] + 1, // 1-based
                            solution[2] + 1, // 1-based
                            solution[3] + 1, // 1-based
                            solution[4] + 1, // 1-based
                            solution[5] + 1, // 1-based
                            solution[6] + 1, // 1-based
                            solution[7] + 1 // 1-based
                    );
                }
            }

            if (test < totalTest - 1) System.out.println();
        }
    }
}

class Precompute {
    private final Comparator<int[]> orderAsc = Comparator
            .<int[]>comparingInt(v -> v[0])
            .thenComparingInt(v -> v[1])
            .thenComparingInt(v -> v[2])
            .thenComparingInt(v -> v[3])
            .thenComparingInt(v -> v[4])
            .thenComparingInt(v -> v[5])
            .thenComparingInt(v -> v[6])
            .thenComparingInt(v -> v[7]);
    private final ArrayList<int[]> results = new ArrayList<>(92);

    // state
    private final int[] positions = new int[8];
    private final boolean[] horizontals = new boolean[8];
    private final boolean[] verticals = new boolean[8];
    private final boolean[] downDiagonals = new boolean[15];
    private final boolean[] upDiagonals = new boolean[15];

    public Collection<int[]> getPossibleSolutions() {
        if (!results.isEmpty()) return results;

        backtrack(0);
        results.sort(orderAsc);
        return results;
    }

    private void backtrack(int col) {
        if (col == 8) {
            results.add(positions.clone());
            return;
        }

        for (int row = 0; row < 8; row++) {
            if (!valid(row, col)) continue;

            set(row, col, true);
            positions[col] = row;

            backtrack(col + 1);

            set(row, col, false);
            positions[col] = -1;
        }
    }

    private boolean valid(int row, int col) {
        return !horizontals[horizontalID(row, col)] &&
                !verticals[verticalID(row, col)] &&
                !downDiagonals[downDiagonalID(row, col)] &&
                !upDiagonals[upDiagonalID(row, col)];
    }

    private void set(int row, int col, boolean label) {
        horizontals[horizontalID(row, col)] = label;
        verticals[verticalID(row, col)] = label;
        downDiagonals[downDiagonalID(row, col)] = label;
        upDiagonals[upDiagonalID(row, col)] = label;
    }

    // range: 0..7
    private int horizontalID(int row, int col) {
        return row;
    }

    // range: 0..7
    private int verticalID(int row, int col) {
        return col;
    }

    // range: 0..14
    private int downDiagonalID(int row, int col) {
        return col - row + 7;
    }

    // range: 0..14
    private int upDiagonalID(int row, int col) {
        return col + row;
    }
}
