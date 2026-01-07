package uhunt.c3.p222;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * 222 - Budget Travel
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=158
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        for (int dataset = 1; ; dataset++) {
            final Input input = new Input();
            input.dataset = dataset;
            input.distanceFromOriginToDestination = in.nextDouble();
            final boolean isEOF = input.distanceFromOriginToDestination < 0;
            if (isEOF) break;
            input.tankCapacity = in.nextDouble();
            input.milesPerGallon = in.nextDouble();
            input.refillCostAtOriginCity = in.nextDouble();
            input.totalStations = in.nextInt();
            input.distanceFromOriginPerStation = new double[input.totalStations];
            input.pricePerGallonPerStation = new double[input.totalStations];
            for (int i = 0; i < input.totalStations; i++) {
                input.distanceFromOriginPerStation[i] = in.nextDouble();
                input.pricePerGallonPerStation[i] = in.nextDouble() / 100;
            }

            final Output output = process.process(input);
            out.format("Data Set #%d\nminimum cost = $%.2f\n", output.dataset, output.minimumCost);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int dataset;
    public double distanceFromOriginToDestination;
    public double tankCapacity;
    public double milesPerGallon;
    public double refillCostAtOriginCity;
    public int totalStations;
    public double[] distanceFromOriginPerStation;
    public double[] pricePerGallonPerStation;
}

class Output {
    public int dataset;
    public double minimumCost;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.dataset = input.dataset;

        final City origin = new City(0, input.refillCostAtOriginCity);
        final City destination = new City(input.distanceFromOriginToDestination, 0);
        final Station[] stations = getStations(input);
        final Car car = new Car(input.tankCapacity, input.milesPerGallon, 0, 0);

        final Car carAtOrigin = car.departure(origin);
        final Map<Station, Car> carPerStation = new HashMap<>();

        // origin-to-station
        {
            final List<Station> preferredStations = getPreferredStations(carAtOrigin, stations);
            final List<Station> avoidedStations = getAvoidedStations(carAtOrigin, stations);
            if (preferredStations.isEmpty()) {
                avoidedStations.forEach(station -> carPerStation.put(station, carAtOrigin.transit(station)));
            } else {
                preferredStations.forEach(station -> carPerStation.put(station, carAtOrigin.transit(station)));
            }
        }

        // station-to-station
        for (int i = 0; i < stations.length - 1; i++) {
            final Station station = stations[i];
            final Car carAtStation = carPerStation.get(station);
            if (carAtStation == null) continue;

            final Station[] nextStations = Arrays.copyOfRange(stations, i + 1, stations.length);
            final List<Station> preferredNextStations = getPreferredStations(carAtStation, nextStations);
            final List<Station> avoidedNextStations = getAvoidedStations(carAtStation, nextStations);
            if (preferredNextStations.isEmpty()) {
                avoidedNextStations.forEach(nextStation -> {
                    final Car carAtNextStation = carAtStation.transit(nextStation);
                    carPerStation.compute(nextStation, (k, v) -> min(v, carAtNextStation));
                });
            } else {
                preferredNextStations.forEach(nextStation -> {
                    final Car carAtNextStation = carAtStation.transit(nextStation);
                    carPerStation.compute(nextStation, (k, v) -> min(v, carAtNextStation));
                });
            }
        }

        // origin-to-destination
        final Car noTransitCar = carAtOrigin.arrival(destination);
        if (noTransitCar != null) {
            output.minimumCost = noTransitCar.cost;
            return output;
        }

        // station-to-destination
        final Car transitCar = carPerStation.values().stream()
            .map(c -> c.arrival(destination))
            .filter(Objects::nonNull)
            .min(Comparator.comparingDouble(c -> c.cost))
            .get();
        output.minimumCost = transitCar.cost;
        return output;
    }

    public Station[] getStations(final Input input) {
        final Station[] stations = new Station[input.totalStations];
        for (int i = 0; i < input.totalStations; i++) {
            final Station station = new Station(
                input.distanceFromOriginPerStation[i],
                input.pricePerGallonPerStation[i]
            );
            stations[i] = station;
        }
        return stations;
    }

    public List<Station> getPreferredStations(final Car car, final Station[] stations) {
        final List<Station> matchedStations = new LinkedList<>();

        for (final Station station : stations) {
            if (car.transit(station) == null) break;
            if (!car.avoidTransit(station)) matchedStations.add(station);
        }

        return matchedStations;
    }

    public List<Station> getAvoidedStations(final Car car, final Station[] stations) {
        final List<Station> matchedStations = new LinkedList<>();

        for (final Station station : stations) {
            if (car.transit(station) == null) break;
            if (car.avoidTransit(station)) matchedStations.add(station);
        }

        return matchedStations;
    }

    private Car min(final Car car1, final Car car2) {
        return car1 == null? car2 : car2 == null? car1 : car1.cost <= car2.cost? car1 : car2;
    }
}

class City {
    public final double position;
    public final double gasolineCost;

    public City(final double position, final double gasolineCost) {
        this.position = position;
        this.gasolineCost = gasolineCost;
    }
}

class Station {
    public final double position;
    public final double gasolineRate;

    public Station(final double position, final double gasolineRate) {
        this.position = position;
        this.gasolineRate = gasolineRate;
    }

    public double gasolineCost(final double usage) {
        return usage * gasolineRate;
    }
}

class Car {
    public final double capacity;
    public final double mileage;
    public final double position;
    public final double cost;

    public Car(
        final double capacity,
        final double mileage,
        final double position,
        final double cost
    ) {
        this.capacity = capacity;
        this.mileage = mileage;
        this.position = position;
        this.cost = cost;
    }

    public Car departure(final City city) {
        return new Car(capacity, mileage, city.position, city.gasolineCost);
    }

    public Car arrival(final City city) {
        final double distance = Math.abs(position - city.position);
        final double usage = distance / mileage;

        return usage > capacity ? null : new Car(capacity, mileage, city.position, cost);
    }

    public Car transit(final Station station) {
        final double distance = Math.abs(position - station.position);
        final double usage = distance / mileage;
        final double gasolineCost = station.gasolineCost(usage);
        final double snackCost = 2;

        return usage > capacity ? null : new Car(capacity, mileage, station.position, cost + gasolineCost + snackCost);
    }

    public boolean avoidTransit(final Station station) {
        final double distance = Math.abs(position - station.position);
        final double usage = distance / mileage;
        final double remaining = capacity - usage;
        return remaining > capacity / 2;
    }
}
