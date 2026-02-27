package uva.uhunt.c1.g3.p10813;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            int[][] bingoCard = new int[5][5];
            for (int j = 0; j < 5; j++) {
                bingoCard[j][0] = in.nextInt();
                bingoCard[j][1] = in.nextInt();
                if (j != 2) bingoCard[j][2] = in.nextInt();
                bingoCard[j][3] = in.nextInt();
                bingoCard[j][4] = in.nextInt();
            }

            int[] bingoNumbers = new int[75];
            for (int j = 0; j < 75; j++)
                bingoNumbers[j] = in.nextInt();

            Solution solution = new Solution(bingoCard, bingoNumbers);
            int total = solution.getTotalStepUntilWin();

            out.format("BINGO after %d numbers announced\n", total);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int[][] bingoCard;
    private final int[] bingoNumbers;

    public Solution(int[][] bingoCard, int[] bingoNumbers) {
        this.bingoCard = bingoCard;
        this.bingoNumbers = bingoNumbers;
    }

    public int getTotalStepUntilWin() {
        for (int i = 0; i < bingoNumbers.length; i++) {
            int number = bingoNumbers[i];
            flag(number);
            if (win()) return i + 1;
        }
        return -1;
    }

    private void flag(int number) {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (bingoCard[i][j] == number) bingoCard[i][j] = 0;
    }

    private boolean win() {
        // check horizontal win
        boolean horizontalWin = false;
        for (int row = 0; row < 5 && !horizontalWin; row++) {
            horizontalWin = equals(0, bingoCard[row][0], bingoCard[row][1], bingoCard[row][2], bingoCard[row][3], bingoCard[row][4]);
        }

        // check vertical win
        boolean verticalWin = false;
        for (int col = 0; col < 5 && !verticalWin; col++) {
            verticalWin = equals(0, bingoCard[0][col], bingoCard[1][col], bingoCard[2][col], bingoCard[3][col], bingoCard[4][col]);
        }

        // check diagonal win
        boolean diagonalWin1 = equals(0, bingoCard[0][0], bingoCard[1][1], bingoCard[2][2], bingoCard[3][3], bingoCard[4][4]);
        boolean diagonalWin2 = equals(0, bingoCard[4][0], bingoCard[3][1], bingoCard[2][2], bingoCard[1][3], bingoCard[0][4]);

        return horizontalWin || verticalWin || diagonalWin1 || diagonalWin2;
    }

    private boolean equals(int target, int... values) {
        for (int value : values) if (value != target) return false;
        return true;
    }
}
