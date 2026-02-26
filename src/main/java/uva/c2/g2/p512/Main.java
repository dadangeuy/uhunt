package uva.c2.g2.p512;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Optional;

/**
 * 512 - Spreadsheet Tracking
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=453
 */
public class Main {
    private static BufferedReader in;
    private static BufferedWriter out;
    private static String line;
    private static String[] lines;

    public static void main(final String... args) throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);

        for (int sheetId = 1; ; sheetId++) {
            readLines();
            final int totalRows = Integer.parseInt(lines[0]);
            final int totalColumns = Integer.parseInt(lines[1]);

            if (totalRows == 0 && totalColumns == 0) break;
            final Sheet sheet = new Sheet(totalRows, totalColumns);

            line = in.readLine().trim();
            final int totalOperations = Integer.parseInt(line);

            for (int i = 0; i < totalOperations; i++) {
                readLines();
                final String command = lines[0];
                switch (command) {
                    case "EX": {
                        final int row1 = Integer.parseInt(lines[1]);
                        final int column1 = Integer.parseInt(lines[2]);
                        final int row2 = Integer.parseInt(lines[3]);
                        final int column2 = Integer.parseInt(lines[4]);
                        sheet.exchange(row1, column1, row2, column2);
                        break;
                    }
                    case "DC": {
                        final int totalArguments = Integer.parseInt(lines[1]);
                        final String[] stringArguments = Arrays.copyOfRange(lines, 2, 2 + totalArguments);
                        final int[] intArguments = Arrays.stream(stringArguments)
                                .mapToInt(Integer::parseInt).toArray();
                        sheet.deleteColumns(intArguments);
                        break;
                    }
                    case "DR": {
                        final int totalArguments = Integer.parseInt(lines[1]);
                        final String[] stringArguments = Arrays.copyOfRange(lines, 2, 2 + totalArguments);
                        final int[] intArguments = Arrays.stream(stringArguments)
                                .mapToInt(Integer::parseInt).toArray();
                        sheet.deleteRows(intArguments);
                        break;
                    }
                    case "IC": {
                        final int totalArguments = Integer.parseInt(lines[1]);
                        final String[] stringArguments = Arrays.copyOfRange(lines, 2, 2 + totalArguments);
                        final int[] intArguments = Arrays.stream(stringArguments)
                                .mapToInt(Integer::parseInt).toArray();
                        sheet.insertColumns(intArguments);
                        break;
                    }
                    case "IR": {
                        final int totalArguments = Integer.parseInt(lines[1]);
                        final String[] stringArguments = Arrays.copyOfRange(lines, 2, 2 + totalArguments);
                        final int[] intArguments = Arrays.stream(stringArguments)
                                .mapToInt(Integer::parseInt).toArray();
                        sheet.insertRows(intArguments);
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("invalid command");
                }
            }

            if (sheetId > 1) out.write('\n');
            out.write(String.format("Spreadsheet #%d\n", sheetId));

            sheet.precomputeQuery();

            readLine();
            final int totalQueries = Integer.parseInt(line);

            for (int i = 0; i < totalQueries; i++) {
                readLines();
                final int originalRow = Integer.parseInt(lines[0]);
                final int originalColumn = Integer.parseInt(lines[1]);

                final Optional<Cell> cellOp = sheet.query(originalRow, originalColumn);
                if (cellOp.isPresent()) {
                    final Cell cell = cellOp.get();
                    out.write(String.format("Cell data in (%d,%d) moved to (%d,%d)\n", cell.originalRow, cell.originalColumn, cell.row, cell.column));
                } else {
                    out.write(String.format("Cell data in (%d,%d) GONE\n", originalRow, originalColumn));
                }
            }
        }

        in.close();
        out.flush();
        out.close();
    }

    private static void readLines() throws IOException {
        readLine();
        lines = line.split("\\s+");
    }

    private static void readLine() throws IOException {
        do line = in.readLine().trim(); while (line.isEmpty());
    }
}

class Sheet {
    public static final int SIZE = 100;
    public final Cell[][] sheet;
    public final Cell[][] precomputeSheet;

    public Sheet(final int totalRows, final int totalColumns) {
        this.sheet = new Cell[SIZE][SIZE];
        for (int row = 1; row <= totalRows; row++) {
            for (int column = 1; column <= totalColumns; column++) {
                this.sheet[row][column] = new Cell(row, column);
            }
        }
        this.precomputeSheet = new Cell[SIZE][SIZE];
    }

    public void exchange(final int row1, final int column1, final int row2, final int column2) {
        final Cell cell1 = sheet[row1][column1];
        final Cell cell2 = sheet[row2][column2];

        update(row2, column2, cell1);
        update(row1, column1, cell2);
    }

    public void deleteColumns(final int... columns) {
        Arrays.sort(columns);

        for (int i = columns.length - 1; i >= 0; i--) {
            final int removedColumn = columns[i];

            for (int row = 1; row < SIZE; row++) {
                for (int column = removedColumn; column < SIZE - 1; column++) {
                    final Cell nextCell = sheet[row][column + 1];
                    update(row, column, nextCell);
                }
            }
        }
    }

    public void deleteRows(final int... rows) {
        Arrays.sort(rows);

        for (int i = rows.length - 1; i >= 0; i--) {
            final int removedRow = rows[i];

            for (int column = 1; column < SIZE; column++) {
                for (int row = removedRow; row < SIZE - 1; row++) {
                    final Cell nextCell = sheet[row + 1][column];
                    update(row, column, nextCell);
                }
            }
        }
    }

    public void insertColumns(final int... columns) {
        Arrays.sort(columns);

        for (int i = columns.length - 1; i >= 0; i--) {
            final int insertedColumn = columns[i];

            for (int row = 1; row < SIZE; row++) {
                for (int column = SIZE - 1; column > insertedColumn; column--) {
                    final Cell prevCell = sheet[row][column - 1];
                    update(row, column, prevCell);
                }
                update(row, insertedColumn, null);
            }
        }
    }

    public void insertRows(final int... rows) {
        Arrays.sort(rows);

        for (int i = rows.length - 1; i >= 0; i--) {
            final int insertedRow = rows[i];

            for (int column = 1; column < SIZE; column++) {
                for (int row = SIZE - 1; row > insertedRow; row--) {
                    final Cell prevCell = sheet[row - 1][column];
                    update(row, column, prevCell);
                }
                update(insertedRow, column, null);
            }
        }
    }

    public void precomputeQuery() {
        for (int row = 1; row < SIZE; row++) {
            for (int column = 1; column < SIZE; column++) {
                final Cell cell = sheet[row][column];
                if (cell != null) {
                    precomputeSheet[cell.originalRow][cell.originalColumn] = cell;
                }
            }
        }
    }

    public Optional<Cell> query(final int originalRow, final int originalColumn) {
        return Optional.ofNullable(precomputeSheet[originalRow][originalColumn]);
    }

    private void update(final int row, final int col, final Cell cell) {
        sheet[row][col] = cell;
        if (cell != null) cell.move(row, col);
    }
}

class Cell {
    public final int originalRow;
    public final int originalColumn;
    public int row;
    public int column;

    public Cell(final int row, final int column) {
        this.originalRow = row;
        this.originalColumn = column;
        this.row = row;
        this.column = column;
    }

    public void move(final int row, final int column) {
        this.row = row;
        this.column = column;
    }
}
