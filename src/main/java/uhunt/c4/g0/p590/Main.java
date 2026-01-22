package uhunt.c4.g0.p590;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

/**
 * 590 - Always on the run
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=531
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        for (int scenario = 1; ; scenario++) {
            final String[] l1 = readSplitLine(in);
            if (l1 == null) break;

            final int totalCities = Integer.parseInt(l1[0]);
            final int totalFlights = Integer.parseInt(l1[1]);
            if (totalCities == 0 && totalFlights == 0) break;

            final List<FlightSchedule> flightSchedules = new ArrayList<>(totalCities * totalCities);

            for (int i = 0; i < totalCities; i++) {
                for (int j = 0; j < totalCities; j++) {
                    if (i == j) continue;
                    final String[] l2 = readSplitLine(in);
                    final int period = Integer.parseInt(l2[0]);
                    final int[] prices = IntStream.range(1, l2.length)
                            .map(day -> Integer.parseInt(l2[day]))
                            .toArray();
                    final FlightSchedule flightSchedule = new FlightSchedule(i, j, period, prices);
                    flightSchedules.add(flightSchedule);
                }
            }

            final Input input = new Input(scenario, totalCities, totalFlights, flightSchedules);
            final Output output = process.process(input);

            out.write(String.format("Scenario #%d\n", output.scenario));
            if (output.cheapestFlightCost.isPresent()) {
                out.write(String.format("The best flight costs %d.\n\n", output.cheapestFlightCost.get()));
            } else {
                out.write("No flight possible.\n\n");
            }
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String[] readSplitLine(final BufferedReader in) throws IOException {
        final String line = readLine(in);
        return line == null ? null : line.split(" ");
    }

    private static String readLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}

class FlightSchedule {
    public static final int NO_FLIGHT = 0;

    public final int from;
    public final int into;
    public final int period;
    public final int[] prices;

    public FlightSchedule(
            final int from,
            final int into,
            final int period,
            final int[] prices
    ) {
        this.from = from;
        this.into = into;
        this.period = period;
        this.prices = prices;
    }

    public Optional<Integer> getPrice(final int day) {
        final int price = prices[day % period];
        return price == NO_FLIGHT ? Optional.empty() : Optional.of(price);
    }
}

class Input {
    public final int scenario;
    public final int totalCities;
    public final int totalFlights;
    public final List<FlightSchedule> flightSchedules;

    public Input(
            final int scenario,
            final int totalCities,
            final int totalFlights,
            final List<FlightSchedule> flightSchedules
    ) {
        this.scenario = scenario;
        this.totalCities = totalCities;
        this.totalFlights = totalFlights;
        this.flightSchedules = flightSchedules;
    }
}

class Output {
    public final int scenario;
    public final Optional<Integer> cheapestFlightCost;

    public Output(final int scenario, final Optional<Integer> cheapestFlightCost) {
        this.scenario = scenario;
        this.cheapestFlightCost = cheapestFlightCost;
    }
}

/**
 * observation:
 * - "making one flight per day", never stay in a city
 * - dijkstra based on flight cost
 * - how to avoid circular route?
 */
class Process {
    public Output process(final Input input) {
        // index flight schedule by origin and destination
        final FlightSchedule[][] flightSchedulePerRoute = new FlightSchedule[input.totalCities][input.totalCities];
        for (final FlightSchedule flightSchedule : input.flightSchedules) {
            flightSchedulePerRoute[flightSchedule.from][flightSchedule.into] = flightSchedule;
        }

        final int paris = 0;
        final int atlanta = input.totalCities - 1;

        final Optional<Integer> cheapestFlightCost = findCheapestFlightCost(
                input.totalCities,
                input.totalFlights,
                flightSchedulePerRoute,
                paris,
                atlanta
        );

        return new Output(input.scenario, cheapestFlightCost);
    }

    private Optional<Integer> findCheapestFlightCost(
            final int totalCities,
            final int totalFlights,
            final FlightSchedule[][] flightSchedulePerRoute,
            final int origin,
            final int destination
    ) {
        return findFlightCost(Move.ORDER_BY_COST_ASC, totalCities, totalFlights, flightSchedulePerRoute, origin, destination);
    }

    private Optional<Integer> findFlightCost(
            final Comparator<Move> priority,
            final int totalCities,
            final int totalFlights,
            final FlightSchedule[][] flightSchedulePerRoute,
            final int origin,
            final int destination
    ) {
        if (totalFlights == 0) return Optional.empty();

        final PriorityQueue<Move> possibleMoves = new PriorityQueue<>(priority);

        final Move initialMove = new Move(0, origin, 0);
        possibleMoves.add(initialMove);

        final int[][] costPerDayAndCity = new int[totalFlights][totalCities];
        for (int day = 0; day < totalFlights; day++) {
            for (int city = 0; city < totalCities; city++) {
                costPerDayAndCity[day][city] = Integer.MAX_VALUE;
            }
        }

        while (!possibleMoves.isEmpty()) {
            final Move move = possibleMoves.remove();

            if (move.day < totalFlights) {
                for (int nextCity = 0; nextCity < totalCities; nextCity++) {
                    if (move.city == nextCity) continue;

                    // check whether flight exists
                    final Optional<Integer> price = flightSchedulePerRoute[move.city][nextCity].getPrice(move.day);
                    if (!price.isPresent()) continue;

                    // check whether current cost is cheaper than previously calculated cost
                    final int cost = move.cost + price.get();
                    final int oldCost = costPerDayAndCity[move.day][nextCity];
                    if (cost > oldCost) continue;
                    costPerDayAndCity[move.day][nextCity] = cost;

                    // queue move to the next city
                    final Move nextMove = new Move(move.day + 1, nextCity, cost);
                    possibleMoves.add(nextMove);
                }
            }
        }

        final int cost = costPerDayAndCity[totalFlights - 1][destination];
        return Optional.ofNullable(cost == Integer.MAX_VALUE ? null : cost);
    }
}

class Move {
    public static final Comparator<Move> ORDER_BY_COST_ASC = Comparator.comparingInt(m -> m.cost);

    public final int day;
    public final int city;
    public final int cost;

    public Move(
            final int day,
            final int city,
            final int cost
    ) {
        this.day = day;
        this.city = city;
        this.cost = cost;
    }
}
