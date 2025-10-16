package uhunt.c4.p11585;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * 11585 - Nurikabe
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2632
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        final int totalGrids = Integer.parseInt(readNonEmpty(in));
        for (int gridId = 0; gridId < totalGrids; gridId++) {
            final String[] l1 = readNonEmpty(in).split(" ");
            final int totalRows = Integer.parseInt(l1[0]);
            final int totalColumns = Integer.parseInt(l1[1]);
            final int totalNumberedCells = Integer.parseInt(l1[2]);

            final List<NumberedCell> numberedCells = new ArrayList<>(totalNumberedCells);
            for (int numberedCellId = 0; numberedCellId < totalNumberedCells; numberedCellId++) {
                final String[] l2 = readNonEmpty(in).split(" ");
                final int row = Integer.parseInt(l2[0]);
                final int column = Integer.parseInt(l2[1]);
                final int number = Integer.parseInt(l2[2]);

                final NumberedCell numberedCell = new NumberedCell(row, column, number);
                numberedCells.add(numberedCell);
            }

            final char[][] grid = new char[totalRows][];
            for (int rowId = 0; rowId < totalRows; rowId++) {
                final String l3 = readNonEmpty(in);
                grid[rowId] = l3.toCharArray();
            }

            final Input input = new Input(
                    totalRows,
                    totalColumns,
                    totalNumberedCells,
                    numberedCells,
                    grid
            );

            final Output output = process.process(input);
            out.write(output.isSolved ? "solved\n" : "not solved\n");
        }

        in.close();
        out.flush();
        out.close();
    }

    private static String readNonEmpty(
            final BufferedReader in
    ) throws IOException {
        String line = in.readLine();
        while (line.isEmpty()) line = in.readLine();
        return line;
    }
}

class Process {
    public Output process(final Input input) {
        if (!isConnectedShaded(input.grid)) {
            return new Output(false);
        }

        if (!isUnshadedEqualToNumberedCell(input.grid, input.numberedCells)) {
            return new Output(false);
        }

        if (!isNumberedCellCanReachEdge(input.grid)) {
            return new Output(false);
        }

        if (!haveUnshadedInEverySubGrid(input.grid)) {
            return new Output(false);
        }

        return new Output(true);
    }

    private boolean isConnectedShaded(final char[][] grid) {
        final char[][] shadedGrid = clone(grid);
        boolean isFloodFilled = false;
        for (int row = 0; row < shadedGrid.length && !isFloodFilled; row++) {
            for (int column = 0; column < shadedGrid[row].length && !isFloodFilled; column++) {
                if (shadedGrid[row][column] == CellType.SHADED) {
                    floodFill(
                            shadedGrid,
                            new Position(row, column),
                            Position::sideNeighbors,
                            CellType.SHADED,
                            CellType.VISITED
                    );
                    isFloodFilled = true;
                }
            }
        }

        return !find(shadedGrid, CellType.SHADED);
    }

    private boolean isUnshadedEqualToNumberedCell(final char[][] grid, final List<NumberedCell> numberedCells) {
        final char[][] countGrid = clone(grid);
        for (final NumberedCell cell : numberedCells) {
            final int count = floodFill(
                    countGrid,
                    new Position(cell.row, cell.column),
                    Position::sideNeighbors,
                    CellType.UNSHADED,
                    CellType.VISITED
            );
            if (count != cell.number) {
                return false;
            }
        }

        return !find(countGrid, CellType.UNSHADED);
    }

    private boolean isNumberedCellCanReachEdge(final char[][] grid) {
        final char[][] edgeGrid = clone(grid);

        for (int row = 0; row < grid.length; row++) {
            final int left = 0;
            if (edgeGrid[row][left] == CellType.UNSHADED) {
                floodFill(
                        edgeGrid,
                        new Position(row, left),
                        Position::sideAndCornerNeighbors,
                        CellType.UNSHADED,
                        CellType.VISITED
                );
            }

            final int right = grid[0].length - 1;
            if (edgeGrid[row][right] == CellType.UNSHADED) {
                floodFill(
                        edgeGrid,
                        new Position(row, right),
                        Position::sideAndCornerNeighbors,
                        CellType.UNSHADED,
                        CellType.VISITED
                );
            }
        }

        for (int column = 0; column < grid[0].length; column++) {
            final int top = 0;
            if (edgeGrid[top][column] == CellType.UNSHADED) {
                floodFill(
                        edgeGrid,
                        new Position(top, column),
                        Position::sideAndCornerNeighbors,
                        CellType.UNSHADED,
                        CellType.VISITED
                );
            }

            final int bottom = grid.length - 1;
            if (edgeGrid[bottom][column] == CellType.UNSHADED) {
                floodFill(
                        edgeGrid,
                        new Position(bottom, column),
                        Position::sideAndCornerNeighbors,
                        CellType.UNSHADED,
                        CellType.VISITED
                );
            }
        }

        return !find(edgeGrid, CellType.UNSHADED);
    }

