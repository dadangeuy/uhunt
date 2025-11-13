package uhunt.c3.p10660;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 10660 - Citizen attention offices
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1601
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        final int totalTestCases = in.nextInt();
        for (int i = 0; i < totalTestCases; i++) {
            final Input input = new Input();
            input.totalAreas = in.nextInt();
            input.areas = new Area[input.totalAreas];
            for (int j = 0; j < input.totalAreas; j++) {
                final Area area = new Area();
                area.row = in.nextInt();
                area.column = in.nextInt();
                area.population = in.nextInt();
                input.areas[j] = area;
            }

            final Output output = process.process(input);
            out.print(output.locations[0]);
            for (int j = 1; j < 5; j++) {
                out.print(' ');
                out.print(output.locations[j]);
            }
            out.print('\n');
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalAreas;
    public Area[] areas;
}

class Output {
    public int[] locations;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final Area[] offices = new Area[]{new Area(), new Area(), new Area(), new Area(), new Area()};
        final Area[] residences = input.areas;
        int minimumTotalDistances = Integer.MAX_VALUE;
        int[] minimumOfficeLocations = null;

        for (int location0 = 0; location0 < 25; location0++) {
            offices[0].setLocation(location0);

            for (int location1 = location0 + 1; location1 < 25; location1++) {
                offices[1].setLocation(location1);

                for (int location2 = location1 + 1; location2 < 25; location2++) {
                    offices[2].setLocation(location2);

                    for (int location3 = location2 + 1; location3 < 25; location3++) {
                        offices[3].setLocation(location3);

                        for (int location4 = location3 + 1; location4 < 25; location4++) {
                            offices[4].setLocation(location4);

                            final int totalDistances = getTotalDistances(offices, residences);
                            if (totalDistances < minimumTotalDistances) {
                                minimumTotalDistances = totalDistances;
                                minimumOfficeLocations = Arrays.stream(offices).mapToInt(Area::getLocation).toArray();
                            }
                        }
                    }
                }
            }
        }

        output.locations = minimumOfficeLocations;
        return output;
    }

    private int getTotalDistances(final Area[] offices, final Area[] residences) {
        int totalDistances = 0;
        for (final Area residence : residences) {
            final int distance = residence.getMinimumDistance(offices);
            totalDistances += distance;
        }
        return totalDistances;
    }
}

class Area {
    public int row;
    public int column;
    public int population;

    public void setLocation(final int location) {
        this.row = location / 5;
        this.column = location % 5;
    }

    public int getLocation() {
        return row * 5 + column;
    }

    public int getDistance(final Area other) {
        final int manhattanDistance = Math.abs(row - other.row) + Math.abs(column - other.column);
        return manhattanDistance * (population + other.population);
    }

    public int getMinimumDistance(final Area... others) {
        return Arrays.stream(others)
            .mapToInt(this::getDistance)
            .min()
            .orElse(0);
    }
}
