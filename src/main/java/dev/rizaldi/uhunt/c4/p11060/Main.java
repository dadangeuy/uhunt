package dev.rizaldi.uhunt.c4.p11060;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static BufferedReader in;
    private static String line;
    private static String[] lines;

    private static int totalBeverage;
    private static String[] beverages = new String[100];
    private static int totalSequence;
    private static String[][] sequences = new String[200][2];

    public static void main(String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        int testCase = 0;

        while ((line = in.readLine()) != null && !line.isEmpty()) {
            testCase++;

            totalBeverage = Integer.parseInt(line);
            for (int i = 0; i < totalBeverage; i++) {
                line = in.readLine();
                beverages[i] = line;
            }

            line = in.readLine();
            totalSequence = Integer.parseInt(line);
            for (int i = 0; i < totalSequence; i++) {
                line = in.readLine();
                lines = line.split(" ", 2);

                sequences[i][0] = lines[0];
                sequences[i][1] = lines[1];
            }

            in.readLine();

            Solution solution = new Solution(totalBeverage, beverages, totalSequence, sequences);
            String[] drinks = solution.getDrinkSequence();

            System.out.format("Case #%d: Dilbert should drink beverages in this order:", testCase);
            for (int i = 0; i < totalBeverage; i++) {
                String beverage = drinks[i];
                System.out.format(" %s", beverage);
            }
            System.out.format(".\n\n");
        }
    }
}

// use topological sort (kahn algo), to process beverage, starting from beverage that has no dependency, and resolve
// the dependency along the way
// use priority queue to preserve the input sequence when drinking
class Solution {
    private final int totalBeverage;
    private final String[] beverages;
    private final int totalSequence;
    private final String[][] sequences;

    public Solution(int totalBeverage, String[] beverages, int totalSequence, String[][] sequences) {
        this.totalBeverage = totalBeverage;
        this.beverages = beverages;
        this.totalSequence = totalSequence;
        this.sequences = sequences;
    }

    public String[] getDrinkSequence() {
        // map beverage to id
        Map<String, Integer> beverageIds = new HashMap<>(2 * totalBeverage);
        for (int id = 0; id < totalBeverage; id++) {
            String beverage = beverages[id];
            beverageIds.put(beverage, id);
        }

        // map beverage to in degree
        Map<String, Integer> inDegrees = new HashMap<>(2 * totalBeverage);
        for (int i = 0; i < totalSequence; i++) {
            String[] sequence = sequences[i];
            String nextBeverage = sequence[1];

            inDegrees.compute(nextBeverage, (k, v) -> v == null ? 1 : v + 1);
        }

        // map beverage to next beverage
        Map<String, List<String>> graph = new HashMap<>(2 * totalBeverage);
        for (int i = 0; i < totalSequence; i++) {
            String[] sequence = sequences[i];
            String beverage = sequence[0];
            String nextBeverage = sequence[1];

            graph
                .computeIfAbsent(beverage, k -> new LinkedList<>())
                .add(nextBeverage);
        }

        // use pq to preserve input sequence during topological sort
        Comparator<String> compareById = Comparator.comparingInt(beverageIds::get);
        PriorityQueue<String> beverageq = new PriorityQueue<>(compareById);
        for (int i = 0; i < totalBeverage; i++) {
            String beverage = beverages[i];
            int inDegree = inDegrees.getOrDefault(beverage, 0);

            boolean canDrink = inDegree == 0;
            if (canDrink) beverageq.add(beverage);
        }

        LinkedList<String> drinks = new LinkedList<>();
        while (!beverageq.isEmpty()) {
            String beverage = beverageq.poll();
            drinks.addLast(beverage);

            graph
                .getOrDefault(beverage, Collections.emptyList())
                .forEach(next -> {
                    int inDegree = inDegrees.compute(next, (k, v) -> v == null ? 0 : v - 1);

                    boolean canDrink = inDegree == 0;
                    if (canDrink) beverageq.add(next);
                });
        }

        return drinks.toArray(new String[0]);
    }
}
