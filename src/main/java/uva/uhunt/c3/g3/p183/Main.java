package uva.uhunt.c3.g3.p183;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            String format = in.next();
            if (format.equals("#")) break;

            int row = in.nextInt();
            int col = in.nextInt();

            StringBuilder bitmapBuilder = new StringBuilder();
            if (format.equals("B")) {
                while (bitmapBuilder.length() < row * col) bitmapBuilder.append(in.next());
            } else {
                bitmapBuilder.append(in.next());
            }
            String bitmap = bitmapBuilder.toString();

            Solution solution = new Solution(format, row, col, bitmap);
            String newBitmap = solution.getBitmapInOtherFormat();
            String newFormat = solution.getOtherFormat();

            System.out.format("%s%4d%4d\n", newFormat, row, col);
            for (int i = 0; i < newBitmap.length(); i += 50)
                System.out.println(newBitmap.substring(i, Math.min(newBitmap.length(), i + 50)));
        }
    }
}

class Solution {
    private final String format;
    private final int row;
    private final int col;
    private final String bitmap;

    public Solution(String format, int row, int col, String bitmap) {
        this.format = format;
        this.row = row;
        this.col = col;
        this.bitmap = bitmap;
    }

    public String getBitmapInOtherFormat() {
        return format.equals("B") ? transformBToD() : transformDToB();
    }

    public String getOtherFormat() {
        return format.equals("B") ? "D" : "B";
    }

    private String transformBToD() {
        char[][] bitmap2 = new char[row][col];
        for (int i = 0, r1 = 0; r1 < row; r1++) {
            for (int r2 = 0; r2 < col; r2++, i++) {
                bitmap2[r1][r2] = bitmap.charAt(i);
            }
        }

        StringBuilder transformBitmap = new StringBuilder();
        transofrmBToDRecursive(transformBitmap, bitmap2, 0, 0, row - 1, col - 1);

        return transformBitmap.toString();
    }

    private void transofrmBToDRecursive(StringBuilder result, char[][] bitmap2, int top, int left, int down, int right) {
        boolean valid = top <= down && left <= right;
        if (!valid) return;

        char color = getColor(bitmap2, top, left, down, right);
        result.append(color);
        if (color == 'D') {
            int mrow = (top + down) / 2;
            int mcol = (left + right) / 2;

            transofrmBToDRecursive(result, bitmap2, top, left, mrow, mcol);
            transofrmBToDRecursive(result, bitmap2, top, mcol + 1, mrow, right);
            transofrmBToDRecursive(result, bitmap2, mrow + 1, left, down, mcol);
            transofrmBToDRecursive(result, bitmap2, mrow + 1, mcol + 1, down, right);
        }
    }

    private char getColor(char[][] bitmap2, int top, int left, int down, int right) {
        char color = bitmap2[top][left];
        for (int row = top; row <= down; row++) {
            for (int col = left; col <= right; col++) {
                if (bitmap2[row][col] != color) {
                    return 'D';
                }
            }
        }

        return color;
    }

    private String transformDToB() {
        char[][] bitmap2 = new char[row][col];
        AtomicInteger bitmapID = new AtomicInteger(0);
        transformDToBRecursive(bitmap2, 0, 0, row - 1, col - 1, bitmapID);

        return flatten(bitmap2);
    }

    private void transformDToBRecursive(char[][] bitmap2, int top, int left, int down, int right, AtomicInteger bitmapID) {
        boolean valid = top <= down && left <= right;
        if (!valid) return;

        char bit = bitmap.charAt(bitmapID.getAndIncrement());

        if (bit == '0' || bit == '1') fill(bitmap2, top, left, down, right, bit);
        else {
            int mrow = (top + down) / 2;
            int mcol = (left + right) / 2;

            transformDToBRecursive(bitmap2, top, left, mrow, mcol, bitmapID);
            transformDToBRecursive(bitmap2, top, mcol + 1, mrow, right, bitmapID);
            transformDToBRecursive(bitmap2, mrow + 1, left, down, mcol, bitmapID);
            transformDToBRecursive(bitmap2, mrow + 1, mcol + 1, down, right, bitmapID);
        }
    }

    private void fill(char[][] bitmap2, int top, int left, int down, int right, char value) {
        for (int row = top; row <= down; row++) {
            for (int col = left; col <= right; col++) {
                bitmap2[row][col] = value;
            }
        }
    }

    private String flatten(char[][] bitmap2) {
        StringBuilder bitmap1 = new StringBuilder();
        for (char[] b : bitmap2) bitmap1.append(b);
        return bitmap1.toString();
    }
}
