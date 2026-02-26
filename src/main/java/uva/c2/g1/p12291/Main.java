package uva.c2.g1.p12291;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 12291 - Polyomino Composer
 * Time limit: 1.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3712
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int largeSize = in.nextInt();
            int smallSize = in.nextInt();
            if (largeSize == 0 && smallSize == 0) break;

            char[][] large = new char[largeSize][];
            for (int i = 0; i < largeSize; i++) large[i] = in.next().toCharArray();

            char[][] small = new char[smallSize][];
            for (int i = 0; i < smallSize; i++) small[i] = in.next().toCharArray();

            Solution solution = new Solution(large, small);
            boolean composed = solution.isComposed();

            out.println(composed ? 1 : 0);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final char EXIST = '*';
    private static final char EMPTY = '.';
    private final char[][] large;
    private final char[][] small;

    public Solution(char[][] large, char[][] small) {
        this.large = large;
        this.small = small;
    }

    public boolean isComposed() {
        int[][] smallSteps = getSteps(small);
        int[][] largePositions = getPositions(large);
        char[][] originalLarge = clone(large);

        for (int i = 0; i < largePositions.length; i++) {
            for (int j = i + 1; j < largePositions.length; j++) {
                // reset large
                copy(originalLarge, large);

                int[] position1 = largePositions[i];
                boolean success1 = stepFill(large, position1, smallSteps, EMPTY);
                if (!success1) continue;

                int[] position2 = largePositions[j];
                boolean success2 = stepFill(large, position2, smallSteps, EMPTY);
                if (!success2) continue;

                boolean filled = count(large, EXIST) == 0;
                if (filled) return true;
            }
        }

        return false;
    }

    private int[][] getSteps(char[][] polyomino) {
        int[][] positions = getPositions(polyomino);
        int[] first = positions[0];
        return Arrays.stream(positions)
                .map(p -> new int[]{p[0] - first[0], p[1] - first[1]})
                .toArray(int[][]::new);
    }

    private int[][] getPositions(char[][] polyomino) {
        LinkedList<int[]> positions = new LinkedList<>();
        for (int i = 0; i < polyomino.length; i++) {
            for (int j = 0; j < polyomino[i].length; j++) {
                if (polyomino[i][j] == EMPTY) continue;
                int[] position = new int[]{i, j};
                positions.addLast(position);
            }
        }
        return positions.toArray(new int[0][]);
    }

    private boolean stepFill(char[][] polyomino, int[] start, int[][] steps, char filler) {
        for (int[] step : steps) {
            int[] position = new int[]{start[0] + step[0], start[1] + step[1]};
            boolean valid = within(0, position[0], polyomino.length - 1) &&
                    within(0, position[1], polyomino[position[0]].length - 1) &&
                    polyomino[position[0]][position[1]] != filler;
            if (!valid) return false;
            polyomino[position[0]][position[1]] = filler;
        }
        return true;
    }

    private boolean within(int from, int value, int to) {
        return from <= value && value <= to;
    }

    private int count(char[][] polyomino, char target) {
        int count = 0;
        for (int i = 0; i < polyomino.length; i++) {
            for (int j = 0; j < polyomino[i].length; j++) {
                count += polyomino[i][j] == target ? 1 : 0;
            }
        }
        return count;
    }

    private char[][] clone(char[][] source) {
        char[][] destination = new char[source.length][];
        for (int i = 0; i < source.length; i++) {
            destination[i] = new char[source[i].length];
        }
        copy(source, destination);
        return destination;
    }

    private void copy(char[][] source, char[][] destination) {
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[i].length; j++) {
                destination[i][j] = source[i][j];
            }
        }
    }
}
