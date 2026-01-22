package uhunt.c4.g0.p11280;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String... args) throws IOException {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 2 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 2 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            int totalCity = in.nextInt();

            String[] cities = new String[totalCity];
            for (int j = 0; j < totalCity; j++) cities[j] = in.next();

            int totalFlight = in.nextInt();

            Flight[] flights = new Flight[totalFlight];
            for (int j = 0; j < totalFlight; j++) {
                String departureCity = in.next();
                String destinationCity = in.next();
                int cost = in.nextInt();

                Flight flight = new Flight(departureCity, destinationCity, cost);
                flights[j] = flight;
            }

            out.print("Scenario #");
            out.println(i + 1);

            Solution solution = new Solution(totalCity, cities, totalFlight, flights);

            int totalQuery = in.nextInt();
            for (int j = 0; j < totalQuery; j++) {
                int maxTransit = in.nextInt();
                Optional<Integer> cost = solution.getLeastTotalCost(maxTransit);

                if (cost.isPresent()) {
                    out.print("Total cost of flight(s) is $");
                    out.println(cost.get());
                } else {
                    out.println("No satisfactory flights");
                }
            }

            if (i < totalCase - 1) out.println();
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Flight {
    public final String departureCity;
    public final String destinationCity;
    public final int cost;

    public Flight(String departureCity, String destinationCity, int cost) {
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.cost = cost;
    }
}

/**
 * 1. calculate all pair shortest path to reach city A with B transit from departure city using floyd-warshall algorithm
 * 2. no negative cost, but it's possible to get free ticket
 * 3. maximum transit == total city
 *
 * <p>
 * time complexity: O(V^3) => O(10^6)
 */
class Solution {
    private static final String departureCity = "Calgary";
    private static final String destinationCity = "Fredericton";
    private static final int notFound = Integer.MAX_VALUE;

    private final int totalCity;
    private final String[] cities;
    private final int totalFlight;
    private final Flight[] flights;

    private final Map<String, Integer> ids;
    private final int departureId;
    private final int destinationId;
    private final int[][] costPerFlight;
    private final int[][] costPerCityPerTransit;

    public Solution(int totalCity, String[] cities, int totalFlight, Flight[] flights) {
        this.totalCity = totalCity;
        this.cities = cities;
        this.totalFlight = totalFlight;
        this.flights = flights;

        ids = new HashMap<>(2 * totalCity);
        for (int i = 0; i < totalCity; i++) this.ids.put(cities[i], i);

        departureId = ids.get(departureCity);
        destinationId = ids.get(destinationCity);
        costPerFlight = buildGraph();
        costPerCityPerTransit = getCostPerCityPerTransit();
    }

    public Optional<Integer> getLeastTotalCost(int maxTransit) {
        maxTransit = Math.min(maxTransit, totalCity - 1);
        int cost = costPerCityPerTransit[destinationId][maxTransit];
        boolean noRoute = cost == Integer.MAX_VALUE;
        return noRoute ? Optional.empty() : Optional.of(cost);
    }

    private int[][] buildGraph() {
        int[][] graph = new int[totalCity][totalCity];
        for (int[] g : graph) Arrays.fill(g, Integer.MAX_VALUE);

        for (Flight flight : flights) {
            int id1 = ids.get(flight.departureCity);
            int id2 = ids.get(flight.destinationCity);
            graph[id1][id2] = Math.min(graph[id1][id2], flight.cost);
        }
        return graph;
    }

    private int[][] getCostPerCityPerTransit() {
        // state: city, transit
        int[][] costPerCityPerTransit = new int[totalCity][totalCity];
        for (int[] arr : costPerCityPerTransit) Arrays.fill(arr, notFound);

        for (int destinationId = 0; destinationId < totalCity; destinationId++) {
            if (costPerFlight[departureId][destinationId] == notFound) continue;

            int ticketCost = costPerFlight[departureId][destinationId];
            costPerCityPerTransit[destinationId][0] = Math.min(costPerCityPerTransit[destinationId][0], ticketCost);
        }

        for (int destinationId = 0; destinationId < totalCity; destinationId++) {
            for (int departureId = 0; departureId < totalCity; departureId++) {
                for (int transit = 0; transit < totalCity - 1; transit++) {
                    int departureCost = costPerCityPerTransit[departureId][transit];
                    if (departureCost == notFound) continue;
                    costPerCityPerTransit[departureId][transit + 1] = Math.min(costPerCityPerTransit[departureId][transit + 1], departureCost);

                    int ticketCost = costPerFlight[departureId][destinationId];
                    if (ticketCost == notFound) continue;

                    int transitCost = departureCost + ticketCost;
                    costPerCityPerTransit[destinationId][transit + 1] = Math.min(costPerCityPerTransit[destinationId][transit + 1], transitCost);
                }
            }
        }

        return costPerCityPerTransit;
    }
}
