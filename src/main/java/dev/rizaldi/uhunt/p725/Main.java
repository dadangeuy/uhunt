package dev.rizaldi.uhunt.p725;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);

        boolean firstRun = true;
        while (true) {
            int target = in.nextInt();
            if (target == 0) break;

            if (!firstRun) System.out.println();
            firstRun = false;

            Solution solution = new Solution(target);
            Collection<int[]> pairs = solution.getQualifiedPairs();

            if (pairs.isEmpty()) System.out.format("There are no solutions for %d.\n", target);
            else pairs.forEach(p -> System.out.format("%05d / %05d = %d\n", p[0], p[1], target));
        }
    }
}

class Solution {
    private final int target;

    public Solution(int target) {
        this.target = target;
    }

    public Collection<int[]> getQualifiedPairs() {
        LinkedList<int[]> pairs = new LinkedList<>();
        for (int abcde = 1234; abcde <= 98765; abcde++) {
            if (abcde % target != 0) continue;
            int fghij = abcde / target;

            boolean valid = valid(abcde, fghij);
            if (!valid) continue;

            pairs.add(new int[]{abcde, fghij});
        }

        return pairs;
    }

    private boolean valid(int abcde, int fghij) {
        boolean validRange = validRange(abcde) && validRange(fghij);
        if (!validRange) return false;

        boolean[] appears = new boolean[10];

        for (int i = 0; i < 5; i++) {
            int digit = abcde % 10;
            if (appears[digit]) return false;

            appears[digit] = true;
            abcde /= 10;
        }

        for (int i = 0; i < 5; i++) {
            int digit = fghij % 10;
            if (appears[digit]) return false;

            appears[digit] = true;
            fghij /= 10;
        }

        return true;
    }

    private boolean validRange(int value) {
        return 1234 <= value && value <= 98765;
    }
}
