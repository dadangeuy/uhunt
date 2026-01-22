package uhunt.c3.g7.p11517;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    private static BufferedReader in;
    private static StringBuilder out;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new StringBuilder();

        int totatlTest = Integer.parseInt(in.readLine());
        for (int i = 0; i < totatlTest; i++) {
            int bill = Integer.parseInt(in.readLine());

            int totalCoin = Integer.parseInt(in.readLine());
            int[] coins = new int[totalCoin];
            for (int j = 0; j < totalCoin; j++) {
                coins[j] = Integer.parseInt(in.readLine());
            }

            Solution solution = new Solution(bill, coins);
            int[] totalPaidAndCoin = solution.getTotalPaidAndCoinMinimum();

            int paid = totalPaidAndCoin[0];
            int coin = totalPaidAndCoin[1];
            out.append(paid).append(' ').append(coin).append('\n');
        }

        System.out.print(out);
    }
}

// important constraint: we can only use each coin once
// use dp to store minimum coin that we need at each paid state
// for each coin, we have a choice to use that coin or skip it
// for each coin, update each paid state, i.e. new paid state = (paid state + coin), new total coin = total coin + 1
// time complexity = O(bill * coin) = 10^6
class Solution {
    private static final int[] totalCoinPerPaid = new int[20_001];
    private final int bill;
    private final int[] coins;

    public Solution(int bill, int[] coins) {
        this.bill = bill;
        this.coins = coins;
    }

    public int[] getTotalPaidAndCoinMinimum() {
        Arrays.fill(totalCoinPerPaid, Integer.MAX_VALUE);
        totalCoinPerPaid[0] = 0;

        for (int coin : coins) {
            for (int paid = bill - 1; paid >= 0; paid--) {
                if (totalCoinPerPaid[paid] == Integer.MAX_VALUE) continue;

                int nextPaid = paid + coin;
                int nextTotalCoin = totalCoinPerPaid[paid] + 1;

                totalCoinPerPaid[nextPaid] = Math.min(totalCoinPerPaid[nextPaid], nextTotalCoin);
            }
        }

        for (int paid = bill; paid <= 20_000; paid++) {
            if (totalCoinPerPaid[paid] == Integer.MAX_VALUE) continue;

            int totalCoin = totalCoinPerPaid[paid];
            return new int[]{paid, totalCoin};
        }

        return null;
    }
}
