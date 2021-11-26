package dev.rizaldi.uhunt.p10130;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int totalTest = in.nextInt();
        while (totalTest-- > 0) {
            int totalObject = in.nextInt();
            int[] prices = new int[totalObject];
            int[] weights = new int[totalObject];
            for (int i = 0; i < totalObject; i++) {
                int price = in.nextInt();
                int weight = in.nextInt();
                prices[i] = price;
                weights[i] = weight;
            }

            int totalPeople = in.nextInt();
            int[] weightLimits = new int[totalPeople];
            for (int i = 0; i < totalPeople; i++) {
                int weightLimit = in.nextInt();
                weightLimits[i] = weightLimit;
            }

            Solution solution = new Solution(totalObject, prices, weights, totalPeople, weightLimits);
            int maxPrice = solution.getMaxPrice();

            System.out.println(maxPrice);
        }
    }
}

class Solution {
    private static final int[] pricePerWeight = new int[31]; // 1 <= weight <= 30
    private final int totalObject;
    private final int[] prices;
    private final int[] weights;
    private final int totalPeople;
    private final int[] weightLimits;

    public Solution(int totalObject, int[] prices, int[] weights, int totalPeople, int[] weightLimits) {
        this.totalObject = totalObject;
        this.prices = prices;
        this.weights = weights;
        this.totalPeople = totalPeople;
        this.weightLimits = weightLimits;
    }

    public int getMaxPrice() {
        int totalMaxPrice = 0;
        for (int peopleID = 0; peopleID < totalPeople; peopleID++) {
            int weightLimit = weightLimits[peopleID];
            int currMaxPrice = getMaxPrice(weightLimit);
            totalMaxPrice += currMaxPrice;
        }

        return totalMaxPrice;
    }

    private int getMaxPrice(int weightLimit) {
        Arrays.fill(pricePerWeight, 0);

        for (int objectID = 0; objectID < totalObject; objectID++) {
            int currPrice = prices[objectID];
            int currWeight = weights[objectID];

            for (int prevWeight = weightLimit; prevWeight >= 0; prevWeight--) {
                int prevPrice = pricePerWeight[prevWeight];

                int nextWeight = prevWeight + currWeight;
                int nextPrice = prevPrice + currPrice;
                if (nextWeight > weightLimit) continue;

                pricePerWeight[nextWeight] = Math.max(pricePerWeight[nextWeight], nextPrice);
            }
        }

        int maxPrice = 0;
        for (int price : pricePerWeight) maxPrice = Math.max(maxPrice, price);

        return maxPrice;
    }
}
