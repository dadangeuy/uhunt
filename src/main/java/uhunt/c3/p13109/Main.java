package uhunt.c3.p13109;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 13109 - Elephants
 * Time limit: 2.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=5020
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCases = in.nextInt();
        for (int i = 0; i < totalCases; i++) {
            int totalElephants = in.nextInt();
            int maxWeight = in.nextInt();
            int[] weights = new int[totalElephants];
            for (int j = 0; j < totalElephants; j++) weights[j] = in.nextInt();

            Solution solution = new Solution(totalElephants, maxWeight, weights);
            int maxTotalElephants = solution.getMaxTotalElephants();
            out.println(maxTotalElephants);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int totalElephants;
    private final int maxWeight;
    private final int[] weights;

    public Solution(int totalElephants, int maxWeight, int[] weights) {
        this.totalElephants = totalElephants;
        this.maxWeight = maxWeight;
        this.weights = weights;
    }

    public int getMaxTotalElephants() {
        int maxTotalElephants = 0, accumulatedWeight = 0;

        Arrays.sort(weights);
        for (int weight : weights) {
            if (accumulatedWeight + weight > maxWeight) break;
            maxTotalElephants++;
            accumulatedWeight += weight;
        }

        return maxTotalElephants;
    }
}
