package uhunt.c3.p12390;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static int MAX_CITY = 500000;

    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        String[] lines;

        int totalCity;
        int totalBox;
        int[] populations = new int[MAX_CITY];

        while ((line = in.readLine()) != null && !line.isEmpty()) {
            lines = line.split(" ");

            totalCity = Integer.parseInt(lines[0]);
            totalBox = Integer.parseInt(lines[1]);
            if (totalCity == -1 && totalBox == -1) break;

            for (int i = 0; i < totalCity; i++) {
                line = in.readLine();
                populations[i] = Integer.parseInt(line);
            }

            in.readLine();

            Solution solution = new Solution(totalCity, totalBox, populations);
            int population = solution.getMaximumPerBox();

            System.out.println(population);
        }
    }
}

class Solution {
    private final int totalCity;
    private final int totalBox;
    private final int[] populations;

    public Solution(int totalCity, int totalBox, int[] populations) {
        this.totalCity = totalCity;
        this.totalBox = totalBox;
        this.populations = populations;
    }

    public int getMaximumPerBox() {
        int lo = 1;
        int hi = maxPopulation();

        while (lo < hi) {
            int mi = (lo + hi) / 2;
            int requiredBox = countBox(mi);

            boolean satisfied = requiredBox <= totalBox;
            if (satisfied) hi = mi;
            else lo = mi + 1;
        }

        return hi;
    }

    private int maxPopulation() {
        int max = populations[0];
        for (int i = 1; i < totalCity; i++) max = Math.max(max, populations[i]);
        return max;
    }

    private int countBox(int maxPopulation) {
        int countBox = 0;
        for (int i = 0; i < totalCity; i++) {
            countBox += divCeil(populations[i], maxPopulation);
        }
        return countBox;
    }

    private int divCeil(int a, int b) {
        boolean divisible = a % b == 0;
        return a / b + (divisible ? 0 : 1);
    }
}
