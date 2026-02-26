package uva.c3.g2.p1262;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

/**
 * 1262 - Password
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3703
 */
public class Main {
    public static void main(final String... args) {
        final Scanner in = new Scanner(new BufferedInputStream(System.in));
        final PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        final Process process = new Process();

        final int totalTestCases = in.nextInt();
        for (int i = 0; i < totalTestCases; i++) {
            final Input input = new Input();
            input.sequence = in.nextInt();
            input.grid1 = new char[6][];
            for (int j = 0; j < 6; j++) {
                input.grid1[j] = in.next().toCharArray();
            }
            input.grid2 = new char[6][];
            for (int j = 0; j < 6; j++) {
                input.grid2[j] = in.next().toCharArray();
            }

            final Output output = process.process(input);
            if (output.isExists) out.println(output.password);
            else out.println("NO");
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Input {
    public int sequence;
    public char[][] grid1;
    public char[][] grid2;
}

class Output {
    public boolean isExists;
    public String password;
}

class Process {
    private static final int TOTAL_ROWS = 6;
    private static final int TOTAL_COLUMNS = 5;

    public Output process(final Input input) {
        final Output output = new Output();

        final char[][] lettersPerColumn = getLettersPerColumn(input.grid1, input.grid2);
        final String[] passwords = findPasswords(lettersPerColumn);
        Arrays.sort(passwords);

        final boolean isValid = (input.sequence - 1) < passwords.length;
        if (isValid) {
            output.isExists = true;
            output.password = passwords[input.sequence - 1];
        } else {
            output.isExists = false;
            output.password = null;
        }

        return output;
    }

    private char[][] getLettersPerColumn(final char[][] grid1, final char[][] grid2) {
        final char[][] lettersPerColumn = new char[5][];

        for (int column = 0; column < 5; column++) {
            final char[] cells1 = getCells(grid1, column);
            final char[] cells2 = getCells(grid2, column);

            final Set<Character> setCells1 = toSet(cells1);
            final Set<Character> setCells2 = toSet(cells2);

            final Set<Character> identicalCells = findIdenticalValue(setCells1, setCells2);
            final char[] arrayIdenticalCells = toArray(identicalCells);
            lettersPerColumn[column] = arrayIdenticalCells;
        }

        return lettersPerColumn;
    }

    private char[] getCells(final char[][] grid, final int column) {
        final char[] cells = new char[TOTAL_ROWS];
        for (int row = 0; row < TOTAL_ROWS; row++) {
            cells[row] = grid[row][column];
        }
        return cells;
    }

    private Set<Character> toSet(final char[] array) {
        final Set<Character> set = new HashSet<>();
        for (final char value : array) set.add(value);
        return set;
    }

    private Set<Character> findIdenticalValue(final Set<Character> set1, final Set<Character> set2) {
        final Set<Character> identical = new HashSet<>();
        for (final char value : set1) {
            if (set2.contains(value)) {
                identical.add(value);
            }
        }
        return identical;
    }

    private char[] toArray(final Set<Character> set) {
        final char[] array = new char[set.size()];
        final Iterator<Character> it = set.iterator();
        for (int i = 0; i < set.size(); i++) array[i] = it.next();
        return array;
    }

    private String[] findPasswords(final char[][] lettersPerColumn) {
        final LinkedList<String> passwords = new LinkedList<>();

        for (final char letter0 : lettersPerColumn[0]) {
            for (final char letter1 : lettersPerColumn[1]) {
                for (final char letter2 : lettersPerColumn[2]) {
                    for (final char letter3 : lettersPerColumn[3]) {
                        for (final char letter4 : lettersPerColumn[4]) {
                            final char[] password = new char[]{letter0, letter1, letter2, letter3, letter4};
                            final String stringPassword = new String(password);
                            passwords.addLast(stringPassword);
                        }
                    }
                }
            }
        }

        return passwords.toArray(new String[0]);
    }
}
