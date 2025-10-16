package uhunt.c1.p12280;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 12280 - A Digital Satire of Digital Age
 * Time limit: 3.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=3432
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            char[][] grid = new char[7][];
            for (int j = 0; j < 7; j++) grid[j] = in.next().toCharArray();
            in.next();

            Solution solution = new Solution(grid);

            out.format("Case %d:\n", i + 1);
            boolean balance = solution.isBalance();
            if (balance) {
                out.println("The figure is correct.");
            } else {
                Scale[] leftAndRight = solution.calibrateScales();
                Scale left = leftAndRight[0], right = leftAndRight[1];
                for (int j = 0; j < 7; j++) {
                    out.print(left.grid()[j]);
                    out.print("||");
                    out.println(right.grid()[j]);
                }
            }
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Scale {
    public static final int TOP = 4;
    public static final int MIDDLE = 5;
    public static final int BOTTOM = 6;
    private static final char[] EMPTY = "........".toCharArray();
    private final int weight;
    private char[][] grid;
    private int position;

    public Scale(char[][] grid) {
        this.grid = grid;
        this.weight = calculateWeight();
        this.position = calculatePosition();
    }

    public int weight() {
        return weight;
    }

    public char[][] grid() {
        return grid;
    }

    public int position() {
        return position;
    }

    private int calculateWeight() {
        int weight = 0;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                if (Character.isAlphabetic(grid[i][j])) weight += calculateBitWeight(grid[i][j]);
        return weight;
    }

    private int calculateBitWeight(char c) {
        int weight = 0;
        char[] bits = Integer.toBinaryString(c).toCharArray();
        for (char bit : bits) weight += bit == '1' ? 500 : 250;
        return weight;
    }

    private int calculatePosition() {
        for (int i = grid.length - 1; i >= 0; i--)
            if (grid[i][0] == '\\') return i;
        return 0;
    }

    public void reposition(int newPosition) {
        int diff = newPosition - position;
        if (diff == 0) return;

        char[][] newGrid = new char[grid.length][];
        Arrays.fill(newGrid, EMPTY);
        for (int i = position, j = newPosition; i >= 0 && j >= 0; i--, j--) newGrid[j] = grid[i];

        grid = newGrid;
        position = newPosition;
    }
}

class Solution {
    private final char[][] grid;
    private final Scale leftScale;
    private final Scale rightScale;

    public Solution(char[][] grid) {
        this.grid = grid;
        this.leftScale = getLeftScale();
        this.rightScale = getRightScale();
    }

    public boolean isBalance() {
        if (leftScale.weight() < rightScale.weight()) {
            return leftScale.position() < rightScale.position();
        } else if (leftScale.weight() > rightScale.weight()) {
            return leftScale.position() > rightScale.position();
        } else {
            return leftScale.position() == rightScale.position();
        }
    }

    public Scale[] calibrateScales() {
        if (leftScale.weight() < rightScale.weight()) {
            leftScale.reposition(Scale.TOP);
            rightScale.reposition(Scale.BOTTOM);
        } else if (leftScale.weight() > rightScale.weight()) {
            leftScale.reposition(Scale.BOTTOM);
            rightScale.reposition(Scale.TOP);
        } else {
            leftScale.reposition(Scale.MIDDLE);
            rightScale.reposition(Scale.MIDDLE);
        }

        return new Scale[]{leftScale, rightScale};
    }

    private Scale getLeftScale() {
        char[][] grid = new char[7][];
        for (int i = 0; i < 7; i++) grid[i] = Arrays.copyOfRange(this.grid[i], 0, 8);
        return new Scale(grid);
    }

    private Scale getRightScale() {
        char[][] grid = new char[7][];
        for (int i = 0; i < 7; i++) grid[i] = Arrays.copyOfRange(this.grid[i], 10, 18);
        return new Scale(grid);
    }
}
