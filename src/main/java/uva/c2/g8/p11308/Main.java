package uva.c2.g8.p11308;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Main {
    private static BufferedReader in;
    private static String[] lines;

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));

        int totalBinder = Integer.parseInt(in.readLine());
        for (int i = 0; i < totalBinder; i++) {
            String binderTitle = in.readLine();

            lines = in.readLine().split(" ", 3);
            int totalIngredient = Integer.parseInt(lines[0]);
            int totalRecipe = Integer.parseInt(lines[1]);
            int budget = Integer.parseInt(lines[2]);

            Map<String, Integer> costPerIngredient = new HashMap<>(totalIngredient * 2);
            for (int j = 0; j < totalIngredient; j++) {
                lines = in.readLine().split(" ", 2);
                String ingredient = lines[0];
                int cost = Integer.parseInt(lines[1]);
                costPerIngredient.compute(ingredient, (k, v) -> v == null ? cost : Math.min(v, cost));
            }

            Comparator<Pair<String, Integer>> compareByCostAndName = Comparator
                .<Pair<String, Integer>>comparingInt(v -> v.second)
                .thenComparing(v -> v.first);
            PriorityQueue<Pair<String, Integer>> budgetRecipes = new PriorityQueue<>(compareByCostAndName);

            for (int j = 0; j < totalRecipe; j++) {
                String recipeName = in.readLine();
                int totalRequirement = Integer.parseInt(in.readLine());
                int cost = 0;
                for (int k = 0; k < totalRequirement; k++) {
                    lines = in.readLine().split(" ", 2);
                    String ingredient = lines[0];
                    int amount = Integer.parseInt(lines[1]);
                    cost += costPerIngredient.get(ingredient) * amount;
                }
                if (cost <= budget) budgetRecipes.add(new Pair<>(recipeName, cost));
            }

            System.out.println(binderTitle.toUpperCase());
            if (budgetRecipes.isEmpty()) System.out.println("Too expensive!");
            else {
                while (!budgetRecipes.isEmpty()) {
                    String recipe = budgetRecipes.poll().first;
                    System.out.println(recipe);
                }
            }
            System.out.println();
        }
    }
}

class Pair<K, V> {
    public final K first;
    public final V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }
}
