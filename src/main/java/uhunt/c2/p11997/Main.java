package uhunt.c2.p11997;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    public static final int MAX_ELEMENT = 750;

    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        String[] lines;

        int totalArray;
        int[][] arrays = new int[MAX_ELEMENT][MAX_ELEMENT];

        while ((line = in.readLine()) != null && !line.isEmpty()) {
            totalArray = Integer.parseInt(line);
            for (int i = 0; i < totalArray; i++) {
                line = in.readLine();
                lines = line.split(" ");
                for (int j = 0; j < totalArray; j++) {
                    arrays[i][j] = Integer.parseInt(lines[j]);
                }
            }

            Solution solution = new Solution(totalArray, arrays);
            int[] topSmallest = solution.topSmallest();

            for (int i = 0; i < totalArray; i++) {
                System.out.format("%d%c", topSmallest[i], i == totalArray - 1 ? '\n' : ' ');
            }
        }
    }
}

class Solution {
    private static final PriorityQueue<Integer> descendingq = new PriorityQueue<>(Main.MAX_ELEMENT + 1, Comparator.reverseOrder());
    private final int totalElement;
    private final int[][] arrays;

    public Solution(int totalElement, int[][] arrays) {
        this.totalElement = totalElement;
        this.arrays = arrays;
    }

    public int[] topSmallest() {
        for (int i = 0; i < totalElement; i++) Arrays.sort(arrays[i], 0, totalElement);

        for (int i = 1; i < totalElement; i++) {
            int[] prev = arrays[i - 1];
            int[] curr = arrays[i];

            for (int j = 0; j < totalElement; j++) {
                int sum = prev[j] + curr[0];
                descendingq.add(sum);
            }

            for (int j = 0; j < totalElement; j++) {
                int minSum = prev[j] + curr[0];
                if (minSum >= descendingq.peek()) break;

                for (int k = 1; k < totalElement; k++) {
                    int sum = prev[j] + curr[k];
                    if (sum >= descendingq.peek()) break;

                    descendingq.add(sum);
                    descendingq.remove();
                }
            }

            for (int j = totalElement - 1; j >= 0; j--) arrays[i][j] = descendingq.remove();
        }

        return arrays[totalElement - 1];
    }
}