    private boolean haveUnshadedInEverySubGrid(final char[][] grid) {
        for (int row = 0; row < grid.length - 1; row++) {
            for (int column = 0; column < grid[row].length - 1; column++) {
                final Position[] subGridPositions = new Position[]{
                        new Position(row, column),
                        new Position(row, column + 1),
                        new Position(row + 1, column),
                        new Position(row + 1, column + 1),
                };

                int count = 0;
                for (final Position position : subGridPositions) {
                    if (grid[position.row][position.column] == CellType.UNSHADED) {
                        count++;
                    }
                }

                if (count == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private int floodFill(
            final char[][] grid,
            final Position startPosition,
            final Function<Position, Position[]> nextPositionsFn,
            final char target,
            final char fill
    ) {
        final LinkedList<Position> positionq = new LinkedList<>();
        int count = 0;

        if (grid[startPosition.row][startPosition.column] == target) {
            positionq.addLast(startPosition);
            grid[startPosition.row][startPosition.column] = fill;
            count++;
        }

        while (!positionq.isEmpty()) {
            final Position position = positionq.removeFirst();

            for (final Position nextPosition : nextPositionsFn.apply(position)) {
                final boolean validRow = 0 <= nextPosition.row && nextPosition.row < grid.length;
                if (!validRow) continue;

                final boolean validColumn = 0 <= nextPosition.column && nextPosition.column < grid[nextPosition.row].length;
                if (!validColumn) continue;

                final boolean validTarget = grid[nextPosition.row][nextPosition.column] == target;
                if (!validTarget) continue;

                positionq.addLast(nextPosition);
                grid[nextPosition.row][nextPosition.column] = fill;
                count++;
            }
        }

        return count;

    }

    private boolean find(final char[][] grid, final char target) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                if (grid[row][column] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    private char[][] clone(char[][] array) {
        final char[][] clone = new char[array.length][];
        for (int i = 0; i < array.length; i++) clone[i] = array[i].clone();
        return clone;
    }
}

class Input {
    public final int totalRows;
    public final int totalColumns;
    public final int totalNumberedCells;
    public final List<NumberedCell> numberedCells;
    public final char[][] grid;

    public Input(
            final int totalRows,
            final int totalColumns,
            final int totalNumberedCells,
            final List<NumberedCell> numberedCells,
            final char[][] grid
    ) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.totalNumberedCells = totalNumberedCells;
        this.numberedCells = numberedCells;
        this.grid = grid;
    }
}

class Output {
    public final boolean isSolved;

    public Output(
            final boolean isSolved
    ) {
        this.isSolved = isSolved;
    }
}

class NumberedCell {
    public final int row;
    public final int column;
    public final int number;

    public NumberedCell(
            int row,
            int column,
            int number
    ) {
        this.row = row;
        this.column = column;
        this.number = number;
    }
}

class CellType {
    public static final char SHADED = '#';
    public static final char UNSHADED = '.';
    public static final char VISITED = 'X';
}

class Position {
    public final int row;
    public final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Position[] sideNeighbors() {
        return new Position[]{
                new Position(row - 1, column),
                new Position(row + 1, column),
                new Position(row, column - 1),
                new Position(row, column + 1),
        };
    }

    public Position[] sideAndCornerNeighbors() {
        return new Position[]{
                new Position(row - 1, column - 1),
                new Position(row - 1, column),
                new Position(row - 1, column + 1),
                new Position(row, column - 1),
                new Position(row, column),
                new Position(row, column + 1),
                new Position(row + 1, column - 1),
                new Position(row + 1, column),
                new Position(row + 1, column + 1),
        };
    }
}
