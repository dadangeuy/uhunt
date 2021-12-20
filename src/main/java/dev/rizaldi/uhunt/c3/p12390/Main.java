package dev.rizaldi.uhunt.c3.p12390;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int totalCity;
        int totalBox;
        int[] populations = new int[500001];

        while (in.hasNextInt()) {
            totalCity = in.nextInt();
            totalBox = in.nextInt();
            if (totalCity == -1 && totalBox == -1) break;

            for (int i = 0; i < totalCity; i++) populations[i] = in.nextInt();

            Solution solution = new Solution(totalCity, totalBox, populations);
            int population = solution.getMaximumPerBox();

            System.out.println(population);
        }
    }
}

class Solution {
    private static final Comparator<int[]> SORT_BY_POPULATION_PER_BOX = Comparator
            .<int[]>comparingDouble(v -> (double) v[0] / (double) v[1])
            .reversed();
    private static final PriorityQueue<int[]> QUEUE = new PriorityQueue<>(SORT_BY_POPULATION_PER_BOX);
    private final int totalCity;
    private final int totalBox;
    private final int[] populations;

    public Solution(int totalCity, int totalBox, int[] populations) {
        this.totalCity = totalCity;
        this.totalBox = totalBox;
        this.populations = populations;
    }

    public int getMaximumPerBox() {
        PriorityQueue<int[]> cityq = newQueue();
        for (int i = 0; i < totalCity; i++) {
            int population = populations[i];
            cityq.add(new int[]{population, 1});
        }

        for (int i = totalCity; i < totalBox; i++) {
            int[] city = cityq.remove();
            int population = city[0];
            int box = city[1];

            if (population == box) return 1;

            cityq.add(new int[]{population, box + 1});
        }

        if (cityq.isEmpty()) return 0;

        int[] city = cityq.peek();
        int population = city[0];
        int box = city[1];

        return (int) Math.ceil((double) population / (double) box);
    }

    private PriorityQueue<int[]> newQueue() {
        QUEUE.clear();
        return QUEUE;
    }
}
