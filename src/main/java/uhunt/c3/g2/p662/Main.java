package uhunt.c3.g2.p662;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 662 - Fast Food
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=603
 */
public class Main {
    public static void main(final String... args) throws InterruptedException {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        for (int chain = 1; ; chain++) {
            final Input input = new Input();

            input.chain = chain;

            input.totalRestaurants = in.nextInt();
            input.totalDepots = in.nextInt();
            final boolean isEOF = input.totalRestaurants == 0 && input.totalDepots == 0;
            if (isEOF) break;

            input.restaurants = new int[input.totalRestaurants];
            for (int i = 0; i < input.totalRestaurants; i++) {
                input.restaurants[i] = in.nextInt();
            }

            final Output output = process.process(input);

            out.format("Chain %d\n", chain);
            for (final Depot depot : output.depots) {
                if (depot.hasOneCoverage()) {
                    out.format(
                        "Depot %d at restaurant %d serves restaurant %d\n",
                        depot.id, depot.restaurant.id, depot.fromRestaurant.id
                    );
                } else {
                    out.format(
                        "Depot %d at restaurant %d serves restaurants %d to %d\n",
                        depot.id, depot.restaurant.id, depot.fromRestaurant.id, depot.untilRestaurant.id
                    );
                }
            }
            out.format("Total distance sum = %d\n\n", output.totalDistanceSum);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int chain;
    public int totalRestaurants;
    public int totalDepots;
    public int[] restaurants;
}

class Output {
    public int chain;
    public Depot[] depots;
    public int totalDistanceSum;
}

class Process {
    public Output process(final Input input) {
        final Chain[][][] chainPerState = new Chain[input.totalRestaurants][input.totalRestaurants][input.totalDepots + 1];

        final Restaurant[] restaurants = IntStream.range(0, input.totalRestaurants)
            .mapToObj(idx -> new Restaurant(idx + 1, input.restaurants[idx]))
            .toArray(Restaurant[]::new);

        final Chain chain = getChain(
            restaurants,
            0,
            input.totalRestaurants - 1,
            input.totalDepots,
            chainPerState
        );

        final Output output = new Output();
        output.chain = input.chain;
        output.depots = chain.getDepots();
        output.totalDistanceSum = chain.cost;

        return output;
    }

    private Chain getChain(
        final Restaurant[] restaurants,
        final int fromRestaurantIdx,
        final int untilRestaurantIdx,
        final int remainingDepots,
        final Chain[][][] chainPerState
    ) {
        final Chain calculatedChain = chainPerState[fromRestaurantIdx][untilRestaurantIdx][remainingDepots];
        if (calculatedChain != null) {
            return calculatedChain;
        }

        if (remainingDepots == 1) {
            final Depot depot = getDepot(restaurants, fromRestaurantIdx, untilRestaurantIdx);
            final Chain chain = new Chain(Stream.of(depot), depot.cost);
            return chainPerState[fromRestaurantIdx][untilRestaurantIdx][remainingDepots] = chain;
        }

        Chain minChain = null;
        for (
            int splitRestaurantIdx = fromRestaurantIdx;
            splitRestaurantIdx <= untilRestaurantIdx - 1;
            splitRestaurantIdx++
        ) {
            final Chain chain1 = getChain(
                restaurants,
                fromRestaurantIdx,
                splitRestaurantIdx,
                1,
                chainPerState
            );

            final Chain chain2 = getChain(
                restaurants,
                splitRestaurantIdx + 1,
                untilRestaurantIdx,
                remainingDepots - 1,
                chainPerState
            );

            final boolean isInvalid = chain1 == null || chain2 == null;
            if (isInvalid) {
                break;
            }

            final Chain chain = chain1.merge(chain2);
            if (minChain == null || chain.cost < minChain.cost) {
                minChain = chain;
            }
        }

        return chainPerState[fromRestaurantIdx][untilRestaurantIdx][remainingDepots] = minChain;
    }

    private Depot getDepot(
        final Restaurant[] restaurants,
        final int fromRestaurantIdx,
        final int untilRestaurantIdx
    ) {
        Depot minimumDepot = null;

        for (int depotIdx = fromRestaurantIdx; depotIdx <= untilRestaurantIdx; depotIdx++) {
            final Restaurant restaurant = restaurants[depotIdx];
            final int cost = IntStream.rangeClosed(fromRestaurantIdx, untilRestaurantIdx)
                .map(idx -> Math.abs(restaurant.location - restaurants[idx].location))
                .sum();

            final Depot depot = restaurant.toDepot(
                restaurants[fromRestaurantIdx],
                restaurants[untilRestaurantIdx],
                cost
            );

            if (minimumDepot == null || depot.cost < minimumDepot.cost) {
                minimumDepot = depot;
            }
        }

        return minimumDepot;
    }
}

class Chain {
    public final Stream<Depot> depots;
    public final int cost;

    public Chain(final Stream<Depot> depots, final int cost) {
        this.depots = depots;
        this.cost = cost;
    }

    public Chain merge(final Chain other) {
        return new Chain(
            Stream.of(depots, other.depots).flatMap(d -> d),
            cost + other.cost
        );
    }

    public Depot[] getDepots() {
        final Depot[] array = depots.toArray(Depot[]::new);
        for (int idx = 0; idx < array.length; idx++) {
            array[idx] = array[idx].setId(idx + 1);
        }
        return array;
    }
}

class Restaurant {
    public final int id;
    public final int location;

    public Restaurant(final int id, final int location) {
        this.id = id;
        this.location = location;
    }

    public Depot toDepot(final Restaurant fromRestaurant, final Restaurant untilRestaurant, int cost) {
        return new Depot(1, this, fromRestaurant, untilRestaurant, cost);
    }
}

class Depot {
    public final int id;
    public final Restaurant restaurant;
    public final Restaurant fromRestaurant;
    public final Restaurant untilRestaurant;
    public final int cost;

    public Depot(
        final int id,
        final Restaurant restaurant,
        final Restaurant fromRestaurant,
        final Restaurant untilRestaurant,
        final int cost
    ) {
        this.id = id;
        this.restaurant = restaurant;
        this.fromRestaurant = fromRestaurant;
        this.untilRestaurant = untilRestaurant;
        this.cost = cost;
    }

    public boolean hasOneCoverage() {
        return fromRestaurant == untilRestaurant;
    }

    public Depot setId(final int id) {
        return new Depot(id, restaurant, fromRestaurant, untilRestaurant, cost);
    }
}
