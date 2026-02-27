package uva.uhunt.c2.g0.p11360;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

interface Operation {
    void perform(int[][] matrix);
}

/**
 * 11360 - Have Fun with Matrices
 * Time limit: 2.000 seconds
 * https://onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2345
 */
public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            int size = in.nextInt();
            int[][] matrix = new int[size][size];
            for (int j = 0; j < size; j++) {
                char[] letters = in.next().toCharArray();
                for (int k = 0; k < size; k++) matrix[j][k] = letters[k] - '0';
            }

            int totalOperation = in.nextInt();
            Operation[] operations = new Operation[totalOperation];
            for (int j = 0; j < totalOperation; j++) {
                String type = in.next();
                switch (type) {
                    case "row":
                        int row1 = in.nextInt() - 1;
                        int row2 = in.nextInt() - 1;
                        operations[j] = new SwapRow(row1, row2);
                        break;
                    case "col":
                        int column1 = in.nextInt() - 1;
                        int column2 = in.nextInt() - 1;
                        operations[j] = new SwapColumn(column1, column2);
                        break;
                    case "inc":
                        operations[j] = new Increment();
                        break;
                    case "dec":
                        operations[j] = new Decrement();
                        break;
                    case "transpose":
                        operations[j] = new Transpose();
                        break;
                }
            }

            Solution solution = new Solution(matrix, operations);
            int[][] finalMatrix = solution.performOperations();

            out.format("Case #%d\n", i + 1);
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    out.print(finalMatrix[j][k]);
                }
                out.println();
            }
            out.println();
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int[][] matrix;
    private final Operation[] operations;

    public Solution(int[][] matrix, Operation[] operations) {
        this.matrix = matrix;
        this.operations = operations;
    }

    public int[][] performOperations() {
        for (Operation operation : operations) operation.perform(matrix);
        return matrix;
    }
}

class SwapRow implements Operation {
    private final int row1;
    private final int row2;

    public SwapRow(int row1, int row2) {
        this.row1 = row1;
        this.row2 = row2;
    }

    @Override
    public void perform(int[][] matrix) {
        int[] tmp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = tmp;
    }
}

class SwapColumn implements Operation {
    private final int column1;
    private final int column2;

    public SwapColumn(int column1, int column2) {
        this.column1 = column1;
        this.column2 = column2;
    }

    @Override
    public void perform(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            int tmp = matrix[row][column1];
            matrix[row][column1] = matrix[row][column2];
            matrix[row][column2] = tmp;
        }
    }
}

class Increment implements Operation {

    @Override
    public void perform(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int v = matrix[i][j];
                matrix[i][j] = v == 9 ? 0 : v + 1;
            }
        }
    }
}

class Decrement implements Operation {

    @Override
    public void perform(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int v = matrix[i][j];
                matrix[i][j] = v == 0 ? 9 : v - 1;
            }
        }
    }
}

class Transpose implements Operation {

    @Override
    public void perform(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < i; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
    }
}
