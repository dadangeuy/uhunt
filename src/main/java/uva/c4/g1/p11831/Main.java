package uva.c4.g1.p11831;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 11831 - Sticker Collector Robot
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2931
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        for (int caseId = 0; ; caseId++) {
            final String[] l1 = Util.readNonEmptyLines(in);
            final int totalRows = Integer.parseInt(l1[0]);
            final int totalColumns = Integer.parseInt(l1[1]);
            final int totalInstructions = Integer.parseInt(l1[2]);

            final boolean isEOF = totalRows == 0 && totalColumns == 0 && totalInstructions == 0;
            if (isEOF) break;

            final char[][] arena = new char[totalRows][];
            for (int rowId = 0; rowId < totalRows; rowId++) {
                arena[rowId] = Util.readNonEmptyLine(in).toCharArray();
            }

            final char[] instructions = Util.readNonEmptyLine(in).toCharArray();

            final Input input = new Input(caseId, totalRows, totalColumns, totalInstructions, arena, instructions);
            final Output output = process.process(input);

            out.write(String.format("%d\n", output.totalCollectedStickers));
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Process {
    public Output process(final Input input) {
        int totalCollectedStickers = 0;

        Position position = findStart(input.arena);
        Direction direction = new Direction(input.arena[position.row][position.column]);

        for (char instruction : input.instructions) {
            Position nextPosition = position;
            Direction nextDirection = direction;

            switch (instruction) {
                case Instruction.MOVE_FORWARD: {
                    switch (direction.value) {
                        case Cell.NORTH: {
                            nextPosition = position.up();
                            break;
                        }
                        case Cell.SOUTH: {
                            nextPosition = position.down();
                            break;
                        }
                        case Cell.EAST: {
                            nextPosition = position.right();
                            break;
                        }
                        case Cell.WEST: {
                            nextPosition = position.left();
                            break;
                        }
                    }
                    break;
                }
                case Instruction.TURN_LEFT: {
                    nextDirection = direction.rotateLeft();
                    break;
                }
                case Instruction.TURN_RIGHT: {
                    nextDirection = direction.rotateRight();
                    break;
                }
            }

            final boolean isValidRow = 0 <= nextPosition.row && nextPosition.row < input.totalRows;
            final boolean isValidColumn = 0 <= nextPosition.column && nextPosition.column < input.totalColumns;
            if (!isValidRow || !isValidColumn) continue;

            final char cell = input.arena[nextPosition.row][nextPosition.column];
            final boolean isUnwalkable = Cell.UNWALKABLE_CELLS.contains(cell);
            if (isUnwalkable) continue;

            final boolean isSticker = cell == Cell.STICKER;
            if (isSticker) {
                totalCollectedStickers++;
                input.arena[nextPosition.row][nextPosition.column] = Cell.NORMAL;
            }

            position = nextPosition;
            direction = nextDirection;
        }

        return new Output(totalCollectedStickers);
    }

    private Position findStart(final char[][] arena) {
        for (final char robotCell : Cell.CLOCKWISE_DIRECTIONS) {
            final Optional<Position> op = find(arena, robotCell);
            if (op.isPresent()) return op.get();
        }
        throw new NullPointerException();
    }

    private Optional<Position> find(final char[][] arena, final char target) {
        for (int rowId = 0; rowId < arena.length; rowId++) {
            for (int columnId = 0; columnId < arena[rowId].length; columnId++) {
                final boolean isTarget = arena[rowId][columnId] == target;
                if (isTarget) return Optional.of(new Position(rowId, columnId));
            }
        }
        return Optional.empty();
    }
}

class Input {
    public final int caseId;
    public final int totalRows;
    public final int totalColumns;
    public final int totalInstructions;
    public final char[][] arena;
    public final char[] instructions;

    public Input(final int caseId, final int totalRows, final int totalColumns, final int totalInstructions, final char[][] arena, final char[] instructions) {
        this.caseId = caseId;
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.totalInstructions = totalInstructions;
        this.arena = arena;
        this.instructions = instructions;
    }
}

class Output {
    public final int totalCollectedStickers;

    public Output(final int totalCollectedStickers) {
        this.totalCollectedStickers = totalCollectedStickers;
    }
}

class Direction {
    public final char value;

    public Direction(final char value) {
        this.value = value;
    }

    public Direction rotateRight() {
        final int id = Cell.CLOCKWISE_DIRECTIONS.indexOf(value);
        final int length = Cell.CLOCKWISE_DIRECTIONS.size();
        final char nextValue = Cell.CLOCKWISE_DIRECTIONS.get((id + 1) % length);
        return new Direction(nextValue);
    }

    public Direction rotateLeft() {
        final int id = Cell.CLOCKWISE_DIRECTIONS.indexOf(value);
        final int length = Cell.CLOCKWISE_DIRECTIONS.size();
        final char prevValue = Cell.CLOCKWISE_DIRECTIONS.get((length + id - 1) % length);
        return new Direction(prevValue);
    }
}

class Position {
    public final int row;
    public final int column;

    public Position(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    public Position left() {
        return new Position(row, column - 1);
    }

    public Position right() {
        return new Position(row, column + 1);
    }

    public Position up() {
        return new Position(row - 1, column);
    }

    public Position down() {
        return new Position(row + 1, column);
    }
}

class Cell {
    // obstacle
    public static final char NORMAL = '.';
    public static final char STICKER = '*';
    public static final char PILLAR = '#';

    // directions of the robot
    public static final char NORTH = 'N';
    public static final char SOUTH = 'S';
    public static final char EAST = 'L';
    public static final char WEST = 'O';

    public static final List<Character> CLOCKWISE_DIRECTIONS = Arrays.asList(
            NORTH,
            EAST,
            SOUTH,
            WEST
    );
    public static final List<Character> WALKABLE_CELLS = Arrays.asList(
            NORMAL,
            STICKER,
            NORTH,
            WEST,
            SOUTH,
            EAST
    );
    public static final List<Character> UNWALKABLE_CELLS = Collections.singletonList(
            PILLAR
    );
}

class Instruction {
    public static final char TURN_RIGHT = 'D';
    public static final char TURN_LEFT = 'E';
    public static final char MOVE_FORWARD = 'F';
}

class Util {
    private static final String SEPARATOR = " ";

    public static String[] readNonEmptyLines(final BufferedReader in) throws IOException {
        final String line = readNonEmptyLine(in);
        return line == null ? new String[0] : line.split(SEPARATOR);
    }

    public static String readNonEmptyLine(final BufferedReader in) throws IOException {
        String line = in.readLine();
        while (line != null && line.isEmpty()) line = in.readLine();
        return line;
    }
}
