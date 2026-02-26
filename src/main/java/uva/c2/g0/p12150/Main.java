package uva.c2.g0.p12150;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    /**
     * case 1
     * 1 0
     * 3 1
     * 2 -1
     * 4 0
     *
     * 1 is at position 0, and initially at position 0 + 0 = 0
     * 3 is at position 1, and initially at position 1 + 1 = 2
     * 2 is at position 2, and initially at position 2 + (-1) = 1
     * 4 is at position 3, and initially at position 3 + 0 = 3
     *
     * initialPositions = new int[]
     */

    private static final Scanner in = new Scanner(System.in);

    public static void main(String... args) {
        int totalCars;
        while ((totalCars = in.nextInt()) != 0) {
            int[] positions = new int[totalCars];
            int[] differences = new int[totalCars];
            for (int position = 0; position < totalCars; position++) {
                positions[position] = in.nextInt();
                differences[position] = in.nextInt();
            }

            Solution solution = new Solution(positions, differences);
            int[] initialPositions = solution.getInitialPositions();

            if (initialPositions == null) {
                System.out.println(-1);
                continue;
            }

            for (int i = 0; i < initialPositions.length; i++) {
                if (i == initialPositions.length - 1) System.out.println(initialPositions[i]);
                else System.out.format("%d ", initialPositions[i]);
            }
        }
    }
}

class Solution {
    private final int[] positions;
    private final int[] differences;
    private final int[] initialPositions;

    public Solution(int[] positions, int[] differences) {
        this.positions = positions;
        this.differences = differences;
        this.initialPositions = new int[positions.length];
        Arrays.fill(initialPositions, -1);
    }

    public int[] getInitialPositions() {
        for (int position = 0; position < positions.length; position++) {
            int car = positions[position];
            int difference = differences[position];

            int initialPosition = position + difference;
            boolean validPosition = 0 <= initialPosition && initialPosition < positions.length;
            if (!validPosition) return null;

            boolean isFilled = initialPositions[initialPosition] != -1;
            if (isFilled) return null;

            initialPositions[initialPosition] = car;
        }

        return initialPositions;
    }
}
