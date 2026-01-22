package uhunt.c1.g6.p12696;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        int totalAllowed = 0;

        for (int i = 0; i < totalCase; i++) {
            double length = in.nextDouble();
            double width = in.nextDouble();
            double depth = in.nextDouble();
            double weight = in.nextDouble();

            Solution solution = new Solution(length, width, depth, weight);
            boolean allowed = solution.isAllowed();

            totalAllowed += allowed ? 1 : 0;
            out.println(allowed ? '1' : '0');
        }

        out.println(totalAllowed);

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private static final double MAX_LENGTH = 56;
    private static final double MAX_WIDTH = 45;
    private static final double MAX_DEPTH = 25;
    private static final double MAX_SUM = 125;
    private static final double MAX_WEIGHT = 7;

    private final double length;
    private final double width;
    private final double depth;
    private final double weight;

    public Solution(double length, double width, double depth, double weight) {
        this.length = length;
        this.width = width;
        this.depth = depth;
        this.weight = weight;
    }

    public boolean isAllowed() {
        boolean condition1 = length <= MAX_LENGTH && width <= MAX_WIDTH && depth <= MAX_DEPTH;
        boolean condition2 = (length + width + depth) <= MAX_SUM;
        boolean condition3 = weight <= MAX_WEIGHT;

        return (condition1 || condition2) && condition3;
    }
}
