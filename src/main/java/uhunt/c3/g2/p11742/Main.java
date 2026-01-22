package uhunt.c3.g2.p11742;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            int totalPerson = in.nextInt(); // 1..8
            int totalConstraint = in.nextInt(); // 0..20
            if (totalPerson == 0 && totalConstraint == 0) break;

            int[][] constraints = new int[totalConstraint][];
            for (int i = 0; i < totalConstraint; i++) {
                int person1 = in.nextInt();
                int person2 = in.nextInt();
                int distance = in.nextInt();

                int[] constraint = new int[]{person1, person2, distance};
                constraints[i] = constraint;
            }

            Solution solution = new Solution(totalPerson, totalConstraint, constraints);
            int total = solution.getTotalPossibleArrangement();

            System.out.println(total);
        }
    }
}

/*
General Overview:
- initiate an empty seat
- for each constraint, try to put the person into empty seat in a recursive manner
- once all constraint has been fulfilled, use permutation to get total way to fill the rest of the seat
- permutation formula: (total seat left)!
- use dp + precompute strategy to calculate permutation
- time complexity:
*/
class Solution {
    private static final int[] factorials = precomputeFactorials(8);
    private final int totalPerson;
    private final int totalConstraint;
    private final int[][] constraints;

    public Solution(int totalPerson, int totalConstraint, int[][] constraints) {
        this.totalPerson = totalPerson;
        this.totalConstraint = totalConstraint;
        this.constraints = constraints;
    }

    private static int[] precomputeFactorials(int until) {
        int[] factorials = new int[until + 1];
        factorials[0] = 1;
        for (int i = 1; i < factorials.length; i++) factorials[i] = factorials[i - 1] * i;

        return factorials;
    }

    // O(tc * tp * tp), worst case: O(20 * 8 * 8 = 320)
    public int getTotalPossibleArrangement() {
        int[] seats = new int[totalPerson];
        Arrays.fill(seats, -1);

        return fillSeats(seats, 0);
    }

    // O(tc * tp * tp)
    private int fillSeats(int[] seats, int constraintID) {
        if (constraintID >= totalConstraint) {
            int seatLeft = count(seats, -1);
            return factorials[seatLeft];
        }

        int[] constraint = constraints[constraintID];
        int person1 = constraint[0];
        int person2 = constraint[1];
        int requiredDistance = constraint[2];

        int prevSeat1 = find(seats, person1);
        int prevSeat2 = find(seats, person2);

        int total = 0;

        for (int seat1 = 0; seat1 < totalPerson; seat1++) {
            if (prevSeat1 != -1 && seat1 != prevSeat1) continue;
            if (seats[seat1] != -1 && seats[seat1] != person1) continue;

            for (int seat2 = 0; seat2 < totalPerson; seat2++) {
                if (seat1 == seat2) continue;
                if (prevSeat2 != -1 && seat2 != prevSeat2) continue;
                if (seats[seat2] != -1 && seats[seat2] != person2) continue;

                int distance = Math.abs(seat1 - seat2);
                if (requiredDistance > 0 && distance > requiredDistance) continue;
                if (requiredDistance < 0 && distance < -requiredDistance) continue;

                int previ = seats[seat1];
                int prevj = seats[seat2];
                seats[seat1] = person1;
                seats[seat2] = person2;

                total += fillSeats(seats, constraintID + 1);

                seats[seat1] = previ;
                seats[seat2] = prevj;
            }
        }

        return total;
    }

    // O(tp)
    private int find(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i] == target)
                return i;
        return -1;
    }

    // O(tp)
    private int count(int[] arr, int target) {
        int count = 0;
        for (int val : arr)
            if (val == target)
                count++;
        return count;
    }
}
