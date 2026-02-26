package uva.c3.g9.p11369;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int totalTest = in.nextInt();
        int[] prices = new int[20001];
        for (int test = 0; test < totalTest; test++) {
            int totalItem = in.nextInt();
            for (int i = 0; i < totalItem; i++) prices[i] = in.nextInt();

            Solution solution = new Solution(totalItem, prices);
            int discount = solution.getMaximumDiscount();

            System.out.println(discount);
        }
    }
}

class Solution {
    private final int totalItem;
    private final int[] prices;

    public Solution(int totalItem, int[] prices) {
        this.totalItem = totalItem;
        this.prices = prices;
    }

    public int getMaximumDiscount() {
        Arrays.sort(prices, 0, totalItem);
        int discount = 0;
        for (int i = totalItem - 3; i >= 0; i -= 3) discount += prices[i];
        return discount;
    }
}
