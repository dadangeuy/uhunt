package uva.c3.g5.p11795;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String... args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        int totalCase = Integer.parseInt(in.readLine());
        for (int i = 0; i < totalCase; i++) {
            int totalRobot = Integer.parseInt(in.readLine());
            char[] myWeapons = in.readLine().toCharArray();
            char[][] robotWeapons = new char[totalRobot][];
            for (int j = 0; j < totalRobot; j++) robotWeapons[j] = in.readLine().toCharArray();

            Solution solution = new Solution(totalRobot, myWeapons, robotWeapons);
            long total = solution.getTotalWayToFinishObjective();

            out.append("Case ").append(i + 1).append(": ").append(total).append('\n');
        }

        System.out.print(out);
    }
}

/**
 * 1) total way = permutation of destroy robot sequence, e.g. ([R1, R2], [R2, R1]) = 2 way
 * 2) explore every permutation recursively (simillar to TSP)
 * 3) use bitmask to store destroyable & destroyed state efficiently
 * 4) use dp to cache the result, using destroyed state as dp state
 * <p>
 * time complexity: O(2^totalRobot) = O(2^16) = O(65536)
 */
class Solution {
    private static final char DESTROYABLE = '1';
    private static final int UNSET = -1;
    private final int totalRobot;
    private final int mMyWeapon; // bitmask of myWeapon
    private final int[] mRobotWeapons; // bitmask of robotWeapons
    private final long[] dpTotalWay; // dp with state = destroyed robot, value = total way

    public Solution(int totalRobot, char[] mMyWeapon, char[][] mRobotWeapons) {
        this.totalRobot = totalRobot;

        this.mMyWeapon = bitmask(mMyWeapon);

        this.mRobotWeapons = new int[totalRobot];
        for (int i = 0; i < totalRobot; i++) this.mRobotWeapons[i] = bitmask(mRobotWeapons[i]);

        this.dpTotalWay = new long[1 << totalRobot];
        Arrays.fill(dpTotalWay, UNSET);
    }

    private int bitmask(char[] array) {
        int bit = 0;
        for (int i = 0, state = 1; i < array.length; i++, state <<= 1) {
            if (array[i] == DESTROYABLE) {
                bit |= state;
            }
        }
        return bit;
    }

    public long getTotalWayToFinishObjective() {
        return getTotalWayRecursive(mMyWeapon, 0);
    }

    private long getTotalWayRecursive(int mDestroyable, int mDestroyed) {
        // use cached value, if already calculated before
        if (dpTotalWay[mDestroyed] != UNSET) return dpTotalWay[mDestroyed];

        // if robot is completely destroyed, return 1 way to finish objective
        boolean completelyDestroyed = mDestroyed == (1 << totalRobot) - 1;
        if (completelyDestroyed) return dpTotalWay[mDestroyed] = 1;

        // try to kill every robot that's alive, retrieve the sum of way
        long totalWay = 0;
        for (int robot = 0, state = 1; robot < totalRobot; robot++, state <<= 1) {
            boolean destroyed = (mDestroyed & state) > 0;
            if (destroyed) continue;

            boolean destroyable = (mDestroyable & state) > 0;
            if (!destroyable) continue;

            int nextMDestroyable = mDestroyable | mRobotWeapons[robot];
            int nextMDestroyed = mDestroyed | state;
            totalWay += getTotalWayRecursive(nextMDestroyable, nextMDestroyed);
        }

        // cache the sum
        return dpTotalWay[mDestroyed] = totalWay;
    }
}
