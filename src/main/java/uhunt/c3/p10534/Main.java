package uhunt.c3.p10534;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int total = in.nextInt();
            int[] values = new int[total];
            for (int i = 0; i < total; i++) values[i] = in.nextInt();

            Solution solution = new Solution(total, values);
            int longestSequence = solution.getLongestWavioSequence();

            System.out.println(longestSequence);
        }
    }
}

class Solution {
    private final int total;
    private final int[] values;

    public Solution(int total, int[] values) {
        this.total = total;
        this.values = values;
    }

    public int getLongestWavioSequence() {
        int[] lis = calculateLIS(values);
        int[] lds = calculateLDS(values);

        int maxLength = 0;
        for (int i = 0; i < total; i++) {
            int increase = lis[i];
            int decrease = lds[i];
            int halfLength = Math.min(increase, decrease);

            int length = halfLength + halfLength - 1;
            maxLength = Math.max(maxLength, length);
        }

        return maxLength;
    }

    private int[] calculateLDS(int[] values) {
        return reverseValues(calculateLIS(reverseValues(values)));
    }

    private int[] calculateLIS(int[] values) {
        int[] lisLength = new int[values.length];
        ArrayList<Integer> lisSequence = new ArrayList<>(values.length);

        for (int i = 0; i < values.length; i++) {
            int value = values[i];

            int replaceID = lisSequence.size();
            while (replaceID > 0) {
                if (value <= lisSequence.get(replaceID - 1)) replaceID--;
                else break;
            }

            if (replaceID == lisSequence.size()) lisSequence.add(value);
            else lisSequence.set(replaceID, value);

            lisLength[i] = lisSequence.size();
        }

        return lisLength;
    }

    private int[] reverseValues(int[] values) {
        int[] reverseValues = new int[values.length];
        for (int i = 0, j = values.length - 1; i < values.length; i++, j--) reverseValues[i] = values[j];
        return reverseValues;
    }
}
