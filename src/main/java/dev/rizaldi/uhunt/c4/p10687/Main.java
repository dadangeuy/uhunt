package dev.rizaldi.uhunt.c4.p10687;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 10687 - Monitoring the Amazon
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1628
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));
        final Process process = new Process();

        while (true) {
            final int totalStations = in.nextInt();
            if (totalStations == 0) break;
            final int[][] stations = new int[totalStations][];
            for (int i = 0; i < totalStations; i++) {
                final int x = in.nextInt();
                final int y = in.nextInt();
                final int[] station = new int[]{x, y};
                stations[i] = station;
            }

            final Input input = new Input(totalStations, stations);
            final Output output = process.process(input);

            if (output.isReachable) {
                out.println("All stations are reachable.");
            } else {
                out.println("There are stations that are unreachable.");
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public final int totalStations;
    public final int[][] stations;

    public Input(final int totalStations, final int[][] stations) {
        this.totalStations = totalStations;
        this.stations = stations;
    }
}

class Output {
    public final boolean isReachable;

    public Output(final boolean isReachable) {
        this.isReachable = isReachable;
    }
}

class Process {
    public Output process(final Input input) {
        final List<Station> stations = IntStream.range(0, input.totalStations)
                .mapToObj(id -> new Station(id, input.stations[id][0], input.stations[id][1]))
                .collect(Collectors.toList());

        final Set<Station> receiverStationSet = new HashSet<>();
        final Queue<Station> stationQueue = new LinkedList<>();

        final Station orderDispatchStation = stations.get(0);
        receiverStationSet.add(orderDispatchStation);
        stationQueue.add(orderDispatchStation);

        while (!stationQueue.isEmpty()) {
            final Station station = stationQueue.remove();
            final Comparator<Station> orderByDistanceAscXAscYDesc = Comparator
                    .comparingDouble(station::getDistance)
                    .thenComparingInt((Station s) -> s.x)
                    .thenComparingInt((Station s) -> -s.y);
            final List<Station> nextStations = stations.stream()
                    .filter(s -> station != s)
                    .sorted(orderByDistanceAscXAscYDesc)
                    .limit(2)
                    .collect(Collectors.toList());

            for (final Station nextStation : nextStations) {
                if (receiverStationSet.contains(nextStation)) continue;
                receiverStationSet.add(nextStation);
                stationQueue.add(nextStation);
            }
        }

        return new Output(receiverStationSet.size() == input.totalStations);
    }
}

class Station {
    public final int id;
    public final int x;
    public final int y;

    public Station(final int id, final int x, final int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public double getDistance(final Station station) {
        final int dx = Math.abs(x - station.x);
        final int dy = Math.abs(y - station.y);
        return dx * dx + dy * dy;
    }
}
