package uhunt.c4.g3.p11573;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 11573 - Ocean Currents
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2620
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        final Input input = new Input();
        input.totalRows = in.nextInt();
        input.totalColumns = in.nextInt();
        input.grid = new char[input.totalRows][];
        for (int i = 0; i < input.totalRows; i++) {
            input.grid[i] = in.next().toCharArray();
        }
        input.totalTrips = in.nextInt();
        input.originAndDestinationCells = new int[input.totalTrips][4];
        for (int i = 0; i < input.totalTrips; i++) {
            input.originAndDestinationCells[i][0] = in.nextInt();
            input.originAndDestinationCells[i][1] = in.nextInt();
            input.originAndDestinationCells[i][2] = in.nextInt();
            input.originAndDestinationCells[i][3] = in.nextInt();
        }

        final Output output = process.process(input);
        for (final int minimumTotalEnergy : output.minimumTotalEnergyPerTrip) {
            out.write(Integer.toString(minimumTotalEnergy));
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalRows;
    public int totalColumns;
    public char[][] grid;
    public int totalTrips;
    public int[][] originAndDestinationCells;
}

class Output {
    public int[] minimumTotalEnergyPerTrip;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.minimumTotalEnergyPerTrip = new int[input.totalTrips];

        for (int trip = 0; trip < input.totalTrips; trip++) {
            final int[] originAndDestination = input.originAndDestinationCells[trip];
            final int[] origin = new int[]{originAndDestination[0] - 1, originAndDestination[1] - 1};
            final int[] destination = new int[]{originAndDestination[2] - 1, originAndDestination[3] - 1};

            final int totalEnergy = getTotalEnergyUsingDijkstra(input.grid, origin, destination);
            output.minimumTotalEnergyPerTrip[trip] = totalEnergy;
        }

        return output;
    }

    private int getTotalEnergyUsingDijkstra(final char[][] grid, final int[] origin, final int[] destination) {
        final Checkpoint initialCheckpoint = new Checkpoint();
        initialCheckpoint.totalEnergy = 0;
        initialCheckpoint.cell = origin;

        final int[][] totalEnergyPerCell = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                totalEnergyPerCell[i][j] = Integer.MAX_VALUE;
            }
        }

        final Deque<Checkpoint> queue = new LinkedList<>();
        queue.addLast(initialCheckpoint);
        totalEnergyPerCell[origin[0]][origin[1]] = 0;

        while (!queue.isEmpty()) {
            final Checkpoint currentCheckpoint = queue.removeFirst();
            final int[] cell = currentCheckpoint.cell;
            final int[][] nextCells = currentCheckpoint.nextCells();
            final int totalEnergy = currentCheckpoint.totalEnergy;
            final int waterDirection = grid[cell[0]][cell[1]] - '0';

            for (int boatDirection = 0; boatDirection < 8; boatDirection++) {
                final int[] nextCell = nextCells[boatDirection];
                if (!isValid(grid, nextCell)) continue;

                final boolean isEqualDirection = waterDirection == boatDirection;

                final int nextTotalEnergy = isEqualDirection ? totalEnergy : totalEnergy + 1;
                final int oldNextTotalEnergy = totalEnergyPerCell[nextCell[0]][nextCell[1]];

                final boolean isOptimalPath = nextTotalEnergy < oldNextTotalEnergy;
                if (isOptimalPath) {
                    totalEnergyPerCell[nextCell[0]][nextCell[1]] = nextTotalEnergy;

                    final Checkpoint nextCheckpoint = new Checkpoint();
                    nextCheckpoint.totalEnergy = nextTotalEnergy;
                    nextCheckpoint.cell = nextCell;

                    if (isEqualDirection) queue.addFirst(nextCheckpoint);
                    else queue.addLast(nextCheckpoint);
                }
            }
        }

        return totalEnergyPerCell[destination[0]][destination[1]];
    }

    private boolean isValid(final char[][] grid, final int[] cell) {
        return 0 <= cell[0] && cell[0] < grid.length && 0 <= cell[1] && cell[1] < grid[0].length;
    }
}

class Checkpoint {
    public int totalEnergy;
    public int[] cell;

    public int[][] nextCells() {
        return new int[][]{
            new int[]{cell[0] - 1, cell[1]},
            new int[]{cell[0] - 1, cell[1] + 1},
            new int[]{cell[0], cell[1] + 1},
            new int[]{cell[0] + 1, cell[1] + 1},
            new int[]{cell[0] + 1, cell[1]},
            new int[]{cell[0] + 1, cell[1] - 1},
            new int[]{cell[0], cell[1] - 1},
            new int[]{cell[0] - 1, cell[1] - 1},
        };
    }
}
