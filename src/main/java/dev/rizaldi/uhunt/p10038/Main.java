package dev.rizaldi.uhunt.p10038;

import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String... args) {
        while (in.hasNextInt()) {
            int total = in.nextInt();
            int[] elements = new int[total];
            for (int i = 0; i < total; i++) elements[i] = in.nextInt();

            Solution solution = new Solution(elements);
            boolean isJolly = solution.isJolly();
            if (isJolly) System.out.println("Jolly");
            else System.out.println("Not jolly");
        }
    }
}

class Solution {
    private final int[] elements;
    private final boolean[] exists;

    public Solution(int[] elements) {
        this.elements = elements;
        this.exists = new boolean[elements.length];
    }

    public boolean isJolly() {
        for (int i = 0, j = 1; j < elements.length; i++, j++) {
            int delta = Math.abs(elements[i] - elements[j]);
            boolean valid = 1 <= delta && delta <= elements.length - 1;
            if (valid) exists[delta] = true;
        }

        for (int i = 1; i <= elements.length - 1; i++) {
            if (!exists[i]) {
                return false;
            }
        }
        return true;
    }
}
