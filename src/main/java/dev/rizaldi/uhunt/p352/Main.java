package dev.rizaldi.uhunt.p352;

import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int imageID = 1;
        while (in.hasNextInt()) {
            int totalLine = in.nextInt();
            char[][] image = new char[totalLine][];
            for (int i = 0; i < totalLine; i++) image[i] = in.next().toCharArray();

            Solution solution = new Solution(image);
            int totalEagle = solution.getTotalEagle();
            System.out.format("Image number %d contains %d war eagles.\n", imageID++, totalEagle);
        }
    }
}

class Solution {
    private final char[][] image;

    public Solution(char[][] image) {
        this.image = image;
    }

    public int getTotalEagle() {
        int total = 0;
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[row].length; col++) {
                boolean isEagle = image[row][col] == '1';
                if (isEagle) {
                    total++;
                    floodFill(row, col);
                }
            }
        }

        return total;
    }

    private void floodFill(int row, int col) {
        image[row][col] = '0';
        for (int nrow = row - 1; nrow <= row + 1; nrow++) {
            for (int ncol = col - 1; ncol <= col + 1; ncol++) {
                if (valid(nrow, ncol) && image[nrow][ncol] == '1') {
                    floodFill(nrow, ncol);
                }
            }
        }
    }

    private boolean valid(int row, int col) {
        return 0 <= row && row < image.length && 0 <= col && col < image[row].length;
    }
}
