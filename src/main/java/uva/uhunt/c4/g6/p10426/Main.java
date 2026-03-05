package uva.uhunt.c4.g6.p10426;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * 10426 - Knights' Nightmare
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=1367
 */
public class Main {
    public static void main(final String... args) throws IOException {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        while (in.hasNext()) {
            final Input input = new Input();
            input.setName = in.next();

            final List<Integer> list = new LinkedList<>();
            while (in.hasNextInt()) list.add(in.nextInt());
            final int[] ints = list.stream().mapToInt(Integer::intValue).toArray();
            final Position[] positions = parsePositions(Arrays.copyOfRange(ints, 2, ints.length));

            input.totalRows = ints[0];
            input.totalColumns = ints[1];
            input.knightPositions = Arrays.copyOfRange(positions, 0, positions.length - 1);
            input.monsterPosition = positions[positions.length - 1];

            final Output output = process.process(input);
            out.format("%s\n", output.setName);
            if (output.isPossible) {
                out.format("Minimum time required is %d minutes.\n", output.minimumTime);
            } else {
                out.format("Meeting is impossible.\n");
            }
        }

        in.close();
        out.flush();
        out.close();
    }

    private static Position[] parsePositions(final int[] ints) {
        final Position[] positions = new Position[ints.length / 2];
        for (int i = 0, j = 0; i < ints.length; i += 2, j++) {
            positions[j] = new Position(ints[i], ints[i + 1]);
        }
        return positions;
    }
}

class Input {
    public String setName;
    public int totalRows;
    public int totalColumns;
    public Position[] knightPositions;
    public Position monsterPosition;

    public int totalKnights() {
        return knightPositions.length;
    }
}

class Output {
    public String setName;
    public boolean isPossible;
    public int minimumTime;
}

class Process {
    public Output process(final Input input) {
        final Output output = new Output();
        output.setName = input.setName;
        output.minimumTime = Integer.MAX_VALUE;

        final Progress[][][][] progressMap = new Progress[input.totalKnights()][][][];
        for (int knight = 0; knight < input.totalKnights(); knight++) {
            progressMap[knight] = breadthFirstSearch(
                input.totalRows,
                input.totalColumns,
                input.monsterPosition,
                input.knightPositions[knight]
            );
        }

        for (int row = 1; row <= input.totalRows; row++) {
            for (int column = 1; column <= input.totalColumns; column++) {
                for (int monsterKnightId = -1; monsterKnightId < input.totalKnights(); monsterKnightId++) {
                    final Integer totalTimes = getTotalTimes(progressMap, monsterKnightId, row, column);
                    if (totalTimes != null) {
                        output.isPossible = true;
                        output.minimumTime = Math.min(output.minimumTime, totalTimes);
                    }
                }
            }
        }

        return output;
    }

    private Progress[][][] breadthFirstSearch(
        final int totalRows,
        final int totalColumns,
        final Position monster,
        final Position knight
    ) {
        final Progress[][][] progressMap = new Progress[2][totalRows + 1][totalColumns + 1];
        final Queue<Progress> queue = new LinkedList<>();
        final Progress initialProgress = new Progress(monster, knight);

        setProgress(progressMap, initialProgress);
        queue.add(initialProgress);

        while (!queue.isEmpty()) {
            final Progress currentProgress = queue.remove();
            final Position currentKnight = currentProgress.knight;
            final Position[] nextKnights = currentKnight.next();

            for (final Position nextKnight : nextKnights) {
                if (nextKnight.isValid(totalRows, totalColumns)) {
                    final Progress nextProgress = currentProgress.next(nextKnight);
                    final boolean notVisited = getProgress(progressMap, nextProgress) == null;
                    if (notVisited) {
                        setProgress(progressMap, nextProgress);
                        queue.add(nextProgress);
                    }
                }
            }
        }

        return progressMap;
    }

    private Progress getProgress(final Progress[][][] progressMap, final Progress progress) {
        return progressMap[progress.isMonsterAwaken ? 1 : 0][progress.knight.row][progress.knight.column];
    }

    private void setProgress(final Progress[][][] progressMap, final Progress progress) {
        progressMap[progress.isMonsterAwaken ? 1 : 0][progress.knight.row][progress.knight.column] = progress;
    }

    private Integer getTotalTimes(
        final Progress[][][][] progressMap,
        final int monsterKnightId,
        final int row,
        final int column
    ) {
        int totalTimes = 0;
        for (int knightId = 0; knightId < progressMap.length; knightId++) {
            final Integer knightTotalTimes = getTotalTimes(
                progressMap,
                knightId,
                knightId == monsterKnightId,
                row,
                column
            );
            if (knightTotalTimes == null) return null;
            totalTimes += knightTotalTimes;
        }
        return totalTimes;
    }

    private Integer getTotalTimes(
        final Progress[][][][] progressMap,
        final int knight,
        final boolean isMonsterAwaken,
        final int row,
        final int column
    ) {
        final Progress progress = progressMap[knight][isMonsterAwaken ? 1 : 0][row][column];
        return progress == null ? null : progress.totalTimes;
    }
}

class Position {
    public final int row;
    public final int column;

    public Position(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    public Position[] next() {
        return new Position[]{
            new Position(row - 2, column - 1),
            new Position(row - 2, column + 1),
            new Position(row + 2, column - 1),
            new Position(row + 2, column + 1),
            new Position(row - 1, column - 2),
            new Position(row - 1, column + 2),
            new Position(row + 1, column - 2),
            new Position(row + 1, column + 2),
        };
    }

    public boolean isValid(final int totalRows, final int totalColumns) {
        return 1 <= row && row <= totalRows && 1 <= column && column <= totalColumns;
    }

    public boolean equals(final Position o) {
        return row == o.row && column == o.column;
    }
}

class Progress {
    public final Position monster;
    public final Position knight;
    public final int totalTimes;
    public final boolean isMonsterAwaken;

    public Progress(final Position monster, final Position knight) {
        this(monster, knight, 0, false);
    }

    private Progress(
        final Position monster,
        final Position knight,
        final int totalTimes,
        final boolean isMonsterAwaken
    ) {
        this.monster = monster;
        this.knight = knight;
        this.totalTimes = totalTimes;
        this.isMonsterAwaken = isMonsterAwaken;
    }

    public Progress next(final Position knight) {
        return new Progress(
            monster,
            knight,
            totalTimes + 1,
            isMonsterAwaken || monster.equals(knight)
        );
    }
}
