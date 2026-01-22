package uhunt.c4.g9.p10259;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * 10259 - Hippity Hopscotch
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1200
 */
public class Main {
    public static void main(final String... agrs) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        final int totalTestCases = in.nextInt();
        for (int testCase = 1; testCase <= totalTestCases; testCase++) {
            final Input input = new Input();
            input.dimension = in.nextInt();
            input.totalJumps = in.nextInt();
            input.pennies = new int[input.dimension][input.dimension];
            for (int row = 0; row < input.dimension; row++) {
                for (int column = 0; column < input.dimension; column++) {
                    input.pennies[row][column] = in.nextInt();
                }
            }

            final Output output = process.process(input);
            if (testCase > 1) out.println();
            out.println(output.maximumTotalPennies);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int dimension;
    public int totalJumps;
    public int[][] pennies;
}

class Output {
    public int maximumTotalPennies;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();

        final Cell[][] cells = createCells(input);
        final Graph<Cell> graph = createGraph(input, cells);
        final Cell initialCell = cells[0][0];
        final Integer[][] maximumTotalValuesPerCell = new Integer[input.dimension][input.dimension];
        output.maximumTotalPennies = depthFirstSearch(graph, initialCell, maximumTotalValuesPerCell);

        return output;
    }

    private int depthFirstSearch(
        final Graph<Cell> graph,
        final Cell cell,
        final Integer[][] maximumTotalValuesPerCell
    ) {
        if (maximumTotalValuesPerCell[cell.row][cell.column] == null) {
            int maximumTotalValues = 0;

            for (final Cell nextCell : graph.get(cell)) {
                final int totalValues = depthFirstSearch(graph, nextCell, maximumTotalValuesPerCell);
                maximumTotalValues = Math.max(maximumTotalValues, totalValues);
            }

            maximumTotalValuesPerCell[cell.row][cell.column] = maximumTotalValues;
        }

        return maximumTotalValuesPerCell[cell.row][cell.column] + cell.value;
    }

    private Graph<Cell> createGraph(final Input input, final Cell[][] cells) {
        final Graph<Cell> graph = new Graph<>();

        for (int row = 0; row < input.dimension; row++) {
            for (int column = 0; column < input.dimension; column++) {
                for (int otherRow = row - input.totalJumps; otherRow <= row + input.totalJumps; otherRow++) {
                    final boolean isValid = 0 <= otherRow && otherRow < input.dimension;
                    if (!isValid) continue;

                    final boolean isGreater = cells[otherRow][column].value > cells[row][column].value;
                    if (!isGreater) continue;

                    graph.add(cells[row][column], cells[otherRow][column]);
                }
                for (int otherColumn = column - input.totalJumps; otherColumn <= column + input.totalJumps; otherColumn++) {
                    final boolean isValid = 0 <= otherColumn && otherColumn < input.dimension;
                    if (!isValid) continue;

                    final boolean isGreater = cells[row][otherColumn].value > cells[row][column].value;
                    if (!isGreater) continue;

                    graph.add(cells[row][column], cells[row][otherColumn]);
                }
            }
        }

        return graph;
    }

    private Cell[][] createCells(final Input input) {
        final Cell[][] cells = new Cell[input.dimension][input.dimension];
        for (int row = 0; row < input.dimension; row++) {
            for (int column = 0; column < input.dimension; column++) {
                final int penny = input.pennies[row][column];
                final Cell cell = new Cell(row, column, penny);
                cells[row][column] = cell;
            }
        }

        return cells;
    }
}

class Cell {
    public final int row;
    public final int column;
    public final int value;

    public Cell(final int row, final int column, final int value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }
}

class Graph<V> {
    private final Map<V, Set<V>> edges = new HashMap<>();

    public void add(final V from, final V into) {
        edges.computeIfAbsent(from, k -> new HashSet<>()).add(into);
    }

    public Collection<V> get(final V from) {
        return edges.getOrDefault(from, Collections.emptySet());
    }
}
