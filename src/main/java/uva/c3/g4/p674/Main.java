package uva.c3.g4.p674;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String... args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        in.lines().forEach(line -> {
            int totalMoney = Integer.parseInt(line);

            Solution solution = new Solution(totalMoney);
            int totalWay = solution.getTotalWay();

            System.out.println(totalWay);
        });
    }
}

class Solution {
    private static final int[] coins = new int[]{1, 5, 10, 25, 50};
    private static final int[][] cachedTotal = newArray2D(coins.length, 7490, -1);
    private final int totalMoney;

    private static int[][] newArray2D(int row, int col, int initial) {
        int[][] arr = new int[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                arr[i][j] = initial;
        return arr;
    }

    public Solution(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getTotalWay() {
        return doGetTotalWay(0, totalMoney);
    }

    private int doGetTotalWay(int coinType, int targetMoney) {
        if (targetMoney < 0) return 0; // invalid target
        if (coinType >= coins.length) return 0; // invalid coin
        if (targetMoney == 0) return 1; // 1-way, use no coin
        if (cachedTotal[coinType][targetMoney] != -1) return cachedTotal[coinType][targetMoney];

        int total = doGetTotalWay(coinType + 1, targetMoney) + doGetTotalWay(coinType, targetMoney - coins[coinType]);
        cachedTotal[coinType][targetMoney] = total;

        return total;
    }
}
