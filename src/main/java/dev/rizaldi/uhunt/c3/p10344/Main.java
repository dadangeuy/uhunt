package dev.rizaldi.uhunt.c3.p10344;

import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int[] values = new int[5];
        while (true) {
            for (int i = 0; i < 5; i++) values[i] = in.nextInt();
            if (equals(values, 0)) break;

            Solution solution = new Solution(values);
            boolean possible = solution.hasPossibleExpression();

            System.out.println(possible ? "Possible" : "Impossible");
        }
    }

    private static boolean equals(int[] arr, int target) {
        boolean equals = true;
        for (int val : arr) equals &= target == val;
        return equals;
    }
}

class Solution {
    private static final IntOperation[] operations = new IntOperation[]{
            (a, b) -> a + b,
            (a, b) -> a - b,
            (a, b) -> a * b
    };
    private static final int target = 23;
    private final int[] values;

    public Solution(int[] values) {
        this.values = values;
    }

    public boolean hasPossibleExpression() {
        return exploreRecursively(0, 0, new boolean[5]);
    }

    public boolean exploreRecursively(int result, int count, boolean[] taken) {
        if (count == 5) return result == target;

        boolean match = false;
        for (int i = 0; i < 5 && !match; i++) {
            if (taken[i]) continue;

            taken[i] = true;
            for (int j = 0; j < 3 && !match; j++) {
                if (count == 0 && j > 0) continue;
                int newResult = operations[j].get(result, values[i]);
                match = exploreRecursively(newResult, count + 1, taken);
            }
            taken[i] = false;
        }

        return match;
    }
}

interface IntOperation {
    int get(int a, int b);
}
