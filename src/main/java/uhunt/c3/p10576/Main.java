package uhunt.c3.p10576;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static BufferedReader in;
    private static String line;
    private static String[] lines;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            lines = line.split(" ", 2);

            int monthlySurplus = Integer.parseInt(lines[0]);
            int monthlyDeficit = Integer.parseInt(lines[1]);

            Solution solution = new Solution(monthlySurplus, monthlyDeficit);
            int earning = solution.getMaxEarning();

            if (earning <= 0) System.out.println("Deficit");
            else System.out.println(earning);
        }
    }
}

// explore all possible earnings
// in each month, we can get either surplus or deficit
// check if it's a valid earnings (deficit for each 5 consecutive month)
// return maximum surplus
// time complexity: O(2^12), combination of surplus/deficit every month
class Solution {
    private static final int[] MONTHLY_EARNINGS = new int[12];
    private final int monthlySurplus;
    private final int monthlyDeficit;

    public Solution(int monthlySurplus, int monthlyDeficit) {
        this.monthlySurplus = monthlySurplus;
        this.monthlyDeficit = monthlyDeficit;
    }

    public int getMaxEarning() {
        return getMaxEarningFromEveryCombination(MONTHLY_EARNINGS, 0);
    }

    public int getMaxEarningFromEveryCombination(int[] monthlyEarnings, int month) {
        if (month == 12) {
            if (!valid(monthlyEarnings)) return 0;
            return sum(monthlyEarnings, 0, 12);
        }

        // path 1: deficit
        monthlyEarnings[month] = -monthlyDeficit;
        int deficitEarning = getMaxEarningFromEveryCombination(monthlyEarnings, month + 1);

        // path 2: surplus
        monthlyEarnings[month] = monthlySurplus;
        int surplusEarning = getMaxEarningFromEveryCombination(monthlyEarnings, month + 1);

        return Math.max(deficitEarning, surplusEarning);
    }

    private boolean valid(int[] monthlyEarnings) {
        for (int startMonth = 0, endMonth = 5; endMonth <= 12; startMonth++, endMonth++) {
            int earning = sum(monthlyEarnings, startMonth, endMonth);
            if (!deficit(earning)) return false;
        }
        return true;
    }

    private int sum(int[] arr, int from, int to) {
        int sum = 0;
        for (int i = from; i < to; i++) sum += arr[i];
        return sum;
    }

    private boolean deficit(int earning) {
        return earning <= 0;
    }
}
