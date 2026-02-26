package uva.c4.g9.p589;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 589 - Pushing Boxes
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=530#google_vignette
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out), 1 << 16);
        final Process process = new Process();

        int mazeId = 1;
        for (String line = in.readLine(); !line.equals("0 0"); line = in.readLine(), mazeId++) {
            String[] lines = line.split(" ");

            final Input input = new Input();
            input.totalRows = Integer.parseInt(lines[0]);
            input.totalColumns = Integer.parseInt(lines[1]);

            if (input.totalRows == 0 && input.totalColumns == 0) break;

            input.maze = new char[input.totalRows][];
            for (int i = 0; i < input.totalRows; i++) input.maze[i] = in.readLine().toCharArray();

            final Output output = process.process(input);
            out.write(String.format("Maze #%d\n", mazeId));
            if (output.moves == null) out.write("Impossible.");
            else out.write(output.moves);
            out.write("\n\n");
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int totalRows;
    public int totalColumns;
    public char[][] maze;
}

class Output {
    public char[] moves;
}

class Process {
    private static final Comparator<Action> SORT_BY_STEP = Comparator
            .comparingInt((Action a) -> a.pushStep)
            .thenComparingInt((Action a) -> a.step);

    public Output process(final Input input) {
        final Output output = new Output();

        final Position playerPosition = find(input.maze, Cell.PLAYER);
        final Position boxPosition = find(input.maze, Cell.BOX);
        final Position targetPosition = find(input.maze, Cell.TARGET);

        final PriorityQueue<Action> actionQueue = new PriorityQueue<>(SORT_BY_STEP);
        final Set<Integer> stateSet = new HashSet<>();

        final Action initialAction = new Action(null, null, playerPosition, boxPosition, targetPosition);
        actionQueue.add(initialAction);
        stateSet.add(initialAction.state());

        while (!actionQueue.isEmpty()) {
            final Action currentAction = actionQueue.remove();

            if (currentAction.isFinished()) {
                output.moves = toCharArray(currentAction.getMoves());
                return output;
            }

            for (final Action nextAction : currentAction.nextActions()) {
                if (!nextAction.isValid(input.maze)) continue;
                if (stateSet.contains(nextAction.state())) continue;

                actionQueue.add(nextAction);
                stateSet.add(nextAction.state());
            }
        }

        return output;
    }

    private Position find(
            final char[][] maze,
            final char target
    ) {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                if (maze[row][col] == target) {
                    return new Position(row, col);
                }
            }
        }
        throw new NullPointerException(String.format("target %s not found.", target));
    }

    private char[] toCharArray(final List<Character> list) {
        final char[] array = new char[list.size()];
        final Iterator<Character> it = list.iterator();
        for (int i = 0; it.hasNext(); i++) {
            array[i] = it.next();
        }
        return array;
    }
}

class Push {
    public static final char NORTH = 'N';
    public static final char SOUTH = 'S';
    public static final char EAST = 'E';
    public static final char WEST = 'W';
}

class Walk {
    public static final char NORTH = 'n';
    public static final char SOUTH = 's';
    public static final char EAST = 'e';
    public static final char WEST = 'w';
}

class Cell {
    public static final char ROCK = '#';
    public static final char EMPTY = '.';
    public static final char PLAYER = 'S';
    public static final char BOX = 'B';
    public static final char TARGET = 'T';
}

class Position {
    public final int row;
    public final int column;

    public Position(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    public Position north() {
        return new Position(row - 1, column);
    }

    public Position south() {
        return new Position(row + 1, column);
    }

    public Position west() {
        return new Position(row, column - 1);
    }

    public Position east() {
        return new Position(row, column + 1);
    }

    public boolean equals(final Position o) {
        return row == o.row && column == o.column;
    }
}

class Action {
    public final Action before;
    public final Character move;
    public final Position player;
    public final Position box;
    public final Position target;
    public final int step;
    public final int pushStep;

    public Action(
            final Action before,
            final Character move,
            final Position player,
            final Position box,
            final Position target
    ) {
        this.before = before;
        this.move = move;
        this.player = player;
        this.box = box;
        this.target = target;
        this.step = before == null ? 0 : before.step + 1;
        this.pushStep = before == null ? 0 : (before.pushStep + (Character.isUpperCase(move) ? 1 : 0));
    }

    public Action[] nextActions() {
        final Action north = new Action(
                this,
                player.north().equals(box) ? Push.NORTH : Walk.NORTH,
                player.north(),
                player.north().equals(box) ? box.north() : box,
                target
        );

        final Action south = new Action(
                this,
                player.south().equals(box) ? Push.SOUTH : Walk.SOUTH,
                player.south(),
                player.south().equals(box) ? box.south() : box,
                target
        );

        final Action east = new Action(
                this,
                player.east().equals(box) ? Push.EAST : Walk.EAST,
                player.east(),
                player.east().equals(box) ? box.east() : box,
                target
        );

        final Action west = new Action(
                this,
                player.west().equals(box) ? Push.WEST : Walk.WEST,
                player.west(),
                player.west().equals(box) ? box.west() : box,
                target
        );

        return new Action[]{north, south, east, west};
    }

    public boolean isFinished() {
        return target.equals(box);
    }

    public List<Character> getMoves() {
        final LinkedList<Character> moves = new LinkedList<>();
        for (
                Action completedAction = this;
                completedAction.move != null;
                completedAction = completedAction.before
        ) {
            moves.addFirst(completedAction.move);
        }
        return moves;
    }

    public boolean isValid(char[][] grid) {
        if (!isValidCell(grid, player)) return false;
        if (!isValidCell(grid, box)) return false;
        if (!isValidCell(grid, target)) return false;

        if (isRock(grid, player)) return false;
        if (isRock(grid, box)) return false;
        if (isRock(grid, target)) return false;

        return true;
    }

    private boolean isValidCell(final char[][] grid, final Position position) {
        return within(0, position.row, grid.length) && within(0, position.column, grid[position.row].length);
    }

    private boolean within(final int from, final int value, final int until) {
        return from <= value && value < until;
    }

    private boolean isRock(final char[][] grid, final Position position) {
        return grid[position.row][position.column] == Cell.ROCK;
    }

    public int state() {
        return box.column + box.row * 1_00 + player.column * 1_00_00 + player.row * 1_00_00_00;
    }
}
