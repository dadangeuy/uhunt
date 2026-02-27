package uva.uhunt.c2.g8.p10608;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int totalTest = in.nextInt();
        for (int test = 0; test < totalTest; test++) {
            int totalCitizen = in.nextInt();
            int totalRelation = in.nextInt();

            int[][] relations = new int[totalRelation][];
            for (int relationID = 0; relationID < totalRelation; relationID++) {
                int citizen1 = in.nextInt() - 1; // 0-based
                int citizen2 = in.nextInt() - 1; // 0-based
                int[] relation = new int[]{citizen1, citizen2};
                relations[relationID] = relation;
            }

            Solution solution = new Solution(totalCitizen, relations);
            int total = solution.getTotalInLargestGroup();

            System.out.println(total);
        }
    }
}

class Solution {
    private final int totalCitizen;
    private final int[][] relations;

    public Solution(int totalCitizen, int[][] relations) {
        this.totalCitizen = totalCitizen;
        this.relations = relations;
    }

    public int getTotalInLargestGroup() {
        int[] disjointSet = new int[totalCitizen];
        Arrays.fill(disjointSet, -1);

        for (int[] relation : relations) {
            int citizen1 = relation[0];
            int citizen2 = relation[1];

            int root1 = findRoot(disjointSet, citizen1);
            int root2 = findRoot(disjointSet, citizen2);
            if (root1 == root2) continue;

            disjointSet[root2] = root1;
        }

        int[] totalCitizenInGroup = new int[totalCitizen];
        for (int citizen = 0; citizen < totalCitizen; citizen++) {
            int root = findRoot(disjointSet, citizen);
            totalCitizenInGroup[root]++;
        }

        int maxTotalCitizen = 0;
        for (int totalCitizen : totalCitizenInGroup) {
            maxTotalCitizen = Math.max(maxTotalCitizen, totalCitizen);
        }

        return maxTotalCitizen;
    }

    private int findRoot(int[] disjointSet, int citizen) {
        while (disjointSet[citizen] != -1) citizen = disjointSet[citizen];
        return citizen;
    }
}
