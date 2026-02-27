package uva.uhunt.c1.g6.p11786;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(new BufferedInputStream(System.in, 1 << 16));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out, 1 << 16));

        int totalCase = in.nextInt();
        for (int i = 0; i < totalCase; i++) {
            char[] terrains = in.next().toCharArray();

            Solution solution = new Solution(terrains);
            int totalWater = solution.getTotalWater();

            out.println(totalWater);
        }

        in.close();
        out.flush();
        out.close();
    }
}

class Solution {
    private final char[] terrains;

    public Solution(char[] terrains) {
        this.terrains = terrains;
    }

    public int getTotalWater() {
        int totalWater = 0;
        LinkedList<Integer> downIds = new LinkedList<>();

        for (int i = 0; i < terrains.length; i++) {
            char terrain = terrains[i];
            if (terrain == '\\') {
                downIds.addLast(i);
            } else if (terrain == '/' && !downIds.isEmpty()) {
                int downId = downIds.removeLast();
                int currentTotalWater = i - downId;
                totalWater += currentTotalWater;
            }
        }

        return totalWater;
    }
}
