package uhunt.c4.g9.p869;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 869 - Airline Comparison
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=810
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        final int totalTestCases = in.nextInt();
        for (int testCaseId = 1; testCaseId <= totalTestCases; testCaseId++) {
            final Input input = new Input();
            input.airline1 = readAirline(in);
            input.airline2 = readAirline(in);

            final Output output = process.process(input);
            if (testCaseId > 1) {
                out.write('\n');
            }
            if (output.isEquivalent) {
                out.write("YES\n");
            } else {
                out.write("NO\n");
            }
        }

        in.close();
        out.flush();
        out.close();
    }

    private static Airline readAirline(final Scanner in) {
        final Airline airline = new Airline();

        airline.totalFlights = in.nextInt();
        airline.flights = new Flight[airline.totalFlights];

        for (int routeIdx = 0; routeIdx < airline.totalFlights; routeIdx++) {
            final Flight flight = new Flight();
            flight.origin = in.next().charAt(0);
            flight.destination = in.next().charAt(0);

            airline.flights[routeIdx] = flight;
        }

        return airline;
    }
}

class Input {
    public Airline airline1;
    public Airline airline2;
}

class Output {
    public boolean isEquivalent;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final boolean[][] connections1 = createConnections(input.airline1);
        final boolean[][] connections2 = createConnections(input.airline2);

        output.isEquivalent = isEquals(connections1, connections2);

        return output;
    }

    private boolean[][] createConnections(final Airline airline) {
        final boolean[][] connections = new boolean[26][26];
        for (final Flight flight : airline.flights) {
            final int originId = flight.getOriginId();
            final int destinationId = flight.getDestinationId();
            connections[originId][destinationId] = true;
            connections[destinationId][originId] = true;
        }

        for (char alternativeId = 0; alternativeId < 26; alternativeId++) {
            for (char originId = 0; originId < 26; originId++) {
                for (char destinationId = 0; destinationId < 26; destinationId++) {
                    if (connections[originId][alternativeId] && connections[alternativeId][destinationId]) {
                        connections[originId][destinationId] = true;
                        connections[destinationId][originId] = true;
                    }
                }
            }
        }

        return connections;
    }

    private boolean isEquals(final boolean[][] first, final boolean[][] second) {
        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < first[i].length; j++) {
                if (first[i][j] != second[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}

class Airline {
    public int id;
    public int totalFlights;
    public Flight[] flights;
}

class Flight {
    public char origin;
    public char destination;

    public int getOriginId() {
        return origin - 'A';
    }

    public int getDestinationId() {
        return destination - 'A';
    }
}
