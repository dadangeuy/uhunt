package uhunt.c2.p101;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * 101 - The Blocks Problem
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&category=0&problem=37&mosmsg=Submission+received+with+ID+30570959
 */
public class Main {
    private static final int NO_VALUE = -1;

    public static void main(final String... args) throws IOException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        final int totalBlocks = Integer.parseInt(in.readLine());
        final int[][] blocks = new int[totalBlocks][totalBlocks];
        for (int i = 0; i < totalBlocks; i++) {
            Arrays.fill(blocks[i], NO_VALUE);
            blocks[i][0] = i;
        }

        for (String line = in.readLine(); !line.equals("quit"); line = in.readLine()) {
            final String[] instructions = line.split(" ");
            final String command1 = instructions[0];
            final int block1 = Integer.parseInt(instructions[1]);
            final String command2 = instructions[2];
            final int block2 = Integer.parseInt(instructions[3]);

            final int[] position1 = findPosition(blocks, block1);
            final int[] position2 = findPosition(blocks, block2);

            final boolean isIllegal = position1[0] == position2[0];
            if (isIllegal) continue;

            if (command1.equals("move")) {
                if (command2.equals("onto")) {
                    // reset stack above #1
                    resetStack(blocks, nextColumn(position1));

                    // reset stack above #2
                    resetStack(blocks, nextColumn(position2));

                    // put #1 above #2
                    moveStack(blocks, position1, position2);
                } else if (command2.equals("over")) {
                    // reset stack above #1
                    resetStack(blocks, nextColumn(position1));

                    // put #1 above #2 stack
                    moveStack(blocks, position1, position2);
                }
            } else if (command1.equals("pile")) {
                if (command2.equals("onto")) {
                    // reset stack above #2
                    resetStack(blocks, nextColumn(position2));

                    // put #1 stack above #2
                    moveStack(blocks, position1, position2);
                } else if (command2.equals("over")) {
                    // put #1 stack above #2 stack
                    moveStack(blocks, position1, position2);
                }
            }
        }

        for (int row = 0; row < totalBlocks; row++) {
            out.write(String.format("%d:", row));
            for (int col = 0; col < totalBlocks; col++) {
                if (blocks[row][col] == -1) break;
                out.write(String.format(" %d", blocks[row][col]));
            }
            out.write('\n');
        }

        in.close();
        out.flush();
        out.close();
    }

    // move stack of cards from position #1 to #2
    private static void moveStack(
            final int[][] array,
            final int[] position1,
            final int[] position2
    ) {
        final int[] array1 = array[position1[0]];
        final int[] array2 = array[position2[0]];

        for (int col1 = position1[1], col2 = position2[1] + 1; col1 < array1.length && col2 < array2.length;) {
            if (array2[col2] != NO_VALUE) {
                col2++;
            } else {
                array2[col2] = array1[col1];
                array1[col1] = NO_VALUE;
                col1++;
                col2++;
            }
        }
    }

    // move stack of cards to its original position
    private static void resetStack(final int[][] array, final int[] position) {
        final int row = position[0];
        for (int col = position[1]; col <= array[row].length; col++) {
            final int value = array[row][col];
            if (value == NO_VALUE) break;

            array[row][col] = NO_VALUE;
            array[value][0] = value;
        }
    }

    private static int[] findPosition(int[][] array, int target) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (array[i][j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new NullPointerException("input violation, target not found.");
    }

    private static int[] nextColumn(int[] position) {
        return new int[]{position[0], position[1] + 1};
    }
}
