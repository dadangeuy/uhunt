package uva.c1.g6.p706;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int size = in.nextInt();
            String number = in.next();
            if (size == 0 && number.equals("0")) break;

            Solution solution = new Solution(size, number.toCharArray());
            char[][] display = solution.getDisplay();

            for (char[] v : display) out.println(v);
            out.println();
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    // h1, h2, h3, v1, v2, v3, v4
    private static final boolean[] zero = new boolean[]{true, false, true, true, true, true, true};
    private static final boolean[] one = new boolean[]{false, false, false, false, true, false, true};
    private static final boolean[] two = new boolean[]{true, true, true, false, true, true, false};
    private static final boolean[] three = new boolean[]{true, true, true, false, true, false, true};
    private static final boolean[] four = new boolean[]{false, true, false, true, true, false, true};
    private static final boolean[] five = new boolean[]{true, true, true, true, false, false, true};
    private static final boolean[] six = new boolean[]{true, true, true, true, false, true, true};
    private static final boolean[] seven = new boolean[]{true, false, false, false, true, false, true};
    private static final boolean[] eight = new boolean[]{true, true, true, true, true, true, true};
    private static final boolean[] nine = new boolean[]{true, true, true, true, true, false, true};
    private static final boolean[][] states = new boolean[][]{zero, one, two, three, four, five, six, seven, eight, nine};

    private final int size;
    private final char[] number;

    public Solution(int size, char[] number) {
        this.size = size;
        this.number = number;
    }

    public char[][] getDisplay() {
        int digitRow = 2 * size + 3;
        int digitCol = size + 2;
        int totalDigit = number.length;

        char[][] display = new char[digitRow][digitCol * totalDigit + totalDigit - 1];
        fill(display, ' ');

        for (int i = 0; i < number.length; i++) {
            print(display, i, number[i] - '0');
        }

        return display;
    }

    private void print(char[][] display, int position, int digit) {
        boolean[] state = states[digit];
        print(display, position, state);
    }

    private void print(char[][] display, int position, boolean[] state) {
        int lcol = position * (size + 2) + position;
        int rcol = lcol + size + 1;
        int urow = 0;
        int mrow = urow + size + 1;
        int brow = mrow + size + 1;

        // print horizontal
        boolean h1 = state[0], h2 = state[1], h3 = state[2];
        for (int col = lcol + 1; col <= rcol - 1; col++) {
            if (h1) display[urow][col] = '-';
            if (h2) display[mrow][col] = '-';
            if (h3) display[brow][col] = '-';
        }

        // print vertical
        boolean v1 = state[3], v2 = state[4], v3 = state[5], v4 = state[6];
        for (int row = urow + 1; row <= mrow - 1; row++) {
            if (v1) display[row][lcol] = '|';
            if (v2) display[row][rcol] = '|';
        }
        for (int row = mrow + 1; row <= brow - 1; row++) {
            if (v3) display[row][lcol] = '|';
            if (v4) display[row][rcol] = '|';
        }
    }

    private void fill(char[][] arr2, char flag) {
        for (int i = 0; i < arr2.length; i++)
            for (int j = 0; j < arr2[i].length; j++)
                arr2[i][j] = flag;
    }
}
