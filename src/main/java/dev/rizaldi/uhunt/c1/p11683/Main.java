package dev.rizaldi.uhunt.c1.p11683;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        while (true) {
            int height = in.hasNextInt() ? in.nextInt() : 0;
            int length = in.hasNextInt() ? in.nextInt() : 0;
            if (height == 0 && length == 0) break;

            int[] targetHeights = new int[length];
            for (int i = 0; i < length; i++) targetHeights[i] = in.nextInt();

            Solution solution = new Solution(height, length, targetHeights);
            int total = solution.getNumberOfTimesLaserTurnedOn();

            out.println(total);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final int height;
    private final int length;
    private final int[] targetHeights;

    public Solution(int height, int length, int[] targetHeights) {
        this.height = height;
        this.length = length;
        this.targetHeights = targetHeights;
    }

    public int getNumberOfTimesLaserTurnedOn() {
        int total = 0;
        int laserHeight = height;

        for (int i = 0; i < length; i++) {
            int targetHeight = targetHeights[i];

            if (targetHeight < laserHeight) total += laserHeight - targetHeight;
            laserHeight = targetHeight;
        }

        return total;
    }
}
