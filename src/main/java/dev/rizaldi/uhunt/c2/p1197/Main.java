package dev.rizaldi.uhunt.c2.p1197;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            int totalStudent = in.nextInt();
            int totalGroup = in.nextInt();

            if (totalStudent == 0 && totalGroup == 0) break;

            int[][] groups = new int[totalGroup][];
            for (int i = 0; i < totalGroup; i++) {
                int totalStudentInGroup = in.nextInt();
                int[] group = new int[totalStudentInGroup];
                for (int j = 0; j < totalStudentInGroup; j++) {
                    group[j] = in.nextInt();
                }
                groups[i] = group;
            }

            Solution solution = new Solution(totalStudent, totalGroup, groups);
            int totalSuspect = solution.getTotalSuspect();

            System.out.println(totalSuspect);
        }
    }
}

class Solution {
    private final int totalStudent;
    private final int totalGroup;
    private final int[][] groups;

    public Solution(int totalStudent, int totalGroup, int[][] groups) {
        this.totalStudent = totalStudent;
        this.totalGroup = totalGroup;
        this.groups = groups;
    }

    public int getTotalSuspect() {
        int[] disjointSet = createDisjointSet();

        int leaderOf0 = findLeader(disjointSet, 0);
        int totalSuspect = 0;
        for (int student = 0; student < totalStudent; student++) {
            int leaderOfStudent = findLeader(disjointSet, student);
            if (leaderOfStudent == leaderOf0) totalSuspect++;
        }

        return totalSuspect;
    }

    private int[] createDisjointSet() {
        int[] disjointSet = new int[totalStudent];
        Arrays.fill(disjointSet, -1);

        for (int[] group : groups) {
            int leader = findLeader(disjointSet, group[0]);
            for (int follower : group) {
                int otherLeader = findLeader(disjointSet, follower);
                if (leader == otherLeader) continue;
                disjointSet[otherLeader] = leader;
            }
        }

        return disjointSet;
    }

    private int findLeader(int[] disjointSet, int follower) {
        int leader = follower;
        while (disjointSet[leader] != -1) leader = disjointSet[leader];
        return leader;
    }
}
