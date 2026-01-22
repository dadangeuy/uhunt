package uhunt.c3.g8.p10908;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 10908 - Largest Square
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1849
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 8));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 8));
        final Process process = new Process();

        final int totalCases = in.nextInt();
        for (int caseId = 0; caseId < totalCases; caseId++) {
            final int height = in.nextInt();
            final int width = in.nextInt();
            final int totalQueries = in.nextInt();

            final char[][] grid = new char[height][];
            for (int row = 0; row < height; row++) {
                grid[row] = in.next().toCharArray();
            }

            final Query[] queries = new Query[totalQueries];
            for (int queryId = 0; queryId < totalQueries; queryId++) {
                final int row = in.nextInt();
                final int column = in.nextInt();
                final Query query = new Query(queryId, row, column);
                queries[queryId] = query;
            }

            final Input input = new Input(
                caseId,
                height,
                width,
                totalQueries,
                grid,
                queries
            );
            final Output output = process.process(input);

            out.format("%d %d %d\n", output.height, output.width, output.totalQueries);
            for (final QueryResult queryResult : output.queryResults) {
                out.format("%d\n", queryResult.length);
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public final int caseId;
    public final int height;
    public final int width;
    public final int totalQueries;
    public final char[][] grid;
    public final Query[] queries;

    public Input(
        final int caseId,
        final int height,
        final int width,
        final int totalQueries,
        final char[][] grid,
        final Query[] queries
    ) {
        this.caseId = caseId;
        this.height = height;
        this.width = width;
        this.totalQueries = totalQueries;
        this.grid = grid;
        this.queries = queries;
    }
}

class Output {
    public final int caseId;
    public final int height;
    public final int width;
    public final int totalQueries;
    public final QueryResult[] queryResults;

    public Output(
        final int caseId,
        final int height,
        final int width,
        final int totalQueries,
        final QueryResult[] queryResults
    ) {
        this.caseId = caseId;
        this.height = height;
        this.width = width;
        this.totalQueries = totalQueries;
        this.queryResults = queryResults;
    }
}

class Process {
    public Output process(final Input input) {
        final QueryResult[] queryResults = new QueryResult[input.totalQueries];
        for (final Query query : input.queries) {
            final int row = query.row;
            final int column = query.column;

            int distance = -1;
            while (isValidSides(input.grid, row, column, distance + 1)) distance++;

            final int length = distance * 2 + 1;
            final QueryResult queryResult = new QueryResult(query.id, length);
            queryResults[queryResult.id] = queryResult;
        }

        return new Output(
            input.caseId,
            input.height,
            input.width,
            input.totalQueries,
            queryResults
        );
    }

    private boolean isValidSides(final char[][] grid, final int centerRow, final int centerColumn, final int distance) {
        if (!isValid(grid, centerRow, centerColumn)) return false;

        final int top = centerRow - distance;
        final int bottom = centerRow + distance;
        final int left = centerColumn - distance;
        final int right = centerColumn + distance;

        final char letter = grid[centerRow][centerColumn];

        for (int row = top; row <= bottom; row++) {
            if (!isValid(grid, row, left, letter)) return false;
            if (!isValid(grid, row, right, letter)) return false;
        }

        for (int column = left; column <= right; column++) {
            if (!isValid(grid, top, column, letter)) return false;
            if (!isValid(grid, bottom, column, letter)) return false;
        }

        return true;
    }

    private boolean isValid(final char[][] grid, final int row, final int column, final char letter) {
        if (!isValid(grid, row, column)) return false;
        return grid[row][column] == letter;
    }

    private boolean isValid(final char[][] grid, final int row, final int column) {
        final boolean validRow = 0 <= row && row < grid.length;
        final boolean validRowColumn = validRow && 0 <= column && column < grid[row].length;
        return validRowColumn;
    }
}

class Query {
    public final int id;
    public final int row;
    public final int column;

    public Query(final int id, final int row, final int column) {
        this.id = id;
        this.row = row;
        this.column = column;
    }
}

class QueryResult {
    public final int id;
    public final int length;

    public QueryResult(final int id, final int length) {
        this.id = id;
        this.length = length;
    }
}
